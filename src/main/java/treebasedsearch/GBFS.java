package treebasedsearch;

import java.util.*;

public class GBFS {
    private List<int[]> path;

    private String[][] grid;
    private int[] initialPosition;
    private List<int[]> goalStates;
    private List<int[]> walls;

    public GBFS(String[][] grid, int[] initialPosition, List<int[]> goalStates, List<int[]> walls) {
        this.grid = grid;
        this.initialPosition = initialPosition;
        this.goalStates = goalStates;
        this.walls = walls;

        this.path = new ArrayList<>();
    }

    private int calculateManhattanDistance(int[] node) {
        int minDistance = Integer.MAX_VALUE;

        // Iterate over all goals and calculate the Manhattan distance to each
        for (int[] goal : goalStates) {
            int distance = Math.abs(goal[1] - node[1]) + Math.abs(goal[0] - node[0]);
            if (distance < minDistance) {
                minDistance = distance;
            }
        }

        return minDistance;  // Return the smallest distance to any goal
    }


    public List<String> search() {
        List<int[]> visitedTiles = new ArrayList<>();

        Queue<int[]> nextTiles = new PriorityQueue<>(Comparator.comparingInt(this::calculateManhattanDistance));
        nextTiles.add(initialPosition);

        Map<String, int[]> parentMap = new HashMap<>();
        parentMap.put(Arrays.toString(initialPosition), null);

        while(!nextTiles.isEmpty()) {
            int[] currentTile = nextTiles.poll();
            int currentRow = currentTile[1];
            int currentCol = currentTile[0];

            for (int[] goal : goalStates) {
                if (currentRow == goal[1] && currentCol == goal[0]) {
                    List<int[]> path = reconstructPath(parentMap, goal);
                    return movementOfPath(path);
                }
            }

            visitedTiles.add(currentTile);
            List<int[]> neighbors = getNeighbors(currentTile);

            for (int[] neighbor : neighbors) {
                if (!hasVisited(visitedTiles, neighbor)) {
                    nextTiles.add(neighbor);
                    parentMap.put(Arrays.toString(neighbor) , currentTile);

                }
            }

        }
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
