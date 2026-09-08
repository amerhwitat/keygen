package org.chimera.compat;

import java.util.List;

/** Windows application compatibility targets; execution is delegated to a native Windows host or approved compatibility runtime. */
public final class WindowsCompatibilityCatalog {
    public record Profile(String id, String family, String architecture, String abi, String runtime) {}
    private WindowsCompatibilityCatalog() {}
    public static List<Profile> all() {
        return List.of(
            new Profile("win9x-legacy", "Windows 95/98/ME", "x86", "Win32/Win16", "native-or-legacy-emulation"),
            new Profile("winnt-32", "Windows NT/2000/XP/2003/2008 32-bit", "x86", "Win32", "native-or-Wine-WOW32"),
            new Profile("winnt-64", "Windows 7/8/8.1/10/11 and Server 2008 R2+", "x86_64", "Win64", "native-or-Wine-WOW64"),
            new Profile("windows-server-arm64", "Windows Server modern ARM64", "arm64", "Win64/ARM64", "native"),
            new Profile("pe32", "Portable Executable 32-bit", "x86", "PE32", "PE loader boundary"),
            new Profile("pe32-plus", "Portable Executable 64-bit", "x86_64", "PE32+", "PE loader boundary")
        );
    }
}
