/**
 * 
 * Execution of MP1
 * @author ROBERT NGUYEN
 *
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};
    
    /**
     * 
     * @param seed
     * @throws NoSuchAlgorithmException
     */
    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }
    
    /**
     * 
     * @return
     * @throws NoSuchAlgorithmException
     */
    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }
    
    /**
     * 
     * @param userName
     * @param inputFileName
     */
    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public String[] process() throws Exception {
        String[] ret = new String[20];
       
        //TODO
        //01. read file and store into titleList
        List<String> titleList = retrieveTitleList();
        //02. return list of words of input
        List<List<String>> wordsList = (List<List<String>>) retrieveWordsList(titleList);
        //03. return list of frequency of words
        Map<String,Integer> frequency = (Map<String, Integer>) retrieveWordsFrequency(wordsList);
        //04. return list of 20 sorted frequency
        List<String> top20List = (List<String>) retrieveTop20Words(frequency);
		ret = top20List.toArray(new String[top20List.size()]);
		//System.out.println(top20List.size());
        return ret;    
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public List<String> retrieveTitleList() throws Exception {
    	String line;
        BufferedReader  br = null;
        List<String> titleList = new ArrayList<String>();
        br = new BufferedReader(new FileReader(this.inputFileName)) ;
        while ( null != (line=br.readLine()) ) {
        	titleList.add(line);
        }
        br.close();
		return titleList;
    	
    }
    
    /**
     * @param titleList
     * @return
     * @throws Exception
     */
    public Object retrieveWordsList(List<String> titleList) throws Exception {
    	List<List<String>> wordsList = new ArrayList<>();
    	List<String> wordsTitle = new ArrayList<String>();
    	Integer[] indexes = this.getIndexes();
    	List<String> stopWordsList = Arrays.<String>asList(this.stopWordsArray);
		String word;

        for(int i = 0; i < indexes.length; i++) {
        	StringTokenizer st = new StringTokenizer(titleList.get(indexes[i]).toLowerCase().trim(),this.delimiters);
        	while (st.hasMoreTokens()) {
        		word = st.nextToken();
        		if ( false == stopWordsList.contains(word) ) {
        			wordsTitle.add(word);
        		}
        	}
        	List<String> wordsTitleTemp = new ArrayList<String>(wordsTitle.size());
        	for(String wordTemp : wordsTitle) {
        		wordsTitleTemp.add(wordTemp);
        	}
        	wordsList.add(wordsTitleTemp);
        	wordsTitle.clear();
        }
		return wordsList;
    }
    
    public Object retrieveWordsFrequency(List<List<String>> wordsList) throws Exception {
    	Map<String,Integer> frequency = new HashMap<String,Integer>();
    	
    	for(List<String> innerList : wordsList) {
    		for(String word : innerList) {
    			Integer f = frequency.get(word);
    			if(null==f) f=0;
    			frequency.put(word,f+1);
    		}
    	}
    	return frequency;
    }
    
    public Object retrieveTop20Words(Map<String,Integer> unsortMap) {
		Map<String, Integer> sortedMap = sortByComparator(unsortMap);
		List<String> top20List = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
			if(top20List.size()>19) break;
				//System.out.println("[Key] : " + entry.getKey()  + " [Value] : " + entry.getValue());
				top20List.add(entry.getKey());
		}
    	return top20List;
    }
    
    public Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = 
			new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
                                           Map.Entry<String, Integer> o2) {
				if(0!=o1.getValue().compareTo(o2.getValue())) 
					return (o1.getValue()).compareTo(o2.getValue());
				else
					return (o2.getKey().compareTo(o1.getKey()));
			}
		});
		//Sort descending
		Collections.reverse(list);
		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public void printMap(Map<String, Integer> map) {
		int count = 0;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if(count<20) {
				System.out.println("[Key] : " + entry.getKey() 
                                      + " [Value] : " + entry.getValue());
				count++;
			} else { 
				return;
			}
		}
	}

    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}
