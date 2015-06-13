package aufgabe01;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.Vertex;

public class FarthestInsertion implements SelectionHeuristic {

	@Override
	public Vertex nextVertex(Graph<Vertex, DefaultWeightedEdge> fullGraph, Set<Vertex> usedVertices) {
		Set<Vertex> vertices = fullGraph.vertexSet();
		double maxDist = 0;
		Vertex maxDistV = null;
		DefaultWeightedEdge eMax = null;
		
		for(Vertex v: vertices){
			if(usedVertices.size() == 0) // 1. Knoten vom TSP
				return v;
			if(usedVertices.contains(v)) continue; // nur knoten die noch nicht in tour
			
			// schaue f�r jeden Knoten im Graph die benutzten Knoten durch und bestimme max dist
			for(Vertex u : usedVertices){
				DefaultWeightedEdge e = fullGraph.getEdge(v	,u);
				double curDist = fullGraph.getEdgeWeight(e);
				if(curDist > maxDist){
					maxDist = curDist;
					maxDistV = v;
				}
			}
		}
		System.out.println("Chosse: "+maxDistV+ " from Edge: "+eMax+" width dist: "+fullGraph.getEdgeWeight(eMax));
		return maxDistV;
	}

	@Override
	public DefaultWeightedEdge getFirstEdge(Graph<Vertex, DefaultWeightedEdge> fullGraph, Vertex usedVertice) {
		Set<DefaultWeightedEdge> neighbors = fullGraph.edgesOf(usedVertice);
		// suche Maximum
		DefaultWeightedEdge maxEdge = null;
		double maxWeight = Double.NEGATIVE_INFINITY;
		for (DefaultWeightedEdge e : neighbors) {
			if(fullGraph.getEdgeWeight(e) > maxWeight){
				maxEdge = e;
				maxWeight = fullGraph.getEdgeWeight(e);
			}
		}
		return maxEdge;
	}
}
