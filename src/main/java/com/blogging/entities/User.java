package com.blogging.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table (name="USERS_TBL")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name= "user_name", nullable = false)
    private String name;

    @Column(name= "user_email", nullable = false)
    private String email;

    @Column(name= "user_password", nullable = false)
    private String password;

    @Column(name= "user_info", nullable = false)
    private String about;
}
