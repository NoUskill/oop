package com.tournament.library_management.repository;

import com.tournament.library_management.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    // Поиск турнира по названию
    Optional<Tournament> findByName(String name);

    // Поиск турниров по названию (частичное совпадение, игнорируя регистр)
    List<Tournament> findByNameContainingIgnoreCase(String name);

    // Поиск турниров по сезону
    List<Tournament> findBySeason(String season);

    // Поиск турниров по типу
    List<Tournament> findByType(String type);

    // Поиск активных турниров
    List<Tournament> findByIsActiveTrue();

    // Поиск завершенных турниров
    List<Tournament> findByIsActiveFalse();

    // Поиск турниров по дате начала
    List<Tournament> findByStartDateAfter(LocalDate date);

    // Поиск турниров по дате окончания
    List<Tournament> findByEndDateBefore(LocalDate date);

    // Проверка существования турнира с таким названием в сезоне
    boolean existsByNameAndSeason(String name, String season);
}