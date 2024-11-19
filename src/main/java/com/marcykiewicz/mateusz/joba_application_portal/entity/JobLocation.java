package com.marcykiewicz.mateusz.joba_application_portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "job_location")
public class JobLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "City is required")
    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @NotNull(message = "Country is required")
    @Column(name = "country")
    private String country;

    @ToString.Exclude
    @OneToMany(
            targetEntity = JobPostActivity.class,
            mappedBy = "jobLocation",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            },
            fetch = FetchType.LAZY
    )
    private List<JobPostActivity> jobPostActivities;

    public JobLocation(Long id, String city, String state, String country) {
        this.id = id;
        this.city = city;
        this.state = state;
        this.country = country;
    }
}
