package uebung01;

import java.util.Map;

import GraphReader.Vertex;

public class DijkstraBest extends Vertex{

	private double weight;
	private DijkstraBest source;
	private Vertex current;
	 
	public DijkstraBest(DijkstraBest source, Vertex current, double weight){
		super(current.getName());
		this.current = current;
		this.source = source;
		this.weight = weight;
	}

	public double getWeight(Map<String, DijkstraBest> map) {
		double weight = 0;
		weight += this.weight;
		DijkstraBest temp = source;
		while(temp.source != null){// gehe über Liste durch Map und summiere
			weight += temp.weight;
			temp = map.get(temp.source.getName());
		}
		return weight;
	}
	

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(DijkstraBest source) {
		this.source = source;
	}

	public Vertex getCurrent() {
		return current;
	}

	public void setCurrent(Vertex current) {
		this.current = current;
	}
	
}
