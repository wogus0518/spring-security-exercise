package com.example.springsecurity1.repository;

import com.example.springsecurity1.domain.User;
import com.example.springsecurity1.domain.dto.UserJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public Optional<User> findByLoginId(String loginId) {
        try {
            return Optional.ofNullable(em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                    .setParameter("loginId", loginId)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
