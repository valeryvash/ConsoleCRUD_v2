package repository.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import model.interfaces.Entity;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepositoryUtil {

    private File file;

    private Type listToken;

    private RepositoryUtil() {}

    public RepositoryUtil(File file, Type listToken) {
        this.file = file;
        this.listToken = listToken;
    }

    public <T extends Entity> Stream<T> readObjectsStream(){
        Stream<T> result = Stream.empty();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String fileContent = br.readLine();
            List<T> buffer = new Gson().fromJson(fileContent, this.listToken);
            if (buffer != null ) result = buffer.stream();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File " + file.getName() + "\n" +
                    "not found at " + file.getPath() + "\n" +
                    ".:Exit:.\n");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO exception during read from file " + file.getName() + "\n" +
                    "from path " + file.getPath() + "\n" +
                    ".:Exit:.\n");
            System.exit(1);
        } catch (JsonSyntaxException e) {
            System.err.println("JsonSyntaxException during read from file "+ file.getName() +"\n" +
                    " from path " + file.getPath() + "\n" +
                    ".:Exit:.");
            System.exit(1);
        }
        return result;
    }

    public <T extends Entity> void writeObjectsStream(Stream<T> stream) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.file))) {
            List<T> buffer = stream.collect(Collectors.toList());
            String fileContent = new Gson().toJson(buffer);
            bw.write(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File " + file.getName() + "\n" +
                    "not found at " + file.getPath() + "\n" +
                    ".:Exit:.\n");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO exception during read from file " + file.getName() + "\n" +
                    "from path " + file.getPath() + "\n" +
                    ".:Exit:.\n");
            System.exit(1);
        }
    }

    public <T extends Entity> void add(T t) throws IllegalArgumentException{
        if ((this.contains(t)) || (t.getId() < 1L)){
            String className = t.getClass().getCanonicalName();
            long id = t.getId();
            throw new IllegalArgumentException("Object of " + className + " class with id: " + id + " already exist.\n" +
                    "Always check the object availability with contains() method call " +
                    " or use update() method instead of add()");
        } else {
            this.writeObjectsStream(
                    Stream.concat(this.readObjectsStream(), Stream.of(t))
            );
        }
    }

    public <T extends Entity> boolean contains(T t) {
        return this.readObjectsStream()
                .anyMatch(t::equals);
    }

    public <T extends Entity> boolean containsId(Long id) {
        return this.readObjectsStream()
                .anyMatch(o -> Objects.equals(o.getId(), id));
    }

    public <T extends Entity> T getById(Long id) throws IllegalArgumentException{
        Optional<Entity> o1 =
                        this.readObjectsStream()
                        .filter(o -> Objects.equals(o.getId(), id))
                        .findFirst();
        if (o1.isEmpty()){
            throw new IllegalArgumentException("Object not exist." +
                    "Always check the object availability with contains() method call ");
        } else {
            return (T) o1.get();
        }
    }

    public <T extends Entity> void update(T t) throws IllegalArgumentException{
        if (!this.contains(t)){
            throw new IllegalArgumentException("Object not exist." +
                    "Always check the object availability with contains() method call");
        } else {
            this.writeObjectsStream(
                    this.readObjectsStream()
                            .map(o -> o.equals(t) ? t : o)
            );
        }
    }

    public <T extends Entity>  void delete(T t) throws IllegalArgumentException{
        if (!this.contains(t)){
            throw new IllegalArgumentException("Object not exist." +
                    "Always check the object availability with contains() method call");
        } else {
            writeObjectsStream(
                    readObjectsStream()
                            .filter(o -> !(o.equals(t)))
            );
        }
    }

    public <T extends Entity> Long getFreeId() {
        long result = 1L;
        Optional<Long> maxId =
                this.readObjectsStream()
                        .map(o -> o.getId())
                        .max(Long::compareTo);
        if (maxId.isPresent()) {
            result = maxId.get() + 1;
        }
        if (this.containsId(result)){
            throw new IllegalArgumentException("getFreeId() method call try to return already existed id");
        } else {
            return result;
        }
    }

}
