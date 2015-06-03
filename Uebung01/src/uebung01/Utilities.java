package uebung01;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import GraphReader.Vertex;

import uebung01.Dijkstra;

public class Utilities {

	
	public static UndirectedGraph<Vertex, DefaultWeightedEdge> getSpanningTreeOf(Graph<Vertex, DefaultWeightedEdge> g) {
		UndirectedGraph<Vertex, DefaultWeightedEdge> ret = 
				new ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//clone Graph
		for(Vertex vertex : g.vertexSet()) {
			ret.addVertex(vertex);
		}
		for(DefaultWeightedEdge edge : g.edgeSet()) {
			ret.addEdge(g.getEdgeSource(edge), g.getEdgeTarget(edge), edge);
		}
		
		//Keep removing the biggest fish, so that the graph stays connected, until all edges have been processed
		Set<DefaultWeightedEdge> checkedEdges = new HashSet<DefaultWeightedEdge>();
		while(!checkedEdges.containsAll(g.edgeSet())) {
			//Find biggest not yet processed edge
			DefaultWeightedEdge biggestEdge = ret.edgeSet().iterator().next();
			for(DefaultWeightedEdge edge : ret.edgeSet()) {
				if(checkedEdges.contains(edge)) {
					continue;
				}
				if(ret.getEdgeWeight(edge) > ret.getEdgeWeight(biggestEdge)) {
					biggestEdge = edge;
				}
			}
			
			//Gather vertices formerly connected by edge and then remove it.
			Vertex source = ret.getEdgeSource(biggestEdge);
			Vertex target = ret.getEdgeTarget(biggestEdge);
			ret.removeEdge(biggestEdge);
			//Check whether graph is still connected. If not, re-insert edge.
			//TODO Make Dijkstra handle unconnected graphs, or find another way to check whether a graph is disconnected.

			//			if(new Dijkstra(ret, source.getName(), target.getName()).getPath().getPath().isEmpty()) {
			try{
				new Dijkstra(ret, source.getName(), target.getName()).getPath();
				ret.addEdge(source, target, biggestEdge);
			}catch(Exception e){
				e.printStackTrace();
			}
			checkedEdges.add(biggestEdge);
		}
		
		return ret;
	}
	
	
}
