package com.vit.lmd.tripMS.Service;

import com.vit.lmd.tripMS.Entity.*;
import com.vit.lmd.tripMS.Exception.ResourceNotFoundException;
import com.vit.lmd.tripMS.ExternalService.driverMS;
import com.vit.lmd.tripMS.ExternalService.stopMS;
import com.vit.lmd.tripMS.ExternalService.taskMS;
import com.vit.lmd.tripMS.ExternalService.vehicleMS;
import com.vit.lmd.tripMS.Repository.TripRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private driverMS driverMS;

    @Autowired
    private stopMS stopMS;

    @Autowired
    private taskMS taskMS;

    public TripServiceImpl(TripRepository tripRepository,
                           driverMS driverMS) {
        this.tripRepository = tripRepository;
        this.driverMS = driverMS;
    }

    private static final Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);


    /*@Override
    public Trip getTripById(int tripId) throws ResourceNotFoundException {
        Trip trip = tripRepository.findById(tripId).
                orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));
        //http://localhost:7100/stops/105
        ArrayList forObject = restTemplate.getForObject("http://localhost:7100/stops/" +tripId, ArrayList.class);
        logger.info("Stops fetched from StopMS: {}", forObject);
        trip.setStops(forObject);
        return trip;
    }*/

    @Override
    public Trip getTripById(int tripId) throws ResourceNotFoundException {
        //get trip by id with vehicle and driver and stop details using feign client
        logger.info("Fetching trip with id: {}", tripId);
        Trip trip = tripRepository.findById(tripId).
                orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));
        logger.info("Trip fetched: {}", trip);
        List<StopDTO> stops = stopMS.getStopsByTripId(tripId);
        logger.info("Stops fetched from StopMS: {}", stops);
        if(stops==null){
            trip.setStops(new ArrayList<>());
            logger.info("No stops found for trip id: {}. Returning trip with empty stops list.", tripId);
            return trip;
        }
        trip.setStops(stops);
        logger.info("Returning trip with stops: {}", trip);
        return trip;

       /* Trip trip = tripRepository.findById(tripId).
                orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));
        //http://localhost:7100/stops/105
        ArrayList forObject = restTemplate.getForObject("http://localhost:7100/stops/" +tripId, ArrayList.class);
        logger.info("Stops fetched from StopMS: {}", forObject);
        trip.setStops(forObject);
        return trip;*/
    }

   /* @Override
    public List<Trip> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();
       Enrich each trip with its stops
        for (Trip trip : trips) {
            ArrayList forObject = restTemplate.getForObject("http://localhost:7100/stops/" +trip.getTripId(), ArrayList.class);
            logger.info("Stops fetched from StopMS for tripId {}: {}", trip.getTripId(), forObject);
            trip.setStops(forObject);
        }
        return trips;
    }*/

@Override
public List<Trip> getAllTrips() throws ResourceNotFoundException {
    logger.info("Fetching all trips");
    List<Trip> trips = tripRepository.findAll();
       //get each trip with its stops using feign client
    //each trip can have multiple stops or list of stops
    for (Trip trip : trips) {
        try {
            List<StopDTO> stops = stopMS.getStopsByTripId(trip.getTripId());
            trip.setStops(stops);
        } catch (FeignException.NotFound e) {
            // No stops for this trip → set empty list
            logger.warn("No stops found for tripId {}. Setting empty list.", trip.getTripId());
            trip.setStops(new ArrayList<>());
        } catch (Exception e) {
            logger.error("Error fetching stops for tripId {}: {}", trip.getTripId(), e.getMessage());
            trip.setStops(new ArrayList<>());
        }
    }
    /*for (Trip trip : trips) {
        List<StopDTO> stops = stopMS.getStopsByTripId(trip.getTripId());
        logger.info("Stops fetched from StopMS for tripId {}: {}", trip.getTripId(), stops);
        if(stops==null){
            trip.setStops(new ArrayList<>());
            continue;
        }
        trip.setStops(stops);
    }*/
    logger.info("Returning all trips with stops");
return trips;
}



    @Override
    public List<Trip> getTripsByDriverId(int driverId) {
    logger.info("Fetching trips for driverId: {}", driverId);
    //get trips by driver id with vehicle and driver and stop details using feign client
    List<Trip> trips = tripRepository.findAll().stream().
            filter(trip -> trip.getDriverId() == driverId)
            .toList();
    for (Trip trip : trips) {
        try {
            List<StopDTO> stops = stopMS.getStopsByTripId(trip.getTripId());
            trip.setStops(stops);
        } catch (FeignException.NotFound e) {
            // No stops for this trip → set empty list
            logger.warn("No stops found for tripId {}. Setting empty list.", trip.getTripId());
            trip.setStops(new ArrayList<>());
        } catch (Exception e) {
            logger.error("Error fetching stops for tripId {}: {}", trip.getTripId(), e.getMessage());
            trip.setStops(new ArrayList<>());
        }
    }
    logger.info("Returning trips for driverId: {}: {}", driverId, trips);
    return trips;



        /*List<Trip> trips = tripRepository.findAll().stream().
                filter(trip -> trip.getDriverId() == driverId)
                .toList();
        return trips;*/
    }

    @Override
    public List<Trip> getTripsByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
    logger.info("Fetching trips between {} and {}", startTime, endTime);
        List<Trip> trips = tripRepository.findAll().stream()
                .filter(trip -> !trip.getStartTime().isBefore(startTime) && !trip.getEndTime().isAfter(endTime))
                .toList();
        StopDTO stopDTO;
        for (Trip trip : trips) {
            try {
                List<StopDTO> stops = stopMS.getStopsByTripId(trip.getTripId());
                trip.setStops(stops);
            } catch (FeignException.NotFound e) {
                // No stops for this trip → set empty list
                logger.warn("No stops found for tripId {}. Setting empty list.", trip.getTripId());
                trip.setStops(new ArrayList<>());
            } catch (Exception e) {
                logger.error("Error fetching stops for tripId {}: {}", trip.getTripId(), e.getMessage());
                trip.setStops(new ArrayList<>());
            }

        }
        logger.info("Returning trips between {} and {}: {}", startTime, endTime, trips);
        return trips;
    }

    @Override
    public List<Trip> getTripsByPickupAddress(String pickupAddress) {
    //get trip by start time and end time with stops using feign client
        logger.info("Fetching trips with pickup address: {}", pickupAddress);
        List<Trip> trips = tripRepository.findAll().stream()
                .filter(trip -> trip.getPickupAddress().equalsIgnoreCase(pickupAddress))
                .toList();
        for (Trip trip : trips) {
            try {
                List<StopDTO> stops = stopMS.getStopsByTripId(trip.getTripId());
                trip.setStops(stops);
            } catch (FeignException.NotFound e) {
                // No stops for this trip → set empty list
                logger.warn("No stops found for tripId {}. Setting empty list.", trip.getTripId());
                trip.setStops(new ArrayList<>());
            } catch (Exception e) {
                logger.error("Error fetching stops for tripId {}: {}", trip.getTripId(), e.getMessage());
                trip.setStops(new ArrayList<>());
            }
        }
        logger.info("Returning trips with pickup address {}: {}", pickupAddress, trips);
        return trips;

       /* List<Trip> trips = tripRepository.findAll().stream()
                .filter(trip -> trip.getPickupAddress().equalsIgnoreCase(pickupAddress))
                .toList();
        return trips;*/
    }

    @Override
    public Trip createTrip(TripDTO tripDTO) throws ResourceNotFoundException {
        return null;
    }

   /* @Override
    public Trip createTrip(TripDTO tripDTO) {
       //persist trip details  without driver/vehicle
        Trip trip=new Trip();
        trip.setStartTime(tripDTO.getStartTime());
        trip.setEndTime(tripDTO.getEndTime());
        trip.setPickupAddress(tripDTO.getPickupAddress());
        Trip savedTrip = tripRepository.save(trip);
        logger.info("Trip saved with id: {}", savedTrip.getTripId());

        //call driverMS to asign driver
        DriverDTO driver;
        try {
            driver=restTemplate.postForObject("http://localhost:7000/drivers/assign-available",null, DriverDTO.class);

                //driver=restTemplate.postForObject("http://localhost:7000/drivers/assign-available", DriverDTO.class);
        }
        catch (Exception e){
            logger.error("Error while assigning driver: {}", e.getMessage());
            throw new RuntimeException("Error while assigning driver: " + e.getMessage());
        }
        if(driver==null){
            logger.error("No available driver found");
            throw new RuntimeException("No available driver found");
        }

        //call vehicleMS to get vehicle for driver
        VehicleDTO vehicle;
        try {
            vehicle=restTemplate.getForObject("http://localhost:7400/vehicles/driver/"+driver.getDriverId() , VehicleDTO.class);
        }
        catch (Exception e){
            logger.error("Error while fetching vehicle for driver id {}: {}", driver.getDriverId(), e.getMessage());
            throw new RuntimeException("Error while fetching vehicle for driver id " + driver.getDriverId() + ": " + e.getMessage());
        }
        if(vehicle==null){
            logger.error("No vehicle found for driver id: {}", driver.getDriverId());
            throw new RuntimeException("No vehicle found for driver id: " + driver.getDriverId());
        }

        //update and save trip with driver and vehicle details
        savedTrip.setDriverId(driver.getDriverId());
        savedTrip.setVehicleId(vehicle.getVehicleId());
        tripRepository.save(savedTrip);
        logger.info("Trip updated with driver id: {} and vehicle id: {}", driver.getDriverId(), vehicle.getVehicleId());
        return savedTrip;
    }*/

    //create trip with driverMS and vehicleMS using feign client
 /*  @Override
   public Trip createTrip(TripDTO tripDTO) throws ResourceNotFoundException {
// trip will call driver to get available driver and vehicle for that driver
       Trip trip=new Trip();
       trip.setStartTime(tripDTO.getStartTime());
       trip.setEndTime(tripDTO.getEndTime());
       trip.setPickupAddress(tripDTO.getPickupAddress());
      // Trip savedTrip = tripRepository.save(trip);
       //logger.info("Trip saved with id: {}", savedTrip.getTripId());
       DriverDTO driver;
        // VehicleDTO vehicle;
         try {
             driver = driverMS.findAvailableDriver();
             if (driver == null) {
                 logger.error("No available driver found");
                 throw new ResourceNotFoundException("No available driver found");
             }
             // VehicleDTO vehicle=vehicleMS.getVehicleByDriverId(driver.getDriverId());
         }
            catch (Exception e){
                logger.error("Error while assigning driver: {}", e.getMessage());
                throw new ResourceNotFoundException("Error while assigning driver: " + e.getMessage());
            }
            driver.setDriverStatus("BUSY");
            trip.setDriverId(driver.getDriverId());
            trip.setVehicleId(driver.getVehicleId());
            Trip savedTrip = tripRepository.save(trip);
            logger.info("Trip saved with id: {}", savedTrip.getTripId());
            return savedTrip;*/







       //persist trip details  with driver/vehicle using feign client
      /* Trip trip=new Trip();
       trip.setStartTime(tripDTO.getStartTime());
       trip.setEndTime(tripDTO.getEndTime());
       trip.setPickupAddress(tripDTO.getPickupAddress());
      // Trip savedTrip = tripRepository.save(trip);
      // logger.info("Trip saved with id: {}", savedTrip.getTripId());
         //call driverMS to asign driver
         DriverDTO driver;
            try {
                driver=driverMS.assignAvailableDriver();
            }
            catch (Exception e){
                logger.error("Error while assigning driver: {}", e.getMessage());
                throw new ResourceNotFoundException("Error while assigning driver: " + e.getMessage());
            }
            if(driver==null){
                logger.error("No available driver found");
                throw new ResourceNotFoundException("No available driver found");
            }
         //call vehicleMS to getvehicle for driver
            VehicleDTO vehicle;
                try {
                    vehicle=vehicleMS.getVehicleByDriverId(driver.getDriverId());
                }
                catch (Exception e){
                    logger.error("Error while fetching vehicle for driver id {}: {}", driver.getDriverId(), e.getMessage());
                    throw new ResourceNotFoundException("Error while fetching vehicle for driver id " + driver.getDriverId() + ": " + e.getMessage());
                }
                if(vehicle==null){
                    logger.error("No vehicle found for driver id: {}", driver.getDriverId());
                    throw new ResourceNotFoundException("No vehicle found for driver id: " + driver.getDriverId());
                }
         //update and save trip with driver and vehicle details
       Trip savedTrip = tripRepository.save(trip);
       logger.info("Trip saved with id: {}", savedTrip.getTripId());
            savedTrip.setDriverId(driver.getDriverId());
            savedTrip.setVehicleId(vehicle.getVehicleId());
            tripRepository.save(savedTrip);
            logger.info("Trip updated with driver id: {} and vehicle id: {}", driver.getDriverId(), vehicle.getVehicleId());
            return savedTrip;*/









       /*//persist trip details  without driver/vehicle
       Trip trip=new Trip();
       trip.setStartTime(tripDTO.getStartTime());
       trip.setEndTime(tripDTO.getEndTime());
       trip.setPickupAddress(tripDTO.getPickupAddress());
       Trip savedTrip = tripRepository.save(trip);
       logger.info("Trip saved with id: {}", savedTrip.getTripId());

       //call driverMS to asign driver
       DriverDTO driver;
       try {
           driver=restTemplate.postForObject("http://localhost:7000/drivers/assign-available",null, DriverDTO.class);

           //driver=restTemplate.postForObject("http://localhost:7000/drivers/assign-available", DriverDTO.class);
       }
       catch (Exception e){
           logger.error("Error while assigning driver: {}", e.getMessage());
           throw new RuntimeException("Error while assigning driver: " + e.getMessage());
       }
       if(driver==null){
           logger.error("No available driver found");
           throw new RuntimeException("No available driver found");
       }

       //call vehicleMS to get vehicle for driver
       VehicleDTO vehicle;
       try {
           vehicle=restTemplate.getForObject("http://localhost:7400/vehicles/driver/"+driver.getDriverId() , VehicleDTO.class);
       }
       catch (Exception e){
           logger.error("Error while fetching vehicle for driver id {}: {}", driver.getDriverId(), e.getMessage());
           throw new RuntimeException("Error while fetching vehicle for driver id " + driver.getDriverId() + ": " + e.getMessage());
       }
       if(vehicle==null){
           logger.error("No vehicle found for driver id: {}", driver.getDriverId());
           throw new RuntimeException("No vehicle found for driver id: " + driver.getDriverId());
       }

       //update and save trip with driver and vehicle details
       savedTrip.setDriverId(driver.getDriverId());
       savedTrip.setVehicleId(vehicle.getVehicleId());
       tripRepository.save(savedTrip);
       logger.info("Trip updated with driver id: {} and vehicle id: {}", driver.getDriverId(), vehicle.getVehicleId());
       return savedTrip;*/
//   }


   /* @Override
    public Trip createTrip(Trip trip) {
        Trip savedTrip = tripRepository.save(trip);


        return savedTrip;
    }*/



    @Override
    public Trip updateTrip(int tripId, Trip trip) throws ResourceNotFoundException {
        logger.info("Updating trip with id: {}", tripId);
       Trip existingTrip = tripRepository.findById(tripId).orElseThrow(() ->
               new ResourceNotFoundException("Trip not found with id: " + tripId));
       logger.info("Existing trip found: {}", existingTrip);
         existingTrip.setStartTime(trip.getStartTime());
            existingTrip.setEndTime(trip.getEndTime());
            existingTrip.setPickupAddress(trip.getPickupAddress());
            existingTrip.setStops(trip.getStops());
            existingTrip.setDriverId(trip.getDriverId());
            Trip updatedTrip = tripRepository.save(existingTrip);
            logger.info("Trip updated: {}", updatedTrip);
            return updatedTrip;
    }

    @Override
    public void deleteTrip(int tripId) throws ResourceNotFoundException {
        logger.info("Deleting trip with id: {}", tripId);
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));
        logger.info("Trip found: {}", trip);
        tripRepository.delete(trip);
    }


    @Transactional
    public Trip createTrip1(Trip trip) throws ResourceNotFoundException {
        //persist trip details
        Trip savedTrip=tripRepository.save(trip);
        logger.info("Trip created with id: {}", savedTrip.getTripId());

        //call driverms to assign driver for the trip and internally driverms will call vehiclems to get vehicle for that driver
        DriverDTO driverDTO;
        try {
            driverDTO=driverMS.assignAvailableDriver(savedTrip.getTripId());
            //update trip
            savedTrip.setDriverId(driverDTO.getDriverId());
            savedTrip.setVehicleId(driverDTO.getVehicleId());
            tripRepository.save(savedTrip);
            logger.info("Trip updated with driver id: {} and vehicle id: {}", driverDTO.getDriverId(), driverDTO.getVehicleId());
        }
        catch (Exception e){
            logger.error("Error while assigning driver for trip id {}: {}", savedTrip.getTripId(), e.getMessage());
            throw new ResourceNotFoundException("Error while assigning driver for trip id " + savedTrip.getTripId() + ": " + e.getMessage());
        }
        //create tasks for the trip by calling taskMS
        TaskDTO taskDTO=new TaskDTO();
        taskDTO.setTripId(savedTrip.getTripId());
        taskDTO.setDriverId(savedTrip.getDriverId());
       // taskDTO.setDescription(savedTrip.getPickupAddress());
        taskDTO.setTaskStatus("IN_PROGRESS");
        try {
            taskMS.createTask(taskDTO);
            logger.info("Task created for trip id: {}", savedTrip.getTripId());
        }
        catch (Exception e){
            logger.error("Error while creating task for trip id {}: {}", savedTrip.getTripId(), e.getMessage());
            throw new ResourceNotFoundException("Error while creating task for trip id " + savedTrip.getTripId() + ": " + e.getMessage());
        }
        logger.info("Trip creation process completed for trip id: {}", savedTrip.getTripId());
        return savedTrip;
    }
}
