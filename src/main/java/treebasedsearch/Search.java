package treebasedsearch;

import java.util.ArrayList;
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

    private boolean isValidNeighbor(int[] position) {
        int currentRow = position[1];
        int currentCol = position[0];

        if (currentRow < 0 || currentRow >= grid.length || currentCol < 0 || currentCol >= grid[0].length) {
            return false;
        }

        // check if position is in a wall
        for (int[] wall : getWalls()) {
            int startRow = wall[1];
            int startCol = wall[0];
            int width = wall[2];
            int height = wall[3];

            if (currentRow >= startRow && currentRow < startRow + width && currentCol >= startCol && currentCol < startCol + height) {
                return false;
            }
        }
        return true;
    }

    public List<int[]> getNeighbors(int[] position) {
        int row = position[1];
        int col = position[0];

        List<int[]> neighbors = new ArrayList<>();
        neighbors.add(new int[]{col, row - 1});
        neighbors.add(new int[]{col - 1, row});
        neighbors.add(new int[]{col, row + 1});
        neighbors.add(new int[]{col + 1, row});

        List<int[]> validNeighbors = new ArrayList<>();

        for (int[] neighbor : neighbors) {
            if (isValidNeighbor(neighbor)) {
                validNeighbors.add(neighbor);
            }
        }


        return validNeighbors;
    }

    public void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }






}
