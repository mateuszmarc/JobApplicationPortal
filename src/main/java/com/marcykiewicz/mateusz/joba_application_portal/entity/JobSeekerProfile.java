package com.marcykiewicz.mateusz.joba_application_portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "job_seeker_profile")
public class JobSeekerProfile implements UserProfile {

    @Id
    private Long id;

    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "user_account_id")
    @MapsId
    private User user;

    @NotNull(message = "First name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "work_authorization")
    private String workAuthorization;

    @Column(name = "employment_type")
    private String employmentType;

    @Column(name = "resume")
    private String resume;

    @Column(name = "profile_photo", nullable = true, length = 64)
    private String profilePhoto;

    @ToString.Exclude
    @OneToMany(targetEntity = Skills.class,
            mappedBy = "jobSeekerProfile",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Skills> skills = new ArrayList<>();

    public JobSeekerProfile(User user) {
        this.user = user;
    }

    @Transient
    public String getPhotosImagePath() {
        String photosPath = null;
        if (profilePhoto != null) {
            photosPath = "/photos/job-seeker/" + id + "/" + profilePhoto;
        }
        return photosPath;
    }
}
