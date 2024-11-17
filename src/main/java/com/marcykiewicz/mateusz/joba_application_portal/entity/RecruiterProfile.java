package com.marcykiewicz.mateusz.joba_application_portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "recruiter_profile")
public class RecruiterProfile {

    @Id
    private Long id;

    @OneToOne(targetEntity = User.class, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "user_account_id")
    @MapsId
    private User user;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "company")
    private String company;

    @Column(name = "profile_photo", nullable = true, length = 64)
    private String profilePhoto;

    public RecruiterProfile(User user) {
        this.user = user;
    }
}
