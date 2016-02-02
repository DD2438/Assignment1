package motion.model;

import java.awt.geom.Point2D;

public class DynamicPoint extends Motion {

    public Point2D.Float velocity = new Point2D.Float(0f, 0f);

    public DynamicPoint(String robot) {
        super(robot);

    }

    @Override
    public void move(Point2D.Float[] path) {
        float[] curent_pos;
        for (int i = 0; i < path.length; i++) {
            curent_pos = remote.getPosition(robotHandle);
            while (curent_pos[0] < path[i].x || curent_pos[1] < path[i].y) {//this will be a problem when moving back!
                singleMove(path[i].x, path[i].y);
                curent_pos = remote.getPosition(robotHandle);
            }
        }
    }

    public void singleMove(float x, float y) {
        float[] curent_pos = remote.getPosition(robotHandle);
        float Vx = (x - curent_pos[0]) / dt;
        float Vy = (y - curent_pos[1]) / dt;
        float ax = (Vx - velocity.x) /dt;
        float ay = (Vy - velocity.y) /dt;
        float at = (float) Math.sqrt(ax * ax + ay * ay);
        if (at > MAX_ACCELERATION) {
            ax = (ax / at) * MAX_ACCELERATION;
            ay = (ay / at) * MAX_ACCELERATION;
        }
        Vx = velocity.x + ax * dt;
        Vy = velocity.y + ay * dt;
        float V = (float) Math.sqrt(Vx * Vx + Vy * Vy);
        if (V > MAX_VELOCITY) {
            Vx = (Vx / V) * MAX_VELOCITY;
            Vy = (Vy / V) * MAX_VELOCITY;
        }
        float[] position = new float[3];
        position[0] = Vx * dt;
        position[1] = Vy * dt;
        velocity.x = Vx;
        velocity.y = Vy;
        remote.setPosition(robotHandle, position);
    }
}
