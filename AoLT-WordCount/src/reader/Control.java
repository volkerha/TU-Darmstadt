package reader;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.Vector;

public class Control {

	public static void main(String[] args) {

		Charset charset = StandardCharsets.UTF_8;
		Path path_train = FileSystems.getDefault().getPath("resources", "de_text.train");
		Path path_test = FileSystems.getDefault().getPath("resources", "de_text.test");

		HashMap<Integer, Bigram> bigramMap_train = new HashMap<Integer, Bigram>();
		HashMap<Integer, Bigram> bigramMap_test = new HashMap<Integer, Bigram>();
		
		System.out.println("Parsing Training Set...");
		FileReader fileReader = new FileReader(path_train, charset);
		fileReader.setBigram(bigramMap_train);
		fileReader.readFileToList();
		System.out.println("Training Set parsed.");
	
		System.out.println("Parsing Test Set...");
		fileReader = new FileReader(path_test, charset);
		fileReader.setBigram(bigramMap_test);
		fileReader.readFileToList();
		System.out.println("Test Set parsed.");
	
		System.out.println("Computing missing words Test->Train...");
		int missInTrain = 0;
		Set<Integer> hashSet_test = bigramMap_test.keySet();
		Iterator<Integer> iterator = hashSet_test.iterator();

		while(iterator.hasNext()){
			if(!bigramMap_train.containsKey(iterator.next())) missInTrain++;
		}
		
		double rate = (double) missInTrain / (double) bigramMap_test.size();
		System.out.println("Percentage of miss Test->Train: " + rate);
		
		Collection<Bigram> bigrams_train = bigramMap_train.values();
		Iterator<Bigram> iterator_train = bigrams_train.iterator();
		Map<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>();
		Bigram bigram;
		
		while(iterator_train.hasNext()){
			bigram = iterator_train.next();
			sortedMap.put(bigram.getCount(), bigram.getHash());
		}
		
		System.out.println("Top20:");
		int iter = sortedMap.size();
		for(Map.Entry<Integer,Integer> entry : sortedMap.entrySet()) {
			iter--;
	        if(iter < 22){
			bigram = bigramMap_train.get(entry.getValue());
	        System.out.println(iter + ": " + bigram.getSeq() + " #" + bigram.getCount());
			}
	    }
		/*Vector<Bigram> top20 = new Vector<Bigram>();
		int largest = 0;
		int largest_i = 0;
		for(int j = 0; j < 22; j++){
			largest = 0;
			for(int i = 0; i < bigramList_train.size(); i++){
				if(bigramList_train.get(i).getCount() > largest){
					largest_i = i;
					largest = bigramList_train.get(i).getCount();
				}
			}
			top20.add(bigramList_train.get(largest_i));
			bigramList_train.remove(largest_i);
		}
		System.out.println("\nMost frequent words in Training Set:");
		for(int i = 0; i < top20.size(); i++){
			if(top20.get(i) != null){
				System.out.println(i + ": " + top20.get(i).getWord() + " #" + top20.get(i).getCount());
			}
		}*/
		
	}

}
