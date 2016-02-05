package RRT;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

import Astar.CsvReader;
import Astar.Remote;
import motion.model.DynamicPoint;
import motion.model.KinematicCar;
import motion.model.KinematicPoint;
import visibility.Polygon;

public class Test {
	
	public static void main(String[] args){
		
		CsvReader reader = new CsvReader(null,0,0);
		float[] x = reader.read("C:\\x.csv", 23);
		float[] y = reader.read("C:\\y.csv", 23);
		int[] pol = reader.readInt("C:\\pol.csv", 23);


		Point2D.Float end= new Point2D.Float(30,70);
		Point2D.Float start= new Point2D.Float(80,30);

	    ArrayList<Polygon> obstacles= new ArrayList<Polygon>();


	    Point[] V = new Point[23];
	    
	    	Polygon current = new Polygon();
			obstacles.add(current);
	    	int first =0;
	    	Point tmp = new Point();
			tmp.setLocation(x[0], y[0]);
			V[0]=tmp;

	    	for(int i=1; i<22 ; i++){
	    		tmp = new Point();
	    		tmp.setLocation(x[i], y[i]);
	    		V[i]=tmp;
	    		if(current==null){
	    			current = new Polygon();
	    			obstacles.add(current);
	    			first =i;
	    		}
	    		else if(pol[i]==1){
	    			current.addLine(new Line2D.Double(V[i],V[i-1]),i);
	    		}
	    		else if(pol[i]==3){
	    		    
	    			
	    			current.addLine(new Line2D.Double(V[i],V[i-1]),i);
	    			current.addLine(new Line2D.Double(V[i],V[first]),i);
	    			current = null;
	    		}
	    	}
	    
	    	CollisionCheck cc= new CollisionCheck(x,y,pol);
	    	
		DynamicPoint dp = new DynamicPoint("node");
		KinematicPoint kp = new KinematicPoint("node");
		KinematicCar kc = new KinematicCar("node");
		
		RRT rrt = new RRT(100,100, end, start, obstacles,kc,cc);
		
		Node last = rrt.last;
		
		ArrayList<Point2D.Float> path = new ArrayList<Point2D.Float>();
		
		while(last != null){
			path.add(last.data);
			last = last.parent;
			
		}
		
		Collections.reverse(path);
		

		
		dp.makeMoves(path);
	
	}

}
