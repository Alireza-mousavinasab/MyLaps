package org.mylaps.kartlap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KartLapDAOImplTest {

    private File tempCsvFile;

    @BeforeEach
    void setUp() throws IOException {
        tempCsvFile = File.createTempFile("karttimes", ".csv");
        tempCsvFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempCsvFile)) {
            writer.write("kart,passingtime\n");
            writer.write("1,12:00:00\n");
            writer.write("2,12:00:01\n");
            writer.write("3,12:00:01\n");
            writer.write("4,12:00:02\n");
            writer.write("5,12:00:02\n");
            writer.write("2,12:01:00\n");
            writer.write("1,12:01:10\n");
            writer.write("4,12:01:10\n");
            writer.write("5,12:01:12\n");
            writer.write("3,12:01:15\n");
            writer.write("2,12:02:10\n");
            writer.write("1,12:02:12\n");
            writer.write("4,12:02:12\n");
            writer.write("5,12:02:16\n");
            writer.write("3,12:02:17\n");
            writer.write("1,12:03:15\n");
            writer.write("2,12:03:20\n");
            writer.write("4,12:03:26\n");
            writer.write("3,12:03:36\n");
            writer.write("5,12:03:37\n");
            writer.write("2,12:04:20\n");
            writer.write("1,12:04:28\n");
            writer.write("5,12:04:33\n");
            writer.write("4,12:04:45\n");
            writer.write("3,12:04:46\n");
        }
    }

    @AfterEach
    void tearDown() {
        if (tempCsvFile != null && tempCsvFile.exists()) {
            tempCsvFile.delete();
        }
    }

    @Test
    void testGetLapsSuccess() {
        KartLapDAOImpl dao = new KartLapDAOImpl();
        List<KartLap> expectedLaps = List.of(
                new KartLap(1, LocalTime.parse("12:00:00", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(2, LocalTime.parse("12:00:01", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(3, LocalTime.parse("12:00:01", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(4, LocalTime.parse("12:00:02", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(5, LocalTime.parse("12:00:02", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(2, LocalTime.parse("12:01:00", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(1, LocalTime.parse("12:01:10", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(4, LocalTime.parse("12:01:10", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(5, LocalTime.parse("12:01:12", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(3, LocalTime.parse("12:01:15", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(2, LocalTime.parse("12:02:10", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(1, LocalTime.parse("12:02:12", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(4, LocalTime.parse("12:02:12", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(5, LocalTime.parse("12:02:16", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(3, LocalTime.parse("12:02:17", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(1, LocalTime.parse("12:03:15", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(2, LocalTime.parse("12:03:20", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(4, LocalTime.parse("12:03:26", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(3, LocalTime.parse("12:03:36", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(5, LocalTime.parse("12:03:37", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(2, LocalTime.parse("12:04:20", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(1, LocalTime.parse("12:04:28", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(5, LocalTime.parse("12:04:33", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(4, LocalTime.parse("12:04:45", DateTimeFormatter.ISO_LOCAL_TIME)),
                new KartLap(3, LocalTime.parse("12:04:46", DateTimeFormatter.ISO_LOCAL_TIME))
        );
        List<KartLap> laps = dao.getLaps();
        assertNotNull(laps);
        assertEquals(expectedLaps.size(), laps.size(), "Size should match");
        for (int i = 0; i < expectedLaps.size(); i++) {
            KartLap expectedLap = expectedLaps.get(i);
            KartLap actualLap = laps.get(i);
            assertEquals(expectedLap.getKart(), actualLap.getKart(), "Kart number should match");
            assertEquals(expectedLap.getPassingTime(), actualLap.getPassingTime(), "Passing time should match");
        }
    }
}