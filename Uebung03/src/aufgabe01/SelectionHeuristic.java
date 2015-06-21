package aufgabe01;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.Vertex;

public interface SelectionHeuristic {

	public Vertex nextVertex(Graph<Vertex, DefaultWeightedEdge> fullGraph, Set<Vertex> usedVertices);
	
	public WeightedGraph<Vertex, DefaultWeightedEdge> getTriangle(Graph<Vertex, DefaultWeightedEdge> fullGraph);
}
