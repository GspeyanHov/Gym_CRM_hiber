package org.epam.gymCrmHiber.repository.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.epam.gymCrmHiber.repository.TrainingRepository;
import org.epam.gymCrmHiber.entity.Training;
import org.epam.gymCrmHiber.entity.TrainingType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Repository
public class TrainingRepositoryImpl implements TrainingRepository {

    private final SessionFactory sessionFactory;

    @Override
    public void create(Training training) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(training);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException("Unable to create training", e);
        }
    }

    @Override
    public Optional<List<Training>> selectByType(TrainingType type) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT t FROM Training t JOIN t.types tt WHERE tt.type = :type_name";
            val query = session.createQuery(hql, Training.class);
            query.setParameter("type_name", type.getType().name()).list();
            return Optional.ofNullable(query.getResultList().isEmpty() ? null : query.getResultList());
        } catch (HibernateException e) {
            throw new HibernateException("Unable to select training", e);
        }
    }

    @Override
    public Optional<List<Training>> getTraineeTrainings(String username, LocalDateTime fromDate, LocalDateTime toDate, String trainerName, TrainingType trainingType) {
        try (Session session = sessionFactory.openSession()) {
            String hql = """
                    select t from Training t \
                    join t.trainees tr \
                    join t.types tt \
                    join t.trainers tn \
                    where tr.username = :username \
                    and t.trainingDate between :fromDate and :toDate \
                    and tt.type = :type_name \
                    and tn.firstname like :trainerName""";
            val trainingQuery = session.createQuery(hql, Training.class)
                    .setParameter("username", username)
                    .setParameter("fromDate", fromDate)
                    .setParameter("toDate", toDate)
                    .setParameter("trainerName", "%" + trainerName + "%")
                    .setParameter("type_name", "%" + trainingType.getType().name() + "%");
            List<Training> trainings = trainingQuery.getResultList();
            if (trainings.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(trainings);
            }
        } catch (HibernateException e) {
            throw new HibernateException("Unable to select trainings for " + username, e);
        }
    }


    @Override
    public Optional<List<Training>> getTrainerTrainings(String username, LocalDateTime fromDate, LocalDateTime toDate, String traineeName) {
        try (Session session = sessionFactory.openSession()) {
            String hql = """
                    select t from Training t \
                    join t.trainers tr \
                    join t.trainees tn \
                    where tr.username = :username \
                    and t.trainingDate between :fromDate and :toDate \
                    and tn.username like :traineeName""";
            val trainingQuery = session.createQuery(hql, Training.class)
                    .setParameter("username", username)
                    .setParameter("fromDate", fromDate)
                    .setParameter("toDate", toDate)
                    .setParameter("traineeName", "%" + traineeName + "%");
            List<Training> trainings = trainingQuery.getResultList();
            if (trainings.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(trainings);
            }
        } catch (HibernateException e) {
            throw new HibernateException("Unable to select trainings for " + username, e);
        }
    }
}


