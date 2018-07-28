package reader;

public class Ngram {

	private String word;
	private int count;
	private int hash;
	
	public Ngram(String word, int hash){
		this.word = word;
		this.hash = hash;
		this.count = 1;
	}
	
	public String getWord(){return word;}
	public int getCount(){ return count;}
	public void addCount(){ this.count++; }
	public int getHash(){ return this.hash; }
}
