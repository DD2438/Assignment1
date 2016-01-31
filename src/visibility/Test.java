package visibility;

import java.awt.Point;

import Astar.NeighborListMap;

public class Test{
	
	public static void main(String[] args){
		
		
		double[] x = {1, 2 , 2, 1,3,4,4,3};
		double[] y = {1, 1 , 5, 5,0,0,1,1};
		int[] pol= {1,1,1,3,1,1,1,3};

		

		Point end= new Point(4,4);
		Point start= new Point(0,1);
		
		VGraph v = new VGraph(x,y,pol,start,end);
		
		boolean[][] g = v.adjG;
		
		
		for(int i=0;i<g.length ; i++){
			System.out.println();
			for(int j=0; j<g[0].length; j++){
				System.out.print(g[i][j]+ " ");
			}
		}
		
		NeighborListMap m = new NeighborListMap(v.adjG, x,y);
		System.out.println(m.getShortestPath());
		
	}
	
}