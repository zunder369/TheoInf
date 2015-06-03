package uebung01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import GraphReader.GraphReader;
import GraphReader.Vertex;

public class Dijkstra {

	private Graph<Vertex, DefaultWeightedEdge> graph;
	private String sStart;
	private String sEnd;
	private Vertex vStart;
	private Vertex vEnd;
	
	private ArrayList<Vertex> checked = new ArrayList<Vertex>();

	Map<String, DijkstraBest> map = new HashMap<String, DijkstraBest>();

	LinkedList<DijkstraBest> queue = new LinkedList<DijkstraBest>();
	
	
	public Dijkstra(Graph<Vertex, DefaultWeightedEdge> graph, String start, String end){
		this.graph = graph;
		this.sStart = start;
		this.sEnd = end;
		
		// add start to stack
		for (Vertex vertex : graph.vertexSet()) {
			if(vertex.getName().equals(start)){
				vStart = vertex;
				DijkstraBest first = new DijkstraBest(null, vertex, 0); // erstes Element mit Abstand 0
				queue.add(first); // reihe erstes Element in Schlange
				map.put(vertex.getName(), first);
			}
		}		
	}
	
	public void iteration(){
		DijkstraBest currentPath = queue.poll(); // hol erste Element (kürzester Weg)
		Vertex currentlyWorkingOn = currentPath.getCurrent();
		Vertex target = null;
		// hole alle Kanten des momentanen Knoten
		Set<DefaultWeightedEdge> edges = graph.edgesOf(currentlyWorkingOn);
		
		// check edges
		for(DefaultWeightedEdge edge : edges){
			System.out.println(currentlyWorkingOn+" checking edge: "+edge);
			// finde raus welche Seite der Kante das Ziel ist
			if (! graph.getEdgeSource(edge).equals(currentlyWorkingOn)) {
				target = graph.getEdgeSource(edge);
			}
			else{
				target = graph.getEdgeTarget(edge);
			}
			// ueberpruefe Kante
			checkEdge(currentPath, target, edge);
		}
		checked.add(currentlyWorkingOn); // Knoten abgearbeitet 
		// move shortes Path to front of queue
		moveShortest();
	}
	
	private void checkEdge(DijkstraBest source, Vertex targetOfEdge, DefaultWeightedEdge edge){
		// schau nach ob Kante bereits abgearbeitet
		if(checked.contains(targetOfEdge) && 
				checked.contains(graph.getEdgeSource(edge)) || 
				checked.contains(graph.getEdgeTarget(edge)))
		{
			System.out.println("\talready checked");
			return;
		}
		DijkstraBest pos = new DijkstraBest(source, targetOfEdge, graph.getEdgeWeight(edge));
		if( ! map.containsKey(targetOfEdge.getName())){// noch nicht in Map
			System.out.println("\tno map entry found, put: "+pos+" in queue and map");
			map.put(targetOfEdge.getName(), pos);
			queue.add(pos);
		}
		else{
			System.out.println("\tentry found");
			DijkstraBest old = map.get(targetOfEdge.getName());
			double oldWeight = old.getWeight(map);
			double newWeight = pos.getWeight(map);
			if(oldWeight > newWeight){ // neues besser
				queue.add(pos); // neues rein,
				queue.remove(old); // altes raus
				System.out.println("\treplacing:\n\told: "+printPath(old)+" weight: "+oldWeight+" \n\tnew: "+printPath(pos)+ "weight: "+newWeight);
				map.put(targetOfEdge.getName(), pos);
			}
		}
	}
	
	public void moveShortest(){
		int count = 0;
		int indexMin = 0;
		if(queue.size() > 1){// mit nur einem element ist das das kleinste
			double minWeight = queue.get(0).getWeight(map);
			for (DijkstraBest path : queue) { // hier koennte man auch ne For-loop mit zaehler nehmen...
				double weight = path.getWeight(map);
				if(weight < minWeight){
					indexMin = count;
					minWeight = weight;
				}
				count ++;
			}
			// move
			DijkstraBest min = queue.get(indexMin);
			queue.remove(min);
			queue.add(0, min);
		}
	}
	
	public double getPath(){
		while (queue.size() > 0) {
			iteration();
		}
		System.out.println(printPath(map.get(sEnd)));
		return map.get(sEnd).getWeight(map);
	}

	/**
	 * Ausgabe des Pfades (Ziel -> Start)
	 * @param path
	 * @return
	 */
	public String printPath(DijkstraBest path){
		StringBuffer b = new StringBuffer();
		b.append("path: "+path);
		DijkstraBest temp = path;
		while(temp != null && temp.getSource() != null){
			temp = map.get(temp.getSource().getName());
			b.append(temp);
		}
		return b.toString();
	}
	
	
	public static void main(String args[]){
		System.out.println("Test");
		GraphReader r = new GraphReader();
		Graph g = r.buildGraph("Dijkstra.txt");
		System.out.println("Graph built");
		Dijkstra d = new Dijkstra(g, "A", "H");
		
		System.out.println("Pfad laenge: "+d.getPath());
		
//		System.out.println(d.queue.size());
//		while(!d.queue.isEmpty()){
//			System.out.println(d.queue.size());
//			DijkstraBest b = d.queue.poll();
//			d.printPath(b);
//		}
//		
//		System.out.println(d.getPath());
	}
}
