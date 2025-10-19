
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