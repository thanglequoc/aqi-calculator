# Release Setup — Maven Central with JReleaser

This document describes the release setup for `aqi-calculator`.

It is based on the working `timer-ninja` setup, but adapted to **Maven** and tightened in a few areas:

- no GPG keys or Sonatype credentials in the repository;
- a passphrase-capable GPG secret;
- one JReleaser configuration file (`jreleaser.yml`) instead of duplicating it in the build script;
- `active: RELEASE` for signing and deployment, so snapshots can never publish by accident;
- sources and Javadoc are attached only in the `publication` profile, as required by Maven Central.

## Pipeline overview

```text
Pull request
  └─ .github/workflows/pull-request.yml
       └─ ./mvnw -B clean verify          (Java 17 / 21 / 25)

Release (manual workflow)
  └─ .github/workflows/release.yml
       1. validate that master's POM version equals the requested version
       2. ./mvnw -B clean verify
       3. ./mvnw -B -Ppublication -DskipTests
            └─ stages artifacts under target/staging-deploy
       4. ./mvnw -B jreleaser:config
       5. ./mvnw -B jreleaser:full-release
            ├─ signs the staged artifacts with PGP
            ├─ deploys to the Sonatype Central Publisher Portal
            └─ creates the GitHub release for tag v<version>
```

The POM version is committed before the release, so the source tag, the POM in the tag,
and the POM published to Maven Central all match.

The Maven equivalents are the same steps described in the JReleaser docs:

- [JReleaser Maven Plugin](https://jreleaser.org/guide/latest/tools/jreleaser-maven.html)
- [Staging Artifacts](https://jreleaser.org/guide/latest/examples/maven/staging-artifacts.html)
- [Publishing to Maven Central](https://jreleaser.org/guide/latest/examples/maven/maven-central.html)

## One-time setup

### 1. Verify the Central Portal namespace

1. Sign in to <https://central.sonatype.com/>.
2. Open **Namespaces**.
3. Confirm that `io.github.thanglequoc` is available and verified.

The current POM uses:

```xml
<groupId>io.github.thanglequoc</groupId>
<artifactId>aqi-calculator</artifactId>
```

If you keep the old `com.github.thanglequoc` coordinate instead, update the POM and `jreleaser.yml` together.

### 2. Generate a Sonatype user token

1. Open <https://central.sonatype.com/usertoken>.
2. Generate a user token.
3. Store the **token username** and **token password**. These are not your normal Sonatype login credentials.

The release workflow maps them to:

- `SONATYPE_USERNAME`
- `SONATYPE_PASSWORD`

and then to JReleaser's:

- `JRELEASER_MAVENCENTRAL_SONATYPE_USERNAME`
- `JRELEASER_MAVENCENTRAL_SONATYPE_PASSWORD`

### 3. Create a dedicated GPG key

Use a dedicated release key, preferably with a passphrase.

```bash
gpg --full-generate-key
gpg --list-secret-keys --keyid-format=long
```

Export the keys:

```bash
gpg --armor --export <KEY_ID> > public_key.asc
gpg --armor --export-secret-keys <KEY_ID> > secret_key.asc
```

Publish the public key:

```bash
gpg --keyserver keyserver.ubuntu.com --send-keys <KEY_ID>
gpg --keyserver keys.openpgp.org --send-keys <KEY_ID>
```

Verify it is available:

```bash
gpg --keyserver keyserver.ubuntu.com --recv-keys <KEY_ID>
```

**Do not commit `public_key.asc` or `secret_key.asc`.** The repository's `.gitignore` ignores `*.asc`, `*.gpg`, and `*.pem`.

### 4. Configure GitHub Actions

Go to **Settings → Secrets and variables → Actions**.

**Variables:**

| Name | Value |
|---|---|
| `GPG_PUBLIC_KEY` | Full ASCII-armored public key, including the BEGIN/END lines |

**Secrets:**

| Name | Value |
|---|---|
| `GPG_SECRET_KEY` | Full ASCII-armored secret key, including the BEGIN/END lines |
| `GPG_PASSPHRASE` | The key passphrase (leave unset only if the key has no passphrase) |
| `SONATYPE_USERNAME` | Sonatype user token username |
| `SONATYPE_PASSWORD` | Sonatype user token password |

`GITHUB_TOKEN` is provided automatically by Actions. The release workflow requests `contents: write` so JReleaser can create the GitHub release.

### 5. Workflow permissions

Confirm **Settings → Actions → General** allows GitHub Actions to create releases. The release workflow uses the repository `GITHUB_TOKEN`; no personal access token is required for the GitHub release.

## Release process

1. Bump the POM `<version>` to the release version, for example `1.3.3`.
2. Update `CHANGELOG.md` and the README installation snippets.
3. Merge the release-ready changes into `master`.
4. Open **Actions → Release - Publish to Maven Central**.
5. Click **Run workflow**.
6. Enter the same release version, for example `1.3.3`.
7. Run the workflow from `master`.

The workflow:

- refuses to run from any branch other than `master`;
- refuses a `SNAPSHOT` version;
- checks that the requested version matches the committed POM version;
- runs the full test suite on Java 17;
- stages the main JAR, sources JAR, Javadoc JAR, and POM under `target/staging-deploy`;
- validates the JReleaser configuration;
- signs and deploys the artifacts;
- creates the GitHub release and tag `v<version>`.

After the release, bump `master` to the next snapshot version (for example `2.1.0-SNAPSHOT`)
in a follow-up commit.

## Local validation

Build and test:

```bash
./mvnw -B clean verify
```

Stage artifacts exactly as the release workflow does:

```bash
./mvnw -B -Ppublication -DskipTests
find target/staging-deploy -type f | sort
```

The staged directory should contain:

```text
target/staging-deploy/io/github/thanglequoc/aqi-calculator/<version>/
├── aqi-calculator-<version>.jar
├── aqi-calculator-<version>-sources.jar
├── aqi-calculator-<version>-javadoc.jar
└── aqi-calculator-<version>.pom
```

Validate the JReleaser configuration without publishing:

```bash
export JRELEASER_GPG_PUBLIC_KEY="$(cat public_key.asc)"
export JRELEASER_GPG_SECRET_KEY="$(cat secret_key.asc)"
export JRELEASER_GPG_PASSPHRASE="<passphrase>"
export JRELEASER_MAVENCENTRAL_SONATYPE_USERNAME="<token-username>"
export JRELEASER_MAVENCENTRAL_SONATYPE_PASSWORD="<token-password>"

./mvnw -B jreleaser:config
```

Only run `./mvnw -B jreleaser:full-release` locally if you actually intend to publish.

## Configuration notes

`jreleaser.yml` is the single source of truth. The POM only declares the plugin and points it at the file:

```xml
<plugin>
    <groupId>org.jreleaser</groupId>
    <artifactId>jreleaser-maven-plugin</artifactId>
    <version>${jreleaser.version}</version>
    <inherited>false</inherited>
    <configuration>
        <configFile>${project.basedir}/jreleaser.yml</configFile>
    </configuration>
</plugin>
```

Important settings:

```yaml
signing:
  active: RELEASE
  pgp:
    active: RELEASE
    armored: true
    mode: MEMORY

deploy:
  maven:
    mavenCentral:
      sonatype:
        active: RELEASE
        url: https://central.sonatype.com/api/v1/publisher
        authorization: BEARER
        sign: true
        stagingRepositories:
          - target/staging-deploy
```

Both `signing.active` and `signing.pgp.active` must be enabled. Setting only `signing.active` is not enough to enable the PGP signer.

The Maven Central deployer automatically applies the Maven Central rules (POM checks, checksums, signatures, and the presence of sources/Javadoc JARs).

## Why Maven instead of Gradle

`timer-ninja` uses Gradle, but this project is a single-module library and Maven is its existing build. Keeping Maven:

- keeps the 1.3.3 release-engineering change reviewable;
- avoids rewriting the build while the AQI calculation will change in 2.0.0;
- still gets the same JReleaser release flow.

If the project later needs a multi-module build or custom build logic, a Gradle migration can be a separate change.

## Troubleshooting

### `Deployer mavenCentral:sonatype is not enabled. Skipping`

- The project version is probably a `SNAPSHOT`, or `active` is not `RELEASE`.
- Check the POM `<version>` and the `active` values in `jreleaser.yml`.

### `signing.pgp.secretKey must not be blank`

- `GPG_SECRET_KEY` is not set in the workflow environment.
- Confirm the secret name and that the value includes the full BEGIN/END block.

### `401 Unauthorized` from Sonatype

- Use the Central Portal **user token**, not your normal Sonatype password.
- Confirm both `SONATYPE_USERNAME` and `SONATYPE_PASSWORD` are set.
- Regenerate the token if it has expired.

### `Key not found on key server`

- Upload the public key to a key server and wait for propagation.
- Verify with `gpg --keyserver keyserver.ubuntu.com --recv-keys <KEY_ID>`.

### Missing sources or Javadoc JAR

- Stage with `-Ppublication`; the profile attaches `-sources.jar` and `-javadoc.jar`.
- Confirm `target/staging-deploy` contains both before running JReleaser.

### Release already exists

- JReleaser is configured with `release.github.overwrite: false`.
- Use a new version, or intentionally change `overwrite` if you are repairing a release.

## Security notes

- Never commit private keys or Sonatype tokens.
- Use a dedicated release key, not a personal signing key.
- Prefer a passphrase-protected key; store the passphrase in `GPG_PASSPHRASE`.
- Rotate the Sonatype token and GPG key if either is exposed.
- Consider adding a GitHub **environment** named `release` with required reviewers, and add `environment: release` to the release job for an approval gate.
- The release workflow only runs on `workflow_dispatch`; there is no tag-push trigger, so a normal push cannot publish.

## Versioning

- The POM version is the source of truth.
- Before a release, commit the release version (for example `1.3.3`).
- After a release, commit the next snapshot version (for example `2.0.0-SNAPSHOT`).
- `1.3.3` is the release-engineering republish under the new `io.github.thanglequoc` coordinates.
- `2.0.0` is reserved for the AQI data/correctness changes.

## Release checklist

- [ ] The POM `<version>` is the release version and is committed to `master`.
- [ ] `./mvnw -B clean verify` passes locally.
- [ ] All PR checks pass on Java 17, 21, and 25.
- [ ] `CHANGELOG.md` has an entry for the release.
- [ ] `README.md` installation snippets use the new coordinates/version.
- [ ] `project.build.outputTimestamp` is set to the release date.
- [ ] The release version is not a `SNAPSHOT`.
- [ ] Sonatype namespace and token are valid.
- [ ] GPG public key is on a key server.
- [ ] GitHub variables/secrets are configured.
- [ ] Run the release workflow.
- [ ] Verify the artifact on <https://central.sonatype.com/> after propagation.
- [ ] Verify the GitHub release and tag.
