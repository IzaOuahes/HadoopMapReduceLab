package cs.Lab2.wordcountperdoc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class WordCountPerDocReducer extends Reducer<Text, Text, Text, Text> {
	public  WordCountPerDocReducer () {
	}
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		HashMap<String, String> hm = new HashMap<String,String>();
		int countWordsPerDoc = 0;

		for (Text val : values) {
			String[] words =  val.toString().split("\t");

			//System.out.println(words[0]);
			int wordCount = Integer.parseInt(words[1]);
			hm.put(words[0]+"\t"+key.toString(), ""+wordCount);

			countWordsPerDoc +=wordCount;

		}


		Set set2 = hm.entrySet();
		Iterator iterator2 = set2.iterator();
		while(iterator2.hasNext()) {
			Entry mentry2 = (Entry)iterator2.next();

			context.write(new Text(mentry2.getKey().toString()), new Text(mentry2.getValue()+"\t"+countWordsPerDoc));

		}




	}
}