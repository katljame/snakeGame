package Snake;

import javax.swing.JFrame;

public class Game extends JFrame {

	public static void main(String[] args) {
		new Game();
	}

	public Game() {
		this.add(new Graphics());
		this.setTitle("Snake Game");
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

}