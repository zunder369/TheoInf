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
//		System.out.println("randoms: "+Arrays.toString(randoms));
		return solveUsingArray(randoms);
	}
	
	public int solveUsingArray(int[] array) {

		int count = 0;
		int sum;
		for(int clause = 0; clause < knf.clauses.length; clause++){
			for(int lit = 1; lit<knf.NumberOfLiterals+1; lit++){
				// 1+1=2, 0+-1=-1 <- ok | 0+1=1, +1-1=0, 1+0=1, 0+0=0 <- nicht ok
				sum = knf.clauses[clause][lit] + array[lit];
				if(sum < 0 || sum > 1){ // beide ok fälle
					count ++;
					break;
				}
			}
		}
		return count;
	}


	private void setKNFAtIndexTo(KNF knf, int i, int val){
		int sum = 0;
		for(int clause = 0; clause<knf.NumberOfClauses; clause++){
			if(knf.clauses[clause][0] > 0){// sonst schon erfüllt oder keine vars mehr
				sum = knf.clauses[clause][i] + val;
				if( sum < 0 || sum > 1 ){// schauen ob Klausel wegfaellt
					knf.clauses[clause][0] = -1;
				}
			}
		}
	}
	
	public int[] solveUsingDerandA(){
		
		int[] setVariables = new int[knf.NumberOfLiterals+1];

		KNF insert0 = new KNF(knf);
		KNF insert1 = new KNF(knf);
		
		double expt0;
		double expt1;
		
		for(int i = 1; i< setVariables.length; i++){
			// erstelle KNF für 0 einsetzen
		    setKNFAtIndexTo(insert0, i, 0);
			// erstelle KNF für 1 einsetzen
		    setKNFAtIndexTo(insert1, i, 1);
			// delete literal
			insert1.deleteLiteral(i);
			insert0.deleteLiteral(i);
			// erwartungswerte berechnen
			expt0 = calcExpectationValue(insert0);
			expt1 = calcExpectationValue(insert1);
//			 System.out.println(i+": expt1: "+expt1+" expt0: "+expt0);
			// copy better expectation Value KNF
			if(expt0 > expt1){
				insert1 = new KNF(insert0);
				setVariables[i] = 0;
			}
			else{
				insert0 = new KNF(insert1);
				setVariables[i] = 1;
			}
		}
		
		int count0 = 0;
		int count_1 = 0;
		int countFalse = 0;
		for(int i = 0; i< knf.NumberOfClauses; i++){
			if(insert0.clauses[i][0] == -1){
				count_1++;
			}
			else if(insert0.clauses[i][0] == 0){
				count0++;
			}
			else{
				System.out.println("zeile: "+i+" "+Arrays.toString(insert0.clauses[i]));
				System.out.println("original: "+Arrays.toString(knf.clauses[i]));
				countFalse++;
			}
		}
		System.out.println("of Clauses: "+knf.NumberOfClauses+" solved: "+count_1+" deleted: "+count0+" with errors: "+countFalse);
//		System.out.println(insert0);
		return setVariables;
	}
	 
	
	private double calcExpectationValue(KNF knf){
		
		// sum über alle clauses (1-1/2^k_j)
		double E = 0;
		// -1 steht für eine bereits erfüllte Klausel
		// ansonsten ist clauses[i][0] die Anzahl der verbleibenden Literale
		for(int i = 0; i<knf.clauses.length; i++){
			if(knf.clauses[i][0] != -1){
//				System.out.println("1-1/"+2+"^"+knf.getClause(i)[0]);
				E += 1-(1/Math.pow(2, knf.getClause(i)[0]));
			}
			else{
				E += 1;
			}
		}
		return E;		
	}
	
}
