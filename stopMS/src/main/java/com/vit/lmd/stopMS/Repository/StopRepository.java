package com.vit.lmd.stopMS.Repository;

import com.vit.lmd.stopMS.Entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StopRepository extends JpaRepository<Stop, Integer> {

    //find stops by tripId
    @Query("SELECT s FROM Stop s WHERE s.tripId = :tripId")
    public Optional<List<Stop>> findByTripId(int tripId);

}
