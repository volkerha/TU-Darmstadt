package reader;

import java.util.Random;
import java.util.Vector;

public class QuickSort {

	public static void sortiere(Vector<Word> wordList) {
	      qSort(wordList, 0, wordList.size()-1);
	   }
	    
	   public static void qSort(Vector<Word> wordList, int links, int rechts) {
	      if (links < rechts) {
	         int i = partition(wordList,links,rechts);
	         qSort(wordList,links,i-1);
	         qSort(wordList,i+1,rechts);
	      }
	   }
	    
	   public static int partition(Vector<Word> wordList, int links, int rechts) {
	      int pivot, i, j;
	      Word help;
	      pivot = wordList.get(rechts).getCount();               
	      i     = links;
	      j     = rechts-1;
	      while(i<=j) {
	         if (wordList.get(i).getCount() > pivot) {     
	            // tausche x[i] und x[j]
	            help = wordList.get(i); 
	            wordList.set(i, wordList.get(j)); 
	            wordList.set(j, help);                     
	            j--;
	         } else i++;            
	      }
	      // tausche x[i] und x[rechts]
	      help      = wordList.get(i);
	      wordList.set(i, wordList.get(rechts)); 
          wordList.set(rechts, help);                     
          
	        
	      return i;
	   }
	
}
