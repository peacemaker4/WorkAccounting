package org.itstep.workaccounting.services;

import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.Role;
import org.itstep.workaccounting.repositories.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public DbUser getUser(Long id) {
        return userRepository.findDbUserById(id);
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
        return userRepository.findAll();
    }

    @Override
    public List<DbUser> getUsersPaged(int currentPage, int length) {
        Pageable paging = PageRequest.of(currentPage, length);

        Page<DbUser> pagedResult = userRepository.findAll(paging);

        return pagedResult.getContent();
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

    @Override
    public DbUser registerUser(DbUser dbUser) {
        DbUser checkDbUser = userRepository.findDbUserByEmail(dbUser.getEmail());
        if (Objects.isNull(checkDbUser)) {
            dbUser.setPassword(passwordEncoder().encode(dbUser.getPassword()));
            return userRepository.save(dbUser);
        }
        return null;
    }
}
