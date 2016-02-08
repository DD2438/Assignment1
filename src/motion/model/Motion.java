package motion.model;

import Astar.Remote;
import coppelia.FloatWA;
import coppelia.remoteApi;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public abstract class Motion {

    public static final float MAX_VELOCITY = 1.0f;
    public static final float MAX_VELOCITY_DYNAMIC = 3.0f;
    public static final float MAX_DIS = 0.25f;
    public static final float MAX_ACCELERATION = 0.4f;
    public static final float CHECK_POINT_RADIUS = 0.5f;
    public static final float PHI_MAX = (float) Math.PI/4;
    public static final float LENGTH = 1.0f;
    protected float current_theta;
    protected float dt = 0.2f;
    protected int robotHandle;
    protected Remote remote;
    private final FloatWA current_pos;
    public float length =2;
    public float orientation = 0;
    public Motion(String robot) {
        remote = Remote.getRemote();
        robotHandle = remote.getHandle(robot);
        current_pos = new FloatWA(3);
        remote.vrep.simxGetObjectPosition(remote.clientID, robotHandle, -1,
                current_pos, remoteApi.simx_opmode_oneshot_wait);
    }
    
    public double angle(Point2D.Float p1, Point2D.Float p2, Point2D.Float p3){
    	Line2D line1 = new Line2D.Double(p1,p2);
    	Line2D line2 = new Line2D.Double(p2,p3);
    	
    	 double angle1 = Math.atan2(line1.getY1() - line1.getY2(),
                 line1.getX1() - line1.getX2());
    	 
    	 double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
                 line2.getX1() - line2.getX2());
    	 
    	 return -((angle1-angle2)>Math.PI? -((2*Math.PI)-(angle1-angle2)) : (angle1-angle2));
    }
    
    
    public Point2D.Float getFront(Point2D.Float current){
    	float x = current.x +  (length *(float) Math.sin(orientation));
    	float y = current.y +  (length *(float) Math.cos(orientation));
    	return new Point2D.Float(x, y);
    	
    }
    
    
    public void restPosition(){
        float[] position = new float[3];
        position[0] = current_pos.getArray()[0];
        position[1] = current_pos.getArray()[1];
        position[2] = current_pos.getArray()[2];
        FloatWA new_pos = new FloatWA(position);
        remote.vrep.simxSetObjectPosition(remote.clientID, robotHandle, -1,
                new_pos, remoteApi.simx_opmode_oneshot_wait);
    }

    public abstract void move(Point2D.Float[] path);

    public void wait(int dur) {
        try {
            Thread.sleep(dur);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    protected boolean checkPointPassed(Point2D.Float checkPoint, Point2D.Float position) {
        float x = checkPoint.x - position.x;
        float y = checkPoint.y - position.y;
        float dist = (float) Math.sqrt(x * x + y * y);
        return CHECK_POINT_RADIUS > dist;
    }

    public Point2D.Float normalize(Point2D.Float p) {
        double dist = Math.sqrt(p.x * p.x + p.y * p.y);
        return new Point2D.Float((float) (p.x / dist), (float) (p.y / dist));
    }

    public float angleInWorld(Point2D.Float centerPt, Point2D.Float targetPt) {
        double angle = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);
        return (float) angle;
    }
}
