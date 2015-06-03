package aufgabe1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.GraphStuff;
import GraphReader.GraphReader;
import GraphReader.MyGMLExporter;
import GraphReader.Vertex;

public class Test {

	
	public static void main(String[] args){
		
		GraphReader r = new GraphReader();
		UndirectedGraph europaClean;
		Graph<Vertex, DefaultWeightedEdge> europaColVar;
		Graph<Vertex, DefaultWeightedEdge> europaCol1;
		GreedyCol greedyCol = new GreedyCol();
		GreedyColVar greedyColVar = new GreedyColVar();

		UndirectedGraph dijkstra;
		Graph dijkstraVar;

		europaClean = r.buildGraph("Europa.txt");
		europaCol1 = greedyCol.colorate(europaClean);
		europaColVar = greedyColVar.colorate(europaClean);
		System.out.println("greedyCol:");
		System.out.println(europaCol1);
		System.out.println("greedyColVar:");
		System.out.println(europaColVar);
		
		try{
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, true);
			gmlExporter.edgePrintsettings(false, false, false);
			File file = new File("EuropaColor1"+".gml");
			Writer fileWriter = new BufferedWriter(new FileWriter(file));
			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)europaCol1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, true);
			gmlExporter.edgePrintsettings(false, false, false);
			File file = new File("EuropaColorVar"+".gml");
			Writer fileWriter = new BufferedWriter(new FileWriter(file));
			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)europaColVar);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		int min = 10;
		int c = min;
		
		dijkstra = r.buildGraph("weltkarte.txt");
//		System.out.println("Europa anzahl");
		System.out.println("weltkarte");
		
		long t = System.currentTimeMillis();
		for (int i = 0; i < 20000; i++) {
			dijkstraVar = greedyColVar.colorate(dijkstra);
//			europaColVar = greedyColVar.colorate(europaClean);
			c = GraphStuff.colorsInGraph(dijkstraVar);
			if(c < min) min = c;
		}
		System.out.println(min);
		System.out.println("time: "+(System.currentTimeMillis()-t));
			
		
	}
	
}
