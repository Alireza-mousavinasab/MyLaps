package org.mylaps.kartlap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class KartLapDAOImpl implements KartLapDAO {

    private static final Logger logger = Logger.getLogger(KartLapDAOImpl.class.getName());

    @Override
    public List<KartLap> getLaps() {
        List<KartLap> kartLaps = new ArrayList<>();
        String csvFile = "src/main/resources/static/karttimes.csv";
        try (Reader reader = new FileReader(csvFile);
             CSVParser csvParser = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {
            for (CSVRecord record : csvParser) {
                Integer kart = Integer.parseInt(record.get(0));
                LocalTime passingTime = LocalTime.parse(record.get(1), DateTimeFormatter.ISO_LOCAL_TIME);
                kartLaps.add(new KartLap(kart, passingTime));
            }
            return kartLaps;
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
            return null;
        }
    }
}
