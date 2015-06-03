package aufgabe1;

import java.util.ArrayList;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.Vertex;

public class GreedyColVar extends GreedyCol{

	@Override
	protected void colorateVertices(Graph<Vertex, DefaultWeightedEdge> newGraph, Set<Vertex> vertices) {
		ArrayList<Vertex> list = new ArrayList<Vertex>(vertices);
		
		try {
			while(list.size() > 0){
				int rand = (int)(Math.random() * list.size());
				Vertex v = list.get(rand);// zufälliges Auswählen 
				list.remove(rand);
				int i;
				// if added for Sudoku to check if vertex already had a color
				if(v.getData() == null)// Knoten hat noch keine Farbe
					i = getColor(newGraph, v);
				else // hatte schon farbe
					i = Integer.valueOf(v.getData());
				verticeNameMap.get(v.getName()).setData(i+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
