package com.tournament.library_management.repository;

import com.tournament.library_management.entity.Match;
import com.tournament.library_management.entity.Team;
import com.tournament.library_management.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    // Поиск матчей по статусу
    List<Match> findByStatus(String status);

    // Поиск матчей по дате (диапазон)
    List<Match> findByMatchDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Поиск матчей по домашней команде
    List<Match> findByHomeTeam(Team homeTeam);

    // Поиск матчей по гостевой команде
    List<Match> findByAwayTeam(Team awayTeam);

    // Поиск матчей по команде (домашние или гостевые)
    List<Match> findByHomeTeamOrAwayTeam(Team homeTeam, Team awayTeam);

    // Поиск матчей по турниру
    List<Match> findByTournament(Tournament tournament);

    // Поиск матчей по турниру и статусу
    List<Match> findByTournamentAndStatus(Tournament tournament, String status);

    // Проверка существования матча между командами
    boolean existsByHomeTeamAndAwayTeam(Team homeTeam, Team awayTeam);

    // Поиск матчей по судье (частичное совпадение, игнорируя регистр)
    List<Match> findByRefereeContainingIgnoreCase(String referee);
}