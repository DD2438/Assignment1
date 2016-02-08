package motion.model;

import java.awt.geom.Point2D;

public class DifferentialDrive extends Motion {

    private static final float OMEGA_MAX = (float) Math.PI / 4;
    private float current_theta;
    public float orientation = 0;
    public float theta = 0;
    public float length = 1F;
    public float v = 0;

    public DifferentialDrive(String robot) {
        super(robot);
        current_theta = 0;
    }

    @Override
    public void move(Point2D.Float[] path) {
        float[] current_pos;
        float theta;
        float orientation = remote.getOrientationFirst(robotHandle);
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

    public void singleMove(float x, float y, float theta) {
        float[] current_pos = remote.getPosition(robotHandle);
        float current_orientation = remote.getOrientation(robotHandle);
        //theta -= current_theta;
        float Vx = (x - current_pos[0]) / dt;
        float Vy = (y - current_pos[1]) / dt;
        float V = (float) Math.sqrt(Vx * Vx + Vy * Vy);
        if (V > MAX_VELOCITY) {
            V = MAX_VELOCITY;
        } else if (V < -MAX_VELOCITY) {
            V = -MAX_VELOCITY;
        }
        //theta = current_theta;
        float thetaV = (theta) / dt;
        if (theta > OMEGA_MAX) {
            theta = OMEGA_MAX;
        } else if (theta < -OMEGA_MAX) {
            theta = -OMEGA_MAX;
        }
//        if ((Math.abs(current_theta + theta) < Math.toRadians(1))) {
//            theta *= -1;
//            theta = current_theta + theta;
//        }
        theta = thetaV * dt;
        Vx = (float) Math.cos(theta) * V;
        Vy = (float) Math.sin(theta) * V;
        float[] position = new float[3];
        position[0] = Vx * dt;
        position[1] = Vy * dt;
        float[] orientation = new float[3];
        orientation[2] = theta;
        current_theta = theta;
        remote.setOrientation(robotHandle, orientation);
        remote.setPosition(robotHandle, position);
    }

    public Point2D.Float calculate(Point2D.Float s, Point2D.Float e, float orientation) {
        this.orientation = orientation;
        Point2D.Float front = getFront(s);
        theta = (float) angle(getFront(front), front, e);
        double Max = 1;
        if (theta > Max) {
            theta = (float) Max;
        }
        if (theta < -Max) {
            theta = (float) -Max;
        }
        theta *= (0.3 + (Math.random() * 0.7));
        if (Math.random() < 0.6) {
            theta = 0;
        }
        float Vx = (e.x - s.x) / dt;
        float Vy = (e.y - s.y) / dt;
        float V = (float) Math.sqrt(((Vx * dt) * (Vx * dt)) + ((Vy * dt) * (Vy * dt)));
        if (V > MAX_VELOCITY) {
            V = MAX_VELOCITY;
        } else {
            V = -MAX_VELOCITY;
        }
        Vx = (float) Math.cos(theta) * V;
        Vy = (float) Math.sin(theta) * V;
        this.v = V;
        return new Point2D.Float(s.x + Vx, s.y + Vy);
    }
}
