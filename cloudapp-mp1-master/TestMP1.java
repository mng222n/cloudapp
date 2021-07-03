/**
 * Unit Test of MP1
 */
import junit.framework.*;
/**
 * @author ROBERT NGUYEN
 *
 */
public class TestMP1 extends TestCase {
	
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
	
	int value1, value2;
	
    MP1 mp = new MP1(userName, inputFileName);
	   
	   // assigning the values
	   protected void setUp(){
	      value1=3;
	      value2=3;
	   }

	   // Test method to add two values
	   public void testAdd(){
	      double result= value1 + value2;
	      assertTrue(result == 6);
	   }
	   
	   // Test of Cloud
	   public void testCloud() {
		   
	   }
}