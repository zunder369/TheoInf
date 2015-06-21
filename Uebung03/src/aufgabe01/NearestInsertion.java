package aufgabe01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import GraphReader.Vertex;

public class NearestInsertion implements SelectionHeuristic {

	@Override
	public Vertex nextVertex(Graph<Vertex, DefaultWeightedEdge> fullGraph, Set<Vertex> usedVertices) {
		Set<Vertex> vertices = fullGraph.vertexSet();
		double minDist = Double.MAX_VALUE;
		Vertex minDistV = null;
		
		List<DefaultWeightedEdge> minEdges = new ArrayList<DefaultWeightedEdge>();
		DefaultWeightedEdge eMin = null;
		
		for(Vertex v: vertices){
			if(usedVertices.contains(v)) continue; // nur knoten die noch nicht in tour
			// schaue für jeden Knoten im Graph die benutzten Knoten durch und bestimme max dist
			for(Vertex u : usedVertices){
				DefaultWeightedEdge e = fullGraph.getEdge(v	,u);
				double curDist = fullGraph.getEdgeWeight(e);
				if(curDist < minDist){
					minDist = curDist;
					minEdges.clear();
//					minDistV = v;
//					eMin = e;
				}
				if(curDist == minDist){
					minEdges.add(e);
				}
			}
		}
//		System.out.println("Possible Edges in NearestInsertion:");
//		System.out.println(Arrays.toString(minEdges.toArray()));
		eMin = minEdges.get((int)(Math.random()*minEdges.size()));
		if(usedVertices.contains(fullGraph.getEdgeSource(eMin))){
			minDistV = fullGraph.getEdgeTarget(eMin);
		}
		else{
			minDistV = fullGraph.getEdgeSource(eMin);
		}
//		System.out.println("Edges to choose from: "+Arrays.toString(minEdges.toArray()));
//		System.out.println("RANDOM Chose: "+minDistV+ " from Edge: "+eMin+" width dist: "+fullGraph.getEdgeWeight(eMin));
		return minDistV;
	}

	@Override
	public WeightedGraph<Vertex, DefaultWeightedEdge> getTriangle(
			Graph<Vertex, DefaultWeightedEdge> fullGraph) {
		WeightedGraph<Vertex, DefaultWeightedEdge> g = new ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//suche kleinste Kante und den dazu am wenigsten entfernten Knoten mit seinen Kanten
		DefaultWeightedEdge firstEdge = null;
		double min = Double.MAX_VALUE;
		for (DefaultWeightedEdge e : fullGraph.edgeSet()) {
			if(fullGraph.getEdgeWeight(e) < min){
				min = fullGraph.getEdgeWeight(e);
				firstEdge = e;
			}
		}
		Vertex s = fullGraph.getEdgeSource(firstEdge);
		Vertex t = fullGraph.getEdgeTarget(firstEdge);
		g.addVertex(s);
		g.addVertex(t);
		g.addEdge(s, t, firstEdge);
		// suche den am wenigsten entfernten Knoten
		
		Vertex o = null;
		o = nextVertex(fullGraph, g.vertexSet());
		// add all to Graph
		g.addVertex(o);
		g.addEdge(s, o, fullGraph.getEdge(s, o));
		g.addEdge(o, t, fullGraph.getEdge(t, o));
		
		return g;
	}

}
