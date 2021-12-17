package repository.implementation;

import com.google.gson.reflect.TypeToken;
import model.Post;
import model.PostStatus;
import model.Tag;
import model.Writer;
import repository.interfaces.PostRepository;
import repository.util.RepositoryUtil;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class GsonPostRepositoryImpl implements PostRepository {

    private final String workFolder;

    private static final String fileName = "posts.json";

    private static final Type typeToken = new TypeToken<List<Post>>(){}.getType();

    private final RepositoryUtil ru;

    public GsonPostRepositoryImpl() {
        this.workFolder = "src/main/resources/";
        File file  = new File(workFolder + fileName);
        this.ru = new RepositoryUtil(file,typeToken);
    }

    public GsonPostRepositoryImpl(String workFolder) {
        this.workFolder = workFolder;
        File file = new File(workFolder + fileName);
        this.ru = new RepositoryUtil(file,typeToken);
    }

    @Override
    public void add(Post p) throws IllegalArgumentException {
        ru.add(p);
    }

    @Override
    public boolean contains(Post p) {
        return ru.contains(p);
    }

    @Override
    public boolean containsId(Long id) {
        return ru.containsId(id);
    }

    @Override
    public Post getById(Long id) throws IllegalArgumentException {
        return ru.getById(id);
    }

    @Override
    public void update(Post p) throws IllegalArgumentException {
        ru.update(p);
    }

    @Override
    public void delete(Post p) throws IllegalArgumentException {
        ru.delete(p);
    }

    @Override
    public void deleteById(Long id) throws IllegalArgumentException {
        this.delete(this.getById(id));
    }

    @Override
    public Stream<Post> getObjectsStream() {
        return ru.readObjectsStream();
    }

    private void writeObjectsStream(Stream<Post> s) {
        ru.writeObjectsStream(s);
    }

    @Override
    public Long getFreeId() throws IllegalArgumentException {
        return ru.getFreeId();
    }

    @Override
    public Stream<Post> getPostsStreamByWriter(Writer w){
        return this.getObjectsStream()
                .filter(p -> w.getPostsIdStream().anyMatch(id -> Objects.equals(id, p.getId())));
    }

    @Override
    public void deleteByStatus(PostStatus ps) {
        writeObjectsStream(
                getObjectsStream().filter(post -> post.getPostStatus() != ps)
        );

    }

    @Override
    public void deleteTagFromPost(Tag t) {
        writeObjectsStream(
                getObjectsStream().map(post -> {
                    post.setTagsIdStream(
                            post.getTagsIdStream()
                                    .filter(id -> !Objects.equals(id, t.getId()))
                    );
                    return post;
                })
        );
    }
}
