package com.tournament.library_management.controller;

import com.tournament.library_management.dto.TournamentDTO;
import com.tournament.library_management.dto.CreateTournamentDTO;
import com.tournament.library_management.entity.Tournament;
import com.tournament.library_management.entity.Team;
import com.tournament.library_management.service.TournamentService;
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
@RequestMapping("/api/tournaments")
@CrossOrigin(origins = "http://localhost:3000")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private TeamService teamService;


    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAllTournaments() {
        List<Tournament> tournaments = tournamentService.findAll();
        List<TournamentDTO> tournamentDTOs = tournaments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tournamentDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable Long id) {
        Optional<Tournament> tournament = tournamentService.findById(id);
        if (tournament.isPresent()) {
            return ResponseEntity.ok(convertToDTO(tournament.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<TournamentDTO> createTournament(@Valid @RequestBody CreateTournamentDTO createTournamentDTO) {
        try {
            Tournament tournament = convertToEntity(createTournamentDTO);
            Tournament savedTournament = tournamentService.save(tournament);

            // Добавляем команды в турнир
            if (createTournamentDTO.getTeamIds() != null) {
                for (Long teamId : createTournamentDTO.getTeamIds()) {
                    Optional<Team> team = teamService.findById(teamId);
                    team.ifPresent(t -> savedTournament.getParticipatingTeams().add(t));
                }
                tournamentService.save(savedTournament);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedTournament));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<TournamentDTO> updateTournament(@PathVariable Long id,
                                                          @Valid @RequestBody CreateTournamentDTO createTournamentDTO) {
        Optional<Tournament> existingTournament = tournamentService.findById(id);
        if (existingTournament.isPresent()) {
            try {
                Tournament tournament = convertToEntity(createTournamentDTO);
                tournament.setId(id);

                // Сохраняем существующие команды
                tournament.setParticipatingTeams(existingTournament.get().getParticipatingTeams());

                Tournament updatedTournament = tournamentService.save(tournament);
                return ResponseEntity.ok(convertToDTO(updatedTournament));
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        Optional<Tournament> tournament = tournamentService.findById(id);
        if (tournament.isPresent()) {
            tournamentService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/active")
    public ResponseEntity<List<TournamentDTO>> getActiveTournaments() {
        List<Tournament> tournaments = tournamentService.findAll();
        List<TournamentDTO> activeTournaments = tournaments.stream()
                .filter(t -> Boolean.TRUE.equals(t.getIsActive()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(activeTournaments);
    }


    @GetMapping("/search")
    public ResponseEntity<List<TournamentDTO>> searchTournaments(@RequestParam String name) {
        List<Tournament> tournaments = tournamentService.findByNameContaining(name);
        List<TournamentDTO> tournamentDTOs = tournaments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tournamentDTOs);
    }


    @PostMapping("/{tournamentId}/teams/{teamId}")
    public ResponseEntity<TournamentDTO> addTeamToTournament(@PathVariable Long tournamentId,
                                                             @PathVariable Long teamId) {
        Optional<Tournament> tournament = tournamentService.findById(tournamentId);
        Optional<Team> team = teamService.findById(teamId);

        if (tournament.isPresent() && team.isPresent()) {
            Tournament t = tournament.get();
            if (!t.getParticipatingTeams().contains(team.get())) {
                t.getParticipatingTeams().add(team.get());
                tournamentService.save(t);
            }
            return ResponseEntity.ok(convertToDTO(t));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{tournamentId}/teams/{teamId}")
    public ResponseEntity<TournamentDTO> removeTeamFromTournament(@PathVariable Long tournamentId,
                                                                  @PathVariable Long teamId) {
        Optional<Tournament> tournament = tournamentService.findById(tournamentId);
        Optional<Team> team = teamService.findById(teamId);

        if (tournament.isPresent() && team.isPresent()) {
            Tournament t = tournament.get();
            t.getParticipatingTeams().remove(team.get());
            tournamentService.save(t);
            return ResponseEntity.ok(convertToDTO(t));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private TournamentDTO convertToDTO(Tournament tournament) {
        TournamentDTO dto = new TournamentDTO();
        dto.setId(tournament.getId());
        dto.setName(tournament.getName());
        dto.setStartDate(tournament.getStartDate());
        dto.setEndDate(tournament.getEndDate());
        dto.setSeason(tournament.getSeason());
        dto.setType(tournament.getType());
        dto.setDescription(tournament.getDescription());
        dto.setIsActive(tournament.getIsActive());

        if (tournament.getParticipatingTeams() != null) {
            dto.setNumberOfTeams(tournament.getParticipatingTeams().size());
        }

        return dto;
    }

    private Tournament convertToEntity(CreateTournamentDTO dto) {
        Tournament tournament = new Tournament();
        tournament.setName(dto.getName());
        tournament.setSeason(dto.getSeason());
        tournament.setType(dto.getType());
        tournament.setStartDate(dto.getStartDate());
        tournament.setEndDate(dto.getEndDate());
        tournament.setDescription(dto.getDescription());
        tournament.setIsActive(true);
        return tournament;
    }
}