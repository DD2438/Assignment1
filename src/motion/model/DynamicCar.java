package motion.model;

import java.awt.geom.Point2D;

public class DynamicCar extends Motion {

    public Point2D.Float velocity = new Point2D.Float(0f, 0f);

    public DynamicCar(String robot) {
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
        float ax = (Vx - velocity.x) / dt;
        float ay = (Vy - velocity.y) / dt;
        float at = (float) Math.sqrt(ax * ax + ay * ay);
        if (at > MAX_ACCELERATION) {
            ax = (ax / at) * MAX_ACCELERATION;
            ay = (ay / at) * MAX_ACCELERATION;
        } else if (at < -MAX_ACCELERATION) {
            ax = (ax / at) * -MAX_ACCELERATION;
            ay = (ay / at) * -MAX_ACCELERATION;
        } 
        Vx = velocity.x + ax * dt;
        Vy = velocity.y + ay * dt;
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
        Vx = (float) -Math.sin(phi) * V + (float) Math.cos(phi) * V;
        Vy = (float) Math.cos(phi) * V + (float) Math.sin(phi) * V;
        float[] position = new float[3];
        position[0] = Vx * dt;
        position[1] = Vy * dt;
        float[] orientation = new float[3];
        orientation[2] = phi;
        current_theta = phi;
        velocity.x = Vx;
        velocity.y = Vy;
        remote.setOrientation(robotHandle, orientation);
        remote.setPosition(robotHandle, position);
    }
}
