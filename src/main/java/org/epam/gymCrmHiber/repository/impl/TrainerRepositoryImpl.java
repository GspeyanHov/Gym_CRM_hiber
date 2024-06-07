package org.epam.gymCrmHiber.repository.impl;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.epam.gymCrmHiber.repository.TrainerRepository;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.util.UsernamePasswordUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Transactional
@Repository
public class TrainerRepositoryImpl implements TrainerRepository {

    private final SessionFactory sessionFactory;

    @Override
    public void create(Trainer trainer) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            String username = UsernamePasswordUtil.generateUsername(trainer.getFirstname(), trainer.getLastname());
            String password = UsernamePasswordUtil.generatePassword();
            trainer.setUsername(username);
            trainer.setPassword(password);
            tx = session.beginTransaction();
            session.save(trainer);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException("Failed to create trainer.", e);
        }
    }

    @Override
    public void update(Trainer trainer) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(trainer);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException("Failed to update trainer.", e);
        }
    }

    @Override
    public Optional<Trainer> selectByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            val query = session.createQuery("FROM Trainer t WHERE t.username = :username", Trainer.class);
            query.setParameter("username", username);
            return Optional.ofNullable(query.uniqueResult());
        } catch (HibernateException e) {
            throw new HibernateException("Error while selecting trainee", e);
        }
    }

    @Override
    public void changeTrainersPassword(String username, String password) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Trainer trainer = session.createQuery("FROM Trainer WHERE username = :username", Trainer.class)
                    .setParameter("username", username)
                    .uniqueResult();
            if (trainer != null) {
                trainer.setPassword(password);
                session.update(trainer);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException("Error while changing password", e);
        }
    }

    @Override
    public void activateTrainer(String username) {
        setTrainerActiveStatus(username, true);
    }

    @Override
    public void deactivateTrainer(String username) {
        setTrainerActiveStatus(username, false);
    }

    @Override
    public Optional<List<Trainer>> getUnassignedTrainers(String traineeUsername) {
        try (Session session = sessionFactory.openSession()) {
            String hql = """
                    select tr from Trainer tr
                    where tr.username not in (
                        select tn.username from Trainer tn
                        join tn.trainees t
                        where t.username = :username
                    )""";
            val query = session.createQuery(hql, Trainer.class);
            query.setParameter("username", traineeUsername);
            return Optional.ofNullable(query.getResultList().isEmpty() ? null : query.getResultList());
        } catch (HibernateException e) {
            throw new HibernateException("Unable to fetch unassigned trainers for " + traineeUsername, e);
        }
    }

    private void setTrainerActiveStatus(String username, boolean activate) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Trainer trainer = session.createQuery("FROM Trainer WHERE username = :username", Trainer.class)
                    .setParameter("username", username)
                    .uniqueResult();
            if (trainer != null) {
                trainer.setActive(activate);
                session.update(trainer);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException("Error while activating trainee", e);
        }
    }
}
