# Chimera II OS Java Track — Licensing

## Project license

Original source code and original documentation in this repository are licensed under the **GNU General Public License, version 3 or any later version (GPL-3.0-or-later)**.

The canonical license is the Free Software Foundation's GNU GPLv3. The repository's `LICENSE` file identifies the governing license and links to the authoritative text.

## Third-party software

GPL licensing of this repository does **not** relicense third-party components. In particular:

- Jakarta EE APIs remain under their own license terms.
- DJL remains under its own license terms.
- H2 remains under its own license terms.
- GNU utilities and daemons remain under their individual GNU project licenses.
- Linux distributions and kernels remain under their respective licenses.
- Windows, Microsoft SDKs, Wine/WOW64, MSYS2, Cygwin and WSL components remain under their respective licenses.
- macOS, Finder and Apple frameworks remain proprietary Apple components and are not redistributed or relicensed by this repository.
- The `W2K-ASM.txt` compatibility corpus is not redistributed by this repository because it contains Microsoft proprietary/confidential notices; the project retains only compatibility metadata describing the reference corpus.

## Runtime model

Chimera II uses catalogs and structured process/package plans to interoperate with external software. Referencing, detecting or launching an external package does not make that package part of the repository's GPL-covered source.

## GNU compatibility catalog

`org.chimera.compat.GnuPlatformCatalog` records common GNU utilities, network services and Windows delivery providers. It is an interoperability catalog, not a vendor bundle of GNU binaries.

For legal interpretation of a particular dependency or distribution, consult the license shipped with that component and qualified legal counsel where necessary.
