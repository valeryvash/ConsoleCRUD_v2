package controller;

import model.Post;
import model.PostStatus;
import model.Tag;
import model.Writer;
import repository.implementation.GsonPostRepositoryImpl;
import repository.implementation.GsonTagRepositoryImpl;
import repository.implementation.GsonWriterRepositoryImpl;
import repository.interfaces.PostRepository;
import repository.interfaces.TagRepository;
import repository.interfaces.WriterRepository;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostViewController {

    private final WriterRepository wr = new GsonWriterRepositoryImpl();

    private final PostRepository pr = new GsonPostRepositoryImpl();

    private final TagRepository tr = new GsonTagRepositoryImpl();

    private final Scanner sc = new Scanner(System.in);

    void showPost(Post p) {
        System.out.println(p);
        tr.getTagsStreamForPost(p).forEach(System.out::println);
    }

    public void postCreate() {
        Writer w = new WriterViewController().getWriter();
        Post p = new Post();
        System.out.println("Input new post contain \n 'q' for quit");

        String s = sc.nextLine();
        if (s.equalsIgnoreCase("q")) System.exit(0);
        p.setPostContent(s);

        getTagsStream().map(Tag::getId).forEach(p::addTagId);
        p.setId(pr.getFreeId());
        pr.add(p);
        w.addPost(p);
        wr.update(w);
        System.out.println("Post created");
        showPost(p);
    }

    private Stream<Tag> getTagsStream() {
        Stream<Tag> result = Stream.empty();
        System.out.println("Input tags. 's' for skip, 'q' for quit");
        do {
            String s = sc.nextLine();

            if (s.equalsIgnoreCase("q")) System.exit(0);
            if (s.equalsIgnoreCase("s")) break;

            if (tr.tagNameContains(s)){
                result = Stream.concat(result, Stream.of(tr.getByName(s)));
            } else {
                Tag t = new Tag(tr.getFreeId(), s);
                tr.add(t);
                result = Stream.concat(result, Stream.of(t));
            }
        } while (true);
        return result;
    }

    public void getAllPosts() {
        pr.getObjectsStream().forEach(this::showPost);
    }

    public void getPostsByStatus() {
        PostStatus ps = getPostStatus();
        pr.getObjectsStream()
                .filter(p -> getPostStatus() == ps)
                .forEach(this::showPost);
    }

    private PostStatus getPostStatus() {
        System.out.println("Choose post status which you prefer\n" +
                "1. ACTIVE\n" +
                "2. DELETED\n" +
                " 'q' for quit");
        do {
            String s = sc.nextLine().toLowerCase();
            if (s.equals("q")) System.exit(0);
            switch (s) {
                case "1" -> {
                    return PostStatus.ACTIVE;
                }
                case "2" -> {
                    return PostStatus.DELETED;
                }
                default -> System.out.println("Wrong point. Input other");
            }
        } while (true);
    }

    public Post getPostById() {
        Post p = new Post();
        System.out.println("Input exist post id \n 'q' for quit");
        do {
            String s = sc.nextLine();
            if (s.equalsIgnoreCase("q")) System.exit(0);
                try {
                    Long id = Long.valueOf(s);
                    if (pr.containsId(id)) {
                        p = pr.getById(id);
                        showPost(p);
                        return p;
                    }
                } catch (NumberFormatException ignored) {}
            System.out.println("Such post doesn't exist! Try again");
        } while (true);
    }

    public void getPostsByTags() {
        List<Tag> tagsList = getTagsStream().toList();
        if (tagsList.isEmpty()) {
            pr.getObjectsStream()
                    .filter(p ->  p.getTagsIdStream().findAny().isEmpty())
                    .forEach(System.out::println);
        } else {
            pr.getObjectsStream()
                    .filter(p -> p.getTagsIdStream().anyMatch(ptId -> tagsList.contains(ptId)))
                    .forEach(System.out::println);
        }
    }

    public void updatePostContentById() {
        Post p = getPostById();

        System.out.println("Input new post contain \n 'q' for quit");

        String s = sc.nextLine();
        if (s.equalsIgnoreCase("q")) System.exit(0);
        p.setPostContent(s);

        pr.update(p);
        System.out.println("Post content updated");
        showPost(p);
    }


    public void updatePostTagsById() {
        Post p = getPostById();
        Stream<Tag> tagStream = getTagsStream();
        p.setTagsIdStream(tagStream.map(Tag::getId));
        pr.update(p);
        System.out.println("Post tags updated");
        showPost(p);
    }

    public void changePostStatusById() {
        Post p = getPostById();
        PostStatus ps = getPostStatus();
        p.setPostStatus(ps);
        pr.update(p);
        showPost(p);
    }

    public void deletePostById() {
        Post p = getPostById();
        pr.delete(p);
        System.out.println("Post deleted");
    }

    public void deletePostsByStatus() {
        PostStatus ps = getPostStatus();
        pr.deleteByStatus(ps);
        System.out.println("Posts deleted");
    }
}
