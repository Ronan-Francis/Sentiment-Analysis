package ie.atu.sw;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class FileMenuSystem {

    private File folder, currentTextFile;
    private List<String> currentText;

    public List<String> getCurrentText() {
        return currentText;
    }

    public void setCurrentText(List<String> currentText) {
        this.currentText = currentText;
    }

    public File getCurrentTextFile() {
        return currentTextFile;
    }

    public void setCurrentTextFile(File currentTextFile) {
        this.currentTextFile = currentTextFile;
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        
        while (choice != 2) {
            System.out.println("Menu:");
            System.out.println("1. List text files in a folder");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    this.selectTextFiles();
                    break;
                case 2:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void selectTextFiles() {
        if (this.getFolder().exists() && this.getFolder().isDirectory()) {
            File[] files = this.getFolder().listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

            if (files != null && files.length > 0) {
                System.out.println("Text files in the folder:");
                for (int i = 0; i < files.length; i++) {
                    System.out.println((i + 1) + ". " + files[i].getName());
                }

                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the number of the text file you want to select: ");

                int fileNumber = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (fileNumber > 0 && fileNumber <= files.length) {
                    // Set the selected text file as the current text file
                    this.setCurrentTextFile(files[fileNumber - 1]);
                    System.out.println("Selected text file: " + this.getCurrentTextFile().getName());

                    // Read the content of the selected text file and update the currentText list
                    try {
                        this.setCurrentText(Files.readAllLines(this.getCurrentTextFile().toPath()));
                        System.out.println("Content of the text file:");
                        for (String line : this.getCurrentText()) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading the text file: " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } else {
                System.out.println("No text files found in the folder.");
            }
        } else {
            System.out.println("Invalid folder path. Please check the path and try again.");
        }
    }

}
