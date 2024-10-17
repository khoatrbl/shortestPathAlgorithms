package treebasedsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search {
    private int[] gridDimension;
    private int[] initialPosition;
    private List<int[]> goalStates;
    private List<int[]> walls;

    private String[][] grid;

    public Search(int[] gridDimension, int[] initialPosition, List<int[]> goalStates, List<int[]> walls) {
        this.gridDimension = gridDimension;
        this.initialPosition = initialPosition;
        this.goalStates = goalStates;
        this.walls = walls;

        setUpGrid();
    }

    public int[] getGridDimension() {
        return gridDimension;
    }

    public void setGridDimension(int[] gridDimension) {
        this.gridDimension = gridDimension;
    }

    public int[] getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(int[] initialPosition) {
        this.initialPosition = initialPosition;
    }

    public List<int[]> getGoalStates() {
        return goalStates;
    }

    public void setGoalStates(List<int[]> goalStates) {
        this.goalStates = goalStates;
    }

    public List<int[]> getWalls() {
        return walls;
    }

    public void setWalls(List<int[]> walls) {
        this.walls = walls;
    }

    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

    private void setUpGrid() {
        int rows = getGridDimension()[0];
        int cols = getGridDimension()[1];

        this.grid = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.grid[i][j] = "[ ] ";
            }
        }

        setUpWalls();
        setUpInitialPosition();
        setUpGoals();
    }

    private void setUpWalls() {
        for (int[] wall : getWalls()) {
            int startRow = wall[1];
            int startCol = wall[0];
            int width = wall[2];
            int height = wall[3];

            for (int i = startRow; i < startRow + height; i++) {
                for (int j = startCol; j < startCol + width; j++) {
                    if (i < this.grid.length && j < this.grid[0].length) {
                        this.grid[i][j] = "[x] "; // Mark wall cells
                    }
                }
            }
        }
    }

    private void setUpInitialPosition() {
        int agentRow = getInitialPosition()[1];
        int agentCol = getInitialPosition()[0];

        this.grid[agentRow][agentCol] = "[A] ";
    }

    private void setUpGoals() {
        for (int[] goal : getGoalStates()) {
            int goalRow = goal[1];
            int goalCol = goal[0];

            this.grid[goalRow][goalCol] = "[G] ";
        }
    }



    public void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    // Implement the search algorithms
    public void depthFirstSearch(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        DFS dfs = new DFS(initialPosition, goalStates, grid, walls);

        List<String> moves = dfs.search();
        List<int[]> path = dfs.getPath();

        if (moves != null) {
            System.out.println("Goal: " + Arrays.toString(path.getLast()));
            System.out.println("Number of steps: " + moves.size());
            for (String move : moves) {
                System.out.print(move + " ");
            }
            System.out.println();
            for (int[] p : path) {
                System.out.print(Arrays.toString(p) + " ");
            }
        } else {
            System.out.println("No path found.");
        }
    }

    public void breadthFirstSearch(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        BFS bfs = new BFS(initialPosition, goalStates, grid, walls);

        List<String> moves = bfs.search();
        List<int[]> path = bfs.getPath();

        if (moves != null) {
            System.out.println("Goal: " + Arrays.toString(path.getLast()));
            System.out.println("Number of steps: " + moves.size());
            for (String move : moves) {
                System.out.print(move + " ");
            }
        } else {
            System.out.println("No path found.");
        }
    }

    public void greedyBestFirstSearch(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        // GBFS search logic here
        System.out.println("Running GBFS...");
    }

    public void aStarSearch(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        // A* search logic here
        System.out.println("Running A*...");
    }

    public void customSearch1(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        // Custom search 1 logic here
        System.out.println("Running Custom Search 1...");
    }

    public void customSearch2(int[] gridSize, int[] initialState, List<int[]> goalStates, List<int[]> walls) {
        // Custom search 2 logic here
        System.out.println("Running Custom Search 2...");
    }






}
