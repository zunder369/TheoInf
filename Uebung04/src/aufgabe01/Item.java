package aufgabe01;

public class Item {

	private String name;
	private int volume;
	private int value;
	
	public Item(String name, int volume, int value){
		this.name = name;
		this.volume = volume;
		this.value = value;
	}
	
	public String toString(){
		return "["+name+ ", vol: " +volume+", val: "+ value+"]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
	
}
