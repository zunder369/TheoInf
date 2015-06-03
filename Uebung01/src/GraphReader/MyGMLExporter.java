package GraphReader;

import java.io.PrintWriter;

import org.jgrapht.Graph;
import org.jgrapht.ext.EdgeNameProvider;
import org.jgrapht.ext.IntegerEdgeNameProvider;
import org.jgrapht.ext.IntegerNameProvider;
import org.jgrapht.ext.VertexNameProvider;

import java.io.*;
import java.util.HashMap;

import org.jgrapht.*;


/**
 * Exports a graph into a GML file (Graph Modelling Language).
 *
 * <p>For a description of the format see <a
 * href="http://www.infosun.fmi.uni-passau.de/Graphlet/GML/">
 * http://www.infosun.fmi.uni-passau.de/Graphlet/GML/</a>.</p>
 *
 * <p>The objects associated with vertices and edges are exported as labels
 * using their toString() implementation. See the {@link
 * #setPrintLabels(Integer)} method. The default behavior is to export no label
 * information.</p>
 *
 * @author Dimitrios Michail
 */
public class MyGMLExporter<V, E>
{
    

    private static final String creator = "JGraphT GML Exporter";
    private static final String version = "1";

    private static final String delim = " ";
    private static final String tab1 = "\t";
    private static final String tab2 = "\t\t";

    // TODO jvs 27-Jan-2008:  convert these to enum

    /**
     * Option to export no vertex or edge labels.
     */
    public static boolean PRINT_NO_LABELS = true;

    /**
     * Option to export only the edge labels.
     */
    public static boolean PRINT_EDGE_LABELS = false;

//    /**
//     * Option to export both edge and vertex labels.
//     */
//    public static boolean PRINT_EDGE_VERTEX_LABELS = false;

    /**
     * Option to export only the vertex labels.
     */
    public static boolean PRINT_VERTEX_LABELS = false;

    public static boolean PRINT_VERTEX_DATA = false;
    
    public static boolean PRINT_EDGE_LABEL = false;
    
    public static boolean PRINT_EDGE_COLOR = false;
    
    public static boolean PRINT_VERTEX_COLOR = false;
    public static boolean PRINT_EDGE_DATA = false;

    private boolean printLabels = PRINT_NO_LABELS;

    private VertexNameProvider<V> vertexIDProvider;
    private VertexNameProvider<V> vertexLabelProvider;
    private EdgeNameProvider<E> edgeIDProvider;
    private EdgeNameProvider<E> edgeLabelProvider;
    private VertexDataProvider<V> vertexColorProvider;
	
    private HashMap<Integer, String> colorMap;

    private void initMap(){
    	colorMap.put(0, "#101010"); // black
    	colorMap.put(1, "#FF0000"); // red
    	colorMap.put(2, "#0000FF"); // blue
    	colorMap.put(3, "#00FF00"); // green
    	colorMap.put(4, "#FFFF00"); // yellow
    	colorMap.put(5, "#FF6600"); // orange
    	colorMap.put(6, "#FF00FF"); // magenta
    	colorMap.put(7, "#00FFFF"); // cyan
    	colorMap.put(8, "#663300"); // Brown
    	colorMap.put(9, "#006600"); // dark green
    	colorMap.put(10, "#000066");// dark blue
    	colorMap.put(11, "#990099");// violet
    	colorMap.put(12, "#A9A9A9");// Gray
    	colorMap.put(13, "#8B0000");// dark red
    	colorMap.put(14, "#808000");// Olive
    	colorMap.put(15, "#9999FF");// dreckig Blau
    }

    public void vertexPrintsettings(boolean label, boolean color){//, boolean data){
//    	PRINT_VERTEX_DATA = data;
    	PRINT_VERTEX_LABELS = label;
    	PRINT_VERTEX_COLOR = color;
    }
    
    public void edgePrintsettings(boolean label, boolean color, boolean data){
    	PRINT_EDGE_COLOR = color;
    	PRINT_EDGE_LABELS = label;
    	PRINT_EDGE_DATA = data;
    }
    

    /**
     * Creates a new GmlExporter object with integer name providers for the
     * vertex and edge IDs and null providers for the vertex and edge labels.
     */
    public MyGMLExporter()
    {
        this(
            new IntegerNameProvider<V>(),
            new VertexNameProvider<V>() {
				@Override
				public String getVertexName(V vertex) {
					return ((Vertex) vertex).getName();
				}
			},
            new IntegerEdgeNameProvider<E>(),
            null);
        vertexColorProvider = new VertexDataProvider<V>() {
			@Override
			public String getVertexData(V vertex) {
				return ((Vertex) vertex).getData();
			}
		};
		colorMap = new HashMap<Integer, String>();
		initMap();
    }

    /**
     * Constructs a new GmlExporter object with the given ID and label
     * providers.
     *
     * @param vertexIDProvider for generating vertex IDs. Must not be null.
     * @param vertexLabelProvider for generating vertex labels. If null, vertex
     * labels will be generated using the toString() method of the vertex
     * object.
     * @param edgeIDProvider for generating vertex IDs. Must not be null.
     * @param edgeLabelProvider for generating edge labels. If null, edge labels
     * will be generated using the toString() method of the edge object.
     */
    public MyGMLExporter(
        VertexNameProvider<V> vertexIDProvider,
        VertexNameProvider<V> vertexLabelProvider,
        EdgeNameProvider<E> edgeIDProvider,
        EdgeNameProvider<E> edgeLabelProvider)
    {
        this.vertexIDProvider = vertexIDProvider;
        this.vertexLabelProvider = vertexLabelProvider;
        this.edgeIDProvider = edgeIDProvider;
        this.edgeLabelProvider = edgeLabelProvider;
    }

    

    private String quoted(final String s)
    {
        return "\"" + s + "\"";
    }

    private void exportHeader(PrintWriter out)
    {
        out.println("Creator" + delim + quoted(creator));
        out.println("Version" + delim + version);
    }

    private void exportVertices(
        PrintWriter out,
        Graph<V, E> g)
    {
        for (V from : g.vertexSet()) {
            out.println(tab1 + "node");
            out.println(tab1 + "[");
            out.println(
                tab2 + "id" + delim + vertexIDProvider.getVertexName(from));
            if (PRINT_VERTEX_LABELS)
            {
                String label =
                    (vertexLabelProvider == null) ? from.toString()
                    : vertexLabelProvider.getVertexName(from);
                    // hier kommt die Data dazu
                    if (printLabels == PRINT_VERTEX_DATA) {
						label += ", "+vertexColorProvider.getVertexData(from);
					}
                out.println(tab2 + "label" + delim + quoted(label));
            }
            // print color
            if( (PRINT_VERTEX_COLOR )){
            	String fill = 
            		(vertexColorProvider == null) ? "#FFFFFF" // white
            		: vertexColorProvider.getVertexData(from);
            	if(fill == null) fill =  "#FFFFFF";
            	if(!fill.equals("#FFFFFF") || !fill.matches("^#......")){// no hex
            		fill = colorMap.get(Integer.parseInt(fill));
            	}
            	out.println(tab2 + "graphics");
            	out.println(tab2 + "[");
            	out.println(tab1 + tab2 + "fill" + delim + quoted(fill));
            	out.println(tab2 + "]");
            }
            
            out.println(tab1 + "]");
        }
    }

    private void exportEdges(
        PrintWriter out,
        Graph<V, E> g)
    {
        for (E edge : g.edgeSet()) {
            out.println(tab1 + "edge");
            out.println(tab1 + "[");
            String id = edgeIDProvider.getEdgeName(edge);
            out.println(tab2 + "id" + delim + id);
            String s = vertexIDProvider.getVertexName(g.getEdgeSource(edge));
            out.println(tab2 + "source" + delim + s);
            String t = vertexIDProvider.getVertexName(g.getEdgeTarget(edge));
            out.println(tab2 + "target" + delim + t);
            if(PRINT_EDGE_LABEL && PRINT_EDGE_DATA){
            	int weight = (int)g.getEdgeWeight(edge);
            	String label =
            			(edgeLabelProvider == null) ? edge.toString()
                         : edgeLabelProvider.getEdgeName(edge);
                out.println(tab2 + "label" + delim + quoted(""+label + ", "+weight));
            }
            else if (PRINT_EDGE_LABELS)
            {
                String label =
                    (edgeLabelProvider == null) ? edge.toString()
                    : edgeLabelProvider.getEdgeName(edge);
                out.println(tab2 + "label" + delim + quoted(""+label));
            }
            else if(PRINT_EDGE_DATA)
            {
            	int weight = (int)g.getEdgeWeight(edge);
                out.println(tab2 + "label" + delim + quoted(""+weight));
            }
            // Edge Color definition missing
            if ((PRINT_EDGE_COLOR))
            {
                int weight = (int)g.getEdgeWeight(edge);
                String color = colorMap.getOrDefault(weight, "#000000");
                System.out.println("weight: "+weight+ " color: "+colorMap.get(weight));
                out.println(tab2 + "graphics");
            	out.println(tab2 + "[");
            	out.println(tab1 + tab2 + "width" + delim + 5);
            	out.println(tab1 + tab2 + "fill" + delim + quoted(color));
            	out.println(tab2 + "]");
//                    out.println(tab2 + "label" + delim + quoted(""+weight));
            }
            //*/
            out.println(tab1 + "]");
        }
    }

    private void export(Writer output, Graph<V, E> g, boolean directed)
    {
        PrintWriter out = new PrintWriter(output);

        for (V from : g.vertexSet()) {
            // assign ids in vertex set iteration order
            vertexIDProvider.getVertexName(from);
        }

        exportHeader(out);
        out.println("graph");
        out.println("[");
        out.println(tab1 + "label" + delim + quoted(""));
        if (directed) {
            out.println(tab1 + "directed" + delim + "1");
        } else {
            out.println(tab1 + "directed" + delim + "0");
        }
        exportVertices(out, g);
        exportEdges(out, g);
        out.println("]");
        out.flush();
        
        out.close();
    }

    /**
     * Exports an undirected graph into a plain text file in GML format.
     *
     * @param output the writer to which the graph to be exported
     * @param g the undirected graph to be exported
     */
    public void export(Writer output, UndirectedGraph<V, E> g)
    {
        export(output, g, false);
    }

    /**
     * Exports a directed graph into a plain text file in GML format.
     *
     * @param output the writer to which the graph to be exported
     * @param g the directed graph to be exported
     */
    public void export(Writer output, DirectedGraph<V, E> g)
    {
        export(output, g, true);
    }

    
    /**
     * Set whether to export the vertex and edge labels. The default behavior is
     * to export no vertex or edge labels.
     *
     * @param i What labels to export. Valid options are {@link
     * #PRINT_NO_LABELS}, {@link #PRINT_EDGE_LABELS}, {@link
     * #PRINT_EDGE_VERTEX_LABELS}, and {@link #PRINT_VERTEX_LABELS}.
     *
     * @throws IllegalArgumentException if a non-supported value is used
     *
     * @see #PRINT_NO_LABELS
     * @see #PRINT_EDGE_LABELS
     * @see #PRINT_EDGE_VERTEX_LABELS
     * @see #PRINT_VERTEX_LABELS
     */
    /*
    public void setPrintLabels(final Integer i)
    {
        if ((i != PRINT_NO_LABELS)
            && (i != PRINT_EDGE_LABELS)
            && (i != PRINT_EDGE_VERTEX_LABELS)
            && (i != PRINT_VERTEX_LABELS)
        	// insert PRINT_VERTEX_DATA
        	&& (i != PRINT_VERTEX_DATA)
        	// insert PRINT_VERTEX_COLOR
        	&& (i != PRINT_VERTEX_COLOR_LABELS)
        	// insert PRINT_EDGE_COLOR
        	&& (i != PRINT_EDGE_COLOR) 
           )
        {
            throw new IllegalArgumentException(
                "Non-supported parameter value: " + Integer.toString(i));
        }
        printLabels = i;
    }
    */

    
    /**
     * Get whether to export the vertex and edge labels.
     *
     * @return One of the {@link #PRINT_NO_LABELS}, {@link #PRINT_EDGE_LABELS},
     * {@link #PRINT_EDGE_VERTEX_LABELS}, or {@link #PRINT_VERTEX_LABELS}.
     */
//    public Integer getPrintLabels()
//    {
//        return printLabels;
//    }
}

// End GmlExporter.java
