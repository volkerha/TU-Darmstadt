package reader;

public class Word {

	private int count;
	private String name;
	
	
	public Word(String name){
		count = 1;
		this.name = name;
	}
	
	public int getCount(){ return count;}
	public String getName() { return name; }
	public void addCount(){ this.count++; }
}
