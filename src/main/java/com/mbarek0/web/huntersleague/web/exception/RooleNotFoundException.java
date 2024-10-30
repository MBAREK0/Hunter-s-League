package com.mbarek0.web.huntersleague.web.exception;

public class RooleNotFoundException extends RuntimeException {
    public RooleNotFoundException(String roleNotFound) {
        super(roleNotFound);
    }
}
