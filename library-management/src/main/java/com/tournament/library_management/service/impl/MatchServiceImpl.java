package com.tournament.library_management.service.impl;

import com.tournament.library_management.entity.Match;
import com.tournament.library_management.entity.Team;
import com.tournament.library_management.entity.Tournament;
import com.tournament.library_management.repository.MatchRepository;
import com.tournament.library_management.repository.TeamRepository;
import com.tournament.library_management.repository.TournamentRepository;
import com.tournament.library_management.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @Override
    public Optional<Match> findById(Long id) {
        return matchRepository.findById(id);
    }

    @Override
    public Match save(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public void deleteById(Long id) {
        matchRepository.deleteById(id);
    }

    @Override
    public List<Match> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        return matchRepository.findByMatchDateBetween(start, end);
    }

    @Override
    public List<Match> findByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Команда с ID " + teamId + " не найдена"));
        return matchRepository.findByHomeTeamOrAwayTeam(team, team);
    }
}