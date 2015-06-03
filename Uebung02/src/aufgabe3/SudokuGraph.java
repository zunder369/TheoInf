package aufgabe3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import aufgabe1.GreedyColVar;
import GraphReader.GraphReader;
import GraphReader.GraphStuff;
import GraphReader.MyGMLExporter;
import GraphReader.Vertex;

public class SudokuGraph extends GreedyColVar {

	
	@Override
	public void initDefaultColor(Graph<Vertex, DefaultWeightedEdge> newGraph) {
		Set<Vertex> vertices = newGraph.vertexSet();
		Vertex[] array = vertices.toArray(new Vertex[vertices.size()]);
		for (int i = 0; i < array.length; i++) {
//			array[i].setData(null);
			// schau nach ob eine Farbe vorhanden, wenn ja, -1 (weil arrays bei 0 anfangen) 
			// spaeter muss +1 gemacht werden beim rausschreiben
			array[i].setData(array[i].getData() == null ? null : Integer.valueOf(array[i].getData())-1+"");
			verticeNameMap.put(array[i].getName(), array[i]);
		}
	}
	
	
	public int[][] solveSudoku(Graph g){
		
		int[][] ret = null;
		Graph solved = colorate(g);
		
		Set<Vertex> vertices = solved.vertexSet();
		int width = (int)Math.sqrt(vertices.size());
		ret = new int[width][width];
		// Knoten nach Name sortieren
		Vertex[] va = vertices.toArray(new Vertex[vertices.size()]);
		Arrays.sort(va, new Comparator<Vertex>() {
			public int compare(Vertex o1, Vertex o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
			// TODO debug
//		System.out.println("sorted array");
//			System.out.println(Arrays.toString(va));
		int pos = 0;
		for (int o = 0; o < width; o++) {
			for (int i = 0; i < width; i++) {
				ret[o][i] = Integer.valueOf(va[pos++].getData()) +1; // hier +1 wegen array start 0
			}
		}
		
		return ret;
				
	}
	
	
	public static void main(String[] args) {
		GraphReader r = new GraphReader();
		SudokuGraph s = new SudokuGraph();
		
		Graph g = r.buildGraph("SudokuTest.txt");
		int[][] array = new int[1][1];
		int[] ar = new int[8];
		
		for(int i = 0 ; i<100000; ++i){
			array = s.solveSudoku(g);
			int c = GraphStuff.colorsInGraph(s.getGraph());
			ar[c]++;
		}
		
		
		System.out.println("### Ergebnis ###");
		for (int[] i : array) {
			System.out.println(Arrays.toString(i));
		}
		System.out.println(Arrays.toString(ar));
		
		try{
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, true);
			gmlExporter.edgePrintsettings(false, false, false);
			File file = new File("SudokuTest"+".gml");
			Writer fileWriter = new BufferedWriter(new FileWriter(file));
			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>) g);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
