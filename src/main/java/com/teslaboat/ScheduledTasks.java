package com.teslaboat;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.teslaboat.services.ReservationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    ReservationManager reservationManager;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void setExpiredReservations() {
        System.out.println("setExpiredReservations");
        reservationManager.markExpired();
    }
}