package knf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class KNFReader {

	public KNF readKNF(String filename) {
		List<String> filecontent = readFile(filename);
		
		int m, n = 0;
		String[] pLine = filecontent.get(0).split(" ");
		m = Integer.valueOf(pLine[2]);
		n = Integer.valueOf(pLine[3]);
		KNF knf = new KNF(m, n);
		
		for(int i = 1; i<filecontent.size(); i++){
			knf.setClause(i-1, clauseLine(filecontent.get(i), m));
		}
		return knf;
	}
	
	/**
	 * returns Content of cnf-Formated file starting with p-Line
	 * @param filename
	 * @return
	 */
	private List<String> readFile(String filename){
		
		List<String> list = new ArrayList<String>();
		
		try{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			boolean start = false;
			String line = "";
			
			while(line != null){
				line = br.readLine();
				if(line != null && line.startsWith("p")) start = true;
				if(line != null && start) list.add(line);
			}
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	private int[] clauseLine(String line, int m){
		
		int[] clause = new int[m+1];
		String[] split = line.split(" ");
		
		for(int i = 0; i< split.length-1; i++){
			int val = Integer.valueOf(split[i]);
			if(val < 0){
				clause[-val] = -1;
			}
			else{
				clause[val] = 1;
			}
		}
		int count = 0;
		for(int i = 1 ; i<clause.length; i++){
			if(clause[i] != 0) count ++;
		}
		clause[0] = count;
		return clause;
	}
}
