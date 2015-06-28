package knf;

import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		KNFReader knfR = new KNFReader();
		
		KNF knf = knfR.readKNF("cnf/cnf/aim-100-1_6-no-1.cnf");
		
		System.out.println(knf);
		
		System.out.println("KNFSolver");
		
		KNFSolver solve = new KNFSolver(knf);
		
		solve.solveUsingA();
	}

}
