package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import com.game.util.PlayerFilter;
import com.game.util.Updater;
import com.game.util.ValidationHelper;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final ValidationHelper validationHelper;
    private final PlayerFilter playerFilter;
    private final Updater updater;

    public PlayerService(PlayerRepository playerRepository, ValidationHelper validationHelper, PlayerFilter playerFilter, Updater updater) {
        this.playerRepository = playerRepository;
        this.validationHelper = validationHelper;
        this.playerFilter = playerFilter;
        this.updater = updater;
    }

    public ResponseEntity<Player> addPlayer(Player player) {
        if (validationHelper.validateNewPlayer(player)) {
            updater.resetLevelAndExperience(player);
            playerRepository.save(player);
            return new ResponseEntity<>(player, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public Integer getPlayersCount(Map<String, String> params) {
        List<Player> players = playerRepository.findAll();
        if (params == null) {
            return players.size();
        } else {
            return playerFilter.filterPlayersList(params, players).size();
        }
    }

    public ResponseEntity<List<Player>> getPlayers(Map<String, String> params) {
        PlayerOrder order = PlayerOrder.ID;
        if (params.containsKey("order"))
            order = PlayerOrder.valueOf(params.get("order"));

        List<Player> playersRaw = playerRepository.findAll();
        playerFilter.sortList(playersRaw, order);
        playersRaw = playerFilter.filterPlayersList(params, playersRaw);
        List<Player> result = getPageFromList(playersRaw, params);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Player> getById(Long id) {
        Player player = playerRepository.findById(id).orElse(null);
        if (id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(player, HttpStatus.OK);
        }
    }

    public ResponseEntity<Player> updatePlayer(Player updatedPlayer, Long id) {
        if (id == null || id <= 0 || !validationHelper.validateForUpdate(updatedPlayer))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Player> playerFromDB = playerRepository.findById(id);
        if (playerFromDB.isPresent()) {
            Player oldPlayer = playerFromDB.get();
            updater.updatePlayer(oldPlayer, updatedPlayer);
            return new ResponseEntity<>(oldPlayer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> deletePlayer(Long id) {
        Player player = playerRepository.findById(id).orElse(null);
        if (player != null) {
            playerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private List<Player> getPageFromList(List<Player> playersRaw, Map<String, String> params){
        PlayerOrder order = PlayerOrder.ID;
        int pageNumber = 0;
        int pageSize = 3;
        if (params.containsKey("order"))
            order = PlayerOrder.valueOf(params.get("order"));
        if (params.containsKey("pageNumber"))
            pageNumber = Integer.parseInt(params.get("pageNumber"));
        if (params.containsKey("pageSize"))
            pageSize = Integer.parseInt(params.get("pageSize"));
        playerFilter.sortList(playersRaw, order);
        PagedListHolder<Player> players = new PagedListHolder<>(playersRaw);
        players.setPageSize(pageSize);
        players.setPage(pageNumber);

        return players.getPageList();
    }
}
