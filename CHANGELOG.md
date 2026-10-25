# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.3.3] - 2026-10-24

Release-engineering republish under the new Maven coordinates. AQI calculation behavior is
unchanged from 1.3.2. Requires Java 17 or newer.

### Changed

- Republished as `io.github.thanglequoc:aqi-calculator`.
- Migrated the release process to JReleaser and the Sonatype Central Publisher Portal.
- Updated the build to Java 17 and JUnit 6.
- Pinned Jackson and Maven plugin versions.
- Added GitHub Actions build, test, dependency-review, and release workflows.
- Updated GitHub Actions to `checkout@v7`, `setup-java@v6`, `upload-artifact@v7`, and `dependency-review-action@v5`.
- Pinned `maven-help-plugin` 3.5.2 for the release workflow's version check.
- Moved Maven Central artifact staging to the `publication` profile.
- Added `Automatic-Module-Name` metadata.

### Removed

- Removed the obsolete Travis CI configuration.
- Removed the stale checked-in `target-jar` artifact.
- Removed the legacy OSSRH/Nexus staging configuration.
- Removed Eclipse-specific `.settings` metadata; the project builds with Maven and CI only.
