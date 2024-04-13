package dev.shubham.demospring.run;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class RunRepository {

    private List<Run> runs = new ArrayList<Run>();
    List<Run> findAll(){
        return runs;
    }

    Optional<Run> findById(int id){
        return runs.stream()
                .filter(run -> run.id()==id)
                .findFirst();
    }

    void create(Run run){
        runs.add(run);
    }

    void update(Run run, Integer id){
        Optional<Run> found = findById(id);
        if(found.isPresent()) {
            runs.set(runs.indexOf(found.get()), run);
        }
    }

    void delete(Integer id){
        runs.removeIf(run -> run.id()==id);
    }

    @PostConstruct
    private void init(){
        runs.add(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                3,
                Locations.INDOOR
        ));
        runs.add(new Run(2,
        "Wed Evening Run",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                3,
                Locations.INDOOR
        ));
    }
}
