package treebasedsearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java search <filename> <method>");
            System.exit(1);
        }

        //Extract filename and method
        String filename = args[0];
        String method = args[1];

//        String filename = "E:\\Java Projects\\TreeBasedSearch\\src\\main\\java\\treebasedsearch\\map4.txt";
//        String method = "dfs";


        // Read the input file
        try {
            List<String> lines = readFile(filename);
            int[] gridDimension = parseGridDimension(lines.get(0)); // First line: grid size [N, M]

            int[] initialPosition = parseCoordinate(lines.get(1)); // Second line: initial state (x, y)
            List<int[]> goalStates = parseGoalStates(lines.get(2)); // Third line: goal states
            List<int[]> walls = parseWalls(lines.subList(3, lines.size())); // Remaining lines: walls

            Search search = new Search(gridDimension, initialPosition, goalStates, walls);
            search.printGrid();

            long startTime = System.nanoTime();
            switch (method.toUpperCase()) {
                case "DFS":
                    System.out.println(filename + " " + method);
                    search.depthFirstSearch(goalStates, walls);
                    runAndMeasureTime(startTime);
                    break;
                case "BFS":
                    System.out.println(filename + " " + method);
                    search.breadthFirstSearch(goalStates, walls);
                    runAndMeasureTime(startTime);
                    break;
                case "GBFS":
                    System.out.println(filename + " " + method);
                    search.greedyBestFirstSearch(goalStates, walls);
                    runAndMeasureTime(startTime);
                    break;
                case "AS":
                    System.out.println(filename + " " + method);
                    search.aStarSearch(goalStates, walls);
                    runAndMeasureTime(startTime);
                    break;
                case "CUS1":
                    System.out.println(filename + " " + method);
                    search.customSearch1(goalStates, walls);
                    runAndMeasureTime(startTime);
                    break;
                case "CUS2":
                    runAndMeasureTime(startTime);
                    break;
                default:
                    System.out.println("Error: Unknown method '" + method + "'");
                    System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read file '" + filename + "'");
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Method to run the search and print out the time taken
    public static void runAndMeasureTime(long startTime) {
        long endTime = System.nanoTime();  // End timer

        long duration = endTime - startTime;  // Calculate time taken
        System.out.println("Time taken: " + duration / 1_000_000.0 + " ms");
    }

    // Helper method to read file into list of lines
    private static List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    // Helper method to parse grid size [N, M]
    private static int[] parseGridDimension(String line) {
        line = line.replace("[", "").replace("]", "");
        String[] parts = line.split(",");
        return new int[]{Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())};
    }

    // Helper method to parse coordinates like (x, y)
    private static int[] parseCoordinate(String line) {
        line = line.replace("(", "").replace(")", "");
        String[] parts = line.split(",");
        return new int[]{Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())};
    }

    // Helper method to parse goal states (xG1,yG1) | (xG2,yG2) | ...
    private static List<int[]> parseGoalStates(String line) {
        String[] goals = line.split("\\|");
        List<int[]> goalStates = new ArrayList<>();
        for (String goal : goals) {
            goalStates.add(parseCoordinate(goal));
        }
        return goalStates;
    }

    // Helper method to parse wall positions (x, y, w, h)
    private static List<int[]> parseWalls(List<String> lines) {
        List<int[]> walls = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.replace("(", "").replace(")", "").split(",");
            walls.add(new int[]{
                    Integer.parseInt(parts[0].trim()), // x
                    Integer.parseInt(parts[1].trim()), // y
                    Integer.parseInt(parts[2].trim()), // w
                    Integer.parseInt(parts[3].trim())  // h
            });
        }
        return walls;
    }


}