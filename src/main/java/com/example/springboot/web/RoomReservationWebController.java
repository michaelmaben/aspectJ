package com.example.springboot.web;

import com.example.springboot.business.domain.RoomReservation;
import com.example.springboot.business.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class RoomReservationWebController {
    private final ReservationService reservationService;

    @Autowired
    public RoomReservationWebController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<RoomReservation> getRoomReservations(@RequestParam(name = "date", required = false) String dateString){
        Date reservationDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            reservationDate = formatter.parse(dateString);
            System.out.println("Reservation Date::: " + reservationDate);
        }catch (ParseException e){
            System.out.println("Invalid Date " + e.getMessage() );
        }
        return reservationService.getRoomReservationsForDate(reservationDate);
    }
}
