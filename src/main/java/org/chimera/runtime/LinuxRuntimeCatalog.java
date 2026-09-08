package org.chimera.runtime;

import java.util.List;
import java.util.Map;

/** Host package catalog; third-party binaries remain outside the Java artifact. */
public final class LinuxRuntimeCatalog {
    public enum Distro { FEDORA, UBUNTU, DEBIAN, AURORA, KALI }
    public record Runtime(String id, String displayName, String version, List<String> executables,
                          Map<Distro, List<String>> packages, String sourceUrl, boolean openSource) {}
    private LinuxRuntimeCatalog() {}

    public static List<Runtime> runtimes() {
        return List.of(
            r("python", "Python", "3.14.7", List.of("python3", "pip3"), "https://www.python.org/", true,
                List.of("python3", "python3-pip")),
            r("java", "OpenJDK", "26.0.2.1", List.of("java", "javac"), "https://www.oracle.com/java/technologies/downloads/", true,
                List.of("openjdk-26-jdk")),
            r("firefox", "Mozilla Firefox", "latest-stable", List.of("firefox"), "https://www.mozilla.org/firefox/linux/", true,
                List.of("firefox")),
            r("chrome", "Google Chrome", "latest-stable", List.of("google-chrome", "google-chrome-stable"), "https://www.google.com/chrome/", false,
                List.of("google-chrome-stable")),
            r("bash", "GNU Bash", "latest-distro-stable", List.of("bash"), "https://www.gnu.org/software/bash/", true, List.of("bash")),
            r("zsh", "Z shell", "5.9.2", List.of("zsh"), "https://zsh.sourceforge.io/", true, List.of("zsh")),
            r("powershell", "Microsoft PowerShell", "7.6.2", List.of("pwsh"), "https://learn.microsoft.com/powershell/", true, List.of("powershell")),
            r("dotnet", ".NET", "10.0.400", List.of("dotnet"), "https://dotnet.microsoft.com/download/dotnet/10.0", true, List.of("dotnet-sdk-10.0")),
            r("nginx", "NGINX HTTP server", "1.30.4-stable", List.of("nginx"), "https://nginx.org/", true, List.of("nginx")),
            r("bind9", "BIND 9 DNS", "9.20.27", List.of("named", "dig", "rndc"), "https://www.isc.org/bind/", true, List.of("bind9", "bind9-utils")),
            r("postfix", "Postfix mail server", "3.11.7", List.of("postfix", "postqueue", "postconf"), "https://www.postfix.org/", true, List.of("postfix"))
        );
    }

    private static Runtime r(String id, String name, String version, List<String> exe, String url, boolean oss, List<String> debian) {
        Map<Distro, List<String>> p = Map.of(
            Distro.FEDORA, debian, Distro.UBUNTU, debian, Distro.DEBIAN, debian,
            Distro.AURORA, debian, Distro.KALI, debian);
        return new Runtime(id, name, version, exe, p, url, oss);
    }

    /** Kali metapackages are preferred to attempting to vendor the entire Kali distribution. */
    public static List<String> kaliSecurityProfiles() {
        return List.of("kali-tools-top10", "kali-tools-information-gathering", "kali-tools-web",
            "kali-tools-vulnerability", "kali-tools-forensics", "kali-tools-reverse-engineering",
            "kali-tools-exploitation", "kali-tools-passwords", "kali-tools-wireless",
            "kali-tools-crypto-stego", "kali-tools-sniffing-spoofing", "kali-tools-reporting");
    }

    public static List<String> kaliTopTools() {
        return List.of("nmap", "burpsuite", "metasploit-framework", "wireshark", "aircrack-ng",
            "hydra", "john", "netexec", "responder", "sqlmap");
    }
}
