package com.game.util;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Updater {
    public void updatePlayer(Player old, Player update) {
        String name = update.getName();
        String title = update.getTitle();
        Race race = update.getRace();
        Profession profession = update.getProfession();
        Integer experience = update.getExperience();
        Date birthday = update.getBirthday();
        Boolean banned = update.getBanned();

        if (name != null)
            old.setName(name);
        if (title != null)
            old.setTitle(title);
        if (race != null)
            old.setRace(race);
        if (profession != null)
            old.setProfession(profession);
        if (birthday != null)
            old.setBirthday(birthday);
        if (banned != null)
            old.setBanned(banned);
        if (experience != null) {
            old.setExperience(experience);
            resetLevelAndExperience(old);
        }
    }

    private Integer getCurrentLevel(Integer experience) {
        return (int) ((Math.sqrt(2500 + 200 * experience) - 50) / 100);
    }

    private Integer getExperienceToNextLevel(Integer level, Integer experience) {
        return (50 * (level + 1) * (level + 2)) - experience;
    }

    public void resetLevelAndExperience(Player player) {
        Integer level = getCurrentLevel(player.getExperience());
        Integer experience = getExperienceToNextLevel(level, player.getExperience());

        player.setLevel(level);
        player.setUntilNextLevel(experience);
    }
}
