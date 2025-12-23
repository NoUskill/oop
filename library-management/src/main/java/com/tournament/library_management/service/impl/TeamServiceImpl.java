package com.tournament.library_management.service.impl;

import com.tournament.library_management.entity.Team;
import com.tournament.library_management.repository.TeamRepository;
import com.tournament.library_management.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void deleteById(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public List<Team> findByNameContaining(String name) {
        return teamRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Team> findByCountry(String country) {
        return teamRepository.findByCountry(country);
    }
    @Override
    public List<Team> findByCity(String city){
        return teamRepository.findByCity(city);
    }
    @Override
    public List<Team> findByCoachNameContaining(String coachName){
        return teamRepository.findByCoachNameContainingIgnoreCase(coachName);
    }
    @Override
    public Optional<Team> findByName(String name){
        return teamRepository.findByName(name);
    }

}