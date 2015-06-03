package aufgabe04;

import GraphReader.Vertex;

public class Matching {

	Vertex v1;
	Vertex v2;
	
	public Matching(Vertex v1, Vertex v2){
		this.v1 = v1;
		this.v2 = v2;
	}
	
	
	public String toString(){
		return "M{"+v1+" - "+v2+"}";
	}
}
