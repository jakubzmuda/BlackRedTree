package ui;

import tree.RedBlackTree;

import java.util.Scanner;

class RedBlackTreeExampleRunner {

    static String addCommand = "add";
    static String deleteCommand = "delete";

    static RedBlackTree redBlackTree = new RedBlackTree();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            String message = redBlackTree.toString() + "\n\n---\n" +
                    "Commands:\n" +
                    "add [x]\n" +
                    "delete [x]\n";
            System.out.println(message);
            processCommand(scanner.nextLine());
        }
    }

    private static void processCommand(String command) {
        try {

            if (command.contains(addCommand) && command.contains(deleteCommand)) {
                throw new RuntimeException();
            } else if (command.startsWith(addCommand)) {
                int key = parseString(command.substring(addCommand.length()+1));
                redBlackTree.insert(key);
            } else if (command.startsWith(deleteCommand)) {
                int key = parseString(command.substring(deleteCommand.length()+1));
                redBlackTree.delete(key);
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            System.out.println("Wrong input format. Try again.");
        }
    }

    private static int parseString(String croppedCommand) {
        try {
            return Integer.parseInt(croppedCommand);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
