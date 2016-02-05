package motion.model;

import java.awt.geom.Point2D;

public class DifferentialDrive extends Motion {

    private static final float OMEGA_MAX = (float) Math.PI / 4;
    private float curent_theta;

    public DifferentialDrive(String robot) {
        super(robot);
    }

    @Override
    public void move(Point2D.Float[] path) {
        float[] curent_pos;
        float theta;
        for (int i = 0; i < path.length; i++) {
            curent_pos = remote.getPosition(robotHandle);
            Point2D.Float center = new Point2D.Float(curent_pos[0], curent_pos[1]);
            theta = angleInWorld(center, path[i]);
            singleMove(path[i].x, path[i].y, theta);
            curent_pos = remote.getPosition(robotHandle);
            center = new Point2D.Float(curent_pos[0], curent_pos[1]);
            while (!checkPointPassed(path[i], center)) {
                theta = angleInWorld(center, path[i]);
                singleMove(path[i].x, path[i].y, theta);
                curent_pos = remote.getPosition(robotHandle);
                center = new Point2D.Float(curent_pos[0], curent_pos[1]);
            }
        }
    }

    public void singleMove(float x, float y, float theta) {
        float[] curent_pos = remote.getPosition(robotHandle);
        float Vx = (x - curent_pos[0]) / dt;
        float Vy = (y - curent_pos[1]) / dt;
        float V = (float) Math.sqrt(Vx * Vx + Vy * Vy);
        if (V > MAX_VELOCITY) {
            V = MAX_VELOCITY;
        }
        //theta = curent_theta;
        //float thetaV = (theta) / dt;
        if (theta > OMEGA_MAX) {
            theta = OMEGA_MAX;
        } else if (theta < -OMEGA_MAX) {
            //theta = -OMEGA_MAX;
        }
        //theta = thetaV * dt;
        Vx = (float) Math.cos(theta) * V;
        Vy = (float) Math.sin(theta) * V;
        float[] position = new float[3];
        position[0] = Vx * dt;
        position[1] = Vy * dt;
        float[] orientation = new float[3];
        orientation[2] = theta;
        curent_theta = theta;
        remote.setOrientation(robotHandle, orientation);
        remote.setPosition(robotHandle, position);
    }

    public float angle(float theta, Point2D.Float centerPt, Point2D.Float targetPt) {
        Point2D.Float target = new Point2D.Float(targetPt.x - centerPt.x, targetPt.y - centerPt.y);
        target = normalize(target);
        System.out.println("Target= " + target);
        Point2D.Float orientation = new Point2D.Float((float) Math.cos(theta), (float) Math.sin(theta));
        System.out.println("Center= " + orientation);
        double angle = Math.atan2(target.y - orientation.y, target.x - orientation.x);
        return (float) angle;
    }

    public float angleBetweenToVectors(Point2D.Float centerPt, Point2D.Float targetPt) {
        Point2D.Float target = new Point2D.Float(targetPt.x - centerPt.x, targetPt.y - centerPt.y);
        target = normalize(target);
        System.out.println("original point = " + centerPt);
        System.out.println("original point = " + targetPt + "            relativ normalized point = " + target);
        double angle = Math.atan2(target.y - centerPt.y, target.x - centerPt.x);
        return (float) angle;
    }

}
