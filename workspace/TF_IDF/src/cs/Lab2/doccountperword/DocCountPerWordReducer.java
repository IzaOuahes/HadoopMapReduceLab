package cs.Lab2.doccountperword;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;


public class DocCountPerWordReducer extends Reducer<Text, Text, Text, Text> {
	TreeSet<Pair> topWordsPair = new TreeSet<>();
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		//System.out.println(key);
		ArrayList<String> words = new ArrayList<String>();    	  
		for (Text val : values) {  
			words.add(val.toString());
			// System.out.println(val.toString()); 
		}

		for(int i=0;i<words.size();i++){

			String [] tmp = words.get(i).split("\t");

			context.write(new Text(key+"\t"+tmp[0]), new Text(tmp[1]+"\t"+tmp[2]+"\t"+words.size()));


			double tfidf = (Double.parseDouble(tmp[1])/Double.parseDouble(tmp[2]))*Math.log(2.0/words.size());

			topWordsPair.add(new Pair(tfidf, key.toString(), tmp[0]));

			if (topWordsPair.size() > 20) {
				topWordsPair.pollFirst();

				//we delete the first pair with the lowest frequency
			}

		}


	}

	protected void cleanup(Context ctxt) throws IOException,InterruptedException {
		//we call this fonction once at the end
		while (!topWordsPair.isEmpty()) {
			Pair pair = topWordsPair.pollLast();
			System.out.println("["+pair.getWord()+"\t"+ pair.getDoc()+"] : " + pair.frequency);
		}
	}

}