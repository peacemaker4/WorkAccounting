package org.itstep.workaccounting.services;

import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService extends UserDetailsService {
    DbUser getUser(String email);

    DbUser getUser(Long id);

    DbUser updateUser(DbUser user);

    boolean deleteUser(Long id);

    List<DbUser> getUsers();

    List<DbUser> getUsersPaged(int currentPage, int length);

    DbUser registerUser(DbUser dbUser);
}

