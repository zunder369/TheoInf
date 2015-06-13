package aufgabe01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.MyGMLExporter;
import GraphReader.Vertex;

public class NearestInsertion implements SelectionHeuristic {

	@Override
	public Vertex nextVertex(Graph<Vertex, DefaultWeightedEdge> fullGraph, Set<Vertex> usedVertices) {
		Set<Vertex> vertices = fullGraph.vertexSet();
		double minDist = Double.MAX_VALUE;
		Vertex minDistV = null;
		
		DefaultWeightedEdge eMin = null;
		
		for(Vertex v: vertices){
			if(usedVertices.size() == 0) // 1. Knoten vom TSP
				return v;
			if(usedVertices.contains(v)) continue; // nur knoten die noch nicht in tour
			
			// schaue für jeden Knoten im Graph die benutzten Knoten durch und bestimme max dist
			for(Vertex u : usedVertices){
				System.out.println("v: "+v+" u: "+u);
				DefaultWeightedEdge e = fullGraph.getEdge(v	,u);
				
				double curDist = fullGraph.getEdgeWeight(e);
				if(curDist < minDist){
					minDist = curDist;
					minDistV = v;
					eMin = e;
				}
			}
		}
		System.out.println("Chosse: "+minDistV+ " from Edge: "+eMin+" width dist: "+fullGraph.getEdgeWeight(eMin));
		return minDistV;
	}

	@Override
	public DefaultWeightedEdge getFirstEdge(
			Graph<Vertex, DefaultWeightedEdge> fullGraph, Vertex usedVertice) {
		Set<DefaultWeightedEdge> neighbors = fullGraph.edgesOf(usedVertice);
		// suche Maximum
		DefaultWeightedEdge minEdge = null;
		double minWeight = Double.POSITIVE_INFINITY;
		for (DefaultWeightedEdge e : neighbors) {
			if(fullGraph.getEdgeWeight(e) < minWeight){
				minEdge = e;
				minWeight = fullGraph.getEdgeWeight(e);
			}
		}
		return minEdge;
	}

}
