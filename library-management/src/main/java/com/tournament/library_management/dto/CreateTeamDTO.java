package com.tournament.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTeamDTO {

    @NotBlank(message = "Название команды обязательно")
    @Size(min = 2, max = 100, message = "Название команды должно быть от 2 до 100 символов")
    private String name;

    private String country;

    private String city;

    private Integer foundedYear;

    private String stadiumName;

    private String coachName;


    public CreateTeamDTO() {}


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Integer getFoundedYear() { return foundedYear; }
    public void setFoundedYear(Integer foundedYear) { this.foundedYear = foundedYear; }

    public String getStadiumName() { return stadiumName; }
    public void setStadiumName(String stadiumName) { this.stadiumName = stadiumName; }

    public String getCoachName() { return coachName; }
    public void setCoachName(String coachName) { this.coachName = coachName; }
}