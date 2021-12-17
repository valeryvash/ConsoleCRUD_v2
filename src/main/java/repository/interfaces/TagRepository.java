package repository.interfaces;

import model.Post;
import model.Tag;

import java.util.stream.Stream;

public interface TagRepository extends GenericRepository<Long,Tag>{
    @Override
    void add(Tag t) throws IllegalArgumentException;

    @Override
    boolean contains(Tag t);

    @Override
    boolean containsId(Long id);

    @Override
    Tag getById(Long id) throws IllegalArgumentException;

    @Override
    void update(Tag t) throws IllegalArgumentException;

    @Override
    void delete(Tag t) throws IllegalArgumentException;

    @Override
    void deleteById(Long id) throws IllegalArgumentException;

    @Override
    Stream<Tag> getObjectsStream();

    @Override
    Long getFreeId() throws IllegalArgumentException;

    boolean tagNameContains(String tagName);

    public Tag getByName(String tagName) throws IllegalArgumentException;

    public Stream<Tag> getTagsStreamForPost(Post p);
}
