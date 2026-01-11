package com.vit.lmd.tripMS.Service;


import com.vit.lmd.tripMS.Entity.Trip;
import com.vit.lmd.tripMS.Entity.TripDTO;
import com.vit.lmd.tripMS.Exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface TripService {

    //get trip by id
    Trip getTripById(int tripId) throws ResourceNotFoundException;

    //get all trips
    List<Trip> getAllTrips() throws ResourceNotFoundException;

    //get trips by driver id
    List<Trip> getTripsByDriverId(int driverId);

    //get trip by start time and end time
    List<Trip> getTripsByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime);

    //get trips by pickup address
    List<Trip> getTripsByPickupAddress(String pickupAddress);

    //create trip
    Trip createTrip(TripDTO tripDTO) throws ResourceNotFoundException;

    //update trip
    Trip updateTrip(int tripId, Trip trip) throws ResourceNotFoundException;

    //delete trip
    void deleteTrip(int tripId) throws ResourceNotFoundException;

}
