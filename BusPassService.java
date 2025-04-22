package com.buspass.service;

import com.buspass.dto.BusPassRequest;
import com.buspass.exception.ResourceNotFoundException;
import com.buspass.exception.InvalidRequestException;
import com.buspass.model.BusPass;
import com.buspass.model.User;
import com.buspass.repository.BusPassRepository;
import com.buspass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BusPassService {
    private final BusPassRepository busPassRepository;
    private final UserRepository userRepository;

    @Autowired
    public BusPassService(BusPassRepository busPassRepository, UserRepository userRepository) {
        this.busPassRepository = busPassRepository;
        this.userRepository = userRepository;
    }

    public List<BusPass> getAllBusPasses() {
        List<BusPass> passes = busPassRepository.findAllWithUser();
        if (passes.isEmpty()) {
            throw new ResourceNotFoundException("No bus passes found");
        }
        return passes;
    }

    public BusPass getBusPassById(Long id) {
        return busPassRepository.findByIdWithUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus pass not found with id: " + id));
    }

    @Transactional
    public BusPass createBusPass(BusPassRequest request) {
        validateBusPassRequest(request);
        
        User user = getUserById(request.getUserId());
        BusPass busPass = new BusPass();
        
        // Set required fields
        busPass.setPassId(generatePassId());
        busPass.setUser(user);
        busPass.setPassType(request.getPassType());
        busPass.setStatus("Active");
        busPass.setPrice(calculatePassPrice(request.getPassType()));
        busPass.setRouteAccess(request.getRouteAccess());
        
        // Handle dates
        LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : LocalDate.now();
        busPass.setStartDate(startDate);
        
        LocalDate endDate = request.getEndDate() != null ? 
            request.getEndDate() : calculateDefaultEndDate(startDate, request.getPassType());
        
        validateDates(startDate, endDate);
        busPass.setEndDate(endDate);

        return busPassRepository.save(busPass);
    }

    @Transactional
    public BusPass updateBusPass(Long id, BusPassRequest request) {
        validateBusPassRequest(request);
        
        BusPass existingPass = getBusPassById(id);
        
        // Update pass type and price if changed
        if (!request.getPassType().equals(existingPass.getPassType())) {
            existingPass.setPassType(request.getPassType());
            existingPass.setPrice(calculatePassPrice(request.getPassType()));
        }
        
        // Update dates
        LocalDate newStartDate = request.getStartDate() != null ? 
            request.getStartDate() : existingPass.getStartDate();
        
        LocalDate newEndDate = request.getEndDate() != null ? 
            request.getEndDate() : calculateDefaultEndDate(newStartDate, request.getPassType());
        
        validateDates(newStartDate, newEndDate);
        existingPass.setStartDate(newStartDate);
        existingPass.setEndDate(newEndDate);
        
        // Update other fields
        if (request.getRouteAccess() != null) {
            existingPass.setRouteAccess(request.getRouteAccess());
        }
        
        if (request.getStatus() != null) {
            existingPass.setStatus(request.getStatus());
        }

        return busPassRepository.save(existingPass);
    }

    @Transactional
    public void deleteBusPass(Long id) {
        if (!busPassRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bus pass not found with id: " + id);
        }
        busPassRepository.deleteById(id);
    }

    public List<BusPass> getBusPassesByStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new InvalidRequestException("Status must be specified");
        }
        List<BusPass> passes = busPassRepository.findByStatusWithUser(status);
        if (passes.isEmpty()) {
            throw new ResourceNotFoundException("No bus passes found with status: " + status);
        }
        return passes;
    }

    // Helper methods
    private void validateBusPassRequest(BusPassRequest request) {
        if (request == null) {
            throw new InvalidRequestException("Bus pass request cannot be null");
        }
        if (request.getUserId() == null) {
            throw new InvalidRequestException("User ID must be specified");
        }
        if (request.getPassType() == null || request.getPassType().isEmpty()) {
            throw new InvalidRequestException("Pass type must be specified");
        }
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new InvalidRequestException("End date cannot be before start date");
        }
    }

    private String generatePassId() {
        return "BP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private double calculatePassPrice(String passType) {
        return switch (passType.toLowerCase()) {
            case "daily" -> 5.0;
            case "weekly" -> 25.0;
            case "monthly" -> 80.0;
            case "annual" -> 800.0;
            case "student" -> 50.0;
            case "senior" -> 40.0;
            default -> throw new InvalidRequestException("Invalid pass type: " + passType);
        };
    }

    private LocalDate calculateDefaultEndDate(LocalDate startDate, String passType) {
        return switch (passType.toLowerCase()) {
            case "daily" -> startDate.plusDays(1);
            case "weekly" -> startDate.plusWeeks(1);
            case "monthly" -> startDate.plusMonths(1);
            case "annual" -> startDate.plusYears(1);
            case "student" -> startDate.plusMonths(6);
            case "senior" -> startDate.plusYears(1);
            default -> throw new InvalidRequestException("Invalid pass type for date calculation: " + passType);
        };
    }
}