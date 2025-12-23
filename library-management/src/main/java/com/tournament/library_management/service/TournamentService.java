package com.tournament.library_management.service;
import com.tournament.library_management.entity.Tournament;
import java.util.List;
import java.util.Optional;

public interface TournamentService {
    List<Tournament> findAll();
    Optional<Tournament> findById(Long id);
    Tournament save(Tournament tournament);
    void deleteById(Long id);
    List<Tournament> findByNameContaining(String name);
    List<Tournament> findBySeason(String season);
}
