package org.epam.gymCrmHiber.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import static org.epam.gymCrmHiber.Main.API_URL;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.util.PasswordChangeRequest;
import org.epam.gymCrmHiber.util.UserCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(API_URL + "/trainees")
@Tag(name = "Trainee_Controller", description = "CRUD for Trainees")
public interface TraineeController {

    @ApiOperation(value = "Save a new trainee", response = Trainee.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee successfully saved"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(value = "/add", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> saveTrainee(@RequestBody Trainee trainee);

    @ApiOperation(value = "Get all trainees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of trainees"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(value = "/all", produces = "application/json", consumes = "application/json")
    ResponseEntity<List<Trainee>> getAllTrainees(@RequestBody UserCredentials credentials);

    @ApiOperation(value = "Get a trainee by username", response = Trainee.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainee"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    @PostMapping(value = "/get", produces = "application/json", consumes = "application/json")
    ResponseEntity<Trainee> getTraineeByUsername(@RequestBody UserCredentials credentials);

    @ApiOperation(value = "Update an existing trainee", response = Trainee.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping(value = "/update", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> updateTrainee(@RequestBody Trainee trainee, @RequestBody UserCredentials credentials);

    @ApiOperation(value = "Delete a trainee", response = Trainee.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    @DeleteMapping(value = "/drop", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> deleteTrainee(@RequestBody UserCredentials credentials);

    @ApiOperation(value = "Change a trainee's password", response = Trainee.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password successfully changed"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(value = "/pass-change", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> changeTraineesPassword(@RequestBody PasswordChangeRequest request);

    @ApiOperation(value = "Activate a trainee", response = Trainee.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee successfully activated"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    @PostMapping(value = "/activate", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> activateTrainee(@RequestBody UserCredentials credentials);

    @ApiOperation(value = "Deactivate a trainee", response = Trainee.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee successfully deactivated"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    @PostMapping(value = "/deactivate", produces = "application/json", consumes = "application/json")
    ResponseEntity<String> deactivateTrainee(@RequestBody UserCredentials credentials);
}
