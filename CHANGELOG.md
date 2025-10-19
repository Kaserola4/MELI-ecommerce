*v1.1.0 - Environment Support*

_Added_
- .env file skeleton example
- Yaml files for environments: `application-prod.yml`, `application-dev.yml` and `application-test.yml`

_Implemented_
- Environment-specific profiles
- System Variables support for environments

_Updated_
- README.MD

_Fixed_
- Startup bath script was not working properly.

*v1.0.0 - Initial release*

_Added_
- README.MD
- CHANGELOG.MD
- Startup script (Linux/macOS and Windows)
- Added DTOs for request/response to avoid exposing JPA entities.
- Added H2-based development profile with seeded sample data.
- Added startup scripts for Linux/macOS and Windows (CMD + PowerShell) to simplify running in different environments.

_Implemented_
- Implemented core domain: `Client`, `Item`, `Order` entities.
- Implemented full CRUD for Items, Clients, and Orders (global and client-scoped endpoints).
- Implemented validation and global exception handling (body and path-parameter validation).

> Future changelog entries should include the version, date, a short title, and a bullet list of changes (features,
> fixes, migrations, breaking changes).