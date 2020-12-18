package com.example.springboot.business.service;

import com.example.springboot.business.domain.RoomReservation;
import com.example.springboot.data.entity.Guest;
import com.example.springboot.data.entity.Reservation;
import com.example.springboot.data.entity.Room;
import com.example.springboot.data.repository.GuestRepository;
import com.example.springboot.data.repository.ReservationRepository;
import com.example.springboot.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository,
                              GuestRepository guestRepository,
                              ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date resDate){
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach((room) -> {
            System.out.println(room.getRoomName());
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomName(room.getRoomName());
            roomReservation.setRoomNumber(room.getRoomName());
            roomReservation.setRoomId(room.getRoomId());
            roomReservationMap.put(roomReservation.getRoomId(), roomReservation);
        });
        Iterable<Reservation> reservations =
                this.reservationRepository.findReservationByReservationDate(new java.sql.Date(resDate.getTime()));
        reservations.forEach(
                (reservation)->{
                    RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
                    Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
                    roomReservation.setFirstName(guest.getFirstName());
                    roomReservation.setLastName(guest.getLastName());
                    System.out.println("RR::" + roomReservation.getRoomName());
                });
        List<RoomReservation> roomReservations = new ArrayList<>(roomReservationMap.values());
        return roomReservations;
    }
}
