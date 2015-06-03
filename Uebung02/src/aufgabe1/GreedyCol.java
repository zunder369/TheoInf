package aufgabe1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.GraphStuff;
import GraphReader.Vertex;

public class GreedyCol {

	protected Map<String, Vertex> verticeNameMap = new HashMap<String, Vertex>();
	protected final int N = 16;
	protected Graph<Vertex, DefaultWeightedEdge> graph;
	
	
	public Graph<Vertex, DefaultWeightedEdge> colorate(Graph<Vertex, DefaultWeightedEdge> g){
		
		Graph<Vertex, DefaultWeightedEdge> newGraph = GraphStuff.buildDeepCopy(g);
		initDefaultColor(newGraph);
		Set<Vertex> vertices = newGraph.vertexSet();
		colorateVertices(newGraph, vertices);
		
		graph = newGraph;
		return newGraph;
	}

	protected void colorateVertices(
			Graph<Vertex, DefaultWeightedEdge> newGraph, Set<Vertex> vertices) {
		try {
			for (Vertex v : vertices) {
				int i = getColor(newGraph, v);
				System.out.println(v + " getColor(): "+ i);
				verticeNameMap.get(v.getName()).setData(i+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Init Graph for Coloring, null represents infinity
	 * puts vertices of graph in a Hashmap, mapped by their name
	 * @param newGraph
	 */
	public void initDefaultColor(Graph<Vertex, DefaultWeightedEdge> newGraph) {
		Set<Vertex> vertices = newGraph.vertexSet();
		Vertex[] array = vertices.toArray(new Vertex[vertices.size()]);
		for (int i = 0; i < array.length; i++) {
			array[i].setData(null);
			verticeNameMap.put(array[i].getName(), array[i]);
		}
		
	}

	public int getColor(Graph<Vertex, DefaultWeightedEdge> g, Vertex v) throws Exception{
		// get neighbors of Vertex v
		Set<Vertex> neigh = GraphStuff.getNeighbors(g, v);
		int[] cols = new int[N]; // N colors available
		// check color of neighbors and increment index of color if found
		for (Vertex vert : neigh) {
			int i = vert.getData() == null ? -1 : Integer.valueOf(vert.getData());
			if(i != -1)
				cols[i]++;
		}
		// choose lowest index containing 0 as own color
		for (int i = 0; i < cols.length; i++) {
			if(cols[i] == 0){
				return i;
			}	
		}
		throw new Exception("Can't colorate Graph with "+N+" colors!");
	}

	
	public Graph getGraph(){
		return graph;
	}
	
}
