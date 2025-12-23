package com.tournament.library_management.dto;

import java.time.LocalDateTime;

public class MatchDTO {
    private Long id;
    private Long homeTeamId;
    private String homeTeamName;
    private Long awayTeamId;
    private String awayTeamName;
    private LocalDateTime matchDate;
    private String venue;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private String status;
    private String referee;
    private Integer attendance;
    private String score;
    private Long tournamentId;
    private String tournamentName;


    public MatchDTO() {}

    public MatchDTO(Long id, Long homeTeamId, String homeTeamName, Long awayTeamId, String awayTeamName,
                    LocalDateTime matchDate, String status) {
        this.id = id;
        this.homeTeamId = homeTeamId;
        this.homeTeamName = homeTeamName;
        this.awayTeamId = awayTeamId;
        this.awayTeamName = awayTeamName;
        this.matchDate = matchDate;
        this.status = status;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getHomeTeamId() { return homeTeamId; }
    public void setHomeTeamId(Long homeTeamId) { this.homeTeamId = homeTeamId; }

    public String getHomeTeamName() { return homeTeamName; }
    public void setHomeTeamName(String homeTeamName) { this.homeTeamName = homeTeamName; }

    public Long getAwayTeamId() { return awayTeamId; }
    public void setAwayTeamId(Long awayTeamId) { this.awayTeamId = awayTeamId; }

    public String getAwayTeamName() { return awayTeamName; }
    public void setAwayTeamName(String awayTeamName) { this.awayTeamName = awayTeamName; }

    public LocalDateTime getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDateTime matchDate) { this.matchDate = matchDate; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public Integer getHomeTeamScore() { return homeTeamScore; }
    public void setHomeTeamScore(Integer homeTeamScore) { this.homeTeamScore = homeTeamScore; }

    public Integer getAwayTeamScore() { return awayTeamScore; }
    public void setAwayTeamScore(Integer awayTeamScore) { this.awayTeamScore = awayTeamScore; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReferee() { return referee; }
    public void setReferee(String referee) { this.referee = referee; }

    public Integer getAttendance() { return attendance; }
    public void setAttendance(Integer attendance) { this.attendance = attendance; }

    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }

    public Long getTournamentId() { return tournamentId; }
    public void setTournamentId(Long tournamentId) { this.tournamentId = tournamentId; }

    public String getTournamentName() { return tournamentName; }
    public void setTournamentName(String tournamentName) { this.tournamentName = tournamentName; }
}