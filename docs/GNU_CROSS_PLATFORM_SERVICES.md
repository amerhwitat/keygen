# GNU / Open-Source Cross-Platform Services

Chimera II exposes a declarative GNU/open-source interoperability catalog through `org.chimera.compat.GnuPlatformCatalog`, complemented by `org.chimera.compat.OpenSourceApplicationCatalog` for mature desktop, development, networking, storage, virtualization and multimedia applications.

## Utility coverage

The catalog includes:

- Core utilities: Coreutils, Findutils, Grep, Sed, Gawk, Diffutils, Patch.
- Archives/compression: Tar, Gzip, Bzip2, XZ, Cpio.
- Build/development: Make, Binutils, GCC, GDB, M4, Autoconf, Automake, Libtool.
- Shell/editor/terminal: Bash, Nano, Screen, Emacs.
- Network/transfer: Wget and GNU Inetutils.
- Cryptographic transport: GnuTLS client/server roles.

These entries describe package/service targets. Chimera does not redistribute the external binaries.

## Extended open-source application layer

The application catalog adds a practical workstation/server ecosystem around the GNU base:

- Office: LibreOffice.
- Creative: GIMP, Inkscape, Krita, Blender.
- Multimedia: VLC, Audacity, OBS Studio, Kdenlive.
- Communication/documents: Thunderbird, Okular, Calibre.
- Development: Git, Kate and existing GCC/GDB/build tooling.
- Networking/security: OpenSSH, Wireshark, curl, Wget, FileZilla, PuTTY, KeePassXC, WireGuard, OpenVPN.
- Virtualization/containers: QEMU, VirtualBox, libvirt, virt-manager, Docker Engine, Podman.
- Storage/backup: rclone, Syncthing, restic.
- Desktop: KDE Dolphin, Kate, Konsole and GNOME Files/System Monitor.
- Archive: 7-Zip alongside GNU archive/compression tools.

See `docs/OPEN_SOURCE_APPLICATIONS_RESEARCH.md` for the research evidence, priority tiers and licensing boundaries.

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

The broader open-source application catalog similarly records whether an application has Linux and Windows delivery paths. Windows-native service supervision remains Windows-native; Linux daemons remain Linux daemons when executed inside WSL2.

## Linux integration

Linux package managers remain the source of installation and update truth. The Java layer can expose declarative package/service plans and capability discovery without embedding distribution packages.

## Security boundary

Network services are cataloged for interoperability and administration. Enabling a daemon must be an explicit administrative action with host firewall, authentication, authorization, logging and least-privilege controls applied by the deployment environment.

## Licensing

The original Chimera II Java-track source is GPL-3.0-or-later. External GNU projects, libraries, operating systems and service implementations retain their own licenses. Application licenses may be GPL, LGPL, MPL, Apache, BSD, MIT or project-specific terms and must be preserved individually. See `docs/LICENSING.md` and `docs/OPEN_SOURCE_APPLICATIONS_RESEARCH.md`.
