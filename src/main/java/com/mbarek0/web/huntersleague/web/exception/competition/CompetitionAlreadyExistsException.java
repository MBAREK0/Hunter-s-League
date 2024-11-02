package com.mbarek0.web.huntersleague.web.exception.competition;

public class CompetitionAlreadyExistsException extends RuntimeException {
    public CompetitionAlreadyExistsException(String competitionAlreadyExists) {
        super(competitionAlreadyExists);
    }
}
