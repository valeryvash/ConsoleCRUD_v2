package view;

import controller.WriterViewController;

import java.util.Scanner;

public class WriterView {

    private static String writerViewMessage =
            "Writers\n" +
                    "Input the point for continue...\n" +
                    "1. Create a new writer\n" +
                    "2. Print writer info\n" +
                    "3. Update writer name\n" +
                    "4. Delete writer \n" +
                    "5. Print writer posts\n" +
                    "6. Print all writers info\n" +

                    "\t'q' for quit\n" +
                    "\t'p' for previous screen\n";

    private static Scanner sc = new Scanner(System.in);

    private static WriterViewController wvc = new WriterViewController();

    public static void run() {
        System.out.println(writerViewMessage);
        choice();
    }

    private static void choice() {
        switch (sc.nextLine().toLowerCase()) {
            case "1" -> wvc.writerCreate();
            case "2" -> wvc.getWriter();
            case "3" -> wvc.updateWriterName();
            case "4" -> wvc.writerDelete();
            case "5" -> wvc.getWriterPosts(true);
            case "6" -> wvc.getAllWriters();

            case "q" -> System.exit(0);
            case "p" -> StartView.run();
            default -> System.out.println("Wrong point. Try another");
        }
        question();
    }

    private static void question() {
        System.out.print("Show 'WriterView' again? 'y' for yes\t");
        switch (sc.nextLine().toLowerCase()) {
            case "y" -> WriterView.run();
            default -> System.exit(0);
        }
    }

}
