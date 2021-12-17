package view;

import controller.PostViewController;
import model.Post;

import java.util.Scanner;

public class PostView {

    private static String postViewMessage =
            " :.:.:.: Posts :.:.:.:  \n" +
                    "Input the point for continue...\n" +
                    "1. Create a new post for writer \n" +
                    "2. Show all posts\n" +
                    "3. Show all post by status\n" +
                    "4. Show post by id\n" +
                    "5. Show posts by tags\n" +
                    "6. Update post content by post id\n" +
                    "7. Update post tags by post id\n" +
                    "8. Change post status by post id\n" +
                    "9. Delete post by id\n" +
                    "10. Delete posts by status\n"+

                    "\t'q' for quit\n" +
                    "\t'p' for previous screen\n";

    private static Scanner sc = new Scanner(System.in);

    private static PostViewController pvc = new PostViewController();

    public static void run() {
        System.out.println(postViewMessage);
        choice();
    }

    private static void choice() {
        switch (sc.nextLine().toLowerCase()) {
            case "1" -> pvc.postCreate();
            case "2" -> pvc.getAllPosts();
            case "3" -> pvc.getPostsByStatus();
            case "4" -> pvc.getPostById();
            case "5" -> pvc.getPostsByTags();//23.11.2021
            case "6" -> pvc.updatePostContentById();//23.11.2021
            case "7" -> pvc.updatePostTagsById();//23.11.2021
            case "8" -> pvc.changePostStatusById();//23.11.2021
            case "9" -> pvc.deletePostById();//23.11.2021
            case "10" -> pvc.deletePostsByStatus();//23.11.2021

            case "q" -> System.exit(0);
            case "p" -> StartView.run();
            default -> System.out.println("Wrong point. Try another");
        }
        question();
    }

    private static void question() {
        System.out.print("Show 'PostView' again? 'y' for yes\t");
        switch (sc.nextLine().toLowerCase()) {
            case "y" -> PostView.run();
            default -> System.exit(0);
        }
    }

}
