package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            meal.setUser(ref);
            return em.merge(meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        User ref = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.DELETE)
                .setParameter(1, ref.id())
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return em.find(Meal.class, id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        User ref = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter(1, ref.id())
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        User ref = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.BETWEEN_HALF_OPEN, Meal.class)
                .setParameter(1, ref.id())
                .setParameter(2, startDateTime)
                .setParameter(3, endDateTime)
                .getResultList();
    }
}