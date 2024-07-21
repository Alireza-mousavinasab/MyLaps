package org.mylaps.kartlap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/")
public class KartLapController {

    private static final Logger logger = Logger.getLogger(KartLapController.class.getName());
    KartLapService kartLapService;

    public KartLapController(KartLapService kartLapService) {
        this.kartLapService = kartLapService;
    }

    @GetMapping("fastest-kart")
    public RaceResultDTO getFastestKartLap() {
        kartLapService.StartRace();
        logger.info("Race has started!");
        return kartLapService.GetFastestLap();
    }
}
