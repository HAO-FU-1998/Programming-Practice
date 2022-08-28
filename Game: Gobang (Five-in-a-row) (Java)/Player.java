package ProjectOfFuHao;

import java.util.Scanner;

public class Player {
	// Different players select different chess piece
	String qizi;

	// Input the coordinate of the piece
	private int x;
	private int y;

	Player() {
	};

	Player(String qizi) {
		this.qizi = qizi;
	}

	Scanner sc = new Scanner(System.in);

	// Play the game
	public void put() {
		System.out.println("Please put down your chess piece in the form of (x y) (0-12):");

		while (true) {
			// Receive the coordinate of the piece
			x = sc.nextInt();
			y = sc.nextInt();

			// Judge repetition and put down the piece
			if (Board.againPut(x, y)) {
				Board.arr[x][y] = qizi;
				break;
			} else {
				System.out.println("The position you choose has already have a piece, please write your selection again:");
				continue;
			}
		}
	}
}

