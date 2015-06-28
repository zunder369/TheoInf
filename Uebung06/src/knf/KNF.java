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


	public void setClause(int i, int[] clauseLine) {
		clauses[i] = clauseLine;
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
