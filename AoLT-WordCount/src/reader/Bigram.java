package reader;

public class Bigram extends Ngram{
	
	private String preword;
	private String seq;
	
	public Bigram(String preword, String word, int hash){
		super(word, hash);
		this.preword = preword;
		this.seq = preword + " " + word;
	}
	
	public String getPreword(){return preword;}
	public String getSeq() {return seq;}
}
