import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class EfficientWordMarkov implements MarkovInterface<WordGram> {
	private String myText;
	private Random myRandom;
	private int myOrder;
	private Map<WordGram, ArrayList<String>> myMap = new HashMap<WordGram, ArrayList<String>>();
	
	private static String PSEUDO_EOS = "";
	private static int RANDOM_SEED = 1234;
	
	public EfficientWordMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		myOrder = order;
	}
	
	public EfficientWordMarkov() {
		this(3);
	}
	
	@Override
	public void setTraining(String text) {
		myMap.clear();
		myText = text;
		String[] words = myText.split("\\s+");
		int k = myOrder;
		for(int i=0; i<=words.length-k; i++) {
			WordGram temp = new WordGram(words, i, k);
			if (myMap.containsKey(temp)) {
				if(i == words.length-k) {
					myMap.get(temp).add(PSEUDO_EOS);
				}
				else {
					myMap.get(temp).add(words[i+k]);
				}
			}
			else { 
				if(i == words.length-k) {
					ArrayList<String> array = new ArrayList<String>();
					array.add(PSEUDO_EOS);
					myMap.put(temp, array);
				}
				else {
					ArrayList<String> array = new ArrayList<String>();
					array.add(words[i+k]);
					myMap.put(temp, array);
				}
			}
		}
	}

	@Override
	public String getRandomText(int length) {
		String[] newText = myText.split("\\s+");
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(newText.length - myOrder);

		WordGram current = new WordGram(newText, index, myOrder);
		//System.out.printf("first random %d for '%s'\n",index,current);
	
		sb.append(current);
		for(int k=0; k < length-myOrder; k++){
			ArrayList<String> follows = getFollows(current);
			if (follows.size() == 0){
				break;
			}
			index = myRandom.nextInt(follows.size());
			
			String nextItem = follows.get(index);
			if (nextItem.equals(PSEUDO_EOS)) {
				//System.out.println("PSEUDO");
				break;
			}

			sb.append(" ");
			sb.append(nextItem);
			current = current.shiftAdd(nextItem);
		}
		return sb.toString();
	}

	@Override
	public ArrayList<String> getFollows(WordGram key) {
		// TODO Auto-generated method stub
		return myMap.get(key);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return myOrder;
	}

}
