package org.itstep.workaccounting.services;

import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.Role;
import org.itstep.workaccounting.repositories.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@EnableWebSecurity
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public UserServiceImpl(final UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DbUser getUser(String email) {
        return userRepository.findDbUserByEmail(email);
    }

    @Override
    public DbUser updateUser(DbUser user) {
        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    @Override
    public List<DbUser> getUsers() {
        return null;
    }

    @Override
    public List<DbUser> getUsersPaged(int currentPage, int length, Role role) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        DbUser dbUser = userRepository.findDbUserByEmail(email);
        if(Objects.nonNull(dbUser)){
            User securityDbUser = new User(dbUser.getEmail(), dbUser.getPassword(), dbUser.getRoles());
            return securityDbUser;
        }
        else{
            return null;
        }
    }
}
