import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JComponent;

/**
 * Class: LevelComponent
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 * Purpose: This class sets up each level screen and handles
 * 			the end of the game.
 */
public class LevelComponent extends JComponent {

	private int score = 0;
	private int lives = 3;
	private int levelCount = 1;
	private int currentLevelNumber = 1;
	private int numTicks;
	private Level currentLevel;
	private Font videoGameFont = null;
	private Record record = new Record();
	private ArrayList<Integer> scoreList;
	
	public LevelComponent() {
		levelSetUp(1);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		File fontFile = new File("kongtext.ttf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		font.deriveFont((float) 12);
		videoGameFont = font.deriveFont(font.getSize() * 24F);

		if (this.lives > 0) {
			drawScore(g2);
		} else {
			drawEndScreen(g2);
		}
	}

	private void drawEndScreen(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, 640, 640);
		g2.setColor(Color.WHITE);
		g2.setFont(videoGameFont);
		g2.drawString("GAME OVER", 200, 100);
		Collections.sort(scoreList);
		if(this.score>this.scoreList.get(scoreList.size()-2)) {
			//if score is larger than the second largest record, 
			//this is a new high score (this way if it's tie with the old highest score, it will not say new record)
			g2.setColor(Color.YELLOW);
			g2.drawString("NEW RECORD!",180,150);
		}
		g2.setColor(Color.RED);	
		boolean printed = false;
		for(int i=0;i<scoreList.size()&&i<9;i++) {//show top 10 only
			int currentScore =scoreList.get(scoreList.size()-i-1);
			String s = (i+1)+": "+currentScore;
			g2.drawString(s, 200, 200+i*20);
			if(currentScore==this.score && printed == false) {
				g2.drawString("<- you", 400, 200+i*20);
				printed = true;
			}
		}
		
		if(scoreList.size()>10&&this.score<scoreList.get(scoreList.size()-10)) {
			//if my score is not in top ten, find it and print the rank out
			g2.drawString("|", 200, 400);
			for(int i=0;i<scoreList.size();i++) {
				if(scoreList.get(i)==this.score) { 
					g2.drawString((scoreList.size()-i)+": "+this.score, 200, 420);
					g2.drawString("<- you", 400, 420);
					break;
				}
			}
		}
	}

	private void drawScore(Graphics2D g2) {
		currentLevel.drawOn(g2);
		g2.setColor(Color.WHITE);
		g2.setFont(videoGameFont);
		g2.drawString("Score:" + score,10, 30);
		g2.drawString(" Lives:" + lives, 440, 30);
	}

	public void setCurrLevel(int number) {
		currentLevelNumber = number;
	}

	public int getCurrLevelNumber() {
		return currentLevelNumber;
	}

	public void updateState() {
		int levelFlag = this.currentLevelNumber;
		if (lives > 0) {
			this.numTicks++;
			currentLevel.update();
		}
		if (levelFlag != this.currentLevelNumber) {
			levelFlag = this.currentLevelNumber;
			this.levelCount++;
		}
	}

	public void levelSetUp(int levelNumber) {
		currentLevelNumber = levelNumber;
		Level level = null;
		try {
			level = new Level(currentLevelNumber, this);
			currentLevelNumber = level.readTextFile();
			this.currentLevel = level;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawScreen() {
		this.repaint();
		this.revalidate();
	}

	public PlayerCharacter getPlayer() {
		return currentLevel.getPlayer();
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public void reduceLives() {
		this.lives -= 1;
		if(this.lives==0) {
			this.record.writeScore(this.score);
			this.scoreList = record.getRecord();
		}
	}

	public void resetLives() {
		this.lives = 3;
	}

	public int getLevelCount() {
		return levelCount;
	}
	
	public int getLives() {
		return this.lives;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public Font getFont() {
		return videoGameFont;
	}
}
