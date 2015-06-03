package uebung01;

import java.util.Iterator;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import GraphReader.GraphReader;
import GraphReader.Vertex;

public class Aufgabe1 {

	public static void main(String[] args) {

		GraphReader r = new GraphReader();

		String[] files = { "Euler1.txt", "Euler2.txt" };

		for (String s : files) {
			ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> graph = r
					.buildGraph(s);

			Set<DefaultWeightedEdge> edges = graph.edgeSet();
			Set<Vertex> vertexes = graph.vertexSet();

			// 2a) Eulerkreis
			// nur Euler wenn max 2 Knoten ungeraden Grad haben
			int countOdd = 0;
			for (Vertex vertex : vertexes) {
				if (graph.degreeOf(vertex) % 2 != 0)
					countOdd++;
			}
			if(countOdd == 0){
				// Ein Eulerkreis existiert dann, wenn kein Knoten mit ungeradem Grad existiert
				System.out.println("Eulerkreis");
			}
			if(countOdd == 2 || countOdd == 0){
				// http://de.wikipedia.org/wiki/Eulerkreisproblem#Verallgemeinerung:_Eulerweg
				System.out.println(s + " hat einen Eulerpfad");
			}
			System.out.println("### 2b) ###");
			// 2b)
			Iterator<Vertex> iter = new DepthFirstIterator<Vertex, DefaultWeightedEdge>(
					graph);
			Vertex vertex;
			while (iter.hasNext()) {
				vertex = iter.next();
				System.out.println(vertex);
			}

			System.out.println("### 2c) ###");
			// 2c) Kreis sobald bei Tiefensuche ein bereits erkannter Knoten nochmal besucht wird
			MyDepthFirstIterator kreisIter = new MyDepthFirstIterator(graph);
			while (kreisIter.hasNext()) {
				System.out.println(kreisIter.next());
			}

		}

	}

}

class MyDepthFirstIterator<V, E> extends DepthFirstIterator<V, E>{

	public MyDepthFirstIterator(Graph<V, E> g) {
		super(g);
	}
	
	protected void encounterVertexAgain(V vertex, E edge){
		V target = getGraph().getEdgeTarget(edge);
		if(isSeenVertex(target)){
			System.out.println("Kreis gefunden bei: "+ vertex + " mit Kante: "+ edge);
		}
	}
	
	
	/**
	 * 
    public static final Integer PRINT_VERTEX_DATA = 5;
    
    public static final Integer PRINT_VERTEX_COLOR = 6;
    
    public static final Integer PRINT_EDGE_COLOR = 7;
    
	 */
	
}


