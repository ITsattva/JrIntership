package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rest/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<Player>> getPlayersList(@RequestParam(required = false) Map<String, String> params) {
        return playerService.getPlayers(params);
    }

    @ResponseBody
    @GetMapping("/count")
    public Integer getPlayersCount(@RequestParam(required = false) Map<String, String> params) {
        return playerService.getPlayersCount(params);
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return playerService.addPlayer(player);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Long id) {
        return playerService.getById(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player, @PathVariable("id") Long id) {
            return playerService.updatePlayer(player, id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deletePlayer(@PathVariable("id") Long id) {
        return playerService.deletePlayer(id);
    }
}
