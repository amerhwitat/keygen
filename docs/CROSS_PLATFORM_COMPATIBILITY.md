# Chimera II OS — Cross-Platform Compatibility

This layer inventories integration boundaries for Windows, Linux, Unix/BSD and macOS. It does not replace operating-system kernels, Finder, Active Directory, SMB/NFS implementations or filesystem drivers.

## IPv4 and IPv6

`NetworkScannerCatalog` covers Nmap, Masscan, ZMap, RustScan, Naabu, arp-scan, fping, hping3, Unicornscan, netdiscover, nbtscan and smbclient. Nmap is the primary cross-platform scanner and supports IPv6 with `-6`. Scanning is restricted to authorized infrastructure.

## Windows

`WindowsCompatibilityCatalog` covers Win16/legacy Windows, 32-bit Win32, 64-bit Win64/PE32+, modern Windows Server/client x86_64 and modern Windows Server ARM64. Execution is delegated to native Windows or an appropriate compatibility runtime such as Wine/WOW64.

## W2K-ASM

The Library contains `W2K-ASM.txt`, a 921,435-line historical Windows assembly/debugging corpus. The visible material includes x86, Alpha and PowerPC code, Win16/Win32 thunking and 8087/emulator dispatch tables. `W2kAsmCompatibilityManifest` integrates the architectural compatibility metadata.

The complete corpus is intentionally not redistributed into the public Java repository because the supplied file contains Microsoft Confidential/proprietary notices. The Library copy remains the source reference. Chimera therefore preserves compatibility targets without embedding or redistributing the confidential source wholesale.

## Filesystems

Windows: FAT12/FAT16/FAT32, exFAT, NTFS, ReFS, CDFS, UDF, CSVFS, NFS and SMB/CIFS.

Linux: ext2/ext3/ext4, XFS, Btrfs, ZFS, F2FS, JFS, NILFS2, ReiserFS, tmpfs, overlayfs, procfs, sysfs, devpts, NFS/NFSv4 and SMB/CIFS.

Unix/BSD: UFS/UFS2, FFS, ZFS, JFS2, VxFS, NFS/NFSv4 and SMB/CIFS.

Apple: APFS, HFS+, UDF, NFS/NFSv4 and SMB/CIFS.

This is a compatibility vocabulary, not a claim that Chimera contains every historical filesystem driver.

## SMB, AD, NFS and Samba

`NetworkInteroperabilityCatalog` provides service roles for Samba `smbd`, `nmbd`, `winbindd`, Samba AD DC, NFS server/client, `smbclient`, LDAP, Kerberos and Avahi/mDNS-DNS-SD. Samba remains the native implementation boundary and is tracked as an external runtime.

## macOS Tahoe 26 / Finder

`MacNetworkCatalog` targets macOS Tahoe 26 and integrates descriptors for SMB, NFS/NFSv4, Bonjour, legacy AFP interoperability, Finder, Network Extension and directory services. Finder remains native; Chimera supplies discovery and orchestration metadata.

## Security boundary

Discovery and scanning APIs must be used only on authorized networks. The catalog does not automatically scan arbitrary Internet ranges, bypass authentication, exploit discovered services or persist credentials.
