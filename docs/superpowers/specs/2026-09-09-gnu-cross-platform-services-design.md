# GNU Cross-Platform Services and Licensing Design

## Goal
Extend Chimera II OS Java compatibility metadata with a curated GNU/open-source tool and service catalog usable on Linux and Windows, and make the repository's original source explicitly GNU GPLv3-or-later licensed.

## Scope
- Add a typed catalog for GNU/open-source utilities and network services commonly useful to Chimera II.
- Cover filesystem, text, archive, build, debugger, transfer, shell, networking, and service roles.
- Represent Windows delivery through native ports, MSYS2/Cygwin, or WSL rather than pretending GNU daemons are native Win32 services.
- Keep Linux package-manager integration declarative and non-executing.
- Preserve existing ABI, compatibility boundaries, and untouched `amerhwitat/test` and `amerhwitat/ChimeraIIOS` repositories.
- Add GPLv3-or-later licensing for original repository code and documentation, while explicitly preserving third-party licenses and runtime binaries under their own terms.

## Proposed catalog families
1. Core GNU utilities: coreutils, findutils, grep, sed, gawk, diffutils, patch.
2. Archives/compression: tar, gzip, bzip2, xz, cpio.
3. Build/development: make, binutils, gcc, gdb, m4, autoconf, automake, libtool.
4. Shell/editor/terminal: bash, nano, screen, emacs.
5. Network/transfer: wget, inetutils, gnutls-cli/serv concepts.
6. GNU network services: inetutils syslogd, ftpd, telnetd, rshd/rexecd, talkd where packaged by the host distribution.
7. Windows interoperability: MSYS2, Cygwin, WSL2 and native Windows ports as provider profiles.

## Licensing
The repository's original code is GPL-3.0-or-later. The repository must not claim that third-party dependencies, operating systems, GNU packages, Microsoft files, Apple components, or native binaries are relicensed by this choice. A license manifest will distinguish project-owned source from external components.

## Testing
Add catalog tests that verify representative tool/service coverage, Linux/Windows provider mappings, and licensing metadata. CI remains the authoritative build check.
