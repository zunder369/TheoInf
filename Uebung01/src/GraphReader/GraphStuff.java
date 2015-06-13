package GraphReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class GraphStuff {

	public static Graph<Vertex, DefaultWeightedEdge> buildDeepCopy(Graph<Vertex, DefaultWeightedEdge> g) {
		Graph<Vertex, DefaultWeightedEdge> c = new ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Set<DefaultWeightedEdge> e = g.edgeSet();
		Set<Vertex> v = g.vertexSet();
		Map<String, Vertex> map = new HashMap<String, Vertex>();
		
		
		for (Vertex vert : v) {
			map.put(vert.getName(), new Vertex(vert.getName(), vert.getData()));
			c.addVertex(map.get(vert.getName()));
		}
		for (DefaultWeightedEdge edge : e) {
			Vertex s = g.getEdgeSource(edge);
			Vertex t = g.getEdgeTarget(edge);
			c.addEdge(map.get(s.getName()), map.get(t.getName()));
		}
		return c;
	}
	
	public static Set<Vertex> getNeighbors(Graph<Vertex, DefaultWeightedEdge> g, Vertex v) {
		Set<Vertex> set = new HashSet<Vertex>();
		Set<DefaultWeightedEdge> edges = g.edgesOf(v);
		
		for (DefaultWeightedEdge e : edges) {
			if(g.getEdgeSource(e).equals(v)){ // v is source
				set.add(g.getEdgeTarget(e));
			}
			else // v is target
				set.add(g.getEdgeSource(e));
		}
		return set;		
	}
	
	public static int colorsInGraph(Graph<Vertex, ?> g){
		
		Set<Vertex> set = g.vertexSet();
		int[] a = new int[set.size()];
		int count = 0;
		
		for (Vertex v : set) {
			if(v.getData() != null)
				a[Integer.valueOf(v.getData())]++;
		}
		for (int i = 0; i < a.length; i++) {
			if(a[i] != 0)
				count ++;
		}	
		return count;
	}
	
	public static Graph<Vertex, DefaultWeightedEdge> invertedGraph(Graph<Vertex, DefaultWeightedEdge> g){
		
		Graph<Vertex, DefaultWeightedEdge> newGraph = new SimpleWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		for(Vertex v : g.vertexSet()){
			newGraph.addVertex(v);
		}
		
		Set<Vertex> neighbors;
		Set<Vertex> invNeighbors;
		for (Vertex v : g.vertexSet()) {
			neighbors = GraphStuff.getNeighbors(g, v);
			invNeighbors = new TreeSet<Vertex>(g.vertexSet());
			invNeighbors.removeAll(neighbors);
			invNeighbors.remove(v);
			for(Vertex o : invNeighbors){
				newGraph.addEdge(v, o);
			}
		}
		return newGraph;
	}


}
