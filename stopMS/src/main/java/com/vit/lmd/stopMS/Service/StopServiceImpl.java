package com.vit.lmd.stopMS.Service;

import com.vit.lmd.stopMS.Entity.Stop;
import com.vit.lmd.stopMS.Exception.ResourceNotFoundException;
import com.vit.lmd.stopMS.Repository.StopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StopServiceImpl implements StopService {

    @Autowired
    private StopRepository stopRepository;

    @Override
    public List<Stop> findAllStops() {
        List<Stop> stops = stopRepository.findAll();
        return stops;
    }
    private static final Logger logger = LoggerFactory.getLogger(StopServiceImpl.class);

    @Override
    public List<Stop> findByTripId(int tripId) throws ResourceNotFoundException {
        logger.info("Fetching stops for tripId: {}", tripId);
       Optional<List<Stop>> stops = stopRepository.findByTripId(tripId);
       if(!stops.isPresent() || stops.get().isEmpty()){
           logger.error("No stops found for tripId: {}", tripId);
           throw new ResourceNotFoundException("Stops not found for tripId: " + tripId);
       }
       logger.info("Found {} stops for tripId: {}", stops.get().size(), tripId);
       return stops.get();
    }

    @Override
    public Stop findByStopId(int stopId) throws ResourceNotFoundException {
        logger.info("Fetching stop for stopId: {}", stopId);
        Stop stop = stopRepository.findById(stopId).orElseThrow(()-> new ResourceNotFoundException("Stop not found for stopId: " + stopId));
        logger.info("Found stop: {}", stop);
        return stop;
    }

    @Override
    public Stop addStops(Stop stop) {
        logger.info("Adding new stop: {}", stop);
        Stop savedStop = stopRepository.save(stop);
        logger.info("Stop added successfully with id: {}", savedStop.getStopId());
        return savedStop;
    }

    @Override
    public Stop updateStops(int stopId, Stop stop) throws ResourceNotFoundException {
       logger.info("Updating stop with stopId: {}", stopId);
        Stop existingStop = stopRepository.findById(stopId).orElseThrow(()-> new ResourceNotFoundException("Stop not found for stopId: " + stopId));
       existingStop.setAddress(stop.getAddress());
       existingStop.setItems(stop.getItems());
       existingStop.setStopType(stop.getStopType());
       existingStop.setTripId(stop.getTripId());
       Stop updatedStop = stopRepository.save(existingStop);
       logger.info("Stop updated successfully for stopId: {}", stopId);
       return updatedStop;
    }

    @Override
    public void deleteByStopId(int stopId) throws ResourceNotFoundException {
        logger.info("Deleting stop with stopId: {}", stopId);
        Stop existingStop = stopRepository.findById(stopId).orElseThrow(()-> new ResourceNotFoundException("Stop not found for stopId: " + stopId));
        logger.info("Stop found: {}", existingStop);
        stopRepository.delete(existingStop);
    }

    @Override
    public void deleteByTripId(int tripId) throws ResourceNotFoundException {
        logger.info("Deleting stops for tripId: {}", tripId);
        Stop existingStop = stopRepository.findById(tripId).orElseThrow(()-> new ResourceNotFoundException("Stop not found for tripId: " + tripId));
        logger.info("Stop found: {}", existingStop);
        stopRepository.delete(existingStop);
    }

    @Override
    public void deleteAll() {
        //to handle null pointer exception
        logger.info("Deleting all stops");
        List<Stop> existingStop = stopRepository.findAll();
        if(existingStop.isEmpty()){
            logger.error("No stops available to delete");
            throw new RuntimeException("No stops available to delete");
        }
        logger.info("Total stops to be deleted: {}", existingStop.size());
        stopRepository.deleteAll();
    }
}
