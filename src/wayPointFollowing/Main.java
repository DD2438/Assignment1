package wayPointFollowing;

import coppelia.IntW;
import coppelia.remoteApi;

public class Main {

    static final float max_steer_angle = -0.235987f;
    static final float motor_torque = 60f;
    static final float dVel = 1f;
    static final float dSteer = 0.1f;
    static final float steer_angle = 0f;
    static final float motor_velocity = dVel * 10;
    static final float brake_force = 0f;

    public static void main(String[] args) {
        System.out.println("Program started");
        remoteApi vrep = new remoteApi();
        vrep.simxFinish(-1); // just in case, close all opened connections
        int clientID = vrep.simxStart("127.0.0.1", 19999, true, true, 5000, 5);
        if (clientID != -1) {
            IntW left_handle = new IntW(0);
            IntW right_handle = new IntW(0);

            IntW steer_handle = new IntW(0);
            IntW motor_handle = new IntW(0);

            vrep.simxGetObjectHandle(clientID, "Pioneer_p3dx_leftMotor", left_handle, remoteApi.simx_opmode_oneshot_wait);
            vrep.simxGetObjectHandle(clientID, "Pioneer_p3dx_rightMotor", right_handle, remoteApi.simx_opmode_oneshot_wait);

            vrep.simxGetObjectHandle(clientID, "steer_joint", steer_handle, remoteApi.simx_opmode_oneshot_wait);
            vrep.simxGetObjectHandle(clientID, "motor_joint", motor_handle, remoteApi.simx_opmode_oneshot_wait);
            
//            FloatW steer_pos = new FloatW(0);
//            vrep.simxGetJointPosition(clientID, steer_handle.getValue(), steer_pos, remoteApi.simx_opmode_oneshot_wait);
            vrep.simxSetJointTargetPosition(clientID, steer_handle.getValue(), -0.4f, remoteApi.simx_opmode_oneshot_wait);
            vrep.simxSetJointForce(clientID, motor_handle.getValue(), 60f,  remoteApi.simx_opmode_streaming);
            vrep.simxSetJointTargetVelocity(clientID, motor_handle.getValue(), -1.9f, remoteApi.simx_opmode_streaming);
            
            //vrep.simxSetJointForce(clientID, left_handle.getValue(), 10.5f, remoteApi.simx_opmode_streaming);
            //           vrep.simxSetJointPosition(clientID, left_handle.getValue(), 7, remoteApi.simx_opmode_streaming);
            //           vrep.simxSetJointTargetPosition(clientID, left_handle.getValue(), 50, remoteApi.simx_opmode_streaming);
            vrep.simxSetJointTargetVelocity(clientID, left_handle.getValue(), 0f, remoteApi.simx_opmode_streaming);

            //vrep.simxSetJointForce(clientID, right_handle.getValue(), 10.5f, remoteApi.simx_opmode_streaming);
            //           vrep.simxSetJointPosition(clientID, right_handle.getValue(), 7, remoteApi.simx_opmode_streaming);
            //           vrep.simxSetJointTargetPosition(clientID, right_handle.getValue(), 50, remoteApi.simx_opmode_streaming);
            vrep.simxSetJointTargetVelocity(clientID, right_handle.getValue(), 0f, remoteApi.simx_opmode_streaming);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Now close the connection to V-REP:	
            vrep.simxFinish(clientID);
        }
    }
}
