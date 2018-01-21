package com.teslaboat.model;

public enum ReservationStatus {
    // when reservation is created in the system but not paid/confirmed
    CREATED,
    // when reservation is paid
    CONFIRMED,
    // expired
    EXPIRED,
    //cancelled
    CANCELLED
}

