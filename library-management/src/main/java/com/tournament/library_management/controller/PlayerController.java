package com.tournament.library_management.controller;

import com.tournament.library_management.dto.PlayerDTO;
import com.tournament.library_management.dto.CreatePlayerDTO;
import com.tournament.library_management.entity.Player;
import com.tournament.library_management.entity.Team;
import com.tournament.library_management.service.PlayerService;
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
@RequestMapping("/api/players")
@CrossOrigin(origins = "http://localhost:3000")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamService teamService;


    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<Player> players = playerService.findAll();
        List<PlayerDTO> playerDTOs = players.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        Optional<Player> player = playerService.findById(id);
        if (player.isPresent()) {
            return ResponseEntity.ok(convertToDTO(player.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@Valid @RequestBody CreatePlayerDTO createPlayerDTO) {
        try {
            Player player = convertToEntity(createPlayerDTO);
            Player savedPlayer = playerService.save(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedPlayer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long id,
                                                  @Valid @RequestBody CreatePlayerDTO createPlayerDTO) {
        Optional<Player> existingPlayer = playerService.findById(id);
        if (existingPlayer.isPresent()) {
            try {
                Player player = convertToEntity(createPlayerDTO);
                player.setId(id);
                Player updatedPlayer = playerService.save(player);
                return ResponseEntity.ok(convertToDTO(updatedPlayer));
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        Optional<Player> player = playerService.findById(id);
        if (player.isPresent()) {
            playerService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTeam(@PathVariable Long teamId) {
        List<Player> players = playerService.findByTeamId(teamId);
        List<PlayerDTO> playerDTOs = players.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }


    @GetMapping("/position/{position}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByPosition(@PathVariable String position) {
        List<Player> players = playerService.findByPosition(position);
        List<PlayerDTO> playerDTOs = players.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }


    @GetMapping("/search")
    public ResponseEntity<List<PlayerDTO>> searchPlayers(@RequestParam String name) {
        List<Player> players = playerService.findAll(); // В реальности нужен метод поиска
        List<PlayerDTO> playerDTOs = players.stream()
                .filter(p -> p.getFullName().toLowerCase().contains(name.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }


    @GetMapping("/top-scorers")
    public ResponseEntity<List<PlayerDTO>> getTopScorers(@RequestParam(defaultValue = "10") int limit) {
        List<Player> players = playerService.findAll();
        List<PlayerDTO> topScorers = players.stream()
                .sorted((p1, p2) -> p2.getGoalsScored().compareTo(p1.getGoalsScored()))
                .limit(limit)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topScorers);
    }


    private PlayerDTO convertToDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setFirstName(player.getFirstName());
        dto.setLastName(player.getLastName());
        dto.setDateOfBirth(player.getDateOfBirth());
        dto.setNationality(player.getNationality());
        dto.setPosition(player.getPosition());
        dto.setJerseyNumber(player.getJerseyNumber());
        dto.setGoalsScored(player.getGoalsScored());
        dto.setAssists(player.getAssists());
        dto.setYellowCards(player.getYellowCards());
        dto.setRedCards(player.getRedCards());
        dto.setMatchesPlayed(player.getMatchesPlayed());
        dto.setFullName(player.getFullName());

        if (player.getTeam() != null) {
            dto.setTeamId(player.getTeam().getId());
            dto.setTeamName(player.getTeam().getName());
        }

        return dto;
    }


    private Player convertToEntity(CreatePlayerDTO dto) {
        Player player = new Player();
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setDateOfBirth(dto.getDateOfBirth());
        player.setNationality(dto.getNationality());
        player.setPosition(dto.getPosition());
        player.setJerseyNumber(dto.getJerseyNumber());

        if (dto.getTeamId() != null) {
            Optional<Team> team = teamService.findById(dto.getTeamId());
            team.ifPresent(player::setTeam);
        }

        return player;
    }
}