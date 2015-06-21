package aufgabe01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import GraphReader.GraphReader;
import GraphReader.MyGMLExporter;
import GraphReader.Vertex;
import aufgabe04.MaximumMatching;

public class Test {

	public static void main(String[] args) {
		int REPEAT = 200000;
		
		GraphReader r = new GraphReader();
		ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> g = r.buildGraph("Einf-Heuristik.txt");
		InsertionAlgorithm nearest = new InsertionAlgorithm(new NearestInsertion());
		InsertionAlgorithm farthest = new InsertionAlgorithm(new FarthestInsertion());

		FarthestInsertion f = new FarthestInsertion();
		List<Vertex> l = new ArrayList<Vertex>(g.vertexSet());
		
		System.out.println();
		
		// voller graph
		try{
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, false);
			gmlExporter.edgePrintsettings(false, false, true);
			File file = new File("Einf-Heuristik"+".gml");
			Writer fileWriter = new BufferedWriter(new FileWriter(file));
			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)g);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		int[] nearestErgebnisse = new int[25];
		int[] farthestErgebnisse = new int[50];
		
		for (int i = 0; i < REPEAT; i++) {
//			System.out.println("\nNearest");
//			Graph nGraph = nearest.solveTSP(g);
//			if(nGraph.edgeSet().size() != nGraph.vertexSet().size()){
//				System.err.println("Nearest Fehler");
//			}
//			double sumNearest = 0;
//			for (Object e : nGraph.edgeSet()) {
//				sumNearest += nGraph.getEdgeWeight(e);
//			}
//			nearestErgebnisse[(int)sumNearest]++;
//			System.out.println("\nFarthest");
			Graph fGraph = farthest.solveTSP(g);
			if(fGraph.edgeSet().size() != fGraph.vertexSet().size()){
				System.err.println("Farthest Fehler");
			}
			double sumFarthest = 0;
			for (Object e : fGraph.edgeSet()) {
				sumFarthest += fGraph.getEdgeWeight(e);
			}
			farthestErgebnisse[(int)sumFarthest]++;
		}
		System.out.println("Nearest:");
		System.out.println(Arrays.toString(nearestErgebnisse));
		for (int i = 0; i < nearestErgebnisse.length; i++) {
			if(nearestErgebnisse[i] != 0){
				System.out.println("Nearest: "+i);
				break;
			}
		}
		System.out.println("Farthest:");
		System.out.println(Arrays.toString(farthestErgebnisse));
		for (int i = 0; i < farthestErgebnisse.length; i++) {
			if(farthestErgebnisse[i] != 0){
				System.out.println("Farthest: "+i);
				break;
			}
		}
//		System.out.println("Weg nearest: "+sumNearest+"      Weg farthest: "+sumFarthest);
		
//		// nearest
//		try{
//			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
//			gmlExporter.vertexPrintsettings(true, false);
//			gmlExporter.edgePrintsettings(false, false, true);
//			File file = new File("nearestInsertionTSP"+".gml");
//			Writer fileWriter = new BufferedWriter(new FileWriter(file));
//			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)nGraph);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		// farthest
//		try{
//			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
//			gmlExporter.vertexPrintsettings(true, false);
//			gmlExporter.edgePrintsettings(false, false, true);
//			File file = new File("farthestInsertionTSP"+".gml");
//			Writer fileWriter = new BufferedWriter(new FileWriter(file));
//			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)fGraph);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
		
		
		
		
		
	}

}
