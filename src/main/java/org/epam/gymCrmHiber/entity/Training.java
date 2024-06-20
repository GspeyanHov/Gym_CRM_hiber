package org.epam.gymCrmHiber.entity;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "training_name")
    private String trainingName;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Trainer> trainers;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Trainee> trainees;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<TrainingType> types;

    @Column(name = "training_date")
    private LocalDateTime trainingDate;

    @Column(name = "duration")
    private double duration;

}