package aufgabe01;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.Vertex;

public class NearestInsertion implements SelectionHeuristic {

	@Override
	public Vertex nextVertex(Graph<Vertex, DefaultWeightedEdge> fullGraph, Set<Vertex> usedVertices) {
		Set<Vertex> vertices = fullGraph.vertexSet();
		double minDist = Double.MAX_VALUE;
		Vertex minDistV = null;
		
		for(Vertex v: vertices){
			if(usedVertices.size() == 0) // 1. Knoten vom TSP
				return v;
			if(usedVertices.contains(v)) continue; // nur knoten die noch nicht in tour
			
			// schaue für jeden Knoten im Graph die benutzten Knoten durch und bestimme max dist
			for(Vertex u : usedVertices){
				DefaultWeightedEdge e = fullGraph.getEdge(v	,u);
				double curDist = fullGraph.getEdgeWeight(e);
				if(curDist < minDist){
					minDist = curDist;
					minDistV = v;
				}
			}
		}
		return minDistV;
	}

}
