package com.tournament.library_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tournament.library_management.entity.Team;
import com.tournament.library_management.repository.MatchRepository;
import com.tournament.library_management.repository.TeamRepository;
import com.tournament.library_management.repository.TournamentRepository;
import com.tournament.library_management.service.TeamService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TeamControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TournamentRepository tournamentRepository;

    private Team testTeam;

    @BeforeEach
    void setUp() {
        matchRepository.deleteAll();


        if (tournamentRepository != null) {
            tournamentRepository.deleteAll();
        }


        teamRepository.deleteAll();

        String uniqueTeamName = "Зенит Санкт-Петербург " + System.currentTimeMillis();
        testTeam = new Team(uniqueTeamName, "Россия", "Санкт-Петербург");
        testTeam.setStadiumName("Газпром Арена");
        testTeam.setCoachName("Сергей Семак");
        testTeam.setFoundedYear(1925);
        testTeam = teamService.save(testTeam);
    }

    @AfterEach
    void tearDown() {
        // Очистка в том же порядке
        matchRepository.deleteAll();

        if (tournamentRepository != null) {
            tournamentRepository.deleteAll();
        }

        teamRepository.deleteAll();
    }


    @Test
    void getAllTeams_ShouldReturnAllTeams() throws Exception {
        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Зенит Санкт-Петербург"));
    }

    @Test
    void getTeamById_WhenTeamExists_ShouldReturnTeam() throws Exception {
        mockMvc.perform(get("/api/teams/{id}", testTeam.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testTeam.getId()))
                .andExpect(jsonPath("$.name").value("Зенит Санкт-Петербург"));
    }

    @Test
    void getTeamById_WhenTeamNotExists_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/teams/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTeam_ShouldReturnCreatedTeam() throws Exception {
        Team newTeam = new Team("Краснодар", "Россия", "Краснодар");
        newTeam.setStadiumName("Стадион Краснодар");
        newTeam.setCoachName("Игорь Шалимов");
        newTeam.setFoundedYear(2008);

        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTeam)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Краснодар"))
                .andExpect(jsonPath("$.city").value("Краснодар"));
    }

    @Test
    void searchTeams_ShouldReturnMatchingTeams() throws Exception {
        mockMvc.perform(get("/api/teams/search")
                        .param("name", "Зенит"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Зенит Санкт-Петербург"));
    }
}