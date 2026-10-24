# GitHub Actions Workflows

| Workflow | Trigger | Purpose |
|---|---|---|
| `pull-request.yml` | Pull requests / pushes to `master` | Build and test on Java 17, 21, and 25; upload test and coverage reports |
| `release.yml` | Manual `workflow_dispatch` | Set the release version, build/test, stage artifacts, and run JReleaser to publish to Maven Central and create the GitHub release |
| `dependency-review.yml` | Pull requests to `master` | Fail on moderate/high vulnerabilities and disallowed licenses |

## Releasing

See [`development/RELEASE_SETUP.md`](../../development/RELEASE_SETUP.md) for the one-time setup and the release checklist.

Quick summary:

1. Bump the POM version to the release version and commit it to `master`.
2. Open **Actions** → **Release - Publish to Maven Central**.
3. Click **Run workflow**.
4. Enter the same release version, such as `2.0.0`.
5. Run it from `master`.

The workflow validates the committed POM version, builds and tests, stages artifacts under
`target/staging-deploy`, validates the JReleaser configuration, and runs
`mvn jreleaser:full-release`.

## Required repository configuration

**Variables** (Settings → Secrets and variables → Actions → Variables):

- `GPG_PUBLIC_KEY` — ASCII-armored GPG public key

**Secrets**:

- `GPG_SECRET_KEY` — ASCII-armored GPG secret key
- `GPG_PASSPHRASE` — GPG key passphrase (may be empty only if the key has no passphrase)
- `SONATYPE_USERNAME` — Sonatype Central Portal user token username
- `SONATYPE_PASSWORD` — Sonatype Central Portal user token password

Never commit GPG secret keys or Sonatype credentials to the repository.
