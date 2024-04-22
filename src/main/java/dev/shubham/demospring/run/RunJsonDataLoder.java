package dev.shubham.demospring.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonDataLoder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoder.class);
    private final JdbcClientRunRepository runRepository;
    private final ObjectMapper objectMapper;


    public RunJsonDataLoder(ObjectMapper objectMapper, JdbcClientRunRepository runRepository) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if(runRepository.count() == 0) {
            try (InputStream inputStream = RunJsonDataLoder.class.getResourceAsStream("/data/runs.json")) {
                Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
                log.info("Reading {} runs from data and saving to in-memory collection", allRuns.runs().size());
                runRepository.saveAll(allRuns.runs());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }else {
            log.info("Not loading data from JSON because the collection is already loaded");
        }
    }
}
