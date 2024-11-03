package com.demo.springai.aidemo.agent.data;

import com.demo.springai.aidemo.agent.MeetingRoomBookingService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.NestedExceptionUtils;

import java.time.LocalDate;
import java.util.function.Function;

@Configuration
public class BookingTools {

    private static final Logger logger = LoggerFactory.getLogger(BookingTools.class);

    @Autowired
    private MeetingRoomBookingService meetingRoomBookingService;

    public record BookingDetailsRequest(String bookingNumber, String name) {
    }

    public record ChangeBookingDatesRequest(String bookingNumber, String name, String date) {
    }

    public record CancelBookingRequest(String bookingNumber, String name) {
    }

    @JsonInclude(Include.NON_NULL)
    public record BookingDetails(String bookingNumber, String name, LocalDate date, BookingStatus bookingStatus,
                                 String bookingClass) {
    }

    @Bean
    @Description("获取机票预定详细信息")
    public Function<BookingDetailsRequest, BookingDetails> getBookingDetails() {
        return request -> {
            try {
                return meetingRoomBookingService.getReservationDetails(request.bookingNumber(), request.name());
            } catch (Exception e) {
                logger.warn("Booking details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new BookingDetails(request.bookingNumber(), request.name(), null, null, null);
            }
        };
    }

    @Bean
    @Description("修改机票预定日期")
    public Function<ChangeBookingDatesRequest, String> changeBooking() {
        return request -> {
            meetingRoomBookingService.changeReservation(request.bookingNumber(), request.name(), request.date());
            return "";
        };
    }

    @Bean
    @Description("取消机票预定")
    public Function<CancelBookingRequest, String> cancelBooking() {
        return request -> {
            meetingRoomBookingService.cancelReservation(request.bookingNumber(), request.name());
            return "";
        };
    }

}
