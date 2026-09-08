package org.chimera.runtime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinuxRuntimeCatalogTest {
    @Test void coreRuntimeCatalogIsPresent() {
        var ids = LinuxRuntimeCatalog.runtimes().stream().map(LinuxRuntimeCatalog.Runtime::id).toList();
        assertTrue(ids.containsAll(java.util.List.of("python", "java", "firefox", "chrome", "bash", "zsh", "powershell", "dotnet", "nginx", "bind9", "postfix")));
    }

    @Test void securityCatalogIncludesRequestedTools() {
        assertTrue(LinuxRuntimeCatalog.kaliTopTools().containsAll(java.util.List.of("burpsuite", "metasploit-framework", "nmap")));
        assertTrue(LinuxRuntimeCatalog.kaliSecurityProfiles().contains("kali-tools-top10"));
    }
}
