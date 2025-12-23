package com.tournament.library_management.dto;

import java.time.LocalDate;
import java.util.List;

public class TournamentDTO {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String season;
    private String type;
    private String description;
    private Boolean isActive;
    private Integer numberOfTeams;
    private Integer playedMatches;
    private List<TeamDTO> participatingTeams;

    public TournamentDTO() {}

    public TournamentDTO(Long id, String name, String season, String type) {
        this.id = id;
        this.name = name;
        this.season = season;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Integer getNumberOfTeams() { return numberOfTeams; }
    public void setNumberOfTeams(Integer numberOfTeams) { this.numberOfTeams = numberOfTeams; }

    public Integer getPlayedMatches() { return playedMatches; }
    public void setPlayedMatches(Integer playedMatches) { this.playedMatches = playedMatches; }

    public List<TeamDTO> getParticipatingTeams() { return participatingTeams; }
    public void setParticipatingTeams(List<TeamDTO> participatingTeams) {
        this.participatingTeams = participatingTeams;
    }
}