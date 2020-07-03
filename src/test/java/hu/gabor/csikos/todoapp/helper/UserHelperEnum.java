package hu.gabor.csikos.todoapp.helper;

import lombok.Getter;

@Getter
public enum UserHelperEnum {

    ADMIN("admin", "PW", "ADMIN"), USER("user", "PW", "USER");


    private String userName;
    private String passWord;
    private String role;

    UserHelperEnum(String userName, String passWord, String role) {
        this.passWord = passWord;
        this.userName = userName;
        this.role = role;
    }

}