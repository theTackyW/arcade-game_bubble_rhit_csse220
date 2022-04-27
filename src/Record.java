import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Class: Record
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 * Purpose: This class handles the end game screen,
 * 			specifically the highscore files and displaying
 * 			of that score.
 * 
 */
public class Record {

	private ArrayList<Integer> record;
	
	public Record() {
		this.readFile();
	}
	public void readFile() {
		this.record = new ArrayList<Integer>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File("record.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("record.txt not found");
			return;
		}
		
		while (scanner.hasNextInt()) {
			int s = scanner.nextInt();
			this.record.add(s);
		}
		scanner.close();
	}
	
	public void writeScore(int score) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter("record.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		for(int i=0;i<this.record.size();i++) {
			pw.println(record.get(i));
		}	
		pw.println(score);
		pw.close();

	}
	
	public ArrayList<Integer> getRecord(){
		this.readFile();
		return this.record;
	}
	
}
