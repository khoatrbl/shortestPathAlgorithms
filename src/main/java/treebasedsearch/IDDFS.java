package treebasedsearch;

import java.util.*;

/* Custom 1: Iterative Deepening Depth First Search (IDDFS)
  Reference: <a href="https://www.geeksforgeeks.org/iterative-deepening-searchids-iterative-deepening-depth-first-searchiddfs/">...</a> */
public class IDDFS {
    private List<int[]> path;
    // To store the parent of each node to reconstruct the path
    private Map<String, int[]> parentMap;

    private String[][] grid;
    private int[] initialPosition;
    private List<int[]> goalStates;
    private List<int[]> walls;

    public IDDFS(String[][] grid, int[] initialPosition, List<int[]> goalStates, List<int[]> walls) {
        this.grid = grid;
        this.initialPosition = initialPosition;
        this.goalStates = goalStates;
        this.walls = walls;
        this.path = new ArrayList<>();
        this.parentMap = new HashMap<>();
    }

    public List<String> search() {
        int depth = 0;

        while (true) {
            System.out.println("Trying depth limit: " + depth);
            List<String> visited = new ArrayList<>();
            parentMap.clear();
            int[] foundGoal = depthLimitedSearch(initialPosition, 0, depth, visited);
            if (foundGoal != null) {
                List<int[]> path = reconstructPath(parentMap, foundGoal);
                return movementOfPath(path);
            }
            depth++;  // Increase depth limit
        }
    }

    private int[] depthLimitedSearch(int[] currentPosition, int currentDepth, int depthLimit, List<String> visited) {
        if (currentDepth > depthLimit) {
            return null;
        }

        // Goal check
        for (int[] goal : goalStates) {
            if (Arrays.equals(currentPosition, goal)) {
                System.out.println("Goal found at depth: " + currentDepth);
                return goal;
            }
        }

        visited.add(Arrays.toString(currentPosition));

        // Explore neighbors
        for (int[] neighbor : getNeighbors(currentPosition)) {
            if (!visited.contains(Arrays.toString(neighbor))) {
                // Record the parent of the neighbor for path reconstruction
                parentMap.put(Arrays.toString(neighbor), currentPosition);

                // Recur with increased depth
                int[] foundGoal = depthLimitedSearch(neighbor, currentDepth + 1, depthLimit, visited);
                if (foundGoal != null) {
                    return foundGoal;
                }
            }
        }

        // Backtrack if no goal found at this depth
        return null;
    }

    private List<int[]> reconstructPath(Map<String,int[]> parentMap, int[] goal) {
        int[] current = goal;

        while (current != null) {
            path.addFirst(current); // Add to the start of the list to reverse the path
            current = parentMap.get(Arrays.toString(current));
        }

        return path;
    }

    private List<String> movementOfPath(List<int[]> path) {
        List<String> movements = new ArrayList<>();

        for (int i = 1; i < path.size(); i++) {
            int[] prev = path.get(i - 1);
            int[] current = path.get(i);

            // Compare rows and columns to determine the movement direction
            if (current[0] == prev[0] && current[1] == prev[1] + 1) {
                movements.add("DOWN");
            } else if (current[0] == prev[0] && current[1] == prev[1] - 1) {
                movements.add("UP");
            } else if (current[0] == prev[0] + 1 && current[1] == prev[1]) {
                movements.add("RIGHT");
            } else if (current[0] == prev[0] - 1 && current[1] == prev[1]) {
                movements.add("LEFT");
            }
        }

        return movements;
    }


    private boolean isValidNeighbor(int[] position) {
        int currentRow = position[1];
        int currentCol = position[0];

        if (currentRow < 0 || currentRow >= grid.length || currentCol < 0 || currentCol >= grid[0].length) {
            return false;
        }

        // check if position is in a wall
        for (int[] wall : this.walls) {
            int startRow = wall[1];
            int startCol = wall[0];
            int width = wall[2];
            int height = wall[3];

            if (currentRow >= startRow && currentRow < startRow + height && currentCol >= startCol && currentCol < startCol + width) {
                return false;
            }
        }
        return true;
    }

    // Check if a location is already visited (specified for array usage)
    private boolean hasVisited(List<int[]> visitedList, int[] location) {
        for (int[] visited : visitedList) {
            if (Arrays.equals(visited, location)) {
                return true;
            }
        }
        return false;
    }

    private List<int[]> getNeighbors(int[] position) {
        // UP, LEFT, DOWN, RIGHT
        int[][] directions = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};


        int row = position[1];
        int col = position[0];

        List<int[]> neighbors = new ArrayList<>();

        for (int[] dir : directions) {
            neighbors.add(new int[] {col + dir[0], row + dir[1]});
        }


        List<int[]> validNeighbors = new ArrayList<>();

        for (int[] neighbor : neighbors) {
            if (isValidNeighbor(neighbor)) {
                validNeighbors.add(neighbor);
            }
        }


        return validNeighbors;
    }

    public List<int[]> getPath() {
        return path;
    }
}
