package opixels.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String gender;
    private String favoriteGame;
    private String interests;
    private String aboutMe;

    public User(String username, String password, String gender, String favoriteGame,
                String interests, String aboutMe) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.favoriteGame = favoriteGame;
        this.interests = interests;
        this.aboutMe = aboutMe;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getFavoriteGame() {
        return favoriteGame;
    }

    public String getInterests() {
        return interests;
    }

    public String getAboutMe() {
        return aboutMe;
    }
}
