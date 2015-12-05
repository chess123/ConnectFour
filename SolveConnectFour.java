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
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = ' ';
			}
		}
		solve(board, true, 0, 5, 0);
	}

	public static char solve(char[][] board, boolean turn, int turns, int x, int y) throws Throwable {
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
			return result;
		}
		if (turns == board.length * board[0].length) {
			draws++;
			return result;
		}
		ArrayList<Integer> results = new ArrayList<Integer>(board[0].length);
		for (int i = 0; i < board[0].length; i++) {
			int bottom;
			if (board[0][i] == ' ')
				continue;
			for (bottom = board.length - 1; bottom >= 0 && board[bottom][i] != ' '; bottom--);
			board[bottom][i] = turn ? 'R':'Y';
			// Thread.sleep(100);
			int code = (int)(solve(board, !turn, turns + 1, bottom, i)); // code?
			if (results.indexOf(code) < 0) results.add(code); // no clue what the heck this does o_O
			board[bottom][i] = ' ';
			if (turn && code == 'R') {
				return 'R';
			}
			if (!turn && code == 'Y') {
				return 'Y';
			}
		}
		if (turn) { // what is this? doesn't this only get called in case of a tie?
			return (char)(min(results));
		}
		else {
			return (char)(max(results));
		}
	}

	public static int min(ArrayList<Integer> results) {
		int min = results.get(0);
		for (int i : results) {
			if (i < min) min = i;
		}
		return min;
	}

	public static int max(ArrayList<Integer> results) {
		int max = results.get(0);
		for (int i : results) {
			if (i > max) max = i;
		}
		return max;
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
