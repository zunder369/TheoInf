package aufgabe03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.GraphStuff;
import GraphReader.Vertex;
import aufgabe1.GreedyCol;

public class GreedyCol2 extends GreedyCol {

	protected Set<Vertex> coloratedVertices;
	protected int currentColor = 1;
	private HashMap<String, Vertex> verticeMap = new HashMap<String, Vertex>();
	
	public GreedyCol2(){
		coloratedVertices = new HashSet<Vertex>();
	}
	
	public Graph<Vertex, DefaultWeightedEdge> colorate(Graph<Vertex, DefaultWeightedEdge> g){
		
		Graph<Vertex, DefaultWeightedEdge> newGraph = GraphStuff.buildDeepCopy(g);
		initDefaultColor(newGraph);
		for (Vertex vertex : newGraph.vertexSet()) {
			verticeMap.put(vertex.getName(), vertex);
		}
		
		// dareal einfaerben
		Graph<Vertex, DefaultWeightedEdge> V = GraphStuff.buildDeepCopy(newGraph); // zum Knoten entfernen
		while(V.vertexSet().size() != 0){
			Set<Vertex> U = findIndependentSet(V);
			colorateVertices(newGraph, U);
			V.removeAllVertices(U);
			currentColor++;
		}
		
		graph = newGraph;
		return newGraph;
	}
	
	protected void colorateVertices(
			Graph<Vertex, DefaultWeightedEdge> g, Set<Vertex> verticesToColorate) {
		// colorate Vertices of Set with currentColor

		for (Vertex vertex : verticesToColorate) {
			// check uncolored set for vertice if found colorate and delete from uncolored
			map.get(vertex.getName()).setData(currentColor+"");
		}
		
		if(currentColor > N){
			System.err.println("Graph kann nicht mit "+N+" Farben gefärbt werden");
			System.exit(-1);
		}
	}

	// GreedyIS
	protected Set<Vertex> findIndependentSet(Graph<Vertex, DefaultWeightedEdge> g){
		// U = null, V = V
		Set<Vertex> u = new HashSet<Vertex>(); // U das unabhaengige Set das gesucht wird
		Graph<Vertex, DefaultWeightedEdge> v = GraphStuff.buildDeepCopy(g); // V , aus V werten Knoten entfernt
		
		while(v.vertexSet().size() != 0){// solange noch Knoten in V sind
			// suche Knoten mit minimalem Grad in V
			List<Vertex> minDegree = findMinDegree((UndirectedGraph)v);
			// wähle einen der Knoten zufällig aus (randomisierung)
			Vertex c = minDegree.get((int)Math.random()*minDegree.size());
			// Füge den Knoten U hinzu
			u.add(c);
			// Lösche den Knoten und seine Nachbarn aus V
			v.removeAllVertices(GraphStuff.getNeighbors(v, c));
			v.removeVertex(c);
		}
		return u;
	}
	
	
	private List<Vertex> findMinDegree(UndirectedGraph<Vertex, DefaultWeightedEdge> v) {
		HashMap<Integer, List<Vertex>> degreeMap = new HashMap<Integer, List<Vertex>>();
		int min = Integer.MAX_VALUE;
		for (Vertex vertex : v.vertexSet()) { // laufe ueber alle Knoten
			int degree = v.degreeOf(vertex); // hole Grad des Knoten
			List<Vertex> cur = degreeMap.get(degree); // hole aus Map die Liste mit den Knoten vom Grad
			if(cur == null){
				cur = new ArrayList<Vertex>();
			}
			if(degree < min){
				min = degree;
			}
			if(degree == min) // Performancegewinn
				cur.add(vertex);
		}
		return degreeMap.get(min);	
	}

	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
