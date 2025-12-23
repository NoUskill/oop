package com.tournament.library_management.service.impl;

import com.tournament.library_management.entity.Tournament;
import com.tournament.library_management.repository.TournamentRepository;
import com.tournament.library_management.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    @Override
    public Optional<Tournament> findById(Long id) {
        return tournamentRepository.findById(id);
    }

    @Override
    public Tournament save(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Override
    public void deleteById(Long id) {
        tournamentRepository.deleteById(id);
    }

    @Override
    public List<Tournament> findByNameContaining(String name) {
        return tournamentRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Tournament> findBySeason(String season) {
        return tournamentRepository.findBySeason(season);
    }
}