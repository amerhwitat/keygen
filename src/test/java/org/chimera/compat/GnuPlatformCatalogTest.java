package org.chimera.compat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GnuPlatformCatalogTest {
    @Test
    void catalogsCoreGnuUtilitiesAndServices() {
        assertTrue(GnuPlatformCatalog.tools().stream().anyMatch(t -> t.id().equals("coreutils")));
        assertTrue(GnuPlatformCatalog.tools().stream().anyMatch(t -> t.id().equals("findutils")));
        assertTrue(GnuPlatformCatalog.tools().stream().anyMatch(t -> t.id().equals("inetutils")));
        assertTrue(GnuPlatformCatalog.services().stream().anyMatch(s -> s.id().equals("inetutils-syslogd")));
        assertTrue(GnuPlatformCatalog.services().stream().anyMatch(s -> s.id().equals("inetutils-ftpd")));
        assertTrue(GnuPlatformCatalog.services().stream().anyMatch(s -> s.id().equals("gnutls-server")));
    }

    @Test
    void mapsGnuCapabilitiesToLinuxAndWindowsProviders() {
        assertTrue(GnuPlatformCatalog.windowsProviders().stream().anyMatch(p -> p.id().equals("msys2")));
        assertTrue(GnuPlatformCatalog.windowsProviders().stream().anyMatch(p -> p.id().equals("cygwin")));
        assertTrue(GnuPlatformCatalog.windowsProviders().stream().anyMatch(p -> p.id().equals("wsl2")));
    }
}
