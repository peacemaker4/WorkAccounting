package org.itstep.workaccounting.models;

import lombok.Data;

@Data
public class UserModel {

    private String email;

    private String fullName;

    private String password;

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

}

