package com.tournament.library_management.controller;

import com.tournament.library_management.dto.MatchDTO;
import com.tournament.library_management.dto.CreateMatchDTO;
import com.tournament.library_management.entity.Match;
import com.tournament.library_management.entity.Team;
import com.tournament.library_management.entity.Tournament;
import com.tournament.library_management.service.MatchService;
import com.tournament.library_management.service.TeamService;
import com.tournament.library_management.service.TournamentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin(origins = "http://localhost:3000")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TournamentService tournamentService;


    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches() {
        List<Match> matches = matchService.findAll();
        List<MatchDTO> matchDTOs = matches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(matchDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Long id) {
        Optional<Match> match = matchService.findById(id);
        if (match.isPresent()) {
            return ResponseEntity.ok(convertToDTO(match.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<MatchDTO> createMatch(@Valid @RequestBody CreateMatchDTO createMatchDTO) {
        try {
            Match match = convertToEntity(createMatchDTO);
            Match savedMatch = matchService.save(match);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedMatch));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Long id,
                                                @Valid @RequestBody CreateMatchDTO createMatchDTO) {
        Optional<Match> existingMatch = matchService.findById(id);
        if (existingMatch.isPresent()) {
            try {
                Match match = convertToEntity(createMatchDTO);
                match.setId(id);
                Match updatedMatch = matchService.save(match);
                return ResponseEntity.ok(convertToDTO(updatedMatch));
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        Optional<Match> match = matchService.findById(id);
        if (match.isPresent()) {
            matchService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/upcoming")
    public ResponseEntity<List<MatchDTO>> getUpcomingMatches() {
        LocalDateTime now = LocalDateTime.now();
        List<Match> matches = matchService.findAll();
        List<MatchDTO> upcomingMatches = matches.stream()
                .filter(m -> m.getMatchDate().isAfter(now) && "SCHEDULED".equals(m.getStatus()))
                .sorted((m1, m2) -> m1.getMatchDate().compareTo(m2.getMatchDate()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(upcomingMatches);
    }


    @GetMapping("/finished")
    public ResponseEntity<List<MatchDTO>> getFinishedMatches() {
        List<Match> matches = matchService.findAll();
        List<MatchDTO> finishedMatches = matches.stream()
                .filter(m -> "FINISHED".equals(m.getStatus()))
                .sorted((m1, m2) -> m2.getMatchDate().compareTo(m1.getMatchDate()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(finishedMatches);
    }


    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<MatchDTO>> getMatchesByTeam(@PathVariable Long teamId) {
        List<Match> matches = matchService.findByTeamId(teamId);
        List<MatchDTO> matchDTOs = matches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(matchDTOs);
    }


    @PutMapping("/{id}/result")
    public ResponseEntity<MatchDTO> updateMatchResult(@PathVariable Long id,
                                                      @RequestParam Integer homeScore,
                                                      @RequestParam Integer awayScore) {
        try {
            Optional<Match> matchOptional = matchService.findById(id);
            if (matchOptional.isPresent()) {
                Match match = matchOptional.get();
                match.setHomeTeamScore(homeScore);
                match.setAwayTeamScore(awayScore);
                match.setStatus("FINISHED");


                updateTeamStatistics(match);

                Match updatedMatch = matchService.save(match);
                return ResponseEntity.ok(convertToDTO(updatedMatch));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private void updateTeamStatistics(Match match) {
        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();
        Integer homeScore = match.getHomeTeamScore();
        Integer awayScore = match.getAwayTeamScore();

        if (homeTeam != null && awayTeam != null && homeScore != null && awayScore != null) {
            homeTeam.setMatchesPlayed(homeTeam.getMatchesPlayed() + 1);
            homeTeam.setGoalsFor(homeTeam.getGoalsFor() + homeScore);
            homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst() + awayScore);

            awayTeam.setMatchesPlayed(awayTeam.getMatchesPlayed() + 1);
            awayTeam.setGoalsFor(awayTeam.getGoalsFor() + awayScore);
            awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + homeScore);

            if (homeScore > awayScore) {
                homeTeam.setWins(homeTeam.getWins() + 1);
                homeTeam.setPoints(homeTeam.getPoints() + 3);
                awayTeam.setLosses(awayTeam.getLosses() + 1);
            } else if (homeScore.equals(awayScore)) {
                homeTeam.setDraws(homeTeam.getDraws() + 1);
                homeTeam.setPoints(homeTeam.getPoints() + 1);
                awayTeam.setDraws(awayTeam.getDraws() + 1);
                awayTeam.setPoints(awayTeam.getPoints() + 1);
            } else {
                homeTeam.setLosses(homeTeam.getLosses() + 1);
                awayTeam.setWins(awayTeam.getWins() + 1);
                awayTeam.setPoints(awayTeam.getPoints() + 3);
            }

            teamService.save(homeTeam);
            teamService.save(awayTeam);
        }
    }


    private MatchDTO convertToDTO(Match match) {
        MatchDTO dto = new MatchDTO();
        dto.setId(match.getId());
        dto.setMatchDate(match.getMatchDate());
        dto.setVenue(match.getVenue());
        dto.setHomeTeamScore(match.getHomeTeamScore());
        dto.setAwayTeamScore(match.getAwayTeamScore());
        dto.setStatus(match.getStatus());
        dto.setReferee(match.getReferee());
        dto.setAttendance(match.getAttendance());

        if (match.getHomeTeam() != null) {
            dto.setHomeTeamId(match.getHomeTeam().getId());
            dto.setHomeTeamName(match.getHomeTeam().getName());
        }

        if (match.getAwayTeam() != null) {
            dto.setAwayTeamId(match.getAwayTeam().getId());
            dto.setAwayTeamName(match.getAwayTeam().getName());
        }

        if (match.getTournament() != null) {
            dto.setTournamentId(match.getTournament().getId());
            dto.setTournamentName(match.getTournament().getName());
        }


        if ("FINISHED".equals(match.getStatus())) {
            dto.setScore(match.getHomeTeamScore() + " - " + match.getAwayTeamScore());
        } else {
            dto.setScore("Не сыгран");
        }

        return dto;
    }


    private Match convertToEntity(CreateMatchDTO dto) {
        Match match = new Match();
        match.setMatchDate(dto.getMatchDate());
        match.setVenue(dto.getVenue());
        match.setReferee(dto.getReferee());
        match.setStatus("SCHEDULED");

        if (dto.getHomeTeamId() != null) {
            Optional<Team> homeTeam = teamService.findById(dto.getHomeTeamId());
            homeTeam.ifPresent(match::setHomeTeam);
        }

        if (dto.getAwayTeamId() != null) {
            Optional<Team> awayTeam = teamService.findById(dto.getAwayTeamId());
            awayTeam.ifPresent(match::setAwayTeam);
        }

        if (dto.getTournamentId() != null) {
            Optional<Tournament> tournament = tournamentService.findById(dto.getTournamentId());
            tournament.ifPresent(match::setTournament);
        }

        return match;
    }
}