package org.chimera.desktop;

import java.util.List;
import java.util.Map;

public record DesktopLaunchPlan(String executable, List<String> arguments, Map<String,String> environment) {
    public DesktopLaunchPlan {
        if (executable == null || executable.isBlank() || executable.contains(" ") || executable.contains(";"))
            throw new IllegalArgumentException("invalid executable");
        arguments = List.copyOf(arguments == null ? List.of() : arguments);
        environment = Map.copyOf(environment == null ? Map.of() : environment);
    }
    public List<String> command() { var result = new java.util.ArrayList<String>(); result.add(executable); result.addAll(arguments); return List.copyOf(result); }
}
