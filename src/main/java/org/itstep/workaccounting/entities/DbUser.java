package org.itstep.workaccounting.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DbUser extends BaseEntity {

    @Column(name = "email", unique = true)
    private String email;

    private String password;

    private String fullName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

}