package com.mr486.safetynet.domain.domain_firestation.expetion;

public class FireStationAlreadyExistsException extends RuntimeException{
    public FireStationAlreadyExistsException(String message) {
        super(message);
    }

}
