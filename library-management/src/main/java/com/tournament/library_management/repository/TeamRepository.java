package com.tournament.library_management.repository;

import com.tournament.library_management.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Поиск по названию (частичное совпадение, игнорируя регистр)
    List<Team> findByNameContainingIgnoreCase(String name);

    // Поиск команды по точному названию
    Optional<Team> findByName(String name);

    // Поиск команд по стране
    List<Team> findByCountry(String country);

    // Поиск команд по городу
    List<Team> findByCity(String city);

    // Поиск команд по тренеру (частичное совпадение, игнорируя регистр)
    List<Team> findByCoachNameContainingIgnoreCase(String coachName);

    // Проверка существования команды с таким названием
    boolean existsByName(String name);
}