# GNU / Open-Source Cross-Platform Services

Chimera II now exposes a declarative GNU/open-source interoperability catalog through `org.chimera.compat.GnuPlatformCatalog`.

## Utility coverage

The catalog includes:

- Core utilities: Coreutils, Findutils, Grep, Sed, Gawk, Diffutils, Patch.
- Archives/compression: Tar, Gzip, Bzip2, XZ, Cpio.
- Build/development: Make, Binutils, GCC, GDB, M4, Autoconf, Automake, Libtool.
- Shell/editor/terminal: Bash, Nano, Screen, Emacs.
- Network/transfer: Wget and GNU Inetutils.
- Cryptographic transport: GnuTLS client/server roles.

These entries describe package/service targets. Chimera does not redistribute the external binaries.

## Network service coverage

The catalog represents GNU Inetutils roles where supported by the host distribution:

- `syslogd` — system logging.
- `ftpd` — FTP service.
- `telnetd` — Telnet service.
- `rshd` / `rexecd` — legacy remote-shell/remote-execution services.
- `talkd` — interactive talk service.
- GnuTLS server/client endpoints for TLS interoperability and testing.

Legacy plaintext remote services should be disabled unless required in a controlled compatibility environment. Prefer SSH, TLS and modern authenticated protocols for production systems.

## Windows interoperability

GNU software is not uniformly native to Windows. The catalog therefore distinguishes delivery providers:

1. **MSYS2** — GNU/POSIX userland integrated with Windows.
2. **Cygwin** — POSIX compatibility environment for Windows.
3. **WSL2** — Linux environment hosted by Windows.
4. **Native port** — only where a maintained Win32 build exists.

Windows-native service supervision remains Windows-native; Linux daemons remain Linux daemons when executed inside WSL2.

## Linux integration

Linux package managers remain the source of installation and update truth. The Java layer can expose declarative package/service plans and capability discovery without embedding distribution packages.

## Security boundary

Network services are cataloged for interoperability and administration. Enabling a daemon must be an explicit administrative action with host firewall, authentication, authorization, logging and least-privilege controls applied by the deployment environment.

## Licensing

The original Chimera II Java-track source is GPL-3.0-or-later. External GNU projects, libraries, operating systems and service implementations retain their own licenses. See `docs/LICENSING.md`.
