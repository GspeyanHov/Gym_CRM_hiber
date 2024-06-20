package org.epam.gymCrmHiber.entity;

import java.time.LocalDate;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
@Table(name = "trainees")
public class Trainee extends User{

    @Column(name = "birth_date")
    private LocalDate birthdate;

    @Embedded
    @Column(name = "address")
    private Address address;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToMany(mappedBy = "trainees", fetch = FetchType.EAGER)
    @Column(name = "trainer_id")
    private Set<Trainer> trainers;
}