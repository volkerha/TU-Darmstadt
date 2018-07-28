package reader;

public class Trigram extends Bigram {
	
	private String prepreword;
	private String seq;
	
	public Trigram(String prepreword, String preword, String word, int hash){
		super(preword, word, hash);
		this.prepreword = prepreword;
		this.seq = prepreword + " " + preword + " " + word;
	}
	
	public String getPrepreword(){return prepreword;}
	public String getSeq(){ return seq; }
}
