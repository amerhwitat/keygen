# Open-Source Windows/Linux Application Research

## Scope

This catalog identifies mature open-source applications that materially increase the completeness and versatility of the Chimera II Java-track environment. It focuses on desktop productivity, development, networking, security, storage, virtualization, containers, multimedia and administration.

The research uses current project documentation and official platform pages. The Java repository records integration metadata and capability boundaries; it does not redistribute third-party binaries.

## Recommended capability layers

| Layer | Applications / projects | Chimera II integration role |
|---|---|---|
| Office | LibreOffice | Documents, spreadsheets and presentations |
| Graphics | GIMP, Inkscape, Krita, Blender | Raster, vector, painting and 3D production |
| Multimedia | VLC, Audacity, OBS Studio, Kdenlive | Playback, audio editing, capture and video production |
| Communication | Thunderbird | Mail, calendar and contacts |
| Documents | Okular | PDF and multi-format document viewing |
| Desktop | KDE Dolphin, Kate, Konsole; GNOME Files/System Monitor | Files, code, terminals, resource administration |
| Security | KeePassXC, Wireshark, OpenSSH | Credential management, packet analysis, secure administration |
| Networking | curl, Wget, FileZilla, PuTTY, WireGuard, OpenVPN | Transfer, remote access and VPN connectivity |
| Development | Git, GCC/GDB from the GNU catalog, Kate | Source control, build/debug and editing |
| Virtualization | QEMU, VirtualBox, libvirt, virt-manager | VM execution and management |
| Containers | Docker Engine, Podman | Containerized application workloads |
| Storage/backup | rclone, Syncthing, restic | Sync, cloud/local transfer and encrypted backup |
| Archive | 7-Zip and GNU archive/compression tools | Broad archive interoperability |
| Productivity | Calibre | Ebook library, conversion and publishing workflows |

## Evidence from upstream projects

GNOME maintains a curated collection of free-software desktop applications covering files, disks, logs, system monitoring, software installation, documents, media, terminals and settings. GNOME Software also provides application discovery and update functionality. See <https://apps.gnome.org/en/> and <https://apps.gnome.org/Software/>.

KDE maintains a large application ecosystem spanning file management, terminals, documents, graphics, multimedia, development, networking and administration. KDE's Windows platform page explicitly lists Windows builds for applications including Dolphin, Kate, Konsole, Kdenlive, Krita and Okular. See <https://apps.kde.org/> and <https://apps.kde.org/platforms/windows/>.

Okular is a multi-platform KDE document viewer supporting Linux and Windows, with PDF and many other document formats. See <https://apps.kde.org/okular/>.

Kate is a KDE programming editor available on Linux, BSD, Windows and macOS and includes LSP, project, Git and terminal integration. See <https://apps.kde.org/kate/>.

Blender is a GPL-licensed free/open-source 3D suite and publishes Linux and Windows builds, including Windows ARM64. See <https://www.blender.org/about/> and <https://www.blender.org/download/>.

Inkscape is a free/open-source vector editor for GNU/Linux and Windows among other platforms. See <https://inkscape.org/about/>.

GIMP is a free/open-source image editor available for GNU/Linux and Windows. See <https://www.gimp.org/>.

OBS Studio is free/open-source recording and streaming software with Windows and Linux releases. See <https://obsproject.com/>.

Wireshark is a free/open-source network protocol analyzer supporting Windows, Linux and UNIX-family systems and is distributed under GPLv2. See <https://www.wireshark.org/>.

Docker Engine is an open-source containerization technology and has native Linux installation paths plus Windows Server daemon support; Docker Desktop provides Windows and Linux desktop experiences. See <https://docs.docker.com/engine/> and <https://docs.docker.com/engine/install/>.

7-Zip publishes Windows x64, x86 and ARM64 installers and Linux console builds. Its code is predominantly LGPL with additional licensing for some components. See <https://www.7-zip.org/download.html> and <https://www.7-zip.org/>.

## Integration policy

1. Prefer distribution package managers on Linux.
2. Prefer signed official installers or maintained package providers on Windows.
3. Use MSYS2/Cygwin/WSL2 when a GNU/POSIX application is not a true native Windows program.
4. Keep application lifecycle separate from the Koronos kernel lifecycle.
5. Represent installation and service operations as structured plans rather than shell-concatenated strings.
6. Track upstream version and license metadata as refreshable catalog information.
7. Never imply that catalog presence means the binary is bundled or already installed.
8. Do not automatically start network daemons, VPNs, remote-access services or packet capture components.
9. Require explicit administrator approval for privileged installation, service activation and host networking changes.
10. Preserve each third-party project's copyright, license and source-disclosure requirements.

## Priority tiers

### Tier 1 — baseline workstation

LibreOffice, Firefox/Chrome from the existing runtime catalog, GIMP, VLC, 7-Zip, Git, OpenSSH, Thunderbird, KeePassXC, curl, Wireshark, Syncthing and restic.

### Tier 2 — developer/research workstation

Blender, Inkscape, Krita, Audacity, OBS Studio, Kdenlive, Kate, Konsole, QEMU, libvirt, virt-manager, Podman and Docker Engine.

### Tier 3 — infrastructure/server

OpenSSH, curl, Wget, rclone, restic, Syncthing, Podman, Docker Engine, QEMU/libvirt, WireGuard and OpenVPN. Server-side daemons must be separately enabled and hardened.

### Tier 4 — compatibility/legacy

MSYS2, Cygwin, WSL2, PuTTY, FileZilla and selected GNU Inetutils services. Legacy plaintext services such as Telnet and RSH should remain disabled by default.

## Licensing boundary

The Chimera II Java-track source remains GPL-3.0-or-later. The applications listed here retain their own licenses. In particular, the repository must not describe all external software as GPLv3 simply because the repository itself is GPLv3-or-later.

Examples include MPL, Apache, BSD, MIT, LGPL and GPL-family projects. Individual project notices and source obligations remain authoritative.

## Architectural conclusion

The strongest approach is a **capability catalog + native package/service plan** rather than embedding a huge collection of third-party binaries. This preserves updateability, distribution integration, security patching and licensing correctness while giving Koronos a unified inventory of what a complete Windows/Linux host can provide.
