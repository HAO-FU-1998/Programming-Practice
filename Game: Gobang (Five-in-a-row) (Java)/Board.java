package ProjectOfFuHao;

public class Board {
	public static final int boardSize = 13;
	static String[][] arr = new String[boardSize][boardSize];

	// Construct the board
	Board() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				arr[i][j] = "+ ";
			}
		}
	}

	// Print the board
	public void printBoard() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
	}

	// Judge whether the position has already have a chess piece
	public static boolean againPut(int x, int y) {
		if (arr[x][y].equals("+ ")) {
			return true;
		} else {
			return false;
		}
	}

	// Judge who is the winner
	public boolean win(String qizi) {
		boolean flag = false;

		// Scan to the right to see if there are five pieces
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j <= boardSize - 5; j++) {
				if (arr[i][j].equals(qizi) && arr[i][j + 1].equals(qizi) && arr[i][j + 2].equals(qizi)
						&& arr[i][j + 3].equals(qizi) && arr[i][j + 4].equals(qizi)) {
					flag = true;
				}
			}
		}

		// Scan to the down бнбн
		for (int i = 0; i <= boardSize - 5; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (arr[i][j].equals(qizi) && arr[i + 1][j].equals(qizi) && arr[i + 2][j].equals(qizi)
						&& arr[i + 3][j].equals(qizi) && arr[i + 4][j].equals(qizi)) {
					flag = true;
				}
			}
		}

		// Scan to the left-down бнбн
		for (int i = 0; i <= boardSize - 5; i++) {
			for (int j = 4; j < boardSize; j++) {
				if (arr[i][j].equals(qizi) && arr[i + 1][j - 1].equals(qizi) && arr[i + 2][j - 2].equals(qizi)
						&& arr[i + 3][j - 3].equals(qizi) && arr[i + 4][j - 4].equals(qizi)) {
					flag = true;
				}
			}
		}

		// Scan to the right-down бнбн
		for (int i = 0; i <= boardSize - 5; i++) {
			for (int j = 0; j <= boardSize - 5; j++) {
				if (arr[i][j].equals(qizi) && arr[i + 1][j + 1].equals(qizi) && arr[i + 2][j + 2].equals(qizi)
						&& arr[i + 3][j + 3].equals(qizi) && arr[i + 4][j + 4].equals(qizi)) {
					flag = true;
				}
			}
		}

		return flag;
	}
}

