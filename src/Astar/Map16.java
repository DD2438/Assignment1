package Astar;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Map16 {

    private Point start;
    private Point end;
    int xMax;
    int yMax;

    public boolean[][] map;
    private int[][] Hmap;
    private int[][] Gmap;
    private Point[][] parents;
    ArrayList<Point> openList = new ArrayList<Point>();
    ArrayList<Point> closedList = new ArrayList<Point>();
    ArrayList<Point> path = new ArrayList<Point>();

    public Map16(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        map = new boolean[xMax][yMax];
        Hmap = new int[xMax][yMax];
        Gmap = new int[xMax][yMax];
        parents = new Point[xMax][yMax];
    }

    public Map16(int xMax, int yMax, int startX, int startY, int endX, int endY) {
        this.xMax = xMax;
        this.yMax = yMax;
        start = new Point(startX, startY);
        end = new Point(endX, endY);

        map = new boolean[xMax][yMax];
        Hmap = new int[xMax][yMax];
        Gmap = new int[xMax][yMax];
        parents = new Point[xMax][yMax];

        init();
    }

    public Map16(boolean[][] map, int startX, int startY, int endX, int endY) {
        this.xMax = map.length;
        this.yMax = map[0].length;
        start = new Point(startX, startY);
        end = new Point(endX, endY);

        this.map = map;
        Hmap = new int[xMax][yMax];
        Gmap = new int[xMax][yMax];
        parents = new Point[xMax][yMax];

        init();
    }

    public void init() {

        //set start as parent to neighboring nodes
        for (int i = start.x - 1; i <= start.x + 1; i++) {
            for (int j = start.y - 1; j <= start.y + 1; j++) {
                if (i == start.x && j == start.y) {
                    continue;
                }
                parents[i][j] = start;
            }
        }

        //add start to open list, set its G-value to 0
        openList.add(start);
        Gmap[start.x][start.y] = 0;
        parents[start.x][start.y] = start;
        System.err.println("initiation done");
    }


    public ArrayList<Point> getShortestPath() {
        Point current;

        while (!openList.isEmpty()) {
            current = openList.remove(0);
            closedList.add(current);
            //System.err.println(current.x+" "+current.y);
            if (getNeighbors(current)) {
                backtrack(current);
                break;
            }
        }
        return path;
    }

    private void backtrack(Point current) {
        path.add(end);
        path.add(current);
        while (!current.equals(start)) {
            current = parents[current.x][current.y];
            path.add(current);
        }

        Collections.reverse(path);
    }

    private boolean getNeighbors(Point current) {
        int x = current.x;
        int y = current.y;
        int newVal;
        Point parent;

        //updates the Gvalues of neighbors
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {

                //Ignore invalid points
                if ((i == x && j == y) ||( ((i==x-2)||i==x+2) &&(j == y-2 || j==y | j==y+2))||
                		( ((j==y-2)||j==y+2) &&(i == x-2 || i==x | i==x+2))
                		|| i < 0 || j < 0 || i >= xMax || j >= yMax || map[i][j]) {
                    continue;
                }

				//obstacle detection. Check if trying to move diagonally
                //if so, check if both sides are invalid, if so, movement is invalid
                try {
                    if(		   (i == x - 1 && j == y - 1 && map[x][y - 1] && map[x - 1][y])
                            || (i == x + 1 && j == y - 1 && map[x][y - 1] && map[x + 1][y])
                            || (i == x - 1 && j == y + 1 && map[x][y + 1] && map[x - 1][y])
                            || (i == x + 1 && j == y + 1 && map[x][y + 1] && map[x + 1][y])
                            || (i == x - 1 && j == y - 2 && (map[x-1][y-1] || map[x][y-1]))
                            || (i == x + 1 && j == y - 2 && (map[x+1][y-1] || map[x][y-1]))
                            || (i == x - 2 && j == y - 1 && (map[x-1][y-1] || map[x-1][y]))
                            || (i == x + 2 && j == y - 1 && (map[x+1][y-1] || map[x+1][y]))
                            || (i == x - 2 && j == y + 1 && (map[x-1][y]   || map[x-1][y+1]))
                            || (i == x + 2 && j == y + 1 && (map[x+1][y]   || map[x+1][y+1]))
                            || (i == x - 1 && j == y + 2 && (map[x-1][y+1] || map[x][y+1]))
                            || (i == x + 1 && j == y + 2 && (map[x+1][y+1] || map[x][y+1]))
                            ) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //Harmless exception
                };

                //return success if end point found
                if (i == end.x && j == end.y) {
                    return true;
                }

                //if new point, add to openlist
                Point temp = new Point(i, j);
                if (!openList.contains(temp) && !closedList.contains(temp)) {
                    openList.add(temp);
                }

				//calculate new value to be parents value + cost
                //
                parent = parents[x][y];
                //if(i==x || j ==x)
                newVal = Gmap[parent.x][parent.y] + 10;
                /*	else 
                 newVal = Gmap[parent.x][parent.y]+;
                 */
                //Change values if new one is smaller
                if (newVal < Gmap[i][j] || Gmap[i][j] == 0) {
                    Gmap[i][j] = newVal;
                    parents[i][j] = current;
                }
            }
        }

        //Sort the openList
        Collections.sort(openList, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                return Gmap[a.x][a.y] - Gmap[b.x][b.y];
            }
        });
        return false;

    }

    public ArrayList<Point> getPath() {
        return path;
    }

    public void print() {
        String[][] ascii = new String[xMax][yMax];
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                if (map[j][i]) {
                    ascii[i][j] = "#";
                } else {
                    ascii[i][j] = " ";
                }
            }
        }
        for (Point p : path) {
            ascii[p.y][p.x] = "w";
        }

        ascii[start.y][start.x] = "S";
        ascii[end.y][end.x] = "E";

        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                System.out.print(ascii[i][j]);
            }
            System.out.println();
        }
    }     
}
