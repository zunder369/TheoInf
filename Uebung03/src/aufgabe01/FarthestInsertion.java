package aufgabe01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import GraphReader.GraphStuff;
import GraphReader.Vertex;

public class FarthestInsertion implements SelectionHeuristic {

	@Override
	public Vertex nextVertex(Graph<Vertex, DefaultWeightedEdge> fullGraph, Set<Vertex> usedVertices) {
		Set<Vertex> vertices = fullGraph.vertexSet();
		double maxDist = 0;
		Vertex maxDistV = null;
		
		List<DefaultWeightedEdge> maxEdges = new ArrayList<DefaultWeightedEdge>();
		DefaultWeightedEdge eMax = null;
		
		for(Vertex v: vertices){
			if(usedVertices.contains(v)) continue; // nur knoten die noch nicht in tour
			// schaue für jeden Knoten im Graph die benutzten Knoten durch und bestimme max dist
			for(Vertex u : usedVertices){
				DefaultWeightedEdge e = fullGraph.getEdge(v	,u);
				double curDist = fullGraph.getEdgeWeight(e);
				if(curDist > maxDist){
					maxDist = curDist;
					maxEdges.clear();
//					maxDistV = v;
//					eMax = e;
				}
				if(curDist == maxDist){
					maxEdges.add(e);
				}
			}
		}
//		System.out.println("Possible Edges in FarthestInsertion:");
//		System.out.println(Arrays.toString(maxEdges.toArray()));
		eMax = maxEdges.get((int)(Math.random()*maxEdges.size()));
		if(usedVertices.contains(fullGraph.getEdgeSource(eMax))){
			maxDistV = fullGraph.getEdgeTarget(eMax);
		}
		else{
			maxDistV = fullGraph.getEdgeSource(eMax);
		}
//		System.out.println("RANDOM Chose: "+maxDistV+ " from Edge: "+eMax+" width dist: "+fullGraph.getEdgeWeight(eMax));
		return maxDistV;
	}

	@Override
	public WeightedGraph<Vertex, DefaultWeightedEdge> getTriangle(
			Graph<Vertex, DefaultWeightedEdge> fullGraph) {
		WeightedGraph<Vertex, DefaultWeightedEdge> g = new ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//suche größte Kante und den dazu am weitesten entfernten Knoten mit seinen Kanten
		DefaultWeightedEdge firstEdge = null;
		double max = Double.MIN_VALUE;
		for (DefaultWeightedEdge e : fullGraph.edgeSet()) {
			if(fullGraph.getEdgeWeight(e) > max){
				max = fullGraph.getEdgeWeight(e);
				firstEdge = e;
			}
		}
		Vertex s = fullGraph.getEdgeSource(firstEdge);
		Vertex t = fullGraph.getEdgeTarget(firstEdge);
		g.addVertex(s);
		g.addVertex(t);
		g.addEdge(s, t, firstEdge);
		// suche den am weitesten entfernten Knoten
		
		Vertex o = null;
		o = nextVertex(fullGraph, g.vertexSet());
		// add all to Graph
		g.addVertex(o);
		g.addEdge(s, o, fullGraph.getEdge(s, o));
		g.addEdge(o, t, fullGraph.getEdge(t, o));
		
		return g;
	}

	
}
