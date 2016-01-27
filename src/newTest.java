
import coppelia.CharWA;
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


public class newTest {

	public static void main(String[] args)
	{
		System.out.println("Program started");
		remoteApi vrep = new remoteApi();
		vrep.simxFinish(-1); // just in case, close all opened connections
		int clientID = vrep.simxStart("127.0.0.1",19999,true,true,5000,5);
		if (clientID!=-1)
		{
			System.out.println("Connected to remote API server");	
			
			//Svars object, måste initieras
			IntW tmpIntW =new IntW(0);		
			FloatWA tmpFWA = new FloatWA(0);
			
			
			/* används för att hämta "handle" av object.
			 * parametrar: 
			 * client iD 
			 * namnet av objectet(kan ändras innuti v-rep)
			 * initierad svarsobject
			 */
			vrep.simxGetObjectHandle(clientID, "Tree", tmpIntW,remoteApi.simx_opmode_oneshot_wait);
			
			//extrahera värde
			int treeHandle = tmpIntW.getValue();

			vrep.simxGetObjectHandle(clientID, "Tree2", tmpIntW,remoteApi.simx_opmode_oneshot_wait);

			int tree2Handle = tmpIntW.getValue();
			
		/*	vrep.simxCreateDummy(clientID,(float)0.1,null,tmpIntW,remoteApi.simx_opmode_oneshot_wait); 
			int dummyHandle = tmpIntW.getValue();
			*/
			FloatWA tree2Pos = new FloatWA(3);
			vrep.simxGetObjectPosition(clientID, tree2Handle,-1,tree2Pos, remoteApi.simx_opmode_oneshot_wait);
			

			FloatWA treePos = new FloatWA(3);
			vrep.simxGetObjectPosition(clientID, treeHandle,-1,treePos, remoteApi.simx_opmode_oneshot_wait);
			
			float[] val1s = treePos.getArray();
			for(float v:val1s)
			System.out.println(v);
			
	//		FloatWA dummyPos = new FloatWA(3);
		//	vrep.simxGetObjectPosition(clientID, dummyHandle,-1,dummyPos, remoteApi.simx_opmode_oneshot_wait);
			
			
			
			
			vrep.simxSetObjectPosition(clientID, tree2Handle,-1,treePos,remoteApi.simx_opmode_oneshot);
			System.out.println("moved tree");
			
			float[] vals = tree2Pos.getArray();
			for(float v:vals)
			System.out.println(v);
			
			wait(5000);
			
			
			System.out.println("moving back");
			
		/*	vrep.simxCreateDummy(clientID,(float)0.1,null,tmpIntW,remoteApi.simx_opmode_oneshot_wait); 
			int dummy2Handle = tmpIntW.getValue();
*/

			FloatWA origin = new FloatWA(vals);
			
			vrep.simxSetObjectPosition(clientID, treeHandle,-1,origin,remoteApi.simx_opmode_oneshot);
			

			System.out.println("moved back");
			wait(500);
			
			float[] mover = new float[3];
			mover[0] = 1;
			mover[1] = 1;
			mover[2] = 1;
			origin = new FloatWA(mover);

			vrep.simxSetObjectPosition(clientID, treeHandle,-1,origin,remoteApi.simx_opmode_oneshot);
			wait(500);
		}

		else
				System.out.println("Failed connecting to remote API server");
			System.out.println("Program ended");
	}
	
	public static void wait(int dur){
		try
		{
			Thread.sleep(dur);
		}
		catch(InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
	}
}
