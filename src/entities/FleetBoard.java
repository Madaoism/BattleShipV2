package entities;

//added ship number, alive, shipHealth to BattleShip
public class FleetBoard {
	// Creates game board 10x10 of location of ships and opponents shots.
	public int dim = 10;
	public String[][] board;
	private BattleShip[] ships;
	public boolean noCollision;
	public boolean gameOver;
	public int shipsLeft;
	public ShotBoard enemyShots;
	
	public FleetBoard(BattleShip[] shipsIn) {
		ships = shipsIn;
		shipsLeft = ships.length;
		board = newBoard(dim);
		noCollision = collisionTest(ships);
		enemyShots = new ShotBoard(shipsIn);
	}

	public FleetBoard(BattleShip[] shipsIn, boolean ai) {
		ships = shipsIn;
		shipsLeft = ships.length;
		board = newBoard(dim);
		for (int i = 1; i <= ships.length; i++) {
			ships[i - 1].shipNumber = i;
		}
		if (ai) {
			enemyShots = new ShotBoard(shipsIn);
			aiPlaceShips(ships);
		// noCollision = collisionTest(ships); //double check
		}
	}

	public void printShips(BattleShip[] ships) {
		for (BattleShip ship : ships) {
			String orientation = "horizontal";
			if (ship.isFlipped())
				orientation = "vertical";
			System.out.println("Ship" + ship.shipNumber + ": is" + orientation
					+ "at cooridinate (" + ship.getxCo() + "," + ship.getyCo()
					+ ")");
		}
	}

	public String[][] newBoard(int dim) {
		String[][] emptyBoard = new String[dim][dim];
		for (int col = 0; col < dim; col++) {
			for (int row = 0; row < dim; row++) {
				emptyBoard[row][col] = "empty";
			}
		}
		return emptyBoard;
	}

	public boolean collisionTest(BattleShip[] ships) {
		boolean noCollisionTemp = true;
		for (int i = 1; i <= ships.length; i++) {
			ships[i - 1].shipNumber = i;
			boolean successful = placeShip(ships[i - 1]);
			if (!successful) {// then there has been a collision and you cannot
								// proceed
				System.out.println("Collision! Ship" + i + "is colliding");
				noCollisionTemp = false;
				break;
			}
		}
		return noCollisionTemp;
	}

	public void aiPlaceShips(BattleShip[] shipsIn) {
		for (BattleShip ship : shipsIn) {
			boolean placed = false;
			while (!placed) {
				ship.flipped = false;
				if (Math.random() > .5) {// if random number is greater than .5,
											// we make it vertical
					ship.flipped = true;
				}
				ship.xCo = generateRandomCoord(ship, "x");
				ship.yCo = generateRandomCoord(ship, "y");
				placed = placeShip(ship);
			}
		}
		System.out.println("Generated random ship positions!");
		printShips(shipsIn);
	}

	public int generateRandomCoord(BattleShip ship, String xy) {
		int viableSpace = dim;
		if ((xy == "y"&&ship.flipped)||(xy == "x" && !ship.flipped)) {//implies short dimension
			int len = ship.shipLength;
			viableSpace = dim-len;
		}
		float floatCo = Math.round(Math.random()*viableSpace - .5);
		int intCo = Math.round(floatCo);
		return intCo;
	}

	public boolean isNoCollision() {
		return noCollision;
	}

	public void setNoCollision(boolean noCollision) {
		this.noCollision = noCollision;
	}
	
	public boolean placeShip(BattleShip ship){
		boolean successful = true;
		ship.shipHealth = ship.shipLength;
		int len = ship.shipLength;
		int X = ship.xCo;
		int Y = ship.yCo;
		boolean flipped = ship.isFlipped();// true = vertical
		int[] validCoords = new int[len]; 
		//testLocation
		if (flipped) {//vertical
			int columnTemp = X;
			for (int rowTemp = Y; rowTemp < Y + len; rowTemp++) {
				if (board[rowTemp][columnTemp]=="empty") { // if no other ships there
					 validCoords[rowTemp-Y] = 1;
				}
				else { // throw error for collision
					validCoords[rowTemp-Y] = 0;
				}
			}
			for (int valid : validCoords) {
				if (valid != 1) successful = false;
			}
			if(successful){
				for (int rowTemp = Y; rowTemp < Y + len; rowTemp++) {
					board[rowTemp][columnTemp] = "ship"+ship.shipNumber;
				}
			}
		}
		else{ //horizontal
			int rowTemp = Y;
			for (int columnTemp = X; columnTemp < X + len; columnTemp++) {
				if (board[rowTemp][columnTemp]=="empty") {// if no other ships there
					validCoords[columnTemp-X] = 1;
				}
				else {// throw error for collision
					validCoords[columnTemp-X] = 0;
				}
			}
			for (int valid : validCoords) {
				if (valid != 1) successful = false;
			}
			if(successful){
				for (int columnTemp = X; columnTemp < X + len; columnTemp++) {
					board[rowTemp][columnTemp] = "ship"+ship.shipNumber;
				}
			}
		}
		return successful;

	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public BattleShip[] getShips() {
		return ships;
	}

	public void setShips(BattleShip[] ships) {
		this.ships = ships;
	}

	public String incomingShotResult(Shot target) {
		String shotResult = "miss";
		int shipNum = -1;
		int X = target.x;
		int Y = target.y;
		if (board[Y][X] == "empty") {
			board[Y][X] = "miss";
			return board[Y][X];
		} else if (board[Y][X].substring(0, 4).equalsIgnoreCase("ship")) {
			shipNum = Integer.parseInt(board[Y][X].substring(4));
			hitShip(shipNum);
			board[Y][X] = "hit" + shipNum;
			return board[Y][X];
		}
		return "error: target already fired here";
	}

	private void hitShip(int shipNum) {
		for (BattleShip ship : ships) {
			if (ship.shipNumber == shipNum) {
				ship.hit();
				int shipTemp = 0;
				
				for (int i = 0; i<ships.length; i++) {
					if (ships[i].getAlive()) shipTemp ++;
				}
				
				shipsLeft = shipTemp;
				if (shipsLeft == 0) {
					gameOver = true;
				}
			}
		}

	}

	public boolean testShotCollision(FiringTarget target) {
		String shotResult = "miss";
		int shipNum = -1;
		int X = target.xCo;
		int Y = target.yCo;
		if (board[X][Y] == "empty") {
			// board[X][Y] = "miss";
			return false;
		}

		else if (board[X][Y].substring(0, 4).equalsIgnoreCase("ship")) {
			// shipNum = Integer.parseInt(board[X][Y].substring(4));
			// hitShip(shipNum);
			// board[X][Y] = "hit ship " + shipNum;
			return false;
		}
		return true;
	}

	//
	public String landedShots(String shotsIn) {
		Shot[] shots = shotInterpret(shotsIn);
		String results = shotResults(shots);
		return results;

	}

	public Shot[] shotInterpret(String shotsIn) {// gage
		String[] shots = shotsIn.split(";");
		Shot[] shotsArr = new Shot[shots.length];
		int count = 0;
		for (String shot : shots) {
			String[] shotTemp = shot.split(",");
			int xCo = Integer.parseInt(shotTemp[0]);
			int yCo = Integer.parseInt(shotTemp[1]);
			Shot shotShot = new Shot(xCo, yCo);
			shotsArr[count] = shotShot;
			count++;
		}
		return shotsArr;
	}

	public String shotFired(FiringTarget target) {
		String shotResult = "miss";
		int shipNum = -1;
		int X = target.getxCo();
		int Y = target.getyCo();
		if (board[X][Y] == "empty") {
			board[X][Y] = "miss";
			target.setResult(0);
			enemyShots.addShot(target.xCo,target.yCo,target.result);
			return shotResult;
		}

		else if (board[X][Y].substring(0, 4).equalsIgnoreCase("ship")) {
			shipNum = Integer.parseInt(board[X][Y].substring(4));
			hitShip(shipNum);
			board[X][Y] = "hit ship " + shipNum;
			target.setResult(shipNum);
			enemyShots.addShot(target.xCo,target.yCo,target.result);
			return "hit";
		}
		return "error: target already fired here";
	}
	public String generateRandom() {
		int[] intCoords = findEmpty();
		String coords = "";
		coords += intCoords[0] + "," + intCoords[1];
		return coords;
	}

	public int[] findEmpty() {
		boolean successful = false;
		int[] coords = new int[2];
		while (!successful) {
			coords[0] = randomNum();
			coords[1] = randomNum();
			successful = collisionTest(coords);
		}
		return coords;
	}

	public boolean collisionTest(int[] coords) {
		int x = coords[0];
		int y = coords[1];
		boolean noCollisionTemp = false;
		if (board[y][x] == "empty")
			noCollisionTemp = true;
		return noCollisionTemp;
	}

	public int randomNum() {
		float floatCo = Math.round(Math.random() * dim - .5);
		int intCo = Math.round(floatCo);
		return intCo;
	}

	private String shotResults(Shot[] shotsIn) {// gage
		String result = "result:";
		int count = 0;
		for (Shot shot : shotsIn) {
			count++;
			result += shotResult(shot);
			if ((count == shotsIn.length))
				break;
			result += ",";

		}
		return result;
	}

	private String shotResult(Shot shotIn) {// gage
		int x = shotIn.x;
		int y = shotIn.y;
		String hit = board[y][x];

		if (hit == "empty") {
			hit = "miss";
		} else if (hit.substring(0, 4).equals("ship")) {
			hit = "hit " + hit + "!";
		} else if (hit.equals("miss") || hit.substring(0, 3).equals("hit")) {
			return "already shot there";
		}
		shotIn.result = hit;
		board[y][x] = hit;
		return hit;
	}

	// private static void err() throws Exception{
	// throw new Exception();
	// }
}
