package repository.interfaces;

import java.util.stream.Stream;

public interface GenericRepository<ID, T> {

    void add(T t) throws IllegalArgumentException;

    boolean contains(T t);

    boolean containsId(ID id);

    T getById(ID id) throws IllegalArgumentException;

    void update(T t) throws IllegalArgumentException;

    void delete(T t) throws IllegalArgumentException;

    void deleteById(ID id) throws IllegalArgumentException;

    Stream<T> getObjectsStream();

    ID getFreeId() throws IllegalArgumentException;
}
