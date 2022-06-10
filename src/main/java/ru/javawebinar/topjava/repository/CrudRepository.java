package ru.javawebinar.topjava.repository;

import java.util.Collection;

public interface CrudRepository<T> {

    T save(T t);

    void delete(Integer id);

    Collection<T> getAll();

    T getById(Integer id);
}
