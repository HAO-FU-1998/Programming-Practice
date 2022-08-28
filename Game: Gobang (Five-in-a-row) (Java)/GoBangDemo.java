package ProjectOfFuHao;

public class GoBangDemo {
	public static void main(String[] args) {
		// Establish the players and the board
		Board b = new Board();
		System.out.println("The initial chessboard is as follows:");
		b.printBoard();

		Player p1 = new Player("X ");
		Player p2 = new Player("O ");

		// Start to play!!
		while (true) {
			System.out.println("Player 1's order:");
			p1.put();
			b.printBoard();
			if (b.win(p1.qizi)) {
				System.out.println("Player 1 wins!");
				break;
			}
			System.out.println("Player 2's order:");
			p2.put();
			b.printBoard();
			if (b.win(p2.qizi)) {
				System.out.println("Player 2 wins!");
				break;
			}
		}
	}
}
