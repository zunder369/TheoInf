package aufgabe04;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import GraphReader.GraphReader;
import GraphReader.GraphStuff;
import GraphReader.MyGMLExporter;
import GraphReader.Vertex;

public class MaximumMatching {

	// [1] https://www.tu-ilmenau.de/fileadmin/public/iti/Lehre/EAII/WS11/Kapitel2-Matchings.pdf
	// Gale Shapely Heiratsantrags-Algorithmus
	
	
	protected Set<Vertex> W; // Frauen - Studenten
	protected Set<Vertex> M; // Maenner - Laender
	protected Graph<Vertex, DefaultWeightedEdge> g;
	protected List<DefaultWeightedEdge> maxMatch;
	
	Set<Vertex> unmatchedW;
	Set<Vertex> unmatchedM;
	
	Set<Vertex> matched;
	
	final int REPEAT = 100; 
	
	
	protected final int N = 16; // available Colors
	protected int[] usedColors = new int[N];
	/**
	 * 
	 * @param g
	 * @param maenner die kleinere der Mengen, falls eine kleiner ist
	 * @param frauen
	 */
	public MaximumMatching(Graph g, Set<Vertex> maenner, Set<Vertex> frauen){
		this.g = g;
		this.W = frauen;
		this.M = maenner;
		maxMatch = new ArrayList<DefaultWeightedEdge>();
		unmatchedW = new TreeSet<Vertex>(W);
		unmatchedM = new TreeSet<Vertex>(M);
		matched = new TreeSet<Vertex>();
		// build initial Matching
		initMatching();
	}
	
	public Graph getMatchedGraph(){
		Graph newGraph = new SimpleWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
				
		List<DefaultWeightedEdge> match = getMaximumMatch();
		
		for (DefaultWeightedEdge e : match) {
			Vertex s = g.getEdgeSource(e);
			Vertex t = g.getEdgeTarget(e);
			System.out.println("edge: "+e);
			newGraph.addVertex(s);
			newGraph.addVertex(t);
			newGraph.addEdge(s, t);
		}
		return newGraph;
	}
	
	private void initMatching() {
		List<Vertex> neigh;
		Vertex w; // alle Nachbarn und ausgewaehlter
		System.out.println("INITIAL");
		for(Vertex m : unmatchedM){
			neigh = new ArrayList<Vertex>(GraphStuff.getNeighbors(g, m));
			neigh.removeAll(matched);
			w = null;
			if(neigh.size() > 0 ) {
				w = neigh.get(0);
				matched.add(w);
				matched.add(m);
				System.out.println(w+" matched to: "+m);
				maxMatch.add(g.getEdge(w, m));
			}
		}
		unmatchedM.removeAll(matched);
		unmatchedW.removeAll(matched);
	}

	public List<DefaultWeightedEdge> getMaximumMatch(){
		DefaultWeightedEdge curEdge;
		Vertex v;
		List<DefaultWeightedEdge> maxFoundMatch = new ArrayList<DefaultWeightedEdge>();
		copy(maxMatch, maxFoundMatch);
		for (int i = 0; i < REPEAT; i++) {
			// entferne ein Match aus momentanem Matching für das es andere Optionen gibt
			if(unmatchedM.size() == 0){
				return maxMatch; // alle M aufgebraucht
			}
			// RANDOMISIERUNG
			int rand = (int)(Math.random()*(maxMatch.size()));
			// schaue bei rand ob es alternativen zum momentanen Match gibt
			curEdge = maxMatch.get(rand);
			v = g.getEdgeTarget(curEdge); // konten von rechter Seite
			if(g.edgesOf(v).size() == 1){//nur dieses match moeglich
				continue;
			}
			// merke dir Knoten von der anderen Seite und suche zu dem ein neues match
			// außer das alte
			maxMatch.remove(curEdge);
			
			Vertex o = g.getEdgeSource(curEdge); // knoten von linker Seite
			matched.remove(o);
			matched.remove(v);
			unmatchedW.add(o);
			unmatchedM.add(v);
			
			// matche neu
			DefaultWeightedEdge mw = matchMW(v, o);
			DefaultWeightedEdge wm = matchWM(o, v);
			
			if(mw != null)
				maxMatch.add(mw);
			if(wm != null)
				maxMatch.add(wm);
			
			// schaue ob mehr Kanten im neuen Match als vorher
//			System.out.println("size: "+maxMatch.size());
			if(maxFoundMatch.size() < maxMatch.size()){
//				System.out.println("new maximum: "+maxMatch.size()+", old: "+maxFoundMatch.size());
				copy(maxMatch, maxFoundMatch);
			}
		}
		return maxFoundMatch;
	}
	
	/**
	 * Ordnet Knoten einem freien Knoten einer Frau zu
	 */
	private DefaultWeightedEdge matchMW(Vertex m, Vertex not) {
		// get neighbors of v
		DefaultWeightedEdge e = null;
		Set<Vertex> neigh = GraphStuff.getNeighbors(g, m);
		neigh.remove(not); // diesen Knoten nicht beachten
		for(Vertex w : neigh){
			// suche neues Match fuer m, das noch nicht gematched ist
			if(unmatchedW.contains(w)){// Gefundener Knoten ist noch nicht gematched
				matched.add(w);
				matched.add(m);
				unmatchedW.remove(w);
				unmatchedM.remove(m);
				e = g.getEdge(w, m);
				return e;
			}
		}
		return e;
	}
	
	/**
	 * Ordnet Knoten einem freien Knoten aus der Frauen Menge zu
	 * @param w
	 * @param not
	 */
	private DefaultWeightedEdge matchWM(Vertex w, Vertex not) {
		// get neighbors of v
		Set<Vertex> neigh = GraphStuff.getNeighbors(g, w);
		DefaultWeightedEdge e = null;
		neigh.remove(not); // diesen Knoten nicht beachten
		for(Vertex m : neigh){
			// suche neues Match fuer w, das noch nicht gematched ist
			if(unmatchedM.contains(m)){// Gefundener Knoten ist noch nicht gematched
				matched.add(m);
				matched.add(w);
				unmatchedM.remove(m);
				unmatchedW.remove(w);
				e = g.getEdge(w, m);
//				maxMatch.add(e);
				return e;
			}
		}
		return e;
	}

	private void copy(List<DefaultWeightedEdge> from, List<DefaultWeightedEdge> to){
		to.clear();
		for (DefaultWeightedEdge e : from) {
			to.add(e);
		}
	}
	
	public static void main(String[] args) {
		GraphReader r = new GraphReader();
		UndirectedGraph<Vertex, DefaultWeightedEdge> g = r.buildGraph("perfectMatching.txt");
		Set<Vertex> laender = new TreeSet<Vertex>();
		Set<Vertex> jungen = new TreeSet<Vertex>();
		for (Vertex v : g.vertexSet()) {
			if(v.getName().length() == 2)
				laender.add(v);
			else
				jungen.add(v);
		}		
		MaximumMatching m = new MaximumMatching(g, laender, jungen);

		Graph match = m.getMatchedGraph();
		
		System.out.println("Match size (number of Edges): "+match.edgeSet().size());
		
		try{
			MyGMLExporter<Vertex, DefaultWeightedEdge> gmlExporter = new MyGMLExporter<Vertex, DefaultWeightedEdge>();
			gmlExporter.vertexPrintsettings(true, false);
			gmlExporter.edgePrintsettings(false, false, false);
			File file = new File("perfectMatching"+".gml");
			Writer fileWriter = new BufferedWriter(new FileWriter(file));
			gmlExporter.export(fileWriter, (UndirectedGraph<Vertex, DefaultWeightedEdge>)match);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println();
	}

}
