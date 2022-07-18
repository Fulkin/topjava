package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> usersWithMealMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, USER_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> mealMap = usersWithMealMap.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> mealMap = usersWithMealMap.get(userId);
        return mealMap != null && mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mealMap = usersWithMealMap.get(userId);
        return mealMap == null ? null : mealMap.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> mealMap = usersWithMealMap.get(userId);
        return  mealMap == null || mealMap.isEmpty() ?
                Collections.emptyList() : mealMap.values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

