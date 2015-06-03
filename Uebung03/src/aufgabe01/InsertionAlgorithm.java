package aufgabe01;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import GraphReader.Vertex;

public class InsertionAlgorithm {

	private InsertionHeuristic heuristic;
	
	public InsertionAlgorithm(InsertionHeuristic eh){
		this.heuristic = eh;
	}
	
	
	public WeightedGraph solveTSP(WeightedGraph g){
		
		WeightedGraph newGraph = new SimpleWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// algorithm using heuristic
		do{
			insert(g, newGraph, heuristic.nextVertex(g, newGraph.vertexSet()));
		} while(g.vertexSet().size() > newGraph.vertexSet().size()); 
		// solange wie der Ursprungsgraph mehr Knoten als der neue Graph enthaelt	
		return newGraph;
	}
	
	
	public void insert(WeightedGraph<Vertex, DefaultWeightedEdge> from, WeightedGraph<Vertex, DefaultWeightedEdge> into, Vertex v){
		into.addVertex(v);
		
		// suche Kante c_i-c_j bei der die Strecke c_i,v,c_j minimiert wird
		// Kante für die neue Strecke minimal muss entfernt werden und dafür Umweg eingebaut
		Set<DefaultWeightedEdge> edges = into.edgeSet();
		DefaultWeightedEdge minEdge = null;
		double minSum = Double.MAX_VALUE;
		Vertex s = null;
		Vertex t = null;
		// gehe ueber alle Kanten der momentanen Rundreise
		for (DefaultWeightedEdge e : edges) {
			s = from.getEdgeSource(e);
			t = from.getEdgeTarget(e);
			double curSum = from.getEdgeWeight(from.getEdge(v, s)) + from.getEdgeWeight(from.getEdge(v, t));
			
			if(curSum < minSum){
				minSum = curSum;
				minEdge = e	;
			}
		}
		s = from.getEdgeSource(minEdge);
		t = from.getEdgeTarget(minEdge);
		// in minEdge ist jetzt die Kante mit dem kleinstem Umweg
		// fuege Umweg ein 
		DefaultWeightedEdge sv = into.addEdge(s, v);
		DefaultWeightedEdge vt = into.addEdge(v, t);
		// weights uebertragen
		into.setEdgeWeight(sv, from.getEdgeWeight(sv));
		into.setEdgeWeight(vt, from.getEdgeWeight(vt));
		// entferne alte Kante
		into.removeEdge(minEdge);
	}
	
	
	
}
