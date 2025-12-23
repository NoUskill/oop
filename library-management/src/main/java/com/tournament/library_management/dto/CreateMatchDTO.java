package com.tournament.library_management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import java.time.LocalDateTime;

public class CreateMatchDTO {

    @NotNull(message = "ID домашней команды обязателен")
    private Long homeTeamId;

    @NotNull(message = "ID гостевой команды обязателен")
    private Long awayTeamId;

    @NotNull(message = "Дата матча обязательна")
    @Future(message = "Дата матча должна быть в будущем")
    private LocalDateTime matchDate;

    private String venue;

    private Long tournamentId;

    private String referee;


    public CreateMatchDTO() {}


    public Long getHomeTeamId() { return homeTeamId; }
    public void setHomeTeamId(Long homeTeamId) { this.homeTeamId = homeTeamId; }

    public Long getAwayTeamId() { return awayTeamId; }
    public void setAwayTeamId(Long awayTeamId) { this.awayTeamId = awayTeamId; }

    public LocalDateTime getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDateTime matchDate) { this.matchDate = matchDate; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public Long getTournamentId() { return tournamentId; }
    public void setTournamentId(Long tournamentId) { this.tournamentId = tournamentId; }

    public String getReferee() { return referee; }
    public void setReferee(String referee) { this.referee = referee; }
}