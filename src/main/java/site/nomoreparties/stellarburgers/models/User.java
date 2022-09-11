package site.nomoreparties.stellarburgers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;
    private String email;
    private String password;

    public void setRandom() {
        this.name = RandomStringUtils.randomAlphanumeric(8);
        this.email = RandomStringUtils.randomAlphanumeric(8) + "@gmail.com";
        this.password = RandomStringUtils.randomAlphanumeric(8);
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}