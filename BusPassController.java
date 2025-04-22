package com.buspass.controller;

import com.buspass.dto.BusPassRequest;
import com.buspass.exception.ResourceNotFoundException;
import com.buspass.exception.InvalidRequestException;
import com.buspass.model.BusPass;
import com.buspass.service.BusPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buspasses")
public class BusPassController {
    private final BusPassService busPassService;

    @Autowired
    public BusPassController(BusPassService busPassService) {
        this.busPassService = busPassService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBusPasses() {
        try {
            List<BusPass> busPasses = busPassService.getAllBusPasses();
            return ResponseEntity.ok(busPasses);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving bus passes: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBusPassById(@PathVariable Long id) {
        try {
            BusPass busPass = busPassService.getBusPassById(id);
            return ResponseEntity.ok(busPass);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving bus pass: " + e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getBusPassesByStatus(@PathVariable String status) {
        try {
            List<BusPass> busPasses = busPassService.getBusPassesByStatus(status);
            return ResponseEntity.ok(busPasses);
        } catch (InvalidRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving bus passes by status: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createBusPass(@RequestBody BusPassRequest busPassRequest) {
        try {
            // Validate request
            if (busPassRequest == null) {
                throw new InvalidRequestException("Bus pass request cannot be null");
            }
            if (busPassRequest.getUserId() == null) {
                throw new InvalidRequestException("User ID must be specified");
            }

            BusPass createdPass = busPassService.createBusPass(busPassRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPass);
        } catch (InvalidRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create bus pass: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBusPass(@PathVariable Long id, @RequestBody BusPassRequest busPassRequest) {
        try {
            // Validate request
            if (busPassRequest == null) {
                throw new InvalidRequestException("Bus pass details cannot be null");
            }
            if (busPassRequest.getUserId() == null) {
                throw new InvalidRequestException("User ID must be specified");
            }

            BusPass updatedPass = busPassService.updateBusPass(id, busPassRequest);
            return ResponseEntity.ok(updatedPass);
        } catch (InvalidRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update bus pass: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBusPass(@PathVariable Long id) {
        try {
            busPassService.deleteBusPass(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete bus pass: " + e.getMessage());
        }
    }
}