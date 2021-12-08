package org.itstep.workaccounting.repositories;

import org.itstep.workaccounting.entities.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<DbUser, Long > {
    DbUser findDbUserByEmail(String email);

}
