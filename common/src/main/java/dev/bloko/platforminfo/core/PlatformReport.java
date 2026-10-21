package dev.bloko.platforminfo.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class PlatformReport {
    private PlatformReport() {
    }

    public static List<String> create(String platform, String version, int players, long uptimeSeconds) {
        return Collections.unmodifiableList(Arrays.asList(
                "PlatformInfo 1.0.0",
                "Platform: " + clean(platform),
                "Version: " + clean(version),
                "Online players: " + Math.max(0, players),
                "Plugin uptime: " + duration(uptimeSeconds)
        ));
    }

    public static String duration(long seconds) {
        long totalSeconds = Math.max(0, seconds);

        long days = totalSeconds / 86400;
        long hours = (totalSeconds / 3600) % 24;
        long minutes = (totalSeconds / 60) % 60;
        long remainingSeconds = totalSeconds % 60;

        return String.format(Locale.ROOT, "%dd %02dh %02dm %02ds",
                days, hours, minutes, remainingSeconds);
    }

    private static String clean(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "Unknown";
        }

        StringBuilder text = new StringBuilder();

        for (int i = 0; i < value.length() && text.length() < 160; i++) {
            char character = value.charAt(i);
            text.append(Character.isISOControl(character) ? ' ' : character);
        }

        return text.toString().trim();
    }
}
