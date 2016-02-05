import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import Astar.Remote;
import coppelia.FloatWA;

public class test{

	public static double angle(Point2D.Float p1, Point2D.Float p2, Point2D.Float p3){
	Line2D line1 = new Line2D.Double(p1,p2);
	Line2D line2 = new Line2D.Double(p2,p3);
	
	 double angle1 = Math.atan2(line1.getY1() - line1.getY2(),
             line1.getX1() - line1.getX2());
	 
	 double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
             line2.getX1() - line2.getX2());
	 
	 return (angle1-angle2);
}
	public static void main(String[] args){
		
		Remote r = new Remote();
		int car = r.getHandle("car");
		int front = r.getHandle("front");
		
		int c = r.getHandle("c");
		int c1 = r.getHandle("c1");
		int c2 = r.getHandle("c2");
		int c3 = r.getHandle("c3");

        float[] temp = r.getPosition(car);
        
        Point2D.Float carP =new Point2D.Float(temp[0], temp[1]);

        Point2D.Float[] cp = new Point2D.Float[4]; 
        
        temp = r.getPosition(c);
        cp[0] =new Point2D.Float(temp[0], temp[1]);
        
        temp = r.getPosition(c1);
        cp[1] =new Point2D.Float(temp[0], temp[1]);
        
        temp = r.getPosition(c2);
        cp[2] =new Point2D.Float(temp[0], temp[1]);
        
        temp = r.getPosition(c3);
        cp[3] =new Point2D.Float(temp[0], temp[1]);
        
        
		FloatWA newangle = new FloatWA(3);
		newangle.getArray()[0]=0;
		newangle.getArray()[1]=0;
		newangle.getArray()[2]=0;

		Point2D.Float fr;
		
		r.vrep.simxSetObjectOrientation(r.clientID, car, -1, newangle,r.vrep.simx_opmode_oneshot);
		
		while(true){
			for(int i=0; i<4; i++){
				
				//float angle =(float) angle(fr, carP, cp[i]);
					//	(float) Math.atan2(cp[i].y - carP.y, cp[i].x - carP.x);		        
				
				newangle.getArray()[2] = angle;
				
				r.vrep.simxSetObjectOrientation(r.clientID, car, car, newangle,r.vrep.simx_opmode_oneshot);
				
				try {
					 Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
}