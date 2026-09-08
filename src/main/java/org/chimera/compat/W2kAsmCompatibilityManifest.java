package org.chimera.compat;

import java.util.Set;

public record W2kAsmCompatibilityManifest(String sourceFileName, long lineCount, Set<String> supportedArchitectures) {
    public static W2kAsmCompatibilityManifest defaultManifest() {
        return new W2kAsmCompatibilityManifest("W2K-ASM.txt", 921435L, Set.of("x86", "Alpha", "PowerPC", "DOSX32", "Win16", "Win32"));
    }
}
