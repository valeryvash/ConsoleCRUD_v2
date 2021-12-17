package repository.implementation;

import com.google.gson.reflect.TypeToken;
import model.Post;
import model.Writer;
import repository.interfaces.WriterRepository;
import repository.util.RepositoryUtil;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class GsonWriterRepositoryImpl implements WriterRepository {

    private final String workFolder;

    private static final String fileName = "writers.json";

    private static final Type typeToken = new TypeToken<List<Writer>>(){}.getType();

    private final RepositoryUtil ru;

    public GsonWriterRepositoryImpl() {
        this.workFolder = "src/main/resources/";
        File file = new File(workFolder + fileName);
        this.ru = new RepositoryUtil(file,typeToken);
    }

    public GsonWriterRepositoryImpl(String workFolder) {
        this.workFolder = workFolder;
        File file = new File(workFolder + fileName);
        this.ru = new RepositoryUtil(file,typeToken);
    }

    @Override
    public void add(Writer w) throws IllegalArgumentException{
        ru.add(w);
    }

    @Override
    public boolean contains(Writer w) {
        return ru.contains(w);
    }

    @Override
    public boolean containsId(Long id) {
        return ru.containsId(id);
    }

    @Override
    public Writer getById(Long id) throws IllegalArgumentException{
        return ru.getById(id);
    }

    @Override
    public void update(Writer w) throws IllegalArgumentException{
        ru.update(w);
    }

    @Override
    public void delete(Writer w) throws IllegalArgumentException{
        ru.delete(w);
    }

    @Override
    public void deleteById(Long id) throws IllegalArgumentException {
        this.delete(this.getById(id));
    }

    @Override
    public Stream<Writer> getObjectsStream() {
        return ru.readObjectsStream();
    }

    private void writeObjectsStream(Stream<Writer> s) {
        ru.writeObjectsStream(s);
    }

    @Override
    public Long getFreeId() throws IllegalArgumentException {
        return ru.getFreeId();
    }

    @Override
    public boolean writerNameContains(String writerName) {
        return this.getObjectsStream().map(Writer::getWriterName).anyMatch(name -> name.equalsIgnoreCase(writerName));
    }

    @Override
    public Writer getByName(String writerName) throws IllegalArgumentException {
        Optional<Writer> writer =
                getObjectsStream()
                        .filter(n -> n.getWriterName().equalsIgnoreCase(writerName))
                        .findFirst();
        if (writer.isEmpty()) {
            throw new IllegalArgumentException("Object not exist." +
                    "Always check the object availability with contains() method call");
        } else {
            return writer.get();
        }
    }
}
