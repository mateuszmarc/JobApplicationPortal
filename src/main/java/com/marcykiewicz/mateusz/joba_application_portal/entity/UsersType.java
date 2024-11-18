package com.marcykiewicz.mateusz.joba_application_portal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users_type")
public class UsersType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id")
    private Long userTypeId;

    @Column(name = "user_type_name")
    private String userTypeName;

    @ToString.Exclude
    @OneToMany(targetEntity = User.class,
            mappedBy = "usersType",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            },
            fetch = FetchType.LAZY)
    private List<User> users;


    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }
}
