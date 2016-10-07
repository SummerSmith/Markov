
public class WordGram implements Comparable<WordGram> {
	
    private String[] myWords;
    private int myHash;
   
	WordGram(String[] source, int start, int size) {
		int x = 0;
		myWords = new String[size];
		for(int i = start; i<start+size; i++) {
			myWords[x++] = source[i];
		}
	}
	
	public int hashCode() {
		
		myHash = 0;
		for(int x = 0; x<myWords.length; x++) {
			myHash = (myHash*13) + myWords[x].hashCode()*(x+1);
		}
		return myHash;
	}
	
	public String toString() {
		String toPrint = "{";
		for(int i = 0; i < myWords.length-1; i++) {
			toPrint+=("\"" + myWords[i] + "\",");
		}
		toPrint+= "\"" + myWords[myWords.length - 1] + "\"}";
		return toPrint; 
	}
	
	public boolean equals(Object other) {
		if (other==null || !(other instanceof WordGram)) {
			return false;
		}	
		WordGram o = (WordGram) other;
		if(this.length() == o.length()) {
			for(int i = 0; i<length(); i++) {
				if(!myWords[i].equals(o.myWords[i])) {
					return false;
				}
			}
			return true;
		}
				
		return false;		
	}
		

	public WordGram shiftAdd(String last) {
		String[] use = new String[myWords.length];
		int index = 0;
		for(int i = 1; i< use.length; i++) {
			use[index] = myWords[i];
			index++;
		}
		use[myWords.length-1] = last;
		WordGram shift = new WordGram(use, 0, myWords.length);
		return shift;
	}

	
	public int length() {
		return myWords.length;
	}
	
	@Override
	public int compareTo(WordGram other) {
		WordGram o = (WordGram) other;
		if(this.length() == o.length()) {
			for(int i = 0; i<length(); i++) {
				if(!myWords[i].equals(o.myWords[i])) {
					return myWords[i].compareTo(o.myWords[i]);
				}
			}
			return 0;
		}
		if(this.myWords.length > o.myWords.length) {
			return 1;
		}	
		else {
			return -1;
		}		
	} 
}
