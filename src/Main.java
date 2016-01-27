import java.awt.Point;
import java.util.ArrayList;
import Astar.CsvReader;
import Astar.Map;

public class Main {

	public static final int MAPSIZEX = 20;
	public static final int MAPSIZEY = 20;
	
	public static void main(String[] args){
		boolean[][] obstacles = new CsvReader("discMap.csv",MAPSIZEX,MAPSIZEY).convert();
		
		Map map = new Map(obstacles,1,1,14,19);
		
		map.getShortestPath();
		ArrayList<Point> path = map.getPath();
		
		Mover mover = new Mover(MAPSIZEX, MAPSIZEY);
		
		for(Point p:path){
			mover.move(p.y, p.x);
		}
	}
}
