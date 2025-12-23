package com.tournament.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class CreateTournamentDTO {

    @NotBlank(message = "Название турнира обязательно")
    @Size(min = 2, max = 100, message = "Название турнира должно быть от 2 до 100 символов")
    private String name;

    @NotBlank(message = "Сезон обязателен")
    private String season;

    @NotBlank(message = "Тип турнира обязателен")
    private String type;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private List<Long> teamIds;

    // Конструкторы
    public CreateTournamentDTO() {}

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Long> getTeamIds() { return teamIds; }
    public void setTeamIds(List<Long> teamIds) { this.teamIds = teamIds; }
}