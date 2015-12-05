import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;

public class SolveConnectFour implements ActionListener {

	static PrintWriter output;
	static int wins, losses, draws;
	static CFPanel panel;
	static Timer timer;
	static int times;

	public SolveConnectFour() {
		timer = new Timer(1000, this);
		timer.start();
		try {
			output = new PrintWriter(new File("ConnectFourSolution.txt"));
		} catch (Exception e) {}
	}

	public void actionPerformed(ActionEvent e) {
		times++;
		// System.out.println("Checked " + (wins + losses + draws) + " nodes at time = " + times);
	}

	public static void main(String[] args) throws Throwable {
		SolveConnectFour scf = new SolveConnectFour();
		char[][] board = new char[6][7];
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = ' ';

		solve(board, true, 0, 5, 0);
	}

<<<<<<< HEAD
	public static String[] solve(char[][] board, boolean turn, int turns, int x, int y) throws Throwable {
		char result = gameOver(board, x, y);
		// if (turns == 25) System.out.println(x + "\t" + y);
		// if (turns > 0) {
			// for (int i = 0; i < turns; i++) {
				//System.out.print("  ");
			// }
			//System.out.println("(" + x + ", " + y + ")");
		// }
		if (result == 'R' || result == 'Y') {
			// for (int i = 0; i < turns; i++) {
				//System.out.print("  ");
			// }
			//System.out.println(result + " wins");
			if (result == 'R') wins++;
			else losses++;
			return new String[] {result + "", ""};
		}
		if (turns == board.length * board[0].length) {
			draws++;
			return new String[] {result + "", ""};
		}
		String best;
		if (turn) best = "Y";
		else best = "R";
		int bestIndex = 0;
		String prevStr = "";
		for (int i = 0; i < board[0].length; i++) {
			int bottom;
			for (bottom = board.length - 1; bottom >= 0; bottom--) {
				if (board[bottom][i] == ' ') break;
			}
			if (bottom == -1) continue;
			if (turn) board[bottom][i] = 'R';
			else board[bottom][i] = 'Y';			// Thread.sleep(100);
			String[] codes = solve(board, !turn, turns + 1, bottom, i); // code?
			String code = codes[0];
			System.out.println(codes[0] + "\t" + codes[1]);
			board[bottom][i] = ' ';
			if (turn && code.charAt(0) < best.charAt(0)) {
				best = new String(code);
				bestIndex = i;
				prevStr = codes[1];
			}
			if (!turn && code.charAt(0) > best.charAt(0)) {
				best = new String(code);
				bestIndex = i;
				prevStr = codes[1];
			}
			if (turn && code.equals("R")) {
				prevStr = codes[1];
				return new String[] {"R", (i + 1) + "" + prevStr};
			}
			if (!turn && code == "Y") {
				prevStr = codes[1];
				return new String[] {"Y", (i + 1) + "" + prevStr};
			}
		}
		return new String[] {best, (bestIndex + 1) + "" + prevStr};
=======
	public static int[] solve(char[][] board, boolean turn, int turns, int x, int y) throws Throwable {
		char result = gameOver(board, x, y);

		if (result != 'f')
			if (result == 'R') {
				wins++;
				return new int[] {1, y};
			}
			else {
				losses++;
				return new int[] {-1, y};
			}
		if (turns == board.length * board[0].length) {
			draws++;
			return new int[] {0, y};
		}
		int bottom;
		int best = turn ? -1:1;
		int bestMove = 0;

		for (int i = 0; i < board[0].length; i++) {
			if (board[0][i] != ' ')
				continue;
			for (bottom = board.length - 1; bottom >= 0 && board[bottom][i] != ' '; bottom--);

			board[bottom][i] = turn ? 'R':'Y';

			int outcome = solve(board, !turn, turns + 1, bottom, i)[0];
			board[bottom][i] = ' ';

			if ((turn && outcome > best) || (!turn && outcome < best)) {
				best = outcome;
				bestMove = i;
				if (best == 1 || best == -1)
					return new int[] {best, bestMove};
			}
		}
		return new int[] {best, bestMove};
>>>>>>> origin/master
	}

  public static char gameOver(char[][] tboard, int x, int y) {
    int countConsecutive = 0;
    char color = ' ';
    int i, a;
    
    for (i = x - 3; i <= x + 3; i++) // Horizontal
      if (i >= 0 && i < tboard.length && countConsecutive < 4)
        if (tboard[i][y] == color)
          countConsecutive++;
        else if (tboard[i][y] == 'R' || tboard[i][y] == 'Y') {
          color = tboard[i][y];
          countConsecutive = 1;
        }
        else	color = ' ';
      else if (countConsecutive == 4)
        return color;
    if (countConsecutive == 4)
      return color;
    
    countConsecutive = 0;
    color = ' ';
    
    for (a = y - 3; a <= y + 3; a++) // Vertical
      if (a >= 0 && a < tboard.length && countConsecutive < 4)
        if (tboard[x][a] == color)
          countConsecutive++;
        else if (tboard[x][a] == 'R' || tboard[x][a] == 'Y') {
          color = tboard[x][a];
          countConsecutive = 1;
        }
        else	color = ' ';
      else if (countConsecutive == 4)
        return color;
    if (countConsecutive == 4)
      return color;
    
    countConsecutive = 0;
    color = ' ';
    
    for (i = x - 3, a = y - 3; i <= x + 3; i++, a++) // diagonal 1 topleft - bottomright
      if (a >= 0 && a < tboard.length && i >= 0 && i < tboard.length && countConsecutive < 4)
        if (tboard[i][a] == color)
          countConsecutive++;
        else if (tboard[i][a] == 'R' || tboard[i][a] == 'Y') {
          color = tboard[i][a];
          countConsecutive = 1;
        }
        else	color = ' ';
      else if (countConsecutive == 4)
        return color;
    if (countConsecutive == 4)
      return color;
    
    countConsecutive = 0;
    color = ' ';
    
    for (i = x - 3, a = y + 3; i <= x + 3; i++, a--) // diagonal 1 topright - bottomleft
      if (a >= 0 && a < tboard.length && i >= 0 && i < tboard.length && countConsecutive < 4)
        if (tboard[i][a] == color)
          countConsecutive++;
        else if (tboard[i][a] == 'R' || tboard[i][a] == 'Y') {
          color = tboard[i][a];
          countConsecutive = 1;
        }
        else	color = ' ';
      else if (countConsecutive == 4)
        return color;
    if (countConsecutive == 4)
      return color;
    
    return 'f';
  }
}
