package controller;

import model.Tag;
import model.Writer;
import repository.implementation.GsonPostRepositoryImpl;
import repository.implementation.GsonTagRepositoryImpl;
import repository.implementation.GsonWriterRepositoryImpl;
import repository.interfaces.PostRepository;
import repository.interfaces.TagRepository;
import repository.interfaces.WriterRepository;

import java.util.Scanner;

public class TagViewController {

    private final WriterRepository wr = new GsonWriterRepositoryImpl();

    private final PostRepository pr = new GsonPostRepositoryImpl();

    private final TagRepository tr = new GsonTagRepositoryImpl();

    private final Scanner sc = new Scanner(System.in);

    public void printAllTags() {
        tr.getObjectsStream().forEach(System.out::println);
    }

    public void updateTag() {
        Tag t = getTag();
        do {
            System.out.println("Input new tag name");
            String s = sc.nextLine();
            if (s.equalsIgnoreCase("q")) System.exit(0);

            if (!tr.tagNameContains(s)) {
                t.setTagName(s);
                break;
            }
            System.out.println("Such tag name already exist! Try again");
        } while (true);
        tr.update(t);
        System.out.println("Tag successfully updated");
    }

    public void deleteTag() {
        Tag t = getTag();
        pr.deleteTagFromPost(t);
        tr.delete(t);
    }

    private Tag getTag() {
        Tag t = new Tag();
        System.out.println("Input exist tag id or name \n 'q' for quit");
        do {
            String s = sc.nextLine();
            if (s.equalsIgnoreCase("q")) System.exit(0);
            if (!tr.tagNameContains(s)) {
                try {
                    Long id = Long.valueOf(s);
                    if (tr.containsId(id)){
                        t = tr.getById(id);
                        System.out.println(t);
                        return t;
                    }
                } catch (NumberFormatException ignored) {}
            } else {
                t = tr.getByName(s);
                System.out.println(t);
                return t;
            }
            System.out.println("Such tag doesn't exist! Try again");
        } while (true);
    }
}
