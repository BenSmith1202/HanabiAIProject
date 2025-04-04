
public class Driver {
	public static void main(String[] args) {
		// TODO: Change from ConsolePlayer to Player after you have an implementation
		Hanabi game = new Hanabi(true, new ConsolePlayer(), new ConsolePlayer());
		game.play();
	}

	/**
	 * Used to evaluate your code
	 * @param numGames - number of games to run
	 * @param verbose - if true, prints each game's score
	 * @return average score
	 */
	public static double simulateGames(final int numGames, boolean verbose){
		int total = 0;
		for (int i = 0; i < numGames; i++) {
			Hanabi next = new Hanabi(false, new Player(), new Player());
			int score;

			try {
				score = next.play();
			}
			catch (Exception e) {
				System.out.println(e.toString());
				if (verbose) {
					System.out.println("Error; Score: 0");
				}
				return 0.0;
			}

			if (verbose) {
				System.out.println("Game " + i + " score: " + score);
			}

			total += score;
		}
		return total/(double)numGames;
	}

}
