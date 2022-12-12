package Snake;

import java.util.Random;

public class Oranges {

	private final int orangeX;
	private final int orangeY;

	// randomly place the orange
	public Oranges() {
		orangeX = createLoc(Graphics.WIDTH);
		// make it so orange will not be placed where the score is
		orangeY = createLoc(Graphics.HEIGHT - 50);
	}

	// random location generator
	private int createLoc(int size) {
		Random random = new Random();
		return random.nextInt(size / Graphics.SIZE) * Graphics.SIZE;
	}

	// show where x is for orange
	public int getOrangeX() {
		return orangeX;
	}

	// show where y is for orange
	public int getOrangeY() {
		return orangeY;
	}
}