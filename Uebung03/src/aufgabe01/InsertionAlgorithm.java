package aufgabe01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	
	private final boolean DEBUG = false;
	
	
	public InsertionAlgorithm(SelectionHeuristic eh){
		this.heuristic = eh;
	}
	
	public WeightedGraph solveTSP(WeightedGraph g){
		if(DEBUG) System.out.println("Solving TSP");
		WeightedGraph newGraph = heuristic.getTriangle(g);
//		System.out.println("Beginning with Triangle: "+newGraph);
		// algorithm using heuristic
		do{
			insert(g, newGraph, heuristic.nextVertex(g, newGraph.vertexSet()));
		} while(g.vertexSet().size() > newGraph.vertexSet().size()); 
		// solange wie der Ursprungsgraph mehr Knoten als der neue Graph enthaelt	
		return newGraph;
	}
	
	
	public void insert(WeightedGraph<Vertex, DefaultWeightedEdge> from, WeightedGraph<Vertex, DefaultWeightedEdge> into, Vertex v){
		if(DEBUG) System.out.println("INSERT");
		into.addVertex(v);
		if(DEBUG) System.out.println("added: "+v);
		// suche Kante c_i-c_j bei der die Strecke c_i,v,c_j minimiert wird
		// Kante für die neue Strecke minimal muss entfernt werden und dafür Umweg eingebaut
		Set<DefaultWeightedEdge> edges = into.edgeSet();
		List<DefaultWeightedEdge> replacableEdges = new ArrayList<DefaultWeightedEdge>();
		DefaultWeightedEdge toReplaceEdge = null;
		Vertex s = null;
		Vertex t = null;
		double minSum = Double.MAX_VALUE;
		double minDif = Double.MAX_VALUE;
		// gehe ueber alle Kanten der momentanen Rundreise
		for (DefaultWeightedEdge e : edges) {
			s = from.getEdgeSource(e);
			t = from.getEdgeTarget(e);
			double curSum = from.getEdgeWeight(from.getEdge(v, s)) + from.getEdgeWeight(from.getEdge(v, t));
			double curDif = curSum - from.getEdgeWeight(e);
//			System.out.println("sum for "+e+": "+curSum+" - Dif: "+curDif);
			if(curDif < minDif){
				minDif = curDif;
				replacableEdges.clear();
			}
			if(curDif == minDif){
				replacableEdges.add(e);
			}
//			if(curSum < minSum){
//				minSum = curSum;
////				toReplaceEdge = e;
//				replacableEdges.clear();
//			}
//			if(curSum == minSum){
//				replacableEdges.add(e);
//			}
		}
		if(DEBUG) System.out.println("Edges to Choose from: "+Arrays.toString(replacableEdges.toArray()));
		
		// RANDOM PART
		toReplaceEdge = replacableEdges.get((int)(Math.random()*replacableEdges.size()));
		if(DEBUG) System.out.println("RANDOM: Chose Edge to Replace: "+toReplaceEdge);
//		if(DEBUG) System.out.println("Chose Edge to Replace: "+toReplaceEdge);

		s = from.getEdgeSource(toReplaceEdge);
		t = from.getEdgeTarget(toReplaceEdge);
		// in minEdge ist jetzt die Kante mit dem kleinstem Umweg
		// fuege Umweg ein 
		DefaultWeightedEdge sv = into.addEdge(s, v);
		DefaultWeightedEdge vt = into.addEdge(v, t);
		// weights uebertragen
		into.setEdgeWeight(sv, from.getEdgeWeight(from.getEdge(v, s)));
		into.setEdgeWeight(vt, from.getEdgeWeight(from.getEdge(v, t)));
		// entferne alte Kante
		into.removeEdge(toReplaceEdge);
	}
	
	
	
}
