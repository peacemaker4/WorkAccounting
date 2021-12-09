package org.itstep.workaccounting.models;

import lombok.Data;

@Data
public class UserModel {

    private String email;

    private String fullName;

    private String password;

    private String role;

}

