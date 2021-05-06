package com.ds.backend.serverbackend.controllers;

import com.ds.backend.serverbackend.model.Driver;
import com.ds.backend.serverbackend.model.Journey;
import com.ds.backend.serverbackend.repository.JourneyRepository;
import com.ds.backend.serverbackend.request.JourneyBookingRequest;
import com.ds.backend.serverbackend.response.ApiResponse;
import com.ds.backend.serverbackend.utility.JBSUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journey")
public class JourneyController {

    @Autowired
    JourneyRepository journeyRepository;

    @PostMapping("/getJourneys")
    public ResponseEntity<List<Journey>> getJourneys(@RequestBody Driver driver){
        try {
            if(driver.getUsername()!=null){
                String username = driver.getUsername();
                List<Journey> allJourneys = journeyRepository.findByUsername(username)
                        .stream()
                        .filter(j -> j.getStatus().equals(JBSUtility.JOURNEY_BOOKED_STATUS))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(allJourneys, HttpStatus.OK);
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            return new ResponseEntity(JBSUtility.JBS_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bookJourney")
    public ResponseEntity<ApiResponse> createJourney(@RequestBody JourneyBookingRequest bookingRequest) {
        try{
            if(!isSlotAvailable(bookingRequest)){
                return ResponseEntity.ok(new ApiResponse(2, JBSUtility.JOURNEY_BOOK_FAILURE_SLOT_UNAVLBL));
            }
            List<Journey> allJourneysForUser = journeyRepository.findByUsername(bookingRequest.getUsername());
            if(allJourneysForUser.isEmpty()){
                Journey newJourney = journeyRepository.save(new Journey(bookingRequest.getUsername(), bookingRequest.getStarting_point(), bookingRequest.getEnding_point(), bookingRequest.getJourney_date_time(), JBSUtility.JOURNEY_BOOKED_STATUS));
                return ResponseEntity.ok(new ApiResponse(0, JBSUtility.JOURNEY_BOOK_SUCCESS));
            }else {
                return checkSlotAlreadyBookedForDriver(allJourneysForUser, bookingRequest);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(-1, JBSUtility.JBS_SERVER_ERROR));
        }
    }

    private Boolean isSlotAvailable(JourneyBookingRequest bookingRequest){

        String startPoint = bookingRequest.getStarting_point();
        String endPoint = bookingRequest.getEnding_point();

        List<Journey> startLocJourneys = journeyRepository.findByStartingPoint(startPoint)
                .stream()
                .filter(j -> j.getStatus().equals(JBSUtility.JOURNEY_BOOKED_STATUS))
                .collect(Collectors.toList());;

        int counter = 0;
        for(Journey existingJourney : startLocJourneys){
            if(counter>50){
                return Boolean.FALSE;
            }
            if(existingJourney.getEndingPoint().equalsIgnoreCase(endPoint)
                && existingJourney.getJourney_date_time().equals(bookingRequest.getJourney_date_time())){
                counter++;
            }
        }

        System.out.println("Number of existing bookings on this route for that time - " +counter);
        return Boolean.TRUE;
    }

    private ResponseEntity<ApiResponse> checkSlotAlreadyBookedForDriver(List<Journey> allJourneysForUser, JourneyBookingRequest bookingRequest){
        List<Date> bookedSlots = new ArrayList<>();
        for(Journey bookedJourney : allJourneysForUser){
            if(bookedJourney.getStatus().equalsIgnoreCase(JBSUtility.JOURNEY_BOOKED_STATUS)){
                bookedSlots.add(bookedJourney.getJourney_date_time());
            }
        }
        if(bookedSlots.contains(bookingRequest.getJourney_date_time())){
            return ResponseEntity.ok(new ApiResponse(1, JBSUtility.JOURNEY_BOOK_FAILURE_SLOT_BOOKED));
        }else{
            Journey newJourney = journeyRepository.save(new Journey(bookingRequest.getUsername(), bookingRequest.getStarting_point(), bookingRequest.getEnding_point(), bookingRequest.getJourney_date_time(), JBSUtility.JOURNEY_BOOKED_STATUS));
            return ResponseEntity.ok(new ApiResponse(0, JBSUtility.JOURNEY_BOOK_SUCCESS));
        }
    }

    @PostMapping("/cancelJourney")
    public ResponseEntity<ApiResponse> cancelJourney(@RequestBody Journey journey) {
        try{
            Optional<Journey> journeyData = journeyRepository.findById(journey.getId());
            if(journeyData.isPresent()){
                Journey updatedJourney = journeyData.get();
                updatedJourney.setStatus(JBSUtility.JOURNEY_CANCELLED_STATUS);
                journeyRepository.save(updatedJourney);
                return ResponseEntity.ok(new ApiResponse(0, JBSUtility.JOURNEY_CANCEL_SUCCESS));
            }else{
                return ResponseEntity.ok(new ApiResponse(1, JBSUtility.JOURNEY_CANCEL_FAILURE_NON_EXIST));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(-1, JBSUtility.JBS_SERVER_ERROR));
        }
    }
}
