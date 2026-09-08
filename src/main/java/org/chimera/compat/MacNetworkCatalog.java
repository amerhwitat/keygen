package org.chimera.compat;

import java.util.List;

public final class MacNetworkCatalog {
    public record Integration(String id, String protocolOrService, String purpose, String hostComponent) {}
    private MacNetworkCatalog() {}
    public static List<Integration> all() {
        return List.of(
            new Integration("smb", "SMB/CIFS", "share browsing", "Finder/mount_smbfs"),
            new Integration("nfs", "NFS/NFSv4", "Unix filesystem access", "mount_nfs"),
            new Integration("bonjour", "mDNS/DNS-SD", "service discovery", "mDNSResponder"),
            new Integration("afp-legacy", "AFP", "legacy Apple interoperability", "native/third-party client"),
            new Integration("finder", "Finder", "filesystem and network browser", "Finder.app"),
            new Integration("network-extension", "Network Extension", "modern network integration", "macOS framework"),
            new Integration("active-directory", "LDAP/Kerberos/SMB", "directory-domain interoperability", "Samba/SSSD/native services")
        );
    }
}
