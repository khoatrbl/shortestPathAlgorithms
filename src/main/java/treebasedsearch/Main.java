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

//         Extract filename and method
        String filename = args[0];
        String method = args[1];

//        String filename = "E:\\Java Projects\\TreeBasedSearch\\src\\main\\java\\treebasedsearch\\RobotNav-test.txt";
//        String method = "DFS";


        // Read the input file
        try {
            List<String> lines = readFile(filename);
            int[] gridDimension = parseGridDimension(lines.get(0)); // First line: grid size [N, M]

            int[] initialPosition = parseCoordinate(lines.get(1)); // Second line: initial state (x, y)
            List<int[]> goalStates = parseGoalStates(lines.get(2)); // Third line: goal states
            List<int[]> walls = parseWalls(lines.subList(3, lines.size())); // Remaining lines: walls

            Search search = new Search(gridDimension, initialPosition, goalStates, walls);
            search.printGrid();

            switch (method.toUpperCase()) {
                case "DFS":
                    depthFirstSearch(gridDimension, initialPosition, goalStates, walls);
                    break;
                case "BFS":
                    breadthFirstSearch(gridDimension, initialPosition, goalStates, walls);
                    break;
                case "GBFS":
                    greedyBestFirstSearch(gridDimension, initialPosition, goalStates, walls);
                    break;
                case "AS":
                    aStarSearch(gridDimension, initialPosition, goalStates, walls);
                    break;
                case "CUS1":
                    customSearch1(gridDimension, initialPosition, goalStates, walls);
                    break;
                case "CUS2":
                    customSearch2(gridDimension, initialPosition, goalStates, walls);
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

    //

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

    // Implement the search algorithms
    private static void depthFirstSearch(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        // DFS search logic here
        System.out.println("Running DFS...");
    }

    private static void breadthFirstSearch(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        // BFS search logic here
        System.out.println("Running BFS...");
    }

    private static void greedyBestFirstSearch(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        // GBFS search logic here
        System.out.println("Running GBFS...");
    }

    private static void aStarSearch(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        // A* search logic here
        System.out.println("Running A*...");
    }

    private static void customSearch1(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        // Custom search 1 logic here
        System.out.println("Running Custom Search 1...");
    }

    private static void customSearch2(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        // Custom search 2 logic here
        System.out.println("Running Custom Search 2...");
    }
}