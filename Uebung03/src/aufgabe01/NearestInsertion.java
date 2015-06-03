package aufgabe01;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.Vertex;

public class NearestInsertion implements InsertionHeuristic {

	@Override
	public Vertex nextVertex(Graph<Vertex, DefaultWeightedEdge> fullGraph, Set<Vertex> used) {
		Set<Vertex> vertices = fullGraph.vertexSet();
		double minDist = Double.MAX_VALUE;
		Vertex minDistV = null;
		
		for(Vertex v: vertices){
			if(used.contains(v)) continue; // nur knoten die noch nicht in tour
			
			// schaue für jeden Knoten im Graph die benutzten Knoten durch und bestimme max dist
			for(Vertex u : used){
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
