package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Vector;

public class FileReader {

	private Path path;
	private Charset charset;
	private int Ngram;
	
	private HashMap<Integer, Ngram> monogramMap;
	private HashMap<Integer, Bigram> bigramMap;
	private HashMap<Integer, Trigram> trigramMap;
	
	public FileReader(Path path, Charset charset) {
		this.path = path;
		this.charset = charset;
	}
	
	public void setMonogram(HashMap<Integer, Ngram> monogramMap){
		this.monogramMap = monogramMap;
		this.Ngram = 0;
	}
	
	public void setBigram(HashMap<Integer, Bigram> bigramMap){
		this.bigramMap = bigramMap;
		this.Ngram = 1;
	}
	
	public void setTrigram(HashMap<Integer, Trigram> trigramMap){
		this.trigramMap = trigramMap;
		this.Ngram = 2;
	}
	
	public void readFileToList(){
		
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	parseLine(line);
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}
	
	private void parseLine(String line){
		String lineSplit[] = line.split(" ");
		int hash;
		String split;
		
		for(int i = Ngram; i < lineSplit.length; i++){
			if(Ngram == 0){
				split = lineSplit[i];
				hash = split.hashCode();
				Ngram monogram;
				
				if((monogram = bigramMap.get(hash)) != null){
					monogram.addCount();
				} else {
					monogram = new Ngram(lineSplit[i], hash);
					monogramMap.put(hash, monogram);
				}
			}
			if(Ngram == 1){
				split = lineSplit[i-1] + " " + lineSplit[i];
				hash = split.hashCode();
				Bigram bigram;
				
				if((bigram = bigramMap.get(hash)) != null){
					bigram.addCount();
				} else {
					bigram = new Bigram(lineSplit[i-1], lineSplit[i], hash);
					bigramMap.put(hash, bigram);
				}
			}
			if(Ngram == 2){
				split = lineSplit[i-2] + " " + lineSplit[i-1] + " " + lineSplit[i];
				hash = split.hashCode();
				Trigram trigram;
				
				if((trigram = trigramMap.get(hash)) != null){
					trigram.addCount();
				} else {
					trigram = new Trigram(lineSplit[i-2], lineSplit[i-1], lineSplit[i], hash);
					trigramMap.put(hash, trigram);
				}
			}
		}	
	}
		
}
