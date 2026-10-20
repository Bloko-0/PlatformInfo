package dev.bloko.platforminfo.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/** A small, immutable text report shared by server, proxy and mod entry points. */
public final class PlatformReport {
    private PlatformReport() { }

    public static List<String> create(String platform, String version, int players, long uptimeSeconds) {
        return Collections.unmodifiableList(Arrays.asList(
                "PlatformInfo 1.0.0",
                "Platform: " + clean(platform),
                "Version: " + clean(version),
                "Online players: " + Math.max(0, players),
                "Plugin uptime: " + duration(uptimeSeconds)));
    }

    public static String duration(long seconds) {
        long safe = Math.max(0, seconds);
        return String.format(Locale.ROOT, "%dd %02dh %02dm %02ds",
                safe / 86400, safe / 3600 % 24, safe / 60 % 60, safe % 60);
    }

    private static String clean(String value) {
        if (value == null || value.trim().isEmpty()) return "Unknown";
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < value.length() && text.length() < 160; i++) {
            char character = value.charAt(i);
            text.append(Character.isISOControl(character) ? ' ' : character);
        }
        return text.toString().trim();
    }
}
