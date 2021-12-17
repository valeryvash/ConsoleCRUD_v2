package repository.interfaces;

import model.Post;
import model.PostStatus;
import model.Writer;

import java.util.stream.Stream;

public interface PostRepository extends GenericRepository<Long, Post> {

    @Override
    void add(Post p) throws IllegalArgumentException;

    @Override
    boolean contains(Post p);

    @Override
    boolean containsId(Long id);

    @Override
    Post getById(Long id) throws IllegalArgumentException;

    @Override
    void update(Post p) throws IllegalArgumentException;

    @Override
    void delete(Post p) throws IllegalArgumentException;

    @Override
    void deleteById(Long id) throws IllegalArgumentException;

    @Override
    Stream<Post> getObjectsStream();

    @Override
    Long getFreeId() throws IllegalArgumentException;

    Stream<Post> getPostsStreamByWriter(Writer w);

    void deleteByStatus(PostStatus ps);
}
