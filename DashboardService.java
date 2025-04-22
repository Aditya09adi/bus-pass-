package com.buspass.service;

import com.buspass.repository.BusPassRepository;
import com.buspass.repository.RouteRepository;
import com.buspass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    private BusPassRepository busPassRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RouteRepository routeRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            // Active passes count
            long activePasses = busPassRepository.countByStatus("Active");
            stats.put("activePasses", activePasses);
            
            // Total users count
            long totalUsers = userRepository.count();
            stats.put("totalUsers", totalUsers);
            
            // Total routes count
            long totalRoutes = routeRepository.count();
            stats.put("totalRoutes", totalRoutes);
            
            // Revenue for current month
            LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
            LocalDate endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
            Double revenue = busPassRepository.calculateRevenueBetweenDates(startOfMonth, endOfMonth);
            stats.put("revenue", revenue != null ? revenue : 0.0);
            
        } catch (Exception e) {
            // Provide default values in case of error
            stats.put("activePasses", 0);
            stats.put("totalUsers", 0);
            stats.put("totalRoutes", 0);
            stats.put("revenue", 0.0);
            throw new RuntimeException("Failed to load dashboard statistics", e);
        }
        
        return stats;
    }
}