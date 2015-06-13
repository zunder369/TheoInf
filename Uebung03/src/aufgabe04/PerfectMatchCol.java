package aufgabe04;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.GraphReader;
import GraphReader.GraphStuff;
import GraphReader.MyGMLExporter;
import GraphReader.Vertex;
import aufgabe3.SudokuGraph;

public class PerfectMatchCol extends SudokuGraph {

	
	public Graph cleanSameColor (Graph g){
		
		Graph cleaned;
		cleaned = GraphStuff.buildDeepCopy(g);
		// suche Knoten mit gleicher Farbe
		Set<Vertex> vertices = new TreeSet<Vertex>(g.vertexSet());
		Set<Vertex> lonely = new TreeSet<Vertex>();//TODO
		for(Vertex land : vertices){
			for(Vertex man : vertices){
				if(land.getData().equals(man.getData())){// gleiche Farbe
					vertices.remove(land);
					vertices.remove(man);
				}
			}
		}
		cleaned.removeAllVertices(vertices);
		return cleaned;
	}
	
	public int getNumberOfCombinations(){
		int count = 0;
		Set<Vertex> vertices = new TreeSet<Vertex>(graph.vertexSet());
		HashMap<String, Integer> dataMap = new HashMap<String, Integer>();
		for(Vertex v : vertices){
			if(dataMap.get(v.getData()) == null){
				dataMap.put(v.getData(), 1);
			}
			else{
				dataMap.put(v.getData(), dataMap.get(v.getData())+1);
			}
		}
		for(Entry<String, Integer> data : dataMap.entrySet()){
			if(data.getValue() == 2){
				count++;
			}
		}
		return count;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GraphReader r = new GraphReader();
		UndirectedGraph<Vertex, DefaultWeightedEdge> g = r.buildGraph("perfectMatching.txt");
		PerfectMatchCol pmc = new PerfectMatchCol();
		
		Graph inv = GraphStuff.invertedGraph(g);
		System.out.println("Inverted:");
		System.out.println(inv);
		Graph invCol  = pmc.colorate(inv);
		
		int[] numberUsedColors = new int[10];
		
		for(int i = 0; i< 100000; i++){
			invCol  = pmc.colorate(inv);
			int num = pmc.getNumberOfCombinations();
			numberUsedColors[num]++;
//			if(pmc.getNumberOfCombinations() == 9){
//				try{
//					MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
//					gmlExporter.vertexPrintsettings(true, true);
//					gmlExporter.edgePrintsettings(false, false, false);
//					File file = new File("perfectMatchingCol"+".gml");
//					Writer fileWriter = new BufferedWriter(new FileWriter(file));
//					gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)invCol);
//				}
//				catch(Exception e){
//					e.printStackTrace();
//				}
//				System.out.println("Fertig");
//				return;
//			}
			
		}
		System.out.println(Arrays.toString(numberUsedColors));

//		try{
//			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
//			gmlExporter.vertexPrintsettings(true, true);
//			gmlExporter.edgePrintsettings(false, false, false);
//			File file = new File("perfectMatchingInv"+".gml");
//			Writer fileWriter = new BufferedWriter(new FileWriter(file));
//			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)inv);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
	}

}
