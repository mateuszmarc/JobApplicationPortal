package com.marcykiewicz.mateusz.joba_application_portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "skills")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "experience_level")
    private String experienceLevel;

    @Column(name = "name")
    private String name;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @ManyToOne(targetEntity = JobSeekerProfile.class,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "job_seeker_profile")
    private JobSeekerProfile jobSeekerProfile;
}
