package com.tournament.library_management.service;

import com.tournament.library_management.entity.Player;
import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<Player> findAll();
    Optional<Player> findById(Long id);
    Player save(Player player);
    void deleteById(Long id);
    List<Player> findByTeamId(Long teamId);
    List<Player> findByPosition(String position);

    List<Player> findByFirstName(String firstName);
    List<Player> findByLastName(String lastName);
    Optional<Player> findByTeamAndJerseyNumber(Long teamId, Integer jerseyNumber);
}