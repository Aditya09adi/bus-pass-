package com.buspass.repository;

import com.buspass.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findByRouteId(String routeId);
    List<Route> findByZone(String zone);
}