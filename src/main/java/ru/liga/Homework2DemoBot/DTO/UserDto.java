package ru.liga.Homework2DemoBot.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.liga.Homework2DemoBot.Model.Gender;
import ru.liga.Homework2DemoBot.Model.User;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private long id;
    private long userId;
    private String gender;
    private String name;
    private String description;
    private String searchGender;

    private String status;


    public UserDto(User user) {
        this.id = user.getId();
        this.gender = user.getGender().toString();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.description = user.getDescription().toString();
        this.searchGender = user.getSearchGender().toString();
    }
}
