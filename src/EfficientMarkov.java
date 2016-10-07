import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EfficientMarkov implements MarkovInterface<String> {
	private String myText;
	private Random myRandom;
	private int myOrder;
	private Map<String, ArrayList<String>> myMap = new HashMap<String, ArrayList<String>>();
	
	private static String PSEUDO_EOS = "";
	private static int RANDOM_SEED = 1234;
	
	public EfficientMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		myOrder = order;
	}
	
	public EfficientMarkov() {
		this(3);
	}
	
	@Override
	public void setTraining(String text) {
		myText = text;

		int k = myOrder;
		for(int i=0; i<=myText.length()-k; i++) {
			if (myMap.containsKey(myText.substring(i, i+k))) {
				if(i == myText.length()-k) {
					myMap.get(myText.substring(i, i+k)).add(PSEUDO_EOS);
				}
				else{
					myMap.get(myText.substring(i, i+k)).add(myText.substring(i+k, i+k+1));
				}	
			}
			else {
				if(i == myText.length()-k) {
					ArrayList<String> array = new ArrayList<String>();
					array.add(PSEUDO_EOS);
					myMap.put(myText.substring(i, i+k), array);
				}
				else{
					ArrayList<String> array = new ArrayList<String>();
					array.add(myText.substring(i+k, i+k+1));
					myMap.put(myText.substring(i, i+k), array);
				}
			}
		}
	}

	@Override
	public String getRandomText(int length) {
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(myText.length() - myOrder);

		String current = myText.substring(index, index + myOrder);
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
			sb.append(nextItem);
			current = current.substring(1)+ nextItem;
		}
		return sb.toString();
	}

	@Override
	public ArrayList<String> getFollows(String key) {
		// TODO Auto-generated method stub
		return myMap.get(key);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return myOrder;
	}

}
