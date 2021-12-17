package repository.interfaces;

import model.Writer;

import java.util.stream.Stream;

public interface WriterRepository extends GenericRepository<Long, Writer>{

    @Override
    void add(Writer w) throws IllegalArgumentException;

    @Override
    boolean contains(Writer w);

    @Override
    boolean containsId(Long id);

    @Override
    Writer getById(Long id) throws IllegalArgumentException;

    @Override
    void update(Writer w) throws IllegalArgumentException;

    @Override
    void delete(Writer w) throws IllegalArgumentException;

    @Override
    void deleteById(Long id) throws IllegalArgumentException;

    @Override
    Stream<Writer> getObjectsStream();

    @Override
    Long getFreeId() throws IllegalArgumentException;

    boolean writerNameContains(String writerName);

    Writer getByName(String name) throws IllegalArgumentException;

}
