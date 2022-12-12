package Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Graphics extends JPanel implements ActionListener {

	static final int WIDTH = 700;
	static final int HEIGHT = 700;
	static final int SIZE = 25;
	static final int GRASS = (WIDTH - SIZE) * (HEIGHT - SIZE);

	int[] x = new int[GRASS];
	int[] y = new int[GRASS];
	int snakeSize;

	Oranges oranges;
	int orangesEaten;

	Random r = new Random();
	String downRight = "DR";
	char letter = downRight.charAt(r.nextInt(downRight.length()));
	char direction = letter;

	boolean isGoing = false;

	int gameSpeed = 100;
	Timer timer = new Timer(gameSpeed, this);

	public Graphics() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(new Color(0, 154, 23));
		this.setFocusable(true);
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// restrict direction of snake so it doesn't hit itself
				if (isGoing) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						if (direction != 'R') {
							direction = 'L';
						}
						break;
					case KeyEvent.VK_RIGHT:
						if (direction != 'L') {
							direction = 'R';
						}
						break;
					case KeyEvent.VK_UP:
						if (direction != 'D') {
							direction = 'U';
						}
						break;
					case KeyEvent.VK_DOWN:
						if (direction != 'U') {
							direction = 'D';
						}
						break;
					}
				} else
				// restart the game
				{
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						start();
					} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						System.exit(0);
					}
				}
			}
		});

		// start the game
		start();
	}

	protected void start()
	// setup the game
	{
		x = new int[GRASS];
		y = new int[GRASS];
		snakeSize = 5;
		orangesEaten = 0;
		gameSpeed = 100;
		direction = letter;
		isGoing = true;
		getOranges();
		timer.start();
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);

		if (isGoing) {
			// make the orange when game is going
			g.setColor(Color.orange);
			g.fillOval(oranges.getOrangeX(), oranges.getOrangeY(), SIZE, SIZE);

			// make the snake when game is going
			g.setColor(Color.lightGray);
			for (int i = 0; i < snakeSize; i++) {
				g.fillRect(x[i], y[i], SIZE, SIZE);
			}
		} else {

			// display game over message
			g.setColor(Color.black);
			g.setFont(new Font("Times New Roman", Font.BOLD, 80));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("Sorry You Lose /:", (WIDTH - metrics2.stringWidth("Sorry You Lose /:")) / 2, HEIGHT / 2);

			// tell user they can restart
			g.setColor(Color.white);
			g.setFont(new Font("Times New Roman", Font.BOLD, 30));
			FontMetrics metrics3 = getFontMetrics(g.getFont());
			g.drawString("Press spacebar to restart or esc to close",
					(WIDTH - metrics3.stringWidth("Press spacebar to restart or escape to close")) / 2,
					g.getFont().getSize() + 50);

		}

		// display score whether game is going or not
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman", Font.BOLD, 30));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + orangesEaten, (WIDTH - metrics.stringWidth("Score: " + orangesEaten)) / 2,
				HEIGHT - 50);

	}

	// make snake move across the grass
	protected void move() {
		for (int i = snakeSize; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		// change snake directions
		switch (direction) {
		case 'U':
			y[0] = y[0] - SIZE;
			break;
		case 'D':
			y[0] = y[0] + SIZE;
			break;
		case 'L':
			x[0] = x[0] - SIZE;
			break;
		case 'R':
			x[0] = x[0] + SIZE;
			break;
		}

	}

	// generate new oranges
	protected void getOranges() {
		oranges = new Oranges();
	}

	// score points when oranges are eaten
	protected void eatOranges() {
		if ((x[0] == oranges.getOrangeX()) && (y[0] == oranges.getOrangeY())) {
			snakeSize++;
			orangesEaten++;
			getOranges();
		}
	}

	protected void collisionTest() {
		// check to see if snake hit themselves
		for (int i = snakeSize; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				isGoing = false;
				break;
			}
		}

		// check too see if snake hit the right
		if (x[0] < 0) {
			isGoing = false;
		}

		// check to see if snake hit the left
		if (x[0] > WIDTH - SIZE) {
			isGoing = false;
		}

		// check to see if snake hit the top
		if (y[0] < 0) {
			isGoing = false;
		}

		// check to see if snake hit the bottom
		if (y[0] > HEIGHT - SIZE) {
			isGoing = false;
		}

		// stop timer if snake hit something
		if (!isGoing) {
			timer.stop();
		}
	}

	@Override
	// keep game running
	public void actionPerformed(ActionEvent e) {
		if (isGoing) {
			move();
			collisionTest();
			eatOranges();
		}

		repaint();
	}
}