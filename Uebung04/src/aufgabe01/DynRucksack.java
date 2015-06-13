package aufgabe01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynRucksack {
	
	final int unendlich = 2000000000;

	List<Item> items;
	
	int[][] optTable;
	String[][] nameTable;
	
	public DynRucksack(){
		// initialize items
		items = new ArrayList<Item>();
		// fill ArrayList
		items.add(new Item("Nothing", 0, 0));
		items.add(new Item("Essen", 23, 15));
		items.add(new Item("Zelt", 33, 23));
		items.add(new Item("Getränke", 11, 15));
		items.add(new Item("Pulover", 35, 33));
		items.add(new Item("MP3-Player", 11, 32));
		
		optTable = new int[getSumValue()][items.size()];
		for(int y = 0; y<optTable.length; y++){
			optTable[y][0] = unendlich; 
		}
		nameTable = new String[getSumValue()][items.size()];
		System.out.println("dimensions of optTable: "+optTable.length+", "+ optTable[0].length);
	}
	
	public int getSumValue(){
		int sum = 0;
		for(Item i: items){
			sum+=i.getValue();
		}
		return sum;
	}
	
	public int getSumVolume(){
		int sum = 0;
		for(Item i: items){
			sum+=i.getVolume();
		}
		return sum;
	}
	/**
	 * Lemma 4.3
	 * @param alpha
	 * @param j
	 * @return
	 */
	public int opt(int alpha, int j){
		System.out.println("opt("+alpha+", "+j+")");
		if(alpha <= 0 && j >= 0){
			// F_j(alpha) = 0
			return 0;
		}
		else if(alpha >=1 && j == 0){
			// F_0(alpha) = unendlich
			int ret = unendlich;//Integer.MAX_VALUE-1000;
			optTable[alpha][j] = ret;
			return ret; 
		}
		else //(alpha >= 1 && j >= 1)
		{			
			// F_j(alpha) = min(F_{j-1}(alpha-p_j)+vol(j),F_{j-1}(alpha))
			int curVal = items.get(j).getValue();
			int curVol = items.get(j).getVolume();
			System.out.println("value: "+curVal+" volume: "+curVol);
			// prüfen ob Zugriffe erlaubt
			if(alpha-curVal < 0) return 0;
			int a = optTable[alpha-curVal][j-1]+curVol;
			int b = optTable[alpha][j-1];
			System.out.println("a: "+a+" , b: "+b);
			int ret = Math.min(a,// F_{j-1}(alpha-p_j)+vol(j)	
							   b);//,F_{j-1}(alpha)
			// zahl eintragen
			optTable[alpha][j] = ret > unendlich ? ret - unendlich : ret;
			// name eintragen
			nameTable[alpha][j] = (a < b) ? //name aus Zeile drüber + name von j  
								(nameTable[alpha-curVal][j-1]+" "+items.get(j).getName()).replace("null", "")
							  : nameTable [alpha][j-1];
			return ret;
		}
	}
	
	public int algo(int maxCapacity){
		
		int alpha = 0;
		int opt = 0;
		do{
			alpha++;
			for(int j = 1; j < items.size(); j++){
				System.out.print("j: "+j+" ");
				opt = opt(alpha, j);
			}
			System.out.println("alpha: "+alpha+" - "+Arrays.toString(optTable[alpha]));
		}while( ! (maxCapacity < opt));
		System.out.println("ergebnis:");
		System.out.println(Arrays.toString(optTable[alpha-1]));
		System.out.println(Arrays.toString(nameTable[alpha-1]));
		return alpha-1;
	}
	
	
	public static void main(String[] args){
		DynRucksack dyn = new DynRucksack();
		
		System.out.println(dyn.algo(65));
		
	}
	
}
