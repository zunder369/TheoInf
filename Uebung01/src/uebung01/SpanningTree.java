package uebung01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import GraphReader.GraphStuff;
import GraphReader.GraphReader;
import GraphReader.MyGMLExporter;
import GraphReader.Vertex;
import GraphReader.GraphStuff;

public class SpanningTree {
	
	List<Vertex> vertices = new ArrayList<Vertex>();
	Map<Vertex, Integer> map = new HashMap<Vertex, Integer>();

	public Graph<Vertex, DefaultWeightedEdge> getSpanningTree(Graph<Vertex, DefaultWeightedEdge> g){
		ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> tree = new ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graph<Vertex, DefaultWeightedEdge> working = GraphStuff.buildDeepCopy(g);
		
		// add min Edge to tree
		DefaultWeightedEdge e = getMinEdge(working, tree.edgeSet());
		System.out.println("Min Edge: "+e);
		while(e != null){
			Vertex source = (Vertex) working.getEdgeSource(e);
			Vertex target = (Vertex) working.getEdgeTarget(e);
			tree.addVertex(source);
			tree.addVertex(target);
			System.out.println("added "+source + " and "+ target +" to spanning Tree");
			System.out.println("weight: "+ working.getEdgeWeight(working.getEdge(source, target)));
			if(!vertices.contains(source)) vertices.add(source);
			if(!vertices.contains(target)) vertices.add(target);
//			vertices.add(source);
//			vertices.add(target);
			if(map.containsKey(source)) 
				map.put(source, map.get(source)+1);
			 else 
				map.put(source, 1);
			 if(map.containsKey(target)) 
				map.put(target, map.get(target)+1);
			 else 
				map.put(target, 1);
			
			System.out.println("old graph:" +working);
			//hier kommt das Kantengewicht in den graphen
			DefaultWeightedEdge o = tree.addEdge(source, target);
			System.out.println("source: "+source+" target: "+target);
//			System.out.println(o);
			tree.setEdgeWeight(o, working.getEdgeWeight(e));
			
			// test
			System.out.println(working.removeEdge(source, target));
			
			clean(working);
			e = getMinEdge(working, tree.edgeSet());
		}
		
		return tree;
	}
	
	private void clean(Graph<Vertex, DefaultWeightedEdge> working) {
		// remove edges that would destroy spanning tree
		// -> Remove Edges from working that would build a circle 
		Vertex s = null;
		Vertex t = null;
		DefaultWeightedEdge e = null;
		System.out.println(Arrays.toString(vertices.toArray()));
		for (int i = 0; i < vertices.size()-1; i++) {
			s = vertices.get(i);
			for (int k = i+1; k < vertices.size(); k++) {
				t = vertices.get(k);
				e = working.getEdge(s, t);
				if (e != null && ( map.get(s) > 1 || map.get(t) > 1)) {
					working.removeEdge(e);
				}
			}
		}		
	}

	/*
	 * Sortiere die kleinste Kante in der Liste nach vorne
	 */
	public DefaultWeightedEdge getMinEdge(Graph<Vertex, DefaultWeightedEdge> g, Set<DefaultWeightedEdge> other){
		Set<DefaultWeightedEdge> set = g.edgeSet(); // hol alle Kanten des Graphen
		
		double minWeight = Integer.MAX_VALUE;
		DefaultWeightedEdge minEdge = null; 
		for (DefaultWeightedEdge e : set) { // such die kürzeste Kante
//			System.out.println("edge: "+e+" weight: "+g.getEdgeWeight(e));
			if(g.getEdgeWeight(e) < minWeight && !other.contains(e)){
				minEdge = (DefaultWeightedEdge) e;
				minWeight = g.getEdgeWeight(e);
			}
		}
		return minEdge;
	}
//	
//	private Graph<Vertex, DefaultWeightedEdge> buildCopy(Graph<Vertex, DefaultWeightedEdge> g) {
//		Graph<Vertex, DefaultWeightedEdge> c = new ListenableUndirectedWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
//		Set<DefaultWeightedEdge> e = g.edgeSet();
//		Set<Vertex> v = g.vertexSet();
//		
//		for (Vertex vert : v) {
//			c.addVertex(vert);
//		}
//		for (DefaultWeightedEdge edge : e) {
//			Vertex s = g.getEdgeSource(edge);
//			Vertex t = g.getEdgeTarget(edge);
//			c.addEdge(s, t, edge);
//		}
//		
//		return c;
//		
//	}



	public static void main(String[] args) {
		GraphReader r = new GraphReader();
		SpanningTree s = new SpanningTree();
		Graph<Vertex, DefaultWeightedEdge> graph = r.buildGraph("Dijkstra.txt");
		
		UndirectedGraph<Vertex, DefaultWeightedEdge> spann = (UndirectedGraph<Vertex, DefaultWeightedEdge>) s.getSpanningTree(graph);
		try(Writer fileWriter = new BufferedWriter(new FileWriter(new File("Dijkstra" + " (spanning tree example)2.gml")))) {
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, false);
			gmlExporter.edgePrintsettings(false, false, true);
			gmlExporter.export(fileWriter, spann);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(spann);
	}

}
