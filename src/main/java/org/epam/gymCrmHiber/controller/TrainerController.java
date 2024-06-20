package org.epam.gymCrmHiber.controller;

import java.util.List;
import static org.epam.gymCrmHiber.Main.API_URL;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.util.PasswordChangeRequest;
import org.epam.gymCrmHiber.util.UserCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(API_URL + "/trainers")
public interface TrainerController {

    @PostMapping(value = "/add", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> saveTrainer(@RequestBody Trainer trainer);

    @PostMapping(value = "/get", produces = "application/json", consumes = "application/json")
    ResponseEntity<Trainer> getTrainerByUsername(@RequestBody UserCredentials credentials);

    @PutMapping(value = "/update", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> updateTrainer(@RequestBody Trainer trainer, @RequestBody UserCredentials credentials);

    @PostMapping(value = "/pass-change", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> changeTrainerPassword(@RequestBody PasswordChangeRequest request);

    @PostMapping(value = "/activate", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> activateTrainer(@RequestBody UserCredentials credentials);

    @PostMapping(value = "/deactivate", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> deactivateTrainer(@RequestBody UserCredentials credentials);

    @PostMapping(value = "/unassigned", produces = "application/json", consumes = "application/json")
    ResponseEntity<List<Trainer>> getUnassignedTrainers(@RequestBody UserCredentials credentials, @RequestParam String traineeUsername);
}
