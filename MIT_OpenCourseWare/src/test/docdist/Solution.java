package test.docdist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/*
 * This program computes the "distance" between two text files as the angle between their word frequency vectors
 * From Original version by Erik D. Demaine on January 31, 2011, in python
 * For each input file, a word-frequency vector is computed as follows
 * i.	the specified file is read
 * ii.	It is converted into a list of alphanumeric words where a word here is a sequence of characters.
 * iii. Non alphanumeric characters are treated as blanks
 * iv.  for each word its frequency of occurrence is determines
 * v. 	word frequency lists are sorted into order alphabetically
 * Distance between two vectors is the angle between them. 
 * If x=(x1,x2,...xn) is the first vector (xi = frequence of word i) and y=(y1,y2....yn) is second vector
 * then the angle between them is d(x,y) = arccos(inner_product(x,y)/(norm(x)*norm(y))
 * where: inner_product(x,y) = x1*y1 + x2*y2 + ...xn*yn
 * 		  norm(x) = sqrt(inner_product(x,x))
 * Algorithm found in 1975 for information retrieval
 */
public class Solution {

	private long startTime = System.currentTimeMillis();
	private long endTime;
	
	public static void main(String[] args) {
		HashMap<String,Integer> word_list_1;
		HashMap<String,Integer> word_list_2;
		if(args.length!=2)
			System.out.println("Please enter valid number of arguments: 2");
		else
		{
			System.out.println(args[0]);
			System.out.println(args[1]);
			Solution sol = new Solution();
			word_list_1 = sol.word_frequencies_for_file(args[0]);
			System.out.println("Number of words in first file:"+word_list_1.size());
			word_list_2 = sol.word_frequencies_for_file(args[1]);
			System.out.println("Number of words in second file:"+word_list_2.size());
			double angle = sol.vector_angle(word_list_1, word_list_2);
			System.out.println("Distance is:"+angle);
		}
	}

	private double vector_angle(HashMap<String, Integer> word_list_1, HashMap<String, Integer> word_list_2) {
		double numerator = inner_product(word_list_1, word_list_2);
		double denominator = inner_product(word_list_1,word_list_1) * inner_product(word_list_2,word_list_2);
		endTime = System.currentTimeMillis();
		System.out.println("time taken:"+(endTime - startTime)+" milli seconds");
		return Math.acos(numerator/denominator);
	}

	private double inner_product(HashMap<String, Integer> word_list_1, HashMap<String, Integer> word_list_2) {
		int inner_product=0;
		for(String key: word_list_1.keySet())
		{
			if(word_list_2.containsKey(key))
				inner_product+= word_list_1.get(key) * word_list_2.get(key);
		}
		return inner_product;
	}

	private HashMap<String, Integer> word_frequencies_for_file(String filename) {
		String folder = "C:/Users/Karthik/Desktop/MIT Courseware/Course Material/lec02/";
		String line = null;
		HashMap<String,Integer> word_list = new HashMap();
		try{
			FileReader fileReader = new FileReader(folder+filename);
			BufferedReader br = new BufferedReader(fileReader);
			while((line = br.readLine())!=null)
			{
				if(line.length()>0)
				{
					String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
					for(String word: words)
					{
						if(word_list.containsKey(word))
							word_list.put(word, word_list.get(word)+1);
						else
							word_list.put(word,1);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return word_list;
	}

}
