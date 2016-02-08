package motion.model;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;


public class KinematicCar extends Motion {
	
	public float orientation =0;
	public float theta = 0;
	public float length =3F;
	public float v = 0;

    public float current_orientation = (float) Math.toRadians(45);

    public KinematicCar(String robot) {
        super(robot);
    }

    @Override
    public void move(Point2D.Float[] path) {
        float[] current_pos;
        float theta;
        for (int i = 0; i < path.length; i++) {
            current_pos = remote.getPosition(robotHandle);
            Point2D.Float center = new Point2D.Float(current_pos[0], current_pos[1]);
            theta = angleInWorld(center, path[i]);
            singleMove(path[i].x, path[i].y, theta);
            current_pos = remote.getPosition(robotHandle);
            center = new Point2D.Float(current_pos[0], current_pos[1]);
            while (!checkPointPassed(path[i], center)) {
                theta = angleInWorld(center, path[i]);
                singleMove(path[i].x, path[i].y, theta);
                current_pos = remote.getPosition(robotHandle);
                center = new Point2D.Float(current_pos[0], current_pos[1]);
            }
        }
    }

    public void singleMove(float x, float y, float phi) {
        float[] current_pos = remote.getPosition(robotHandle);
        float Vx = (x - current_pos[0]) / dt;
        float Vy = (y - current_pos[1]) / dt;
        float V = (float) Math.sqrt(Vx * Vx + Vy * Vy);
        if (V > MAX_VELOCITY) {
            V = MAX_VELOCITY;
        } else if (V < -MAX_VELOCITY) {
            V = -MAX_VELOCITY;
        }
        if (phi > PHI_MAX) {
            phi = PHI_MAX;
        } else if (phi < -PHI_MAX) {
            phi = -PHI_MAX;
        }

//        if ((Math.abs(current_theta + phi) < Math.toRadians(1))) {
//            phi *= -1;
//            phi = current_theta + phi;
//        }

        float thetaV = (float) (V / LENGTH * Math.tan(phi))/dt;
        phi = thetaV * dt;
        Vx = (float) Math.cos(phi) * V;
        Vy = (float) Math.sin(phi) * V;
        float[] position = new float[3];
        position[0] = Vx * dt;
        position[1] = Vy * dt;
        float[] orientation = new float[3];
        orientation[2] = phi;
        current_theta = phi;
        remote.setOrientation(robotHandle, orientation);
        remote.setPosition(robotHandle, position);
    }
  

    
    
    public Point2D.Float calculate(Point2D.Float s, Point2D.Float e, float orientation){
    	this.orientation = orientation;
    	
    	Point2D.Float front = getFront(s);
    	
    	theta = (float) angle(getFront(front), front, e);
    	double Max = 1;
    	if(theta>Max)
    		theta =(float) Max;
    	if(theta<-Max)
    		theta =(float) -Max;

    	theta *= (0.3+(Math.random()*0.7));
    	
    	if(Math.random()<0.6)
    		theta = 0;
    	
    	float Vx = (e.x - s.x) /dt ;
        float Vy = (e.y - s.y) /dt;
                
        float V = (float) Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt)));
        if (V > MAX_VELOCITY) {
            Vx = (Vx / V) * MAX_VELOCITY;
            Vy = (Vy / V) * MAX_VELOCITY;
        }
        
        V = (float) (Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt))));

        float newX = (float) (V*Math.cos(orientation));
        
        float newY = (float) (V*Math.sin(orientation));
        
        this.orientation += (float) ((V/length)* Math.tan(theta));
        this.v = V;
        
		return new Point2D.Float(s.x+newX, s.y+newY);    	
    }

    
    
	public Float calculateToGoal(Point2D.Float s, Point2D.Float e, float orientation) {
    	this.orientation = orientation;
    	
    	Point2D.Float front = getFront(s);
    	
    	theta = (float) angle(getFront(front), front, e);
    	double Max = 1;
    	if(theta>Max)
    		theta =(float) Max;
    	if(theta<-Max)
    		theta =(float) -Max;
    	
    	float Vx = (e.x - s.x) /dt ;
        float Vy = (e.y - s.y) /dt;
                
        float V = (float) Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt)));
        if (V > MAX_VELOCITY) {
            Vx = (Vx / V) * MAX_VELOCITY;
            Vy = (Vy / V) * MAX_VELOCITY;
        }
        
        V = (float) (Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt)))*(0.5+Math.random()*0.5));

        float newX = (float) (V*Math.cos(orientation));
        
        float newY = (float) (V*Math.sin(orientation));
        
        this.orientation += (float) ((V/length)* Math.tan(theta));
        this.v = V;
		return new Point2D.Float(s.x+newX, s.y+newY);
	}
    
    
}
