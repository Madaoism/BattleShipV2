package entities;

public class AI {
	public int dim = 10;
	public BattleShip[] ships;
	public int numShips = ships.length;
	private static FleetBoard aiFleet;
	private static ShotBoard aiShots;
	private static boolean playing = false;
	

	public AI(BattleShip[] unplacedShips){
		playing = true;
		aiFleet = new FleetBoard(unplacedShips, true);
//		while (playing) {
//			fireMissiles(aiShots);
//		}
	}
	public String recieveMissiles(String input){
		String results = aiFleet.landedShots(input);
		return results;
	}
	private String incomingShotResult(Shot shot) {
		String result = aiFleet.incomingShotResult(shot);
		return result;
	}
	
	public String fireMissiles(ShotBoard shots){
		String messageOut = "fired:";
		for(int i = 0; i<numShips; i++){
			messageOut+= generateRandom(shots);
			if (i<(numShips-1)) messageOut+=";";
		}
		return messageOut;
	}
	
	private String generateRandom(ShotBoard shots) {
		return shots.generateRandom();
	}
	
	
}
