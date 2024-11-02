package com.mbarek0.web.huntersleague.web.exception.competition;

public class CompetitionNotFoundException extends RuntimeException {
    public CompetitionNotFoundException(String competitionNotFound) {
        super(competitionNotFound);
    }
}
