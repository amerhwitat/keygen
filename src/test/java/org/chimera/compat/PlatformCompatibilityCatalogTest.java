package org.chimera.compat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatformCompatibilityCatalogTest {
    @Test
    void exposesWindowsLinuxUnixFilesystems() {
        assertTrue(FileSystemCatalog.windows().contains(FileSystemCatalog.Type.NTFS));
        assertTrue(FileSystemCatalog.windows().contains(FileSystemCatalog.Type.REFS));
        assertTrue(FileSystemCatalog.linux().contains(FileSystemCatalog.Type.EXT4));
        assertTrue(FileSystemCatalog.linux().contains(FileSystemCatalog.Type.BTRFS));
        assertTrue(FileSystemCatalog.unix().contains(FileSystemCatalog.Type.UFS));
        assertTrue(FileSystemCatalog.unix().contains(FileSystemCatalog.Type.ZFS));
        assertTrue(FileSystemCatalog.apple().contains(FileSystemCatalog.Type.APFS));
    }

    @Test
    void exposesDualStackNetworkScannerCatalog() {
        assertTrue(NetworkScannerCatalog.all().stream().anyMatch(s -> s.id().equals("nmap") && s.ipv4() && s.ipv6()));
        assertTrue(NetworkScannerCatalog.all().stream().anyMatch(s -> s.id().equals("masscan") && s.ipv4()));
        assertTrue(NetworkScannerCatalog.all().stream().anyMatch(s -> s.id().equals("nmap") && s.ipv6()));
    }

    @Test
    void exposesWindowsCompatibilityAndAppleNetworkProfiles() {
        assertTrue(WindowsCompatibilityCatalog.all().stream().anyMatch(p -> p.architecture().equals("x86")));
        assertTrue(WindowsCompatibilityCatalog.all().stream().anyMatch(p -> p.architecture().equals("x86_64")));
        assertTrue(MacNetworkCatalog.all().stream().anyMatch(p -> p.id().equals("smb")));
        assertTrue(MacNetworkCatalog.all().stream().anyMatch(p -> p.id().equals("finder")));
    }

    @Test
    void validatesLegacyAsmReferenceWithoutBundlingConfidentialSource() {
        W2kAsmCompatibilityManifest manifest = W2kAsmCompatibilityManifest.defaultManifest();
        assertTrue(manifest.sourceFileName().equals("W2K-ASM.txt"));
        assertTrue(manifest.lineCount() > 900_000);
        assertTrue(manifest.supportedArchitectures().contains("x86"));
        assertTrue(manifest.supportedArchitectures().contains("Alpha"));
        assertTrue(manifest.supportedArchitectures().contains("PowerPC"));
    }
}
