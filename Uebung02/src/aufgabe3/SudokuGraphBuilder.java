package aufgabe3;

import java.util.Set;
import java.util.TreeSet;

public class SudokuGraphBuilder {
	
	int rows;
	int cols;
	
	int width;
	
	String[][] field;
	
	Set<String> kanten = new TreeSet<String>();
		
		
	public SudokuGraphBuilder(int width){
		this.rows = width;
		this.cols = width;
		this.width = (int)Math.sqrt(width);
		field = new String[rows][cols];
	}
	
	public void buildGrid(){

		 // erst mal die Knoten ausgeben
		System.out.println("# Knoten");
		for(int y = 0; y < rows; y++){ // außen
			for (int x = 0; x < cols; x++) {// innen
				System.out.println("knoten "+y+":"+x);
				field[y][x] = y+":"+x;
			}
		}
		System.out.println();
		System.out.println("# Kanten");
		// zeilen verbinden
		for(int x = 0; x < cols; x++){ // außen
			for (int i = 0; i < cols; i++) {
				for (int pos = i+1; pos < cols; pos++) {
					kanten.add("kante "+x+':'+i+' '+x+':'+pos);
				}
			}
		}
		// spalten verbinden
		for(int y = 0; y < rows; y++){ // außen
			for (int i = 0; i < rows; i++) {
				for (int pos = i+1; pos < rows; pos++) {
					kanten.add("kante "+i+':'+y+' '+pos+':'+y);
				}
			}
		}
		// jedes Feld untereinander verbinden
		String i;
		String o;
		for (int y = 0; y < rows; y+=width) {
			for (int x = 0; x < cols; x+=width) {
				// kästelchen
//				System.out.println("neues kästelchen");
				for (int i_y = 0; i_y < width; i_y++) {
					for (int i_x = 0; i_x < width; i_x++) {
						// ein feld im kästelchen
						// also...
						// feld im kästelchen y+i : x+i
						i = field[y+i_y][x+i_x];
//						System.out.println(i);
						for (int o_y = 0; o_y < width; o_y++) {
							for (int o_x = 0; o_x < width; o_x++) {
								// alle anderen felder im Kästelchen
								if(o_y != i_y && o_x != i_x && !field[y+o_y][x+o_x].equals("-")){// feld nicht mit sich selbst
									
									o = field[y+o_y][x+o_x];
									kanten.add("kante "+i+' '+o);
//									System.out.println("-->"+o);
								}
							}
						}
						field[y+i_y][x+i_x] = "-";
					}
				}
				
			}
		}
			
		// Kanten ausgeben
		for (String string : kanten) {
			System.out.println(string);
		}
	}
	
	
	
	
	public static void main(String[] args) {
		SudokuGraphBuilder s = new SudokuGraphBuilder(16);
		s.buildGrid();
	}

}
