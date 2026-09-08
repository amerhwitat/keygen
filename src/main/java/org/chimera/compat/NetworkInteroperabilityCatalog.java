package org.chimera.compat;

import java.util.List;

public final class NetworkInteroperabilityCatalog {
    public record Service(String id, String protocol, String role, String linuxPackage, String daemonOrClient) {}
    private NetworkInteroperabilityCatalog() {}
    public static List<Service> all() {
        return List.of(
            new Service("samba-smbd", "SMB2/SMB3", "SMB file server", "samba", "smbd"),
            new Service("samba-nmbd", "NetBIOS", "legacy name service", "samba", "nmbd"),
            new Service("samba-winbindd", "Winbind/AD", "domain identity integration", "samba-winbind", "winbindd"),
            new Service("samba-ad-dc", "AD DS/Kerberos/LDAP/DNS", "Active Directory domain controller", "samba-dc", "samba"),
            new Service("nfs-server", "NFSv3/NFSv4", "network filesystem server", "nfs-utils", "nfs-server"),
            new Service("nfs-client", "NFSv3/NFSv4", "network filesystem client", "nfs-utils", "mount.nfs"),
            new Service("smb-client", "SMB2/SMB3", "network share browser/client", "samba-client", "smbclient"),
            new Service("ldap", "LDAP", "directory integration", "openldap-clients", "ldapsearch"),
            new Service("kerberos", "Kerberos", "domain authentication", "krb5-workstation", "kinit"),
            new Service("bonjour", "mDNS/DNS-SD", "zero-configuration discovery", "avahi", "avahi-daemon")
        );
    }
}
