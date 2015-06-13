package aufgabe01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
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
		GraphReader r = new GraphReader();
		ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> g = r.buildGraph("Einf-Heuristik.txt");
		InsertionAlgorithm nearest = new InsertionAlgorithm(new NearestInsertion());
		InsertionAlgorithm farthest = new InsertionAlgorithm(new NearestInsertion());

		
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
		

		Graph nGraph = nearest.solveTSP(g);
//		Graph fGraph = farthest.solveTSP(g);
		
		// nearest
		try{
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, false);
			gmlExporter.edgePrintsettings(false, false, true);
			File file = new File("nearestInsertionTSP"+".gml");
			Writer fileWriter = new BufferedWriter(new FileWriter(file));
			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)nGraph);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
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
//		
//		System.out.println();
//		
//		
		
		
	}

}
