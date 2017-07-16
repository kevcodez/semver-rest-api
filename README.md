# semver-rest-api
Spring Boot Service written in Kotlin that offers a REST API for semantic versioning, built on [Java SemVer](https://github.com/zafarkhaja/jsemver).

[![Build Status](https://travis-ci.org/kevcodez/semver-rest-api.svg?branch=master)](https://travis-ci.org/kevcodez/semver-rest-api)

[![Coverage Status](https://coveralls.io/repos/github/kevcodez/semver-rest-api/badge.svg)](https://coveralls.io/github/kevcodez/semver-rest-api)

## Version information

GET /info/2.1.0-beta

```json
{
  "major": 2,
  "minor": 1,
  "patch": 0,
  "preRelease": "beta",
  "normal": "2.1.0"
}
```

## Version range validation

POST /validate

```json
{
  "versionRange": "^1.0.0",
  "versionsToValidate": [
    "1.0.0",
    "1.1.0",
    "2.0.0",
    "3.0.0"
  ]
}
```

```json
{
  "versionRange": "^1.0.0",
  "validatedVersions": {
    "2.0.0": false,
    "3.0.0": false,
    "1.0.0": true,
    "1.1.0": true
  }
}
```

## Increment versions

The API offers endpoints to increment major, minor, patch, build metadata and pre release version.

GET /increment/major/1.0.0

```json
{
  "major": 2,
  "minor": 0,
  "patch": 0,
  "normal": "2.0.0"
}
```

## API documentation

The API is documented using Swagger. You can access the Swagger-UI on /swagger-ui.html.
