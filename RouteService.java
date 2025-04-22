package com.buspass.service;

import com.buspass.exception.ResourceNotFoundException;
import com.buspass.model.Route;
import com.buspass.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + id));
    }

    public Route createRoute(Route route) {
        // Generate route ID
        route.setRouteId("R" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        route.setStatus("Active");
        return routeRepository.save(route);
    }

    public Route updateRoute(Long id, Route routeDetails) {
        Route route = getRouteById(id);
        route.setRouteName(routeDetails.getRouteName());
        route.setStartPoint(routeDetails.getStartPoint());
        route.setEndPoint(routeDetails.getEndPoint());
        route.setZone(routeDetails.getZone());
        route.setDistance(routeDetails.getDistance());
        route.setStatus(routeDetails.getStatus());
        return routeRepository.save(route);
    }

    public void deleteRoute(Long id) {
        Route route = getRouteById(id);
        routeRepository.delete(route);
    }

    public List<Route> getRoutesByZone(String zone) {
        return routeRepository.findByZone(zone);
    }
}