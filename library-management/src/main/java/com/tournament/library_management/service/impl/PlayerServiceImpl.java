package com.tournament.library_management.service.impl;

import com.tournament.library_management.entity.Player;
import com.tournament.library_management.entity.Team;
import com.tournament.library_management.repository.PlayerRepository;
import com.tournament.library_management.repository.TeamRepository;
import com.tournament.library_management.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public List<Player> findByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Команда с ID " + teamId + " не найдена"));
        return playerRepository.findByTeam(team);
    }

    @Override
    public List<Player> findByPosition(String position) {
        return playerRepository.findByPosition(position);
    }
    @Override
    public List<Player> findByFirstName(String firstName){
        return  playerRepository.findByFirstNameContainingIgnoreCase(firstName);
    }
    @Override
    public List<Player> findByLastName(String lastName){
        return playerRepository.findByLastNameContainingIgnoreCase(lastName);
    }
    @Override
    public Optional<Player> findByTeamAndJerseyNumber(Long teamId, Integer jerseyNumber){
        Optional<Team> temp=teamRepository.findById(teamId);
        return playerRepository.findByTeamAndJerseyNumber(temp.get(),jerseyNumber);
    }
}