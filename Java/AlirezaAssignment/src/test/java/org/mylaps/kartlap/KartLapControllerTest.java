package org.mylaps.kartlap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KartLapControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getFastestKartLap() {
        RaceResultDTO expectedWinner = new RaceResultDTO(
                2,
                Duration.ofMinutes(4).plus(Duration.ofSeconds(19)),
                5,
                LocalTime.of(12, 0, 1),
                LocalTime.of(12, 4, 20));

        webTestClient.get()
                .uri("/api/v1/fastest-kart")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.kart").isEqualTo(expectedWinner.getKart())
                .jsonPath("$.lapDuration").isEqualTo(expectedWinner.getLapDuration())
                .jsonPath("$.lapNumber").isEqualTo(expectedWinner.getLapNumber())
                .jsonPath("$.lapStartTime").isEqualTo(expectedWinner.getLapStartTime().toString())
                .jsonPath("$.lapEndTime").isEqualTo(expectedWinner.getLapEndTime().toString());
    }
}