package knf;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		KNFReader knfR = new KNFReader();
		
		File folder = new File("cnf/cnf/");
		String[] filelist = folder.list();
		
		List<String> files = new ArrayList<String>();
		
		for (int i = 0; i < filelist.length; i++) {
			// alle aim mit yes
//			if(filelist[i].startsWith("aim") && filelist[i].contains("yes")){
//				files.add(filelist[i]);
//			}
			if(filelist[i].equals("aim-200-6_0-yes1-1.cnf")){
//			if(filelist[i].equals("aim-50-1_6-yes1-1.cnf")){
				files.add(filelist[i]);
			}
		}
		
		KNFSolver solve;
		
		for (String string : files) {
			KNF knf = knfR.readKNF(folder.getAbsolutePath()+'/'+string);
			solve = new KNFSolver(knf);
			System.out.println(string);
			int rand = solve.solveUsingA();
			int[] variableSetting = solve.solveUsingDerandA();
			System.out.println(Arrays.toString(variableSetting));
			System.out.println("derand:"+ solve.solveUsingArray(variableSetting));
			System.out.println("rand: "+rand);
			System.out.println();
		}
		
		
//		KNF knf = knfR.readKNF("cnf/cnf/aim-50-1_6-yes1-3.cnf");
//		KNF knf = knfR.readKNF("deinzer-cnf.txt");
//		System.out.println(knf);
		
//		System.out.println("KNFSolver");
//		
//		KNFSolver solve = new KNFSolver(knf);
//		
//		int max = 0;
//		for(int i = 0; i<1; i++){ /// zahl einstellen
//			int solved = solve.solveUsingA();
//			if(solved > max){
//				max = solved;
//			}
//		}
//		System.out.println(max);
//		System.out.println();
//		System.out.println("KNF derandomized");
//		System.out.println();
//		int[] variableSetting = solve.solveUsingDerandA();
//		System.out.println(Arrays.toString(variableSetting));
//		
//		
//		System.out.println(solve.solveUsingArray(variableSetting));
//		System.out.println(knf);
//		int[] varSetting2 = solve.solveUsingDerandA();
//		if(Arrays.equals(variableSetting, varSetting2)){
//			System.out.println("gleich");
//		}
//		else{
//			System.out.println(Arrays.toString(variableSetting));
//			System.out.println(Arrays.toString(varSetting2));
//		}
		
	}

}
