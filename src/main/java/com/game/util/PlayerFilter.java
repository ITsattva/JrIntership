package com.game.util;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PlayerFilter {

    public List<Player> filterPlayersList(Map<String, String> params, List<Player> players) {
        if (params.containsKey("name")) {
            String name = params.get("name");
            players = players.stream().filter(x -> x.getName().contains(name)).collect(Collectors.toList());
        }
        if (params.containsKey("title")) {
            String title = params.get("title");
            System.out.println(title);
            players = players.stream().filter(x -> x.getTitle().contains(title)).collect(Collectors.toList());
        }
        if (params.containsKey("race")) {
            Race race = Race.valueOf(params.get("race"));
            players = players.stream().filter(x -> x.getRace().equals(race)).collect(Collectors.toList());
        }
        if (params.containsKey("profession")) {
            Profession profession = Profession.valueOf(params.get("profession"));
            players = players.stream().filter(x -> x.getProfession() == profession).collect(Collectors.toList());
        }
        if (params.containsKey("after")) {
            Long after = Long.parseLong(params.get("after"));
            System.out.println("after: " + after);
            players = players.stream().filter(x -> x.getBirthday().getTime() > after).collect(Collectors.toList());
        }
        if (params.containsKey("before")) {
            Long before = Long.parseLong(params.get("before"));
            System.out.println("before: " + before);
            players = players.stream().filter(x -> x.getBirthday().getTime() < before).collect(Collectors.toList());
        }
        if (params.containsKey("banned")) {
            Boolean banned = Boolean.valueOf(params.get("banned"));
            players = players.stream().filter(x -> x.getBanned() == banned).collect(Collectors.toList());
        }
        if (params.containsKey("minLevel")) {
            Integer minLevel = Integer.parseInt(params.get("minLevel"));
            players = players.stream().filter(x -> x.getLevel() >= minLevel).collect(Collectors.toList());
        }
        if (params.containsKey("maxLevel")) {
            Integer maxLevel = Integer.parseInt(params.get("maxLevel"));
            players = players.stream().filter(x -> x.getLevel() <= maxLevel).collect(Collectors.toList());
        }
        if (params.containsKey("minExperience")) {
            Integer minExperience = Integer.parseInt(params.get("minExperience"));
            System.out.println("minExperience: " + minExperience);
            players = players.stream().filter(x -> x.getExperience() >= minExperience).collect(Collectors.toList());
        }
        if (params.containsKey("maxExperience")) {
            Integer maxExperience = Integer.parseInt(params.get("maxExperience"));
            System.out.println("maxExperience: " + maxExperience);
            players = players.stream().filter(x -> x.getExperience() <= maxExperience).collect(Collectors.toList());
        }

        return players;
    }

    public void sortList(List<Player> players, PlayerOrder order) {
        switch (order) {
            case ID:
                players.sort(Comparator.comparing(Player::getId));
                break;
            case NAME:
                players.sort(Comparator.comparing(Player::getName));
                break;
            case LEVEL:
                players.sort(Comparator.comparing(Player::getLevel));
                break;
            case BIRTHDAY:
                players.sort(Comparator.comparing(Player::getBirthday));
                break;
            case EXPERIENCE:
                players.sort(Comparator.comparing(Player::getExperience));
                break;
        }
    }


}
