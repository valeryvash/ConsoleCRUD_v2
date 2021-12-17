package repository.implementation;

import com.google.gson.reflect.TypeToken;
import model.Post;
import model.Tag;
import model.Writer;
import repository.interfaces.TagRepository;
import repository.util.RepositoryUtil;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class GsonTagRepositoryImpl implements TagRepository {

    private final String workFolder;

    private static final String fileName = "tags.json";

    private static final Type typeToken = new TypeToken<List<Tag>>(){}.getType();

    private final RepositoryUtil ru;

    public GsonTagRepositoryImpl() {
        this.workFolder = "src/main/resources/";
        File file  = new File(workFolder + fileName);
        this.ru = new RepositoryUtil(file,typeToken);
    }

    public GsonTagRepositoryImpl(String workFolder) {
        this.workFolder = workFolder;
        File file = new File(workFolder + fileName);
        this.ru = new RepositoryUtil(file,typeToken);
    }

    @Override
    public void add(Tag t) throws IllegalArgumentException {
        ru.add(t);
    }

    @Override
    public boolean contains(Tag t) {
        return ru.contains(t);
    }

    @Override
    public boolean containsId(Long id) {
        return ru.containsId(id);
    }

    @Override
    public Tag getById(Long id) throws IllegalArgumentException {
        return ru.getById(id);
    }

    @Override
    public void update(Tag t) throws IllegalArgumentException {
        ru.update(t);
    }

    @Override
    public void delete(Tag t) throws IllegalArgumentException {
        ru.delete(t);
    }

    @Override
    public void deleteById(Long id) throws IllegalArgumentException {
        this.delete(this.getById(id));
    }

    @Override
    public Stream<Tag> getObjectsStream() {
        return ru.readObjectsStream();
    }

    private void writeObjectsStream(Stream<Tag> s) {
        ru.writeObjectsStream(s);
    }

    @Override
    public Long getFreeId() throws IllegalArgumentException {
        return ru.getFreeId();
    }

    @Override
    public boolean tagNameContains(String tagName) {
        return this.getObjectsStream().map(Tag::getTagName).anyMatch(name -> name.equalsIgnoreCase(tagName));
    }

    @Override
    public Tag getByName(String tagName) throws IllegalArgumentException {
        Optional<Tag> tag =
                getObjectsStream()
                        .filter(n -> n.getTagName().equalsIgnoreCase(tagName))
                        .findFirst();
        if (tag.isEmpty()) {
            throw new IllegalArgumentException("Object not exist." +
                    "Always check the object availability with contains() method call");
        } else {
            return tag.get();
        }
    }

    @Override
    public Stream<Tag> getTagsStreamForPost(Post p) {
        return getObjectsStream().filter(t -> p.getTagsIdStream().anyMatch(id -> Objects.equals(t.getId(), id)));
    }
}
