package controller;

import model.Post;
import model.Writer;
import repository.implementation.GsonPostRepositoryImpl;
import repository.implementation.GsonTagRepositoryImpl;
import repository.implementation.GsonWriterRepositoryImpl;
import repository.interfaces.PostRepository;
import repository.interfaces.TagRepository;
import repository.interfaces.WriterRepository;

import java.util.Scanner;
import java.util.stream.Stream;

public class WriterViewController {

    private final WriterRepository wr = new GsonWriterRepositoryImpl();

    private final PostRepository pr = new GsonPostRepositoryImpl();

    private final TagRepository tr = new GsonTagRepositoryImpl();

    private Scanner sc = new Scanner(System.in);

    public WriterViewController() {}

    public void writerCreate() {
        System.out.println("Input new writer name \n 'q' for quit");
        Writer w = new Writer();
        do {
            String s = sc.nextLine();
            if (s.equalsIgnoreCase("q")) System.exit(0);
            if (!wr.writerNameContains(s)) {
                w.setWriterName(s);
                w.setId(wr.getFreeId());
                wr.add(w);
                System.out.println("New writer created");
                System.out.println(w);
                break;
            } else {
                System.out.println("Writer name already exist! Try another");
            }
        } while (true);
    }

    public Writer getWriter() {
        Writer w = new Writer();
        System.out.println("Input exist writer id or name \n 'q' for quit");
        do {
            String s = sc.nextLine();
            if (s.equalsIgnoreCase("q")) System.exit(0);
            if (!wr.writerNameContains(s)) {
                try {
                    Long id = Long.valueOf(s);
                    if (wr.containsId(id)){
                        w = wr.getById(id);
                        System.out.println(w);
                        return w;
                    }
                } catch (NumberFormatException e) {}
            } else {
                w = wr.getByName(s);
                System.out.println(w);
                return w;
            }
            System.out.println("Such writer doesn't exist! Try again");
        } while (true);
    }

    public void updateWriterName() {
        Writer w = getWriter();
        System.out.println("Input new writer name \n 'q' for quit");
        do {
            String s = sc.nextLine();
            if (s.equalsIgnoreCase("q")) System.exit(0);
            if (!wr.writerNameContains(s)) {
                w.setWriterName(s);
                wr.update(w);
                System.out.println("Writer updated");
                System.out.println(w);
                break;
            } else {
                System.out.println("Such writer name already exist! Try another ");
            }
        } while (true);
    }

    public void writerDelete() {
        Writer w = getWriter();
        do{
            System.out.println("Are you sure want to delete this writer?\n" +
                    " All writer posts will be deleted\n " +
                    "\n 'y' for yes \n 'q' for quit");
            String s = sc.nextLine().toLowerCase();
            if (s.equalsIgnoreCase("q")) System.exit(0);
            if (s.equalsIgnoreCase("y")) {
                wr.delete(w);
                w.getPostsIdStream().forEach(pr::deleteById);
                System.out.println("Writer deleted.");
                break;
            }
        } while(true);
    }

    public Stream<Post> getWriterPosts(boolean print) {
        Writer w = getWriter();
        if (w.getPostsIdStream().findAny().isPresent()){
            Stream<Post> writerPosts = pr.getPostsStreamByWriter(w);
            if (print) {
                writerPosts.forEach(this::accept);
                return pr.getPostsStreamByWriter(w);
            } else {
                return writerPosts;
            }
        } else {
            System.out.println("Writer has no posts");
            return Stream.empty();
        }
    }

    public void getAllWriters() {
        wr.getObjectsStream().forEach(System.out::println);
    }

    private void accept(Post post) {
        System.out.println(post);
        if (post.getTagsIdStream().findAny().isPresent()) {
            tr.getTagsStreamForPost(post).forEach(System.out::println);
        } else {
            System.out.println(" :.:.:.: No tags :.:.:.: ");
        }
    }

}
