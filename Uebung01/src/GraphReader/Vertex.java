package GraphReader;

public class Vertex {

	protected String name = null;
	protected String data = null;
	
	public Vertex(String name, String data) {
		super();
		this.name = name;
		this.data = data;
	}
	
	public Vertex(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
	public String toString(){
		return data == null ? "["+name+"]" : "["+name+ ", "+data+"]";
	}
	
}
