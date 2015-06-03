package GraphReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

public class GraphReader {

	private ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> graph;
	private List<Vertex> vertexList;
	private HashMap<String, Vertex> vertexMap;
	
	public ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> getGraph() {
		return graph;
	}

	/**
	 * V = String E = DefaultEdge
	 * 
	 * @param filename
	 * @return
	 */
	public ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> buildGraph(
			String filename) {
		// init
		graph = new ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		vertexList = new ArrayList<Vertex>();
		vertexMap = new HashMap<String, Vertex>();
		
		//build
		ArrayList<String> filecontent = readFile(filename);
		ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> graph 
			= new ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		for (String line : filecontent) {
			if (line != null && line.startsWith("knoten")) {
				addKnoten(graph, line);
			} else if (line != null && line.startsWith("kante")) {
				addKante(graph, line);
			} else {
				// skip
			}
		}
		System.out.println(graph);
		return graph;
	}

	private void addKante(
			ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> graph,
			String line) {
		String[] splitted = line.split(" ");
		DefaultWeightedEdge e = graph.addEdge(vertexMap.get(splitted[1]), vertexMap.get(splitted[2]));
		if (splitted.length > 3)
			graph.setEdgeWeight(e, Integer.parseInt(splitted[3]));
	}

	private void addKnoten(
			ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> graph,
			String line) {
		String[] splitted = line.split(" ");
		Vertex v = null;

		v = new Vertex(splitted[1]);
		if(splitted.length > 2){
			v.setData(splitted[2]);
		}
		System.out.println(v);
		graph.addVertex(v);
		vertexList.add(v);
		vertexMap.put(v.getName(), v);
	}

	/**
	 * Reads content of File (filename)
	 * 
	 * @param filename
	 * @return Content of File as String
	 */
	private ArrayList<String> readFile(String filename) {

		ArrayList<String> buffer = new ArrayList<String>();
		try {
			FileReader freader = new FileReader(filename);
			BufferedReader br = new BufferedReader(freader);

			String line = null;
			do {
				line = br.readLine();
				buffer.add(line);
			} while (line != null);
			br.close();
			return buffer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public static void main(String[] args) {


		GraphReader r = new GraphReader();

		String[] files = {"Euler1.txt", "Euler2.txt", "Dijkstra.txt"};
		
		for (String s : files){
			UndirectedGraph<Vertex, DefaultWeightedEdge> graph = r.buildGraph(s);
			
			try{
				MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
				gmlExporter.vertexPrintsettings(true, false);
				gmlExporter.edgePrintsettings(false, false, true);
				File file = new File(s+".gml");
				Writer fileWriter = new BufferedWriter(new FileWriter(file));
				gmlExporter.export(fileWriter, graph);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		UndirectedGraph<Vertex, DefaultWeightedEdge> graph = r.buildGraph("ColorTest.txt");
		try{
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, true);
			File file = new File("ColorTest"+".gml");
			Writer fileWriter = new BufferedWriter(new FileWriter(file));
			gmlExporter.export(fileWriter, graph);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		graph = r.buildGraph("ColorEdgeTest.txt");
		try{
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, false);
			gmlExporter.edgePrintsettings(false, true, true);
			File file = new File("ColorEdgeTest"+".gml");
			Writer fileWriter = new BufferedWriter(new FileWriter(file));
			gmlExporter.export(fileWriter, graph);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		graph = r.buildGraph("Dijkstra.txt");
		System.out.println(graph);
		System.out.println(graph.vertexSet().stream().filter
				(v -> v.name.equals("A")).collect(Collectors.toList()).get(0));
		graph.removeVertex(
				graph.vertexSet().stream().filter
				(v -> v.name.equals("A")).collect(Collectors.toList()).get(0)
						);
		System.out.println(graph);
		
		graph = r.buildGraph(files[2]);
		try(Writer fileWriter = new BufferedWriter(new FileWriter(new File(files[2] + " (spanning tree example).gml")))) {
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, false);
			gmlExporter.edgePrintsettings(false, true, true);
//			UndirectedGraph<Vertex, DefaultWeightedEdge> toExport = getSpanningTreeOf(graph);
//			gmlExporter.export(fileWriter, toExport);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(graph);
		Vertex a = graph.vertexSet().stream().filter
				(v -> v.name.equals("A")).collect(Collectors.toList()).get(0);
		Vertex b = graph.vertexSet().stream().filter
				(v -> v.name.equals("B")).collect(Collectors.toList()).get(0);
		System.out.println(graph.getEdge(a, b));
		System.out.println(graph.getEdge(b,a));
	}

}
