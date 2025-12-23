package com.tournament.library_management.service;

import com.tournament.library_management.entity.Team;
import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<Team> findAll();
    Optional<Team> findById(Long id);
    Team save(Team team);
    void deleteById(Long id);
    List<Team> findByNameContaining(String name);
    List<Team> findByCountry(String country);
    List<Team> findByCity(String city);
    List<Team> findByCoachNameContaining(String coachName);
    Optional<Team> findByName(String name);
}