package com.demo.springai.aidemo.agent;


import com.demo.springai.aidemo.agent.data.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MeetingRoomBookingService {

	private final BookingData db;

	public MeetingRoomBookingService() {
		db = new BookingData();

		initDemoData();
	}

	private void initDemoData() {
		List<String> names = List.of("云小宝", "李千问", "张百炼", "王通义", "刘魔搭");
		List<String> meetingRooms = List.of("会议室A", "会议室B", "会议室C", "会议室D", "会议室E");
		Random random = new Random();

		var users = new ArrayList<User>();
		var reservations = new ArrayList<Booking>();

		for (int i = 0; i < 5; i++) {
			String name = names.get(i);
			String room = meetingRooms.get(random.nextInt(meetingRooms.size()));
			User user = new User();
			user.setName(name);

			LocalDate date = LocalDate.now().plusDays(2 * (i + 1));

			Booking reservation = new Booking("10" + (i + 1), date, user, BookingStatus.CONFIRMED, room);
			user.getBookings().add(reservation);

			users.add(user);
			reservations.add(reservation);
		}

		// Reset the database on each start
		db.setCustomers(users);
		db.setBookings(reservations);
	}

	public List<BookingTools.BookingDetails> getBookings() {
		return db.getBookings().stream().map(this::toReservationDetails).toList();
	}

	private Booking findReservation(String reservationNumber, String name) {
		return db.getBookings()
				.stream()
				.filter(r -> r.getBookingNumber().equalsIgnoreCase(reservationNumber))
				.filter(r -> r.getCustomer().getName().equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Booking not found"));
	}

	public BookingTools.BookingDetails getReservationDetails(String reservationNumber, String name) {
		var reservation = findReservation(reservationNumber, name);
		return toReservationDetails(reservation);
	}

	public void changeReservation(String reservationNumber, String name, String room) {
		var reservation = findReservation(reservationNumber, name);
		if (reservation.getDate().isBefore(LocalDate.now().plusDays(1))) {
			throw new IllegalArgumentException("Booking cannot be changed within 24 hours of the start date.");
		}
		reservation.setBookingClass(room);
	}

	public void cancelReservation(String reservationNumber, String name) {
		var reservation = findReservation(reservationNumber, name);
		if (reservation.getDate().isBefore(LocalDate.now().plusDays(2))) {
			throw new IllegalArgumentException("Booking cannot be cancelled within 48 hours of the start date.");
		}
		reservation.setBookingStatus(BookingStatus.CANCELLED);
	}

	private BookingTools.BookingDetails toReservationDetails(Booking reservation) {
		return new BookingTools.BookingDetails(reservation.getBookingNumber(), reservation.getCustomer().getName(), reservation.getDate(),
				reservation.getBookingStatus(), reservation.getBookingClass());
	}
}