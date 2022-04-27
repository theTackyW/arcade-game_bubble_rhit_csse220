import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class: Level
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 * Purpose: This class handles updating the entities on the level
 *			and their collisions
 * 
 */
public class Level {

	private String filename;
	private int levelNum;
	private final int BLOCK_SIZE = 40;
	private char[][] levelLayout = new char[16][16];
	private boolean allEnemiesKilled = false;
	private int timeTicks = 0;
	private int levelStartTicks = 0;

	private PlayerCharacter player;
	private LevelComponent comp;
	private ArrayList<AbstractEnemy> enemies = new ArrayList<AbstractEnemy>();
	private ArrayList<LevelPlatform> platforms = new ArrayList<LevelPlatform>();
	private ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
	private ArrayList<Fruit> fruit = new ArrayList<Fruit>();

	public Level(int currentLevel, LevelComponent comp) throws IOException {
		this.levelNum = currentLevel;
		this.filename = "level" + currentLevel + ".txt";
		this.comp = comp;
		player = new PlayerCharacter(comp, 41, 560, 0, 0, 40, 40);
	}

	public int readTextFile() throws IOException {
		FileReader file = null;
		Scanner s = null;
		boolean fileRead = false;

		while (fileRead == false) {
			try {
				file = new FileReader(filename);
				s = new Scanner(file);
				fileRead = true;
			} catch (FileNotFoundException e) {
				levelNum = 1;
				filename = "level" + levelNum + ".txt";
			}
		}

		for (int j = 0; j < levelLayout[0].length; j++) {
			String current = s.next();
			for (int i = 0; i < levelLayout.length; i++) {

				levelLayout[i][j] = current.charAt(i);
			}
		}
		int numEnemyOne = Integer.parseInt(s.next());
		int numEnemyTwo = Integer.parseInt(s.next());
		for (int i = 1; i <= numEnemyOne; i++)
			enemies.add(new Mover(comp, 600, 50, Math.random() * .50 + .75, 0, 40, 40));
		for (int i = 1; i <= numEnemyTwo; i++)
			enemies.add(new Shooter(comp, 600, 50, Math.random() * .50 + .75, 0, 40, 40));
		file.close();
		levelStartTicks = 0;
		return levelNum;
	}

	public void drawOn(Graphics2D g2) {
		if(levelStartTicks < 200) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, 640, 640);
			g2.setColor(Color.WHITE);
			g2.setFont(comp.getFont());
			g2.drawString("Level " + comp.getLevelCount(), 240, 200);
			levelStartTicks++;
		}
		else {
			for (int i = 0; i < levelLayout.length; i++) {
				for (int j = 0; j < levelLayout[0].length; j++) {
					if (levelLayout[i][j] == 'X') {
						Color color = new Color((int) (Math.random() * 0x1000000));
						g2.setColor(color);
						g2.fillRect(i * 40, j * 40, BLOCK_SIZE, BLOCK_SIZE);
						platforms.add(new LevelPlatform(40, 40, Color.RED, i * 40, j * 40));
					} else {
						g2.setColor(Color.BLACK);
						g2.fillRect(i * 40, j * 40, BLOCK_SIZE, BLOCK_SIZE);

					}
				}
			}
			player.drawOn(g2);
			for (AbstractEnemy e : enemies) {
				e.drawOn(g2);
			}

			for (Fruit f : fruit) {
				f.drawOn(g2);
			}

			for (Bubble b : bubbles) {
				b.drawOn(g2);
			}
		}
	}

	public void handleEnemyDamage() {
		for (AbstractEnemy e : enemies) {
			if (!player.isInvunerable()) {
				if (e.overlaps(player)) {
					player.hit();
				}
				for (Bullet b : e.getBulletList()) {
					if (b.overlaps(player)) {
						b.markToRemove();
						player.hit();
					}

				}
			}
		}
	}

	public void handleTrapEnemy() {
		for (Bubble b : bubbles) {
			for (AbstractEnemy e : enemies) {
				if (b.overlaps(e) && !b.getContainsEnemy()) {
					b.trapEnemy(e);
					e.markToRemove();
				}
			}
		}
	}

	public void update() {
		if(levelStartTicks >= 200) {
			updatePlayer();
			updateEnemies();
			updateFruit();
			updateBubbles();

			handleTrapEnemy();
			handleEnemyDamage();

			remove();
			checkLevelAdvance();
		}
	}

	private void checkLevelAdvance() {
		int flag = 0;
		for (Bubble b : bubbles) {
			if (b.getContainsEnemy()) {
				flag++;
			}
		}
		if (enemies.isEmpty() && flag == 0) {
			this.allEnemiesKilled = true;
		}
		if (this.allEnemiesKilled) {
			this.timeTicks++;

		}
		if (this.timeTicks >= 300) {
			this.allEnemiesKilled = false;
			this.timeTicks = 0;
			comp.levelSetUp(comp.getCurrLevelNumber() + 1);
			comp.repaint();
		}
	}

	public void remove() {
		List<GameObject> shouldRemove = new ArrayList<>();

		for (GameObject object : enemies) {
			if (object.shouldRemove()) {
				shouldRemove.add(object);
			}
		}

		for (GameObject object : shouldRemove) {
			enemies.remove(object);
			object.onRemove();
		}
	}

	private void updateBubbles() {
		for (Bubble b : bubbles) {
			b.update();
		}

		List<Bubble> shouldRemove = new ArrayList<>();

		for (Bubble b : bubbles) {
			if (b.shouldRemove())
				shouldRemove.add(b);
			if (b.overlaps(player) && (b.getDistTraveled() > 100 || b.getDistTraveled() < -100)) {
				shouldRemove.add(b);
				if (b.getContainsEnemy()) {
					fruit.add(new Fruit(comp, b));
				}
			}
		}

		for (Bubble b : shouldRemove) {
			bubbles.remove(b);
			b.onRemove();
		}
	}

	public void updatePlayer() {
		player.update();
		if (player.shouldRemove()) {
			this.player = new PlayerCharacter(comp, 41, 560, 0, 0, 40, 40);
		}
	}

	public void updateEnemies() {
		for (AbstractEnemy e : enemies) {
			e.update();
		}
	}

	public void updateFruit() {
		for (Fruit f : fruit) {
			f.update();
		}
		List<Fruit> shouldRemove = new ArrayList<>();

		for (Fruit f : fruit) {
			if (f.overlaps(player) && f.getKillable()) {
				shouldRemove.add(f);
			}
		}

		for (Fruit f : shouldRemove) {
			fruit.remove(f);
			f.onRemove();
		}
	}

	public PlayerCharacter getPlayer() {
		return player;
	}

	public void addFruit(Fruit f) {
		fruit.add(f);
	}

	public void addBubble(Bubble b) {
		bubbles.add(b);
	}

	public void addEnemy(AbstractEnemy e) {
		enemies.add(e);
	}

	public ArrayList<LevelPlatform> getPlatforms() {
		return platforms;
	}
}
