package org.epam.gymCrmHiber.repository.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.epam.gymCrmHiber.repository.TraineeRepository;
import org.epam.gymCrmHiber.entity.Trainee;
import org.epam.gymCrmHiber.entity.Trainer;
import org.epam.gymCrmHiber.util.UsernamePasswordUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Repository
public class TraineeRepositoryImpl implements TraineeRepository {

    private final SessionFactory sessionFactory;

    @Override
    public void create(Trainee trainee) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            String username = UsernamePasswordUtil.generateUsername(trainee.getFirstname(), trainee.getLastname());
            String password = UsernamePasswordUtil.generatePassword();
            trainee.setUsername(username);
            trainee.setPassword(password);
            trainee.setAddress(trainee.getAddress());
            trainee.setBirthdate(trainee.getBirthdate());
            tx = session.beginTransaction();
            session.save(trainee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException("Error while creating trainee", e);
        }
    }

    @Override
    public void update(Trainee trainee) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(trainee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException("Error while updating trainee", e);
        }
    }

    @Override
    public void delete(String username) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(session.get(Trainee.class, username));
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException("Error while deleting trainee", e);
        }
    }

    @Override
    public Optional<Trainee> selectByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            val query = session.createQuery("FROM Trainee t WHERE t.username = :username", Trainee.class);
            query.setParameter("username", username);
            return Optional.ofNullable(query.uniqueResult());
        } catch (HibernateException e) {
            throw new HibernateException("Error while selecting trainee", e);
        }
    }

    @Override
    public Optional<List<Trainee>> listAll() {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.createQuery("FROM Trainee", Trainee.class)
                    .list());
        }
    }

    @Override
    public void changeTraineesPassword(String username, String password) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Trainee trainee = session.createQuery("FROM Trainee WHERE username = :username", Trainee.class)
                    .setParameter("username", username)
                    .uniqueResult();
            if (trainee != null) {
                trainee.setPassword(password);
                session.update(trainee);
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
    public void activateTrainee(String username) {
        setTraineeActiveStatus(username, true);
    }

    @Override
    public void deactivateTrainee(String username) {
        setTraineeActiveStatus(username, false);
    }

    @Override
    public void updateTraineeTrainers(String username, List<Trainer> trainers) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Trainee trainee = session.createQuery("FROM Trainee WHERE username = :username", Trainee.class)
                    .setParameter("username", username)
                    .uniqueResult();
            if (trainee != null) {
                trainee.setTrainers(new HashSet<>(trainers));
                session.update(trainee);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException("Error while updating trainee " + username + " trainers", e);
        }
    }

    private void setTraineeActiveStatus(String username, boolean activate) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Trainee trainee = session.createQuery("FROM Trainee WHERE username = :username", Trainee.class)
                    .setParameter("username", username)
                    .uniqueResult();
            if (trainee != null) {
                trainee.setActive(activate);
                session.update(trainee);
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
