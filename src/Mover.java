
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


public class Mover {
	
	public  int mapsizeX;
	public  int mapsizeY;
	public  float[] mover = new float[3];
	public  FloatWA pointer;
	int clientID;
	int playerHandle;
	remoteApi vrep ;
	

	public Mover(int mapsizeX, int mapsizeY){
		this.mapsizeX = mapsizeX;
		this.mapsizeY = mapsizeY;
		
		mover[2]=1;
		
		System.out.println("Program started");
		vrep = new remoteApi();
		vrep.simxFinish(-1); // just in case, close all opened connections
		int clientID = vrep.simxStart("127.0.0.1",19999,true,true,5000,5);
		if (clientID!=-1)
		{
			System.out.println("Connected to remote API server");	
			
			//Svars object, måste initieras
			IntW tmpIntW =new IntW(0);		
			
			
			/* används för att hämta "handle" av object.
			 * parametrar: 
			 * client iD 
			 * namnet av objectet(kan ändras innuti v-rep)
			 * initierad svarsobject
			 */
			vrep.simxGetObjectHandle(clientID, "Player", tmpIntW,remoteApi.simx_opmode_oneshot_wait);
			
			//extrahera värde
			playerHandle = tmpIntW.getValue();						
		}

		else
				System.out.println("Failed connecting to remote API server");
			System.out.println("Program ended");
	}
	
	public void move(int x, int y){	
		
		mover[0] = (float) x-(mapsizeX/2)+ 0.5f;
		mover[1] = (float) y-(mapsizeY/2)- 0.5f;
		pointer = new FloatWA(mover);
				
		vrep.simxSetObjectPosition(clientID, playerHandle, -1, pointer, remoteApi.simx_opmode_oneshot);
		wait(300);
		
	}
	
	public  void wait(int dur){
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
