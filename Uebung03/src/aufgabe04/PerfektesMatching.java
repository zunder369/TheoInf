package aufgabe04;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
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

public class PerfektesMatching {

	// [1] https://www.tu-ilmenau.de/fileadmin/public/iti/Lehre/EAII/WS11/Kapitel2-Matchings.pdf
	// Gale Shapely Heiratsantrags-Algorithmus
	
	/**
	 * TODO laeuft nocht nicht...
	 * TODO
	 * TODO
	 */
	
	protected Set<Vertex> W; // Frauen - Studenten
	protected Set<Vertex> U; // Maenner - Laender
	protected Graph g;
	protected Set<Matching> M;
	
	protected final int N = 16; // available Colors
	protected int[] usedColors = new int[N];
	/**
	 * 
	 * @param g
	 * @param maenner die kleinere der Mengen, falls eine kleiner ist
	 * @param frauen
	 */
	public PerfektesMatching(Graph g, Set<Vertex> maenner, Set<Vertex> frauen){
		this.g = GraphStuff.buildDeepCopy(g);
		this.W = frauen;
		this.U = maenner;
		M = new HashSet<Matching>();
	}
	
	public Graph match() throws Exception{
		// laut [1] terminiert Algorithmus nach spaetestens n^2 durchlaufen
		int exceptionCount = (W.size() + U.size())*(W.size()+U.size());
		List<Vertex> unmatched = new ArrayList<Vertex>(U);
		for(int i = 0; unmatched.size() != 0 ;i++){ // noch ungematchte Laender, i fuer exceptionCount mitzaehlen
			Vertex cur = unmatched.get((int)Math.random()*unmatched.size());
			Set<Vertex> availableW = GraphStuff.getNeighbors(g, cur);
			// mache verfuegbaren Frauen einen Antrag
			for (Vertex vertex : availableW) {
				if(vertex.getData() == null){ // 1. Fall Frau frei
					M.add(new Matching(cur, vertex));
					vertex.setData(cur.toString());
				}
				else{ // Frau schon vergeben
					
				}
			}
			
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphReader r = new GraphReader();
		UndirectedGraph g = r.buildGraph("perfectMatching-NF.txt");
		
		try{
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, false);
			gmlExporter.edgePrintsettings(false, false, false);
			File file = new File("perfectMatching-NF"+".gml");
			Writer fileWriter = new BufferedWriter(new FileWriter(file));
			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)g);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
