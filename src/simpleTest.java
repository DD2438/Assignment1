// Copyright 2006-2015 Coppelia Robotics GmbH. All rights reserved. 
// marc@coppeliarobotics.com
// www.coppeliarobotics.com
// 
// -------------------------------------------------------------------
// THIS FILE IS DISTRIBUTED "AS IS", WITHOUT ANY EXPRESS OR IMPLIED
// WARRANTY. THE USER WILL USE IT AT HIS/HER OWN RISK. THE ORIGINAL
// AUTHORS AND COPPELIA ROBOTICS GMBH WILL NOT BE LIABLE FOR DATA LOSS,
// DAMAGES, LOSS OF PROFITS OR ANY OTHER KIND OF LOSS WHILE USING OR
// MISUSING THIS SOFTWARE.
// 
// You are free to use/modify/distribute this file for whatever purpose!
// -------------------------------------------------------------------
//
// This file was automatically created for V-REP release V3.2.3 rev4 on December 21st 2015

import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.IntWA;
import coppelia.remoteApi;

// Make sure to have the server side running in V-REP: 
// in a child script of a V-REP scene, add following command
// to be executed just once, at simulation start:
//
// simExtRemoteApiStart(19999)
//
// then start simulation, and run this program.
//
// IMPORTANT: for each successful call to simxStart, there
// should be a corresponding call to simxFinish at the end!
public class simpleTest {

    public final static String objectname = "XYZCameraProxy";

    public static void main(String[] args) {
        System.out.println("Program started");
        remoteApi vrep = new remoteApi();
        vrep.simxFinish(-1); // just in case, close all opened connections
        int clientID = vrep.simxStart("127.0.0.1", 19999, true, true, 5000, 5);
        if (clientID != -1) {
            System.out.println("Connected to remote API server");

            // Now try to retrieve data in a blocking fashion (i.e. a service call):
            IntWA objectHandles = new IntWA(1);

            int ret = vrep.simxGetObjects(clientID, remoteApi.sim_handle_all, objectHandles, remoteApi.simx_opmode_oneshot_wait);
            if (ret == remoteApi.simx_return_ok) {
                System.out.format("Number of objects in the scene: %d\n", objectHandles.getArray().length);
            } else {
                System.out.format("Remote API function call returned with error code: %d\n", ret);
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

			// Now retrieve streaming data (i.e. in a non-blocking fashion):
            //		long startTime=System.currentTimeMillis();
            IntW mouseX = new IntW(0);
            vrep.simxGetIntegerParameter(clientID, remoteApi.sim_intparam_mouse_x, mouseX, remoteApi.simx_opmode_streaming); // Initialize streaming
		/*	while (System.currentTimeMillis()-startTime < 5000)
             {
             ret=vrep.simxGetIntegerParameter(clientID,vrep.sim_intparam_mouse_x,mouseX,vrep.simx_opmode_buffer); // Try to retrieve the streamed data
             if (ret==vrep.simx_return_ok) // After initialization of streaming, it will take a few ms before the first value arrives, so check the return code
             System.out.format("Mouse position x: %d\n",mouseX.getValue()); // Mouse position x is actualized when the cursor is over V-REP's window
             }*/

            // Now send some data to V-REP in a non-blocking fashion:
            vrep.simxAddStatusbarMessage(clientID, "Hello V-REP!", remoteApi.simx_opmode_oneshot);

            // Before closing the connection to V-REP, make sure that the last command sent out had time to arrive. You can guarantee this with (for example):
            IntW pingTime = new IntW(0);
            vrep.simxGetPingTime(clientID, pingTime);

			// Now close the connection to V-REP:	
            //vrep.simxFinish(clientID);
			//while(true);
			//vrep.simxGetFloatSignal(clientID,"PIDSignal1",tmp,remoteApi.simx_opmode_oneshot_wait);
            //PIDSignal[0]=tmp.getValue();
            /*
             int[] dummyhandle = new int[4];
             IntW temp = new IntW(4);
			
             vrep.simxCreateDummy(clientID,1,null,temp, vrep.simx_opmode_oneshot_wait);
             dummyhandle[0] = temp.getValue();
			
             coppelia.FloatWA pos = new coppelia.FloatWA(3);
             pos.initArray(3);
             System.out.println(vrep.simxGetLastCmdTime( clientID));
             vrep.simxGetObjectHandle(clientID, "Tree", tmp,vrep.simx_opmode_oneshot_wait);
             handle = tmp.getValue();
             vrep.simxSetObjectPosition(clientID,handle, -1 ,pos , vrep.simx_opmode_oneshot);
             */
            int handle;
            IntW tmp = new IntW(0);
            vrep.simxGetObjectHandle(clientID, "Tree", tmp, remoteApi.simx_opmode_oneshot_wait);
            handle = tmp.getValue();
            System.out.println(handle);

            IntW Dumhandle = new IntW(0);
            vrep.simxCreateDummy(clientID, (float) 0.1, null, Dumhandle, remoteApi.simx_opmode_oneshot_wait);
            int dummyHandle = Dumhandle.getValue();

            System.out.println(dummyHandle);

            FloatWA tmpF = new FloatWA(3);
            vrep.simxGetObjectPosition(clientID, dummyHandle, -1, tmpF, remoteApi.simx_opmode_oneshot_wait);
            vrep.simxSetObjectPosition(clientID, handle, -1, tmpF, remoteApi.simx_opmode_oneshot);

            IntW Dumhan1dle = new IntW(0);
            vrep.simxCreateDummy(clientID, (float) 0.1, null, Dumhandle, remoteApi.simx_opmode_oneshot_wait);
            dummyHandle = Dumhan1dle.getValue();

            Dumhandle = new IntW(0);
            vrep.simxCreateDummy(clientID, (float) 0.1, null, Dumhandle, remoteApi.simx_opmode_oneshot_wait);
            dummyHandle = Dumhandle.getValue();
            /*
             [ErrLocH, LocH] = vrep.simxGetObjectHandle(robot.clientID,Robot_name,vrep.simx_opmode_oneshot_wait);
             [ErrGrid, H] = vrep.simxCreateDummy(robot.clientID,0.1,[],vrep.simx_opmode_oneshot_wait); 
             [ErrLoc, Loc] = vrep.simxGetObjectPosition(robot.clientID,LocH,-1,vrep.simx_opmode_oneshot_wait);
             [ErrSetPos] = vrep.simxSetObjectPosition(robot.clientID,H,-1,Loc,vrep.simx_opmode_oneshot);
             */
            //System.out.println(handle.toString());
        } else {
            System.out.println("Failed connecting to remote API server");
        }
        System.out.println("Program ended");
    }
}
