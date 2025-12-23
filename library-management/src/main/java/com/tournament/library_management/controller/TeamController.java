package com.tournament.library_management.controller;

import com.tournament.library_management.dto.TeamDTO;
import com.tournament.library_management.dto.CreateTeamDTO;
import com.tournament.library_management.entity.Team;
import com.tournament.library_management.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "http://localhost:3000")
public class TeamController {

    @Autowired
    private TeamService teamService;


    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<Team> teams = teamService.findAll();
        List<TeamDTO> teamDTOs = teams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        Optional<Team> team = teamService.findById(id);
        if (team.isPresent()) {
            return ResponseEntity.ok(convertToDTO(team.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody CreateTeamDTO createTeamDTO) {
        try {
            Team team = convertToEntity(createTeamDTO);
            Team savedTeam = teamService.save(team);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedTeam));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id,
                                              @Valid @RequestBody CreateTeamDTO createTeamDTO) {
        Optional<Team> existingTeam = teamService.findById(id);
        if (existingTeam.isPresent()) {
            try {
                Team team = convertToEntity(createTeamDTO);
                team.setId(id);
                Team updatedTeam = teamService.save(team);
                return ResponseEntity.ok(convertToDTO(updatedTeam));
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        Optional<Team> team = teamService.findById(id);
        if (team.isPresent()) {
            teamService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<TeamDTO>> searchTeams(@RequestParam String name) {
        List<Team> teams = teamService.findByNameContaining(name);
        List<TeamDTO> teamDTOs = teams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOs);
    }


    @GetMapping("/standings")
    public ResponseEntity<List<TeamDTO>> getStandings() {
        List<Team> teams = teamService.findAll();
        // Сортируем по очкам, разнице голов и т.д.
        List<TeamDTO> standings = teams.stream()
                .sorted((t1, t2) -> {
                    if (!t1.getPoints().equals(t2.getPoints())) {
                        return t2.getPoints().compareTo(t1.getPoints());
                    }
                    Integer gd1 = t1.getGoalsFor() - t1.getGoalsAgainst();
                    Integer gd2 = t2.getGoalsFor() - t2.getGoalsAgainst();
                    if (!gd1.equals(gd2)) {
                        return gd2.compareTo(gd1);
                    }
                    return t2.getGoalsFor().compareTo(t1.getGoalsFor());
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(standings);
    }


    @GetMapping("/country/{country}")
    public ResponseEntity<List<TeamDTO>> getTeamsByCountry(@PathVariable String country) {
        List<Team> teams = teamService.findByCountry(country);
        List<TeamDTO> teamDTOs = teams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOs);
    }


    private TeamDTO convertToDTO(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setCountry(team.getCountry());
        dto.setCity(team.getCity());
        dto.setFoundedYear(team.getFoundedYear());
        dto.setStadiumName(team.getStadiumName());
        dto.setCoachName(team.getCoachName());
        dto.setPoints(team.getPoints());
        dto.setMatchesPlayed(team.getMatchesPlayed());
        dto.setWins(team.getWins());
        dto.setDraws(team.getDraws());
        dto.setLosses(team.getLosses());
        dto.setGoalsFor(team.getGoalsFor());
        dto.setGoalsAgainst(team.getGoalsAgainst());
        dto.setGoalDifference(team.getGoalsFor() - team.getGoalsAgainst());
        dto.setPlayersCount(team.getPlayers() != null ? team.getPlayers().size() : 0);
        return dto;
    }


    private Team convertToEntity(CreateTeamDTO dto) {
        Team team = new Team();
        team.setName(dto.getName());
        team.setCountry(dto.getCountry());
        team.setCity(dto.getCity());
        team.setFoundedYear(dto.getFoundedYear());
        team.setStadiumName(dto.getStadiumName());
        team.setCoachName(dto.getCoachName());
        return team;
    }
}