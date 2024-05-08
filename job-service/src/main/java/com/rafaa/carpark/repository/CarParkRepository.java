package com.rafaa.carpark.repository;

import com.rafaa.carpark.entity.CarPark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarParkRepository extends JpaRepository<CarPark, UUID> {
    @Query("SELECT c FROM CarPark c WHERE (c.carParkName = :name OR :name IS NULL) " +
            "AND " +
            "(c.timezone = :timezone OR :timezone IS NULL)")
    List<CarPark> getAllCarParks(String name, String timezone);
}
