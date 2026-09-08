package org.chimera.compat;

import java.util.List;

/** Inventory of widely used authorized IPv4/IPv6 discovery and scanning tools. */
public final class NetworkScannerCatalog {
    public record Scanner(String id, boolean ipv4, boolean ipv6, String role, String packageName) {}
    private NetworkScannerCatalog() {}
    public static List<Scanner> all() {
        return List.of(
            new Scanner("nmap", true, true, "host, port, service and OS discovery", "nmap"),
            new Scanner("masscan", true, true, "high-speed port discovery", "masscan"),
            new Scanner("zmap", true, true, "internet-scale research scanning", "zmap"),
            new Scanner("rustscan", true, true, "fast port discovery", "rustscan"),
            new Scanner("naabu", true, true, "fast TCP/UDP port discovery", "naabu"),
            new Scanner("arp-scan", true, false, "LAN ARP discovery", "arp-scan"),
            new Scanner("fping", true, true, "parallel reachability checks", "fping"),
            new Scanner("hping3", true, true, "packet probing and diagnostics", "hping3"),
            new Scanner("unicornscan", true, true, "asynchronous network probing", "unicornscan"),
            new Scanner("netdiscover", true, false, "local network discovery", "netdiscover"),
            new Scanner("nbtscan", true, false, "NetBIOS name discovery", "nbtscan"),
            new Scanner("smbclient", true, true, "SMB share enumeration/client", "smbclient")
        );
    }
}
