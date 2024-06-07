package org.epam.gymCrmHiber.entity;

import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trainers")
public class Trainer extends User {

    @Column(name = "specialization")
    private String specialization;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToMany
    @JoinTable(
            name = "trainer_trainees",
            joinColumns = {@JoinColumn(name = "trainer_id")},
            inverseJoinColumns = {@JoinColumn(name = "trainee_id")}
    )
    private Set<Trainee> trainees;

}