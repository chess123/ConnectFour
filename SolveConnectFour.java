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

	public static int[] solve(char[][] board, boolean turn, int turns, int x, int y) throws Throwable {
		if (turns == board.length * board[0].length) {
			draws++;
			return new int[] {0, y};
		}

		char color = turn ? 'R':'Y';

		int result = winningMove(board, color);
		if (result >= 0)
			if (turn) {
				wins++;
				return new int[] {1, result};
			}
			else {
				losses++;
				return new int[] {-1, result};
			}

		int bottom, outcome;

		result = winningMove(board, turn ? 'Y':'R');
		if (result >= 0) {
			for (bottom = board.length - 1; bottom >= 0 && board[bottom][result] != ' '; bottom--);
			board[bottom][result] = color;

			outcome = solve(board, !turn, turns + 1, bottom, result)[0];
			board[bottom][result] = ' ';
			return new int[] {outcome, result};
		}

		
		int best = turn ? -1:1;
		int bestMove = 0;

		for (int i = 0; i < board[0].length; i++) {
			if (board[0][i] != ' ')
				continue;
			for (bottom = board.length - 1; bottom >= 0 && board[bottom][i] != ' '; bottom--);

			board[bottom][i] = color;

			outcome = solve(board, !turn, turns + 1, bottom, i)[0];
			board[bottom][i] = ' ';

			if ((turn && outcome > best) || (!turn && outcome < best)) {
				best = outcome;
				bestMove = i;
				if (best == 1 || best == -1)
					return new int[] {best, bestMove};
			}
		}
		return new int[] {best, bestMove};
	}

	public static int winningMove(char[][] tboard, char color) {
		int bottom;
		char result;
		for (int i = 0; i < tboard[0].length; i++) {
			if (tboard[0][i] != ' ')
				continue;
			for (bottom = tboard.length - 1; bottom >= 0 && tboard[bottom][i] != ' '; bottom--);
			tboard[bottom][i] = color;
			result = gameOver(tboard, bottom, i);
			tboard[bottom][i] = ' ';
			if (result == color)
				return i;
		}
		return -1;
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
