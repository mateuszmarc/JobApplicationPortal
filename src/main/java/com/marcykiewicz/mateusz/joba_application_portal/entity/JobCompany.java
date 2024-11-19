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
@Table(name = "job_company")
public class JobCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "logo")
    private String logo;

    @NotNull(message = "Company name is required")
    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(
            targetEntity = JobPostActivity.class,
            mappedBy = "jobCompany",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<JobPostActivity> jobPostActivities;

    public JobCompany(Long id, String logo, String name) {
        this.id = id;
        this.logo = logo;
        this.name = name;
    }
}
