package discretePathFinder;


import Astar.Remote;
import java.awt.Point;
import java.util.ArrayList;
import Astar.CsvReader;
import Astar.Map;
import Astar.Map16;

public class Main {

    public static final int MAPSIZEX = 20;
    public static final int MAPSIZEY = 20;

    public static void main(String[] args) {
        boolean[][] obstacles = new CsvReader("discMap.csv", MAPSIZEX, MAPSIZEY).convert();

        Map16 map = new Map16(obstacles, 1, 1, 14, 19);

        Remote remote = Remote.getRemote();

        Mover mover = new Mover(MAPSIZEX, MAPSIZEY, remote);
        MapBuilder MB = new MapBuilder(map, remote, mover);

        map.getShortestPath();
        ArrayList<Point> path = map.getPath();

        for (Point p : path) {
            mover.move(p.y, p.x);
        }
    }
}
