package com.mbarek0.web.huntersleague.web.exception.competition;

public class OnlyOneCompetitionCanBeScheduledPerWeekException extends RuntimeException {
    public OnlyOneCompetitionCanBeScheduledPerWeekException(String message) {
        super(message);
    }
}
