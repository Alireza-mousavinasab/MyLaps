package org.mylaps.kartlap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KartLapServiceTest {

    private KartLapDAO mockKartLapDAO;
    private KartLapService kartLapService;

    @BeforeEach
    void setUp() {
        mockKartLapDAO = mock(KartLapDAO.class);
        kartLapService = new KartLapService(mockKartLapDAO);
    }

    @Test
    void startRace() {
        List<KartLap> mockLaps = List.of(
                new KartLap(1, LocalTime.parse("12:00:00")),
                new KartLap(2, LocalTime.parse("12:00:01")),
                new KartLap(1, LocalTime.parse("12:00:05"))
        );

        when(mockKartLapDAO.getLaps()).thenReturn(mockLaps);
        kartLapService.StartRace();
        List<KartLap> actualLaps = null;
        try {
            var field = KartLapService.class.getDeclaredField("kartLaps");
            field.setAccessible(true);
            actualLaps = (List<KartLap>) field.get(kartLapService);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        assertNotNull(actualLaps);
        assertEquals(mockLaps.size(), actualLaps.size());
        assertTrue(actualLaps.containsAll(mockLaps));
    }

    @Test
    void getFastestLap() {
        List<KartLap> mockLaps = List.of(
                new KartLap(1, LocalTime.parse("12:00:00")),
                new KartLap(1, LocalTime.parse("12:00:10")),
                new KartLap(1, LocalTime.parse("12:00:15")),
                new KartLap(1, LocalTime.parse("12:00:25")),
                new KartLap(1, LocalTime.parse("12:00:35"))
        );

        RaceResultDTO expectedResult = new RaceResultDTO(
                1,
                Duration.ofSeconds(35),
                5,
                LocalTime.parse("12:00:00"),
                LocalTime.parse("12:00:35")
        );

        when(mockKartLapDAO.getLaps()).thenReturn(mockLaps);

        kartLapService.StartRace();
        RaceResultDTO result = kartLapService.GetFastestLap();

        assertNotNull(result);
        assertEquals(expectedResult.getKart(), result.getKart());
        assertEquals(expectedResult.getLapDuration(), result.getLapDuration());
        assertEquals(expectedResult.getLapNumber(), result.getLapNumber());
        assertEquals(expectedResult.getLapStartTime(), result.getLapStartTime());
        assertEquals(expectedResult.getLapEndTime(), result.getLapEndTime());
    }
}