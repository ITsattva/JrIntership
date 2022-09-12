package com.game.util;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ValidationHelper {

    public boolean validateNewPlayer(Player player) {
        String name = player.getName();
        String title = player.getTitle();
        Integer experience = player.getExperience();
        Date birthday = player.getBirthday();

        if (name == null || name.length() > 12 || name.equals("")) {
            return false;
        } else if (title.length() > 30) {
            return false;
        } else if (experience == null || experience > 10_000_000 || experience < 0) {
            return false;
        } else return birthday.getYear() >= (2000 - 1900) && birthday.getYear() <= (3000 - 1900);
    }

    public boolean validateForUpdate(Player player) {
        String name = player.getName();
        String title = player.getTitle();
        Integer experience = player.getExperience();
        Date birthday = player.getBirthday();

        if (name != null)
            if (name.length() > 12 || name.equals("")) {
                return false;
            }
        if (title != null)
            if (title.length() > 30) {
                return false;
            }
        if (experience != null)
            if (experience > 10_000_000 || experience < 0) {
                return false;
            }
        if (birthday != null)
            if (birthday.getYear() < (2000 - 1900) || birthday.getYear() > (3000 - 1900)) {
                return false;
            }

        return true;
    }
}