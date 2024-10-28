package com.mbarek0.web.huntersleague.exception;

public class RooleNotFoundException extends RuntimeException {
    public RooleNotFoundException(String roleNotFound) {
        super(roleNotFound);
    }
}
