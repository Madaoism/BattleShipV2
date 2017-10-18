package entities;

public class ShotBoard {
	public int dim = 10;
	public int[][] board;
	public boolean noCollision;
	public int shipsLeft;
	public boolean[] shipsFound;
	public int[] ships;

	public ShotBoard(BattleShip[] shipsIn) {
		ships = new int[shipsIn.length];
		for (int i = 0; i<shipsIn.length; i++) ships[i] = shipsIn[i].getShipLength();
		shipsLeft = ships.length;
		board = newBoard(dim);
		shipsFound = new boolean[ships.length];
		for (boolean ship: shipsFound){
			ship = false;
		}
	}
	public void addShot(int y, int x, int ship) {
		board[y][x] = ship;
		if (ship >0 ) hitShip(ship);
		System.out.println("hit"+ship);
	}
	public int[] findEmpty(){
		boolean successful = false;
		int[] coords = new int[2];
		while(!successful){
			coords[0] = randomNum();
			coords[1] = randomNum();
			successful = testEmpty(coords);
		}
		return coords;
	}
	public int[] findShip(int num){
		int[] coords= new int[2];
		for (int i = 0; i<dim;i++){
			for (int j = 0;j<dim;j++){
				if (board[j][i]== num){
					coords[0] = j;
					coords[1] = i;
					break;
				}
			}
		}
		System.out.println("Ship"+num+"at"+coords[0]+","+coords[1]);
		return coords;
	}
	public boolean testEmpty(int[] coords){
		int x = coords[0];
		int y = coords[1];
		boolean noCollisionTemp = false;
		if (board[y][x]==-1) noCollisionTemp = true;
		return noCollisionTemp;
	}
	public int randomNum(){
		float floatCo = Math.round(Math.random()*dim - .5);
		int intCo = Math.round(floatCo);
		return intCo;
	}
	public boolean foundAny(){
		boolean found = false;
		for (boolean ship: shipsFound){
			if (ship) found = true;
		}
		return found;
	}
	public int randomFoundShip(){
		int intCo = 0;
		if (!foundAny()) return 0;
		boolean successful = false;
		while (!successful) {
			float floatCo = Math.round(Math.random()*shipsFound.length - .5);
			intCo = Math.round(floatCo);
			if (shipsFound[intCo]) successful = true;
		}
		
		return intCo+1;
	}

	private void hitShip(int shipNum) {
		shipsFound[shipNum-1] = true;
		ships[shipNum-1]--;
		if (ships[shipNum-1]==0) shipsFound[shipNum-1] = false;
	}

	public int[][] newBoard(int dim) {
		int[][] emptyBoard = new int[dim][dim];
		for (int col = 0; col<dim; col++) {
			for (int row = 0; row< dim; row++) {
				emptyBoard[row][col] = -1;
			}
		}
		return emptyBoard;
	}
	
	public int[] findSmartShot() {
//		int[] coords = new int[2];
		int[] knownCoords = new int[2];
		int ship = randomFoundShip();
		if (ship!=0){
			knownCoords = findShip(ship);
			String direction = findDirection(knownCoords,ship);
			if (direction == "vertical") {
				//find empty touching in column
				for (int i = 0; i <ships[ship];i++){
					if (board[knownCoords[0]][knownCoords[1]+i]==0&&knownCoords[1]<10) {
						int [] up = {knownCoords[0],knownCoords[1]+i};
						
						if (testEmpty(up))return up;
					}
					else if (board[knownCoords[0]][knownCoords[1]-i]==0&&knownCoords[1]>=0) {
						int [] down = {knownCoords[0],knownCoords[1]-i};
						if (testEmpty(down))return down;
					}
				}
			}
			else if(direction=="horizontal"){
				//find empty touching in row
				for (int i = 0; i <ships[ship];i++){
					if (board[knownCoords[0]+i][knownCoords[1]]==0&&knownCoords[0]<10) {
						int [] right = {knownCoords[0]+i,knownCoords[1]};
						if (testEmpty(right))return right;
					}
					else if (board[knownCoords[0]-i][knownCoords[1]]==0&&knownCoords[0]>=0) {
						int [] left = {knownCoords[0]-i,knownCoords[1]};
						if (testEmpty(left))return left;
					}
				}
			}
		}
		return knownCoords;
	}
	private String findDirection(int[] knownCoords, int ship) {
		for (int i = 0; i<dim; i++) {
			if (board[knownCoords[0]][i]==ship&&i!=knownCoords[1]){
				return "horizontal";
			}
			else if (board[i][knownCoords[1]]==ship&&i!=knownCoords[0]){
				return "vertical";
			}
		}
		return "unknown";
	}
}
