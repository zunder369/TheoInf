package knf;

import java.util.Arrays;

public class KNFSolver {

	KNF knf;
	
	public KNFSolver(KNF knf){
		this.knf = knf;
	}
	
	
	public int solveUsingA(){
		// generate m randoms
		int[] randoms = new int[knf.NumberOfLiterals+1];
		int r;
		for(int i = 0; i< randoms.length; i++){
			r = (int)(Math.random()*2);
			randoms[i] = r;
		}
		
		int count = 0;
		int sum;
		for(int clause = 0; clause < knf.clauses.length; clause++){
			for(int lit = 1; lit<knf.NumberOfLiterals+1; lit++){
				// 1+1=2, 0+-1=-1 <- ok | 0+1=1, +1-1=0, 1+0=1, 0+0=0 <- nicht ok
				sum = knf.clauses[clause][lit] + randoms[lit];
				if(sum < 1 || sum > 1){ // beide ok fälle
					count ++;
					break;
				}
			}
		}		
		return count;
	}
	
}
