package lt.toda.app;

import java.io.*;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        File dataFile = new File("./data/data.csv");

        boolean runApp = true;

        while (runApp) {
            System.out.print("\nWrite action for data managing (print / add / exit): ");
            switch (scanner.nextLine().toLowerCase()) {
                case "print": {
                    System.out.printf("\n%s", getData(dataFile));
                    break;
                }
                case "add": {
                    Employee employee = new Employee();

                    System.out.print("\tenter name: ");
                    employee.setFirstName(parseInput("word"));

                    System.out.print("\tenter occupation: ");
                    employee.setOccupation(parseInput("word"));

                    System.out.print("\tenter salary: ");
                    employee.setSalary(Integer.parseInt(parseInput("number")));

                    appendData(dataFile, employee.toString());
                    break;
                }
                case "exit": {
                    runApp = false;
                    break;
                }
                default: {
                    System.out.println("\tunknown action, try again...");
                    break;
                }
            }
        }
    }

    static StringBuilder getData(File file) {
        StringBuilder fileData = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String fileLine = bufferedReader.readLine();
            while (fileLine != null) {
                for (String line : fileLine.split(",")) {
                    fileData.append(String.format(" %-20s |", line));
                }
                fileData.append("\n");
                fileLine = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.printf("\terror: data file \"%s\" not found\n", file);
        }
        return fileData;
    }

    static String parseInput(String type) {
        while (true) {
            String input = scanner.nextLine();
            if ("word".equals(type) && input.matches("(\\p{L}+\\s?)+")
                    || "number".equals(type) && input.matches("[0-9]+")) {
                return input;
            } else {
                System.out.print("\tdata type mismatch, please enter valid characters: ");
            }
        }
    }

    static void appendData(File file, String newData) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(newData);
            System.out.println("\nNew data saved successfully!");
        } catch (Exception e) {
            System.out.printf("\terror: data file \"%s\" not found\n", file);
        }
    }
}
