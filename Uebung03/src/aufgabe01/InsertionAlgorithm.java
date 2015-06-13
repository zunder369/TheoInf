package aufgabe01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import GraphReader.MyGMLExporter;
import GraphReader.Vertex;

public class InsertionAlgorithm {

	private SelectionHeuristic heuristic;
	
	public InsertionAlgorithm(SelectionHeuristic eh){
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
		System.out.println("INSERT");
		into.addVertex(v);
		System.out.println("added: "+v);
		//fuege erste Kante hinzu
		if(into.edgeSet().size() == 0){
			DefaultWeightedEdge e = heuristic.getFirstEdge(from, v);
			into.addVertex(from.getEdgeSource(e));
			into.addVertex(from.getEdgeTarget(e));
			into.addEdge(from.getEdgeSource(e), from.getEdgeTarget(e));
			into.setEdgeWeight(e, from.getEdgeWeight(e));
			return;
		}
		// suche Kante c_i-c_j bei der die Strecke c_i,v,c_j minimiert wird
		// Kante für die neue Strecke minimal muss entfernt werden und dafür Umweg eingebaut
		Set<DefaultWeightedEdge> edges = into.edgeSet();
		DefaultWeightedEdge minEdge = null;
		Vertex s = null;
		Vertex t = null;
		double minSum = Double.MAX_VALUE;
		// gehe ueber alle Kanten der momentanen Rundreise
		for (DefaultWeightedEdge e : edges) {
			s = from.getEdgeSource(e);
			t = from.getEdgeTarget(e);
			System.out.println("source: "+s+" target: "+t);
			double curSum = from.getEdgeWeight(from.getEdge(v, s)) + from.getEdgeWeight(from.getEdge(v, t));
			System.out.println("cursum: "+curSum);
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
		into.setEdgeWeight(sv, from.getEdgeWeight(from.getEdge(v, s)));
		into.setEdgeWeight(vt, from.getEdgeWeight(from.getEdge(v, t)));
		// entferne alte Kante
		into.removeEdge(minEdge);
	}
	
	
	
}
