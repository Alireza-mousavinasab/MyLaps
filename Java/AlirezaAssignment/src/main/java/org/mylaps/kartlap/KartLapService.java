package org.mylaps.kartlap;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class KartLapService {

    KartLapDAO kartLapDAO;
    List<KartLap> kartLaps;
    private static final Logger logger = Logger.getLogger(KartLapService.class.getName());

    public KartLapService(KartLapDAO kartLapDAO) {
        this.kartLapDAO = kartLapDAO;
    }

    public void StartRace() {
        kartLaps = kartLapDAO.getLaps();
    }

    public RaceResultDTO GetFastestLap() {
        if (kartLaps != null) {
            try {
                Optional<RaceResultDTO> fastestKart = kartLaps.stream()
                        .collect(Collectors.groupingBy(KartLap::getKart))
                        .entrySet().stream()
                        .filter(entry -> entry.getValue().size() == 5)
                        .map(entry -> {
                            List<KartLap> orderedLaps = entry.getValue().stream()
                                    .sorted(Comparator.comparing(KartLap::getPassingTime))
                                    .toList();

                            List<Duration> lapDurations = new ArrayList<>();
                            for (int i = 1; i < orderedLaps.size(); i++) {
                                lapDurations.add(Duration.between(orderedLaps.get(i - 1).getPassingTime(), orderedLaps.get(i).getPassingTime()));
                            }

                            Duration totalDuration = lapDurations.stream()
                                    .reduce(Duration.ZERO, Duration::plus);

                            return new RaceResultDTO(
                                    entry.getKey(),
                                    totalDuration,
                                    5,
                                    orderedLaps.get(0).getPassingTime(),
                                    orderedLaps.get(orderedLaps.size() - 1).getPassingTime()
                            );
                        })
                        .sorted(Comparator.comparing(RaceResultDTO::getLapDuration))
                        .findFirst();

                if (fastestKart.isPresent()) {
                    return fastestKart.get();
                } else {
                    logger.warning("There is no data to get the fastest lap");
                    return new RaceResultDTO(null, Duration.ZERO, 0, null, null);
                }
            } catch (Exception ex) {
                logger.severe("There is an error in getting the fastest lap: " + ex.getMessage());
                return new RaceResultDTO(null, Duration.ZERO, 0, null, null);
            }
        }
        logger.severe("There is a problem in fetching the data from CSV file");
        return new RaceResultDTO(null, Duration.ZERO, 0, null, null);
    }
}
