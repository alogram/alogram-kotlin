# Changelog

All notable changes to the Alogram PayRisk Kotlin SDK will be documented in this file.

## [0.1.6-rc.3] - 2026-02-10

### Added
- Coroutines-first client architecture with `suspend` functions.
- Integrated Dokka for professional KDoc generation.
- Resilient retry logic (429 & 5xx) with jittered backoff.
- Native OpenTelemetry support.

### Changed
- Synchronized with Payments Risk API v0.1.6-rc.3.

## [0.1.6-rc.1] - 2026-02-10

### Added
- Retrofit2 based client with Moshi serialization.
- Automated tracing support via OpenTelemetry.

### Changed
- Synchronized with Payments Risk API v0.1.6.