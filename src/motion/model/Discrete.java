package motion.model;

import java.awt.geom.Point2D;

public class Discrete extends Motion {

    public Discrete(String robot) {
        super(robot);
    }

    @Override
    public void move(Point2D.Float[] path) {
        float[] curent_pos;
        for (int i = 0; i < path.length; i++) {
            curent_pos = remote.getPosition(robotHandle);
            Point2D.Float center = new Point2D.Float(curent_pos[0], curent_pos[1]);
            float theta = angleInWorld(center, path[i]);          
            boolean dis_coverd = singleMove(path[i].x, path[i].y, theta);
            while (!dis_coverd) {
                curent_pos = remote.getPosition(robotHandle);
                center = new Point2D.Float(curent_pos[0], curent_pos[1]);
                theta = angleInWorld(center, path[i]);
                dis_coverd = singleMove(path[i].x, path[i].y,theta);
            }
        }

    }

    public boolean singleMove(float x, float y, float theta) {
        float[] curent_pos = remote.getPosition(robotHandle);
        float[] position = new float[3];
        boolean dis_coverd = true;
        position[0] = x - curent_pos[0];
        position[1] = y - curent_pos[1];
        float dist = (float) Math.sqrt(position[0] * position[0] + position[1] * position[1]);
        if (dist > MAX_DIS) {
            position[0] = (position[0] / dist) * MAX_DIS;
            position[1] = (position[1] / dist) * MAX_DIS;
            dis_coverd = false;
        }
        float[] orientation = new float[3];
        orientation[2] = theta;
        remote.setOrientation(robotHandle, orientation);
        remote.setPosition(robotHandle, position);
        return dis_coverd;
    }
}
