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

	private static final double RANDOM = 0.5;
	protected Set<Vertex> coloratedVertices;
	protected int currentColor = 1;
	
	public GreedyCol2(){
		coloratedVertices = new HashSet<Vertex>();
	}
	
	public Graph<Vertex, DefaultWeightedEdge> colorate(Graph<Vertex, DefaultWeightedEdge> g){
		
		// initialisierung - Graph kopieren und HashMap fuellen
		Graph<Vertex, DefaultWeightedEdge> newGraph = GraphStuff.buildDeepCopy(g);
		initDefaultColor(newGraph);
		
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
			verticeNameMap.get(vertex.getName()).setData(currentColor+"");
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
		Set<Vertex> vertices = g.vertexSet();
		
		while(vertices.size() != 0){// solange noch Knoten in V sind
			// suche Knoten mit minimalem Grad in V
			Vertex minDegree = findVerticeOfMinDegree((UndirectedGraph)g);// missing Random
			// Füge den Knoten U hinzu
			u.add(minDegree);
			// Lösche den Knoten und seine Nachbarn aus V
			vertices.removeAll(GraphStuff.getNeighbors(g, minDegree));
			vertices.remove(minDegree);
		}
		return u;
	}
	
	
	private Vertex findVerticeOfMinDegree(UndirectedGraph<Vertex, DefaultWeightedEdge> v) {
		int min = Integer.MAX_VALUE;
		Vertex vert = null;
		for (Vertex vertex : v.vertexSet()) { // laufe ueber alle Knoten
			int degree = v.degreeOf(vertex); // hole Grad des Knoten
			if(degree < min){
				min = degree;
				vert = vertex;
			}
//			else if(degree == min && Math.random() < RANDOM){ // randomisiert Knoten im Minimum wechseln
//				vert = vertex;
//			}
		}
		return vert;	
	}

	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
