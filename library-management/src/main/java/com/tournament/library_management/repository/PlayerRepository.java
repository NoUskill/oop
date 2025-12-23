package com.tournament.library_management.repository;

import com.tournament.library_management.entity.Player;
import com.tournament.library_management.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    // Поиск игроков по команде
    List<Player> findByTeam(Team team);

    // Поиск игроков по ID команды
    List<Player> findByTeamId(Long teamId);

    // Поиск игроков по позиции
    List<Player> findByPosition(String position);

    // Поиск игроков по стране (национальности)
    List<Player> findByNationality(String nationality);

    // Поиск игроков по имени (частичное совпадение, игнорируя регистр)
    List<Player> findByFirstNameContainingIgnoreCase(String firstName);

    // Поиск игроков по фамилии (частичное совпадение, игнорируя регистр)
    List<Player> findByLastNameContainingIgnoreCase(String lastName);

    // Поиск игроков по имени и фамилии
    List<Player> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName);

    // Поиск игрока по номеру в команде
    Optional<Player> findByTeamAndJerseyNumber(Team team, Integer jerseyNumber);

    // Проверка уникальности номера в команде
    boolean existsByTeamAndJerseyNumber(Team team, Integer jerseyNumber);
}