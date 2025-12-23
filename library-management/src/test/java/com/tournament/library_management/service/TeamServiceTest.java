package com.tournament.library_management.service;

import com.tournament.library_management.entity.Team;
import com.tournament.library_management.repository.TeamRepository;
import com.tournament.library_management.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    private Team testTeam;

    @BeforeEach
    void setUp() {
        testTeam = new Team("Спартак Москва", "Россия", "Москва");
        testTeam.setId(1L);
        testTeam.setStadiumName("Открытие Банк Арена");
        testTeam.setCoachName("Гильерме Абад");
        testTeam.setFoundedYear(1922);
    }


    @Test
    void findAll_ShouldReturnAllTeams() {

        List<Team> expectedTeams = Arrays.asList(testTeam);
        when(teamRepository.findAll()).thenReturn(expectedTeams);


        List<Team> actualTeams = teamService.findAll();


        assertEquals(1, actualTeams.size());
        assertEquals("Спартак Москва", actualTeams.get(0).getName());
        verify(teamRepository).findAll();
    }

    @Test
    void findById_WhenTeamExists_ShouldReturnTeam() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));

        Optional<Team> result = teamService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Спартак Москва", result.get().getName());
        verify(teamRepository).findById(1L);
    }

    @Test
    void findById_WhenTeamNotExists_ShouldReturnEmpty() {
        when(teamRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Team> result = teamService.findById(999L);

        assertFalse(result.isPresent());
        verify(teamRepository).findById(999L);
    }

    @Test
    void save_ShouldReturnSavedTeam() {
        when(teamRepository.save(any(Team.class))).thenReturn(testTeam);

        Team result = teamService.save(testTeam);

        assertEquals("Спартак Москва", result.getName());
        verify(teamRepository).save(testTeam);
    }

    @Test
    void deleteById_ShouldCallRepository() {
        doNothing().when(teamRepository).deleteById(1L);

        teamService.deleteById(1L);

        verify(teamRepository).deleteById(1L);
    }

    @Test
    void findByNameContaining_ShouldReturnMatchingTeams() {
        String searchName = "спартак";
        List<Team> expectedTeams = Arrays.asList(testTeam);
        when(teamRepository.findByNameContainingIgnoreCase(searchName)).thenReturn(expectedTeams);

        List<Team> result = teamService.findByNameContaining(searchName);

        assertEquals(1, result.size());
        assertEquals("Спартак Москва", result.get(0).getName());
        verify(teamRepository).findByNameContainingIgnoreCase(searchName);
    }

    @Test
    void findByCountry_ShouldReturnTeamsFromCountry() {
        String country = "Россия";
        List<Team> expectedTeams = Arrays.asList(testTeam);
        when(teamRepository.findByCountry(country)).thenReturn(expectedTeams);

        List<Team> result = teamService.findByCountry(country);

        assertEquals(1, result.size());
        assertEquals("Россия", result.get(0).getCountry());
        verify(teamRepository).findByCountry(country);
    }

    @Test
    void findByName_WhenTeamExists_ShouldReturnTeam() {
        String exactName = "Спартак Москва";
        when(teamRepository.findByName(exactName)).thenReturn(Optional.of(testTeam));

        Optional<Team> result = teamService.findByName(exactName);

        assertTrue(result.isPresent());
        assertEquals(exactName, result.get().getName());
        verify(teamRepository).findByName(exactName);
    }

    @Test
    void findByCity_ShouldReturnTeamsFromCity() {
        String city = "Москва";
        List<Team> expectedTeams = Arrays.asList(testTeam);
        when(teamRepository.findByCity(city)).thenReturn(expectedTeams);

        List<Team> result = teamService.findByCity(city);

        assertEquals(1, result.size());
        assertEquals("Москва", result.get(0).getCity());
        verify(teamRepository).findByCity(city);
    }

    @Test
    void findByCoachNameContaining_ShouldReturnMatchingTeams() {
        String coachName = "Гильерме";
        List<Team> expectedTeams = Arrays.asList(testTeam);
        when(teamRepository.findByCoachNameContainingIgnoreCase(coachName)).thenReturn(expectedTeams);

        List<Team> result = teamService.findByCoachNameContaining(coachName);

        assertEquals(1, result.size());
        assertEquals("Гильерме Абад", result.get(0).getCoachName());
        verify(teamRepository).findByCoachNameContainingIgnoreCase(coachName);
    }
}