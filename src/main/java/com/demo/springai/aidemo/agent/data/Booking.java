package com.demo.springai.aidemo.agent.data;

import java.time.LocalDate;

public class Booking {

	private String bookingNumber;

	private LocalDate date;

	private LocalDate bookingTo;

	private User user;

	private BookingStatus bookingStatus;

	private String bookingClass;

	public Booking(String bookingNumber, LocalDate date, User user, BookingStatus bookingStatus, String bookingClass) {
		this.bookingNumber = bookingNumber;
		this.date = date;
		this.user = user;
		this.bookingStatus = bookingStatus;
		this.bookingClass = bookingClass;
	}

	public String getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getBookingTo() {
		return bookingTo;
	}

	public void setBookingTo(LocalDate bookingTo) {
		this.bookingTo = bookingTo;
	}

	public User getCustomer() {
		return user;
	}

	public void setCustomer(User user) {
		this.user = user;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}


	public String getBookingClass() {
		return bookingClass;
	}

	public void setBookingClass(String bookingClass) {
		this.bookingClass = bookingClass;
	}

}