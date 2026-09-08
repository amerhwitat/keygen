package org.chimera.compat;

import java.util.List;

/**
 * Declarative catalog of GNU/open-source utilities and network services.
 *
 * <p>The catalog describes host integration targets; it does not bundle or
 * execute third-party binaries. Linux package managers and Windows providers
 * remain responsible for installation, updates, signatures and service
 * supervision.</p>
 */
public final class GnuPlatformCatalog {
    public record Tool(String id, String packageName, String role,
                       boolean linux, boolean windows) {}

    public record Service(String id, String packageName, String role,
                          String protocol, boolean linux, List<String> windowsProviders) {}

    public record WindowsProvider(String id, String hostModel, String executionModel,
                                  String notes) {}

    private GnuPlatformCatalog() {}

    public static List<Tool> tools() {
        return List.of(
                tool("coreutils", "coreutils", "file, process and text utilities"),
                tool("findutils", "findutils", "filesystem search and traversal"),
                tool("grep", "grep", "pattern search"),
                tool("sed", "sed", "stream editing"),
                tool("gawk", "gawk", "AWK text processing"),
                tool("diffutils", "diffutils", "file and directory comparison"),
                tool("patch", "patch", "source patch application"),
                tool("tar", "tar", "archive creation and extraction"),
                tool("gzip", "gzip", "gzip compression"),
                tool("bzip2", "bzip2", "bzip2 compression"),
                tool("xz", "xz", "XZ compression"),
                tool("cpio", "cpio", "archive streaming"),
                tool("make", "make", "build orchestration"),
                tool("binutils", "binutils", "assembler linker and binary utilities"),
                tool("gcc", "gcc", "GNU compiler collection"),
                tool("gdb", "gdb", "native debugger"),
                tool("m4", "m4", "macro processor"),
                tool("autoconf", "autoconf", "configure-script generation"),
                tool("automake", "automake", "portable build-file generation"),
                tool("libtool", "libtool", "portable library build support"),
                tool("bash", "bash", "GNU shell"),
                tool("nano", "nano", "terminal text editor"),
                tool("screen", "screen", "terminal multiplexer"),
                tool("emacs", "emacs", "extensible editor and development environment"),
                tool("wget", "wget", "HTTP HTTPS and FTP retrieval"),
                tool("inetutils", "inetutils", "network clients and servers"),
                tool("gnutls", "gnutls", "TLS client/server and cryptographic transport")
        );
    }

    public static List<Service> services() {
        return List.of(
                service("inetutils-syslogd", "inetutils", "system logging daemon", "syslog", List.of("msys2", "cygwin", "wsl2")),
                service("inetutils-ftpd", "inetutils", "FTP server", "FTP", List.of("cygwin", "wsl2")),
                service("inetutils-telnetd", "inetutils", "Telnet server", "TELNET", List.of("cygwin", "wsl2")),
                service("inetutils-rshd", "inetutils", "remote shell server", "RSH", List.of("cygwin", "wsl2")),
                service("inetutils-rexecd", "inetutils", "remote execution server", "REXECD", List.of("cygwin", "wsl2")),
                service("inetutils-talkd", "inetutils", "interactive talk daemon", "TALK", List.of("cygwin", "wsl2")),
                service("gnutls-server", "gnutls", "TLS test/server endpoint", "TLS", List.of("msys2", "cygwin", "wsl2")),
                service("gnutls-cli", "gnutls", "TLS client endpoint", "TLS", List.of("msys2", "cygwin", "wsl2"))
        );
    }

    public static List<WindowsProvider> windowsProviders() {
        return List.of(
                new WindowsProvider("msys2", "Windows", "native Windows process with POSIX/GNU userland",
                        "Use MSYS2 packages for GNU command-line tooling; Windows service registration remains host-native."),
                new WindowsProvider("cygwin", "Windows", "Cygwin POSIX compatibility layer",
                        "Use Cygwin packages for GNU/POSIX applications; daemon lifecycle is delegated to the Cygwin service model."),
                new WindowsProvider("wsl2", "Windows", "Linux virtualized environment",
                        "Use a real Linux distribution under WSL2 for Linux-native GNU packages and daemons."),
                new WindowsProvider("native-port", "Windows", "native Win32 port",
                        "Use only when the specific GNU utility has an actively maintained Windows-native port." )
        );
    }

    private static Tool tool(String id, String packageName, String role) {
        return new Tool(id, packageName, role, true, true);
    }

    private static Service service(String id, String packageName, String role,
                                   String protocol, List<String> windowsProviders) {
        return new Service(id, packageName, role, protocol, true, List.copyOf(windowsProviders));
    }
}
