package com.mbarek0.web.huntersleague.web.exception.competition;

public class CompetitionIsNotOpenForRegistrationException extends RuntimeException{
    public CompetitionIsNotOpenForRegistrationException(String message) {
        super(message);
    }
}
