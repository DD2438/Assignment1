
import Astar.Remote;
import Astar.Map;

public class MapBuilder {

    boolean[][] board;
    Remote r;
    Mover m;

    public MapBuilder(Map map, Remote r, Mover m) {

        board = map.map;
        this.m = m;
        this.r = r;

	int wallHandle = r.getHandle("Wall");

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j]) {
                    m.move(j, i, r.duplicate(wallHandle));
                }
            }
        }
    }
}
