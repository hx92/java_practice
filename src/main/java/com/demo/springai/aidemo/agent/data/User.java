package com.demo.springai.aidemo.agent.data;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String name;

	private List<Booking> bookings = new ArrayList<>();

	public User() {
	}

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

}