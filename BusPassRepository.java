package com.buspass.repository;

import com.buspass.model.BusPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusPassRepository extends JpaRepository<BusPass, Long> {
    Optional<BusPass> findByPassId(String passId);
    
    @Query("SELECT bp FROM BusPass bp JOIN FETCH bp.user WHERE bp.id = :id")
    Optional<BusPass> findByIdWithUser(@Param("id") Long id);
    
    @Query("SELECT bp FROM BusPass bp JOIN FETCH bp.user")
    List<BusPass> findAllWithUser();
    
    @Query("SELECT bp FROM BusPass bp JOIN FETCH bp.user WHERE bp.status = :status")
    List<BusPass> findByStatusWithUser(@Param("status") String status);
    
   // @Query("SELECT SUM(bp.price) FROM BusPass bp WHERE bp.startDate BETWEEN :startDate AND :endDate")
    //Double calculateRevenueBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query("SELECT COUNT(bp) FROM BusPass bp WHERE bp.status = :status")
    long countByStatus(@Param("status") String status);

    @Query("SELECT SUM(bp.price) FROM BusPass bp WHERE bp.startDate BETWEEN :startDate AND :endDate")

    Double calculateRevenueBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}