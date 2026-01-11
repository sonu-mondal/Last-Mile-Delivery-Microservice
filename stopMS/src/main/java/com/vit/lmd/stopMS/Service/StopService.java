package com.vit.lmd.stopMS.Service;

import com.vit.lmd.stopMS.Entity.Stop;
import com.vit.lmd.stopMS.Exception.ResourceNotFoundException;

import java.util.List;

public interface StopService {

    //get all stops
    public List<Stop> findAllStops();

    //get stops by tripId
    public List<Stop> findByTripId(int tripId) throws ResourceNotFoundException;

    //get stop by stopId
    public Stop findByStopId(int stopId) throws ResourceNotFoundException;

    //add stop
    public Stop addStops(Stop stop);

    //update stop
    public Stop updateStops(int stopId, Stop stop) throws ResourceNotFoundException;

    //delete stop
    public void deleteByStopId(int stopId) throws ResourceNotFoundException;

    //delete stops by tripId
    public void deleteByTripId(int tripId) throws ResourceNotFoundException;

    //delete all stops
    public void deleteAll();
}
