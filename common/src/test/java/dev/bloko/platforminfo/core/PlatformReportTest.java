package dev.bloko.platforminfo.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class PlatformReportTest {
    @Test
    void formatsReportsForEachPlatform() {
        for (String platform : List.of("Paper", "Spigot", "Purpur", "Folia",
                "Velocity", "BungeeCord", "Fabric")) {
            List<String> report = PlatformReport.create(platform, "test-version", 12, 90061);

            assertEquals("Platform: " + platform, report.get(1));
            assertEquals("Version: test-version", report.get(2));
            assertEquals("Online players: 12", report.get(3));
            assertEquals("Plugin uptime: 1d 01h 01m 01s", report.get(4));
        }
    }

    @Test
    void handlesMissingValuesAndNegativeCounters() {
        List<String> report = PlatformReport.create(null, "  ", -5, -1);

        assertEquals("Platform: Unknown", report.get(1));
        assertEquals("Version: Unknown", report.get(2));
        assertEquals("Online players: 0", report.get(3));
        assertEquals("Plugin uptime: 0d 00h 00m 00s", report.get(4));
    }

    @Test
    void removesLineBreaksAndLimitsMessageLength() {
        List<String> report = PlatformReport.create(
                "Paper\nInjected\rMessage", "x".repeat(300), 0, 0);

        assertFalse(report.get(1).contains("\n"));
        assertFalse(report.get(1).contains("\r"));
        assertEquals(169, report.get(2).length());
    }

    @Test
    void formatsLargeDurations() {
        assertEquals("106751991167300d 15h 30m 07s",
                PlatformReport.duration(Long.MAX_VALUE));
    }

    @Test
    void doesNotAllowReportChanges() {
        assertThrows(UnsupportedOperationException.class,
                () -> PlatformReport.create("Paper", "test", 0, 0).add("extra"));
    }
}
