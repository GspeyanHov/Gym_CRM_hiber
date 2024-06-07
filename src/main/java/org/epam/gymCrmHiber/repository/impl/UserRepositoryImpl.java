package org.epam.gymCrmHiber.repository.impl;

import org.epam.gymCrmHiber.repository.UserRepository;
import org.epam.gymCrmHiber.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class UserRepositoryImpl<T extends User> implements UserRepository<T> {

    protected final SessionFactory sessionFactory;

    @Override
    @Deprecated
    @SuppressWarnings("unchecked")
    public Optional<T> findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("username", username));
            return Optional.ofNullable((T) criteria.uniqueResult());
        }
    }
}