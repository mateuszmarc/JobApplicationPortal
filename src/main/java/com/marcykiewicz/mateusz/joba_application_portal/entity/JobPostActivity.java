package com.marcykiewicz.mateusz.joba_application_portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "job_post_activity")
public class JobPostActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_post_id")
    private Long id;

    @NotNull(message = "Job title is required")
    @Column(name = "job_title")
    private String jobTitle;

    @NotNull(message = "Job type is required")
    @Column(name = "job_type")
    private String jobType;

    @NotNull(message = "Job type is required")
    @Length(max = 100, message = "Job description can have 100 characters")
    @Column(name = "description_of_job")
    private String description;

    @Column(name = "posted_date")
    private LocalDateTime postedDateTime;

    @NotNull(message = "This information is required")
    @Column(name = "remote")
    private String remote;

    @Pattern(regexp = "[0-9]*", message = "Invalid format")
    @Column(name = "salary")
    private String salary;

    @ManyToOne(
            targetEntity = JobCompany.class,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "job_company_id")
    private JobCompany jobCompany;

    @ManyToOne(
            targetEntity = JobLocation.class,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "job_location_id")
    private JobLocation jobLocation;

    @ManyToOne(
            targetEntity = User.class,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "posted_by_id")
    private User user;
}
