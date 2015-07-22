package knf;

import java.util.Arrays;

public class KNF {

	int NumberOfLiterals;
	int NumberOfClauses;
	
	int[][] clauses;
	
	
	public KNF(int m, int n) {
		NumberOfLiterals = m;
		NumberOfClauses = n;
		
		clauses = new int[NumberOfClauses][NumberOfLiterals+1];
	}
	
	/**
	 * Deepcopy
	 * @param other
	 */
	public KNF(KNF other){
		this.NumberOfClauses = other.NumberOfClauses;
		this.NumberOfLiterals = other.NumberOfLiterals;
		this.clauses = new int[NumberOfClauses][];
		for(int i = 0; i<clauses.length; i++){
			clauses[i] = other.getClause(i).clone();
		}
	}

	public void setClause(int i, int[] clauseLine) {
		clauses[i] = clauseLine;
	}
	
	public int[] getClause(int i){
		return clauses[i];
	}
	
	public void deleteLiteral(int lit){
		for(int i = 0; i<clauses.length; i++){
			if(clauses[i][lit] != 0){// variable ist belegt
				clauses[i][lit] = 0;
				if(clauses[i][0] > 0)// mehr als 0 literale oder nicht gelöst
					clauses[i][0]--;
			}
		}
	}

	
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("KNF("+NumberOfLiterals+", "+NumberOfClauses+"){\n");
		for (int i = 0; i < clauses.length; i++) {
			buff.append(Arrays.toString(clauses[i])+'\n');
		}
		return buff.toString();
	}
}
