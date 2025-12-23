package com.tournament.library_management.service;

import com.tournament.library_management.entity.Match;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchService {
    List<Match> findAll();
    Optional<Match> findById(Long id);
    Match save(Match match);
    void deleteById(Long id);
    List<Match> findByDateBetween(LocalDateTime start, LocalDateTime end);
    List<Match> findByTeamId(Long teamId);
}