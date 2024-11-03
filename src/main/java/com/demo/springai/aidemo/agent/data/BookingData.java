package com.demo.springai.aidemo.agent.data;

import java.util.ArrayList;
import java.util.List;

public class BookingData {

	private List<User> users = new ArrayList<>();

	private List<Booking> bookings = new ArrayList<>();

	public List<User> getCustomers() {
		return users;
	}

	public void setCustomers(List<User> users) {
		this.users = users;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

}
