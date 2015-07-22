package aufgabe03;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.GraphReader;
import GraphReader.GraphStuff;
import GraphReader.MyGMLExporter;
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
		currentColor = 1;
		
		// dareal einfaerben
		Graph<Vertex, DefaultWeightedEdge> V = GraphStuff.buildDeepCopy(newGraph); // zum Knoten entfernen
		while(V.vertexSet().size() != 0){
			System.out.println("Remaining uncolored Vertices in Graph: "+V.vertexSet().size());
			Set<Vertex> U = findIndependentSet(V);
			colorateVertices(newGraph, U);
			V.removeAllVertices(U);
			try{
				MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
				gmlExporter.vertexPrintsettings(true, true);
				gmlExporter.edgePrintsettings(false, false, false);
				File file = new File("worldGreedyCol#"+currentColor+".gml");
				Writer fileWriter = new BufferedWriter(new FileWriter(file));
				gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)V);
			}
			catch(Exception e){
				e.printStackTrace();
			}
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

//	// GreedyIS
//	protected Set<Vertex> findIndependentSet(Graph<Vertex, DefaultWeightedEdge> g){
//		// U = null, V = V
//		Set<Vertex> u = new HashSet<Vertex>(); // U das unabhaengige Set das gesucht wird
//		Set<Vertex> vertices = new HashSet<Vertex>(g.vertexSet());
//		
//		while(vertices.size() != 0){// solange noch Knoten in V sind
//			// suche Knoten mit minimalem Grad in V
//			Vertex minDegree = findVerticeOfMinDegree((UndirectedGraph)g, vertices);// missing Random
//			// Füge den Knoten U hinzu
//			u.add(minDegree);
//			// Lösche den Knoten und seine Nachbarn aus V
//			Set<Vertex> neighbors = GraphStuff.getNeighbors(g, minDegree);
//			vertices.removeAll(neighbors);
//			vertices.remove(minDegree);
//		}
//		return u;
//	}
//	
//	
//	private Vertex findVerticeOfMinDegree(UndirectedGraph<Vertex, DefaultWeightedEdge> v, Set<Vertex> vertices) {
//		int min = Integer.MAX_VALUE;
//		Vertex vert = null;
//		
//		for (Vertex vertex : vertices) { // laufe ueber alle Knoten
//			int degree = v.degreeOf(vertex); // hole Grad des Knoten
//			if(degree < min){
//				min = degree;
//				vert = vertex;
//			}
//			else if(degree == min && Math.random() < RANDOM){ // randomisiert Knoten im Minimum wechseln
//				vert = vertex;
//			}
//		}
//		return vert;	
//	}

	// GreedyIS
		protected Set<Vertex> findIndependentSet(Graph<Vertex, DefaultWeightedEdge> g){
			// U = null, V = V
			Set<Vertex> u = new HashSet<Vertex>(); // U das unabhaengige Set das gesucht wird
			Set<Vertex> vertices = new HashSet<Vertex>(g.vertexSet());
			
			while(vertices.size() != 0){// solange noch Knoten in V sind
				// suche Knoten mit minimalem Grad in V
				Vertex maxDegree = findVerticeOfMaxDegree((UndirectedGraph)g, vertices);// missing Random
				// Füge den Knoten U hinzu
				u.add(maxDegree);
				// Lösche den Knoten und seine Nachbarn aus V
				Set<Vertex> neighbors = GraphStuff.getNeighbors(g, maxDegree);
				vertices.removeAll(neighbors);
				vertices.remove(maxDegree);
			}
			return u;
		}
		
		
		private Vertex findVerticeOfMaxDegree(UndirectedGraph<Vertex, DefaultWeightedEdge> v, Set<Vertex> vertices) {
			int max = Integer.MIN_VALUE;
			Vertex vert = null;
			
			for (Vertex vertex : vertices) { // laufe ueber alle Knoten
				int degree = v.degreeOf(vertex); // hole Grad des Knoten
				if(degree > max){
					max = degree;
					vert = vertex;
				}
				else if(degree == max && Math.random() < RANDOM){ // randomisiert Knoten im Minimum wechseln
					vert = vertex;
				}
			}
			return vert;	
		}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		GraphReader r = new GraphReader();
		UndirectedGraph worldClean;
		Graph<Vertex, DefaultWeightedEdge> worldCol = null;
		GreedyCol2 greedy = new GreedyCol2();
		
		worldClean = r.buildGraph("../Uebung02/weltkarte.txt");
		
		int[] colsNeeded = new int[20];

		int min = 10;
		int c = min;
		
		long t = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			worldCol = greedy.colorate(worldClean);
			c = GraphStuff.colorsInGraph(worldCol);
			colsNeeded[c]++;
			if(c < min) min = c;
		}
		System.out.println("time: "+(System.currentTimeMillis()-t));

		System.out.println(min);
		System.out.println(Arrays.toString(colsNeeded));
		
		try{
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, true);
			gmlExporter.edgePrintsettings(false, false, false);
			File file = new File("worldGreedyCol2"+".gml");
			Writer fileWriter = new BufferedWriter(new FileWriter(file));
			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)worldCol);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
