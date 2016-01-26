package Astar;

public class Test {

	public static void main(String[] args){
		boolean[][] obstacles = new CsvReader("discMap.csv",20,20).convert();
		
		Map map = new Map(obstacles,1,1,14,19);
	//	Map map = new Map(20,20,1,1,19,14);
		
	//	System.out.println(map.getShortestPath().toString());
		map.getShortestPath();
		map.print();
	}
}
