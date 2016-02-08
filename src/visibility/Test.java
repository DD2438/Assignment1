package visibility;

import java.awt.Point;
import java.awt.geom.Point2D;

import Astar.CsvReader;
import Astar.NeighborListMap;

public class Test{

	public static final int size =13;
	public static void main(String[] args){
		

		CsvReader reader = new CsvReader(null,0,0);
		float[] x = reader.read("C:\\x2.csv", size);
		float[] y = reader.read("C:\\y2.csv", size);
		int[] pol = reader.readInt("C:\\pol2.csv", size);
		
		

		Point2D.Float start= new Point2D.Float(30,70);
		Point2D.Float end= new Point2D.Float(180,75);
		
		VGraph v = new VGraph(x,y,pol,start,end);
		
		boolean[][] g = v.adjG;
		

		for(int i=0;i<g.length ; i++){
			System.out.println();
			for(int j=0; j<g[0].length; j++){
				System.out.print(g[i][j]+ " ");
			}
		}
		
		NeighborListMap m = new NeighborListMap(v.adjG, x,y,start, end);
		
		System.out.println(m.getShortestPath());
		
	}
	
}