package ru.liga.Homework2DemoBot.Cache;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.Homework2DemoBot.DTO.UserDto;
import ru.liga.Homework2DemoBot.Model.BotState;
import ru.liga.Homework2DemoBot.Model.Gender;
import ru.liga.Homework2DemoBot.Model.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@ToString
@Component
public class UserCache {

    private List<User> users = new ArrayList<>();


    public void addPersonCache(Long userId, BotState botState) {
        if (!containsKey(userId)) {
            log.info("Add to cache user: " + userId);
            users.add(User.builder()
                    .userId(userId)
                    .botState(botState)
                    .pageCounter(1)
                    .build());
        }
    }

    public void setPersonCache(Long userId, UserDto userDto) {
        User user = getUsersCurrentPerson(userId);
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setDescription(new StringBuilder(userDto.getDescription()));
        user.setGender(Gender.valueOf(userDto.getGender()));
        user.setSearchGender(Gender.valueOf(userDto.getSearchGender()));
        user.setBotState(BotState.PROFILE_DONE);
        user.setPageCounter(1);
        users.add(user);
        log.info("Set to user: " + userId + " from DTO " + user);
    }

    public void setNameAndDescription(String str, Long userId, String reg) {
        User user = getUsersCurrentPerson(userId);
        String[] params = str.split(reg);
        for (int i = 0; i < params.length; i++) {
            if (i == 0) {
                user.setName(params[0]);
                user.setDescription(new StringBuilder());
            } else {
                if (user.getDescription() == null) {
                    user.setDescription(user.getDescription().append(params[i]));
                } else {
                    user.setDescription(user.getDescription().append(" ").append(params[i]));
                }
            }
        }
        log.info("Set to user: " + userId + " name, descr " + user);
    }

    public String getNameAndDescription(Long userId) {
        User result = getUsersCurrentPerson(userId);
        return result.getName() + "\n" + result.getDescription();
    }


    public void setNewState(Long userId, BotState botState) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                user.setBotState(botState);
            }
        }
        log.info("Set to user: " + userId + " state - " + botState);
    }


    public void setNewGender(Long userId, Gender gender) {
        User user = getUsersCurrentPerson(userId);
        user.setGender(gender);
        log.info("Set to user: " + userId + " gender - " + gender);
    }

    public void setTypeSearch(Long userId, Gender gender) {
        User user = getUsersCurrentPerson(userId);
        user.setSearchGender(gender);
        log.info("Set to user: " + userId + " genderSearch - " + gender);
    }


    public BotState getUsersCurrentBotState(Long userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user.getBotState();
            }
        }
        return BotState.DEFAULT;
    }


    public User getUsersCurrentPerson(Long userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return User.builder().userId(userId).build();
    }


    public boolean containsKey(Long userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
    public void setPages(Long userId, int counter) {
        User user = getUsersCurrentPerson(userId);
        user.setPages(counter);
        log.info("Set to user: " + userId + " pages - " + counter);
    }

    /**
     * Получение общего количества страниц в поиске/любимцах
     *
     * @param userId Id пользака из телеграмма
     * @return Кол-во страниц
     */
    public int getPages(Long userId) {
        User user = getUsersCurrentPerson(userId);
        return user.getPages();
    }

    /**
     * Установление текущего id профиля в поиске
     *
     * @param userId        Id пользака из телеграмма
     * @param likedPersonId Id профиля
     */
    public void setLikedPersonId(Long userId, Long likedPersonId) {
        User user = getUsersCurrentPerson(userId);
        user.setLikedPersonId(likedPersonId);
        log.info("Set to user: " + userId + " likedPersonId - " + likedPersonId);
    }

    /**
     * Получение текущего id профиля в поиске
     *
     * @param userId Id пользака из телеграмма
     * @return Id профиля
     */
    public Long getLikedPersonId(Long userId) {
        User user = getUsersCurrentPerson(userId);
        return user.getLikedPersonId();
    }

    /**
     * Сброс общего количества страниц в поиске/любимцах
     *
     * @param userId Id пользака из телеграмма
     */
    public void resetPagesCounter(Long userId) {
        User user = getUsersCurrentPerson(userId);
        user.setPageCounter(1);
    }

    /**
     * Увелечение на 1 номера страницы на которой находимся
     *
     * @param userId Id пользака из телеграмма
     * @return Номер страницы
     */
    public int incrementPagesCounter(Long userId) {
        User user = getUsersCurrentPerson(userId);
        int counter = user.getPageCounter();
        int pages = user.getPages();
        if (counter < pages) {
            user.setPageCounter(counter + 1);
        } else {
            user.setPageCounter(1);
        }
        int resultCounter = user.getPageCounter();
        log.info("Set to user: " + userId + " pagesCounter - " + resultCounter);
        return resultCounter;
    }

    /**
     * Уменьшение на 1 номера страницы на которой находимся
     *
     * @param userId Id пользака из телеграмма
     * @return Номер страницы
     */
    public int minusPagesCounter(Long userId) {
        User user = getUsersCurrentPerson(userId);
        int counter = user.getPageCounter();
        int pages = user.getPages();
        if (counter == 1) {
            user.setPageCounter(pages);
        } else {
            user.setPageCounter(counter - 1);
        }
        int resultCounter = user.getPageCounter();
        log.info("Set to user: " + userId + " pagesCounter - " + resultCounter);
        return resultCounter;
    }

}