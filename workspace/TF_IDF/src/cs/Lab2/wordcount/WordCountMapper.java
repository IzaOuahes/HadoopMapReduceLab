package cs.Lab2.wordcount;


import org.apache.hadoop.mapreduce.Job;  
import org.apache.hadoop.io.*;        
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

// To complete according to your problem
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private static Set<String> googleStopwords;

	static {
		googleStopwords = new HashSet<String>();
		googleStopwords.add("I"); googleStopwords.add("a");
		googleStopwords.add("about"); googleStopwords.add("an");
		googleStopwords.add("are"); googleStopwords.add("as");
		googleStopwords.add("at"); googleStopwords.add("be");
		googleStopwords.add("by"); googleStopwords.add("com");
		googleStopwords.add("de"); googleStopwords.add("en");
		googleStopwords.add("for"); googleStopwords.add("from");
		googleStopwords.add("how"); googleStopwords.add("in");
		googleStopwords.add("is"); googleStopwords.add("it");
		googleStopwords.add("la"); googleStopwords.add("of");
		googleStopwords.add("on"); googleStopwords.add("or");
		googleStopwords.add("that"); googleStopwords.add("the");
		googleStopwords.add("this"); googleStopwords.add("to");
		googleStopwords.add("was"); googleStopwords.add("what");
		googleStopwords.add("when"); googleStopwords.add("where");
		googleStopwords.add("who"); googleStopwords.add("will");
		googleStopwords.add("with"); googleStopwords.add("and");
		googleStopwords.add("the"); googleStopwords.add("www");
	}

	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	// Overriding of the map method
	@Override

	protected void map(LongWritable keyE, Text valE, Context context) throws IOException,InterruptedException
	{

		// Get the name of the file from the inputsplit in the context
		String filename = ((FileSplit) context.getInputSplit()).getPath().getName().toString();



		// To complete according to the processing
		// Processing : keyI = ..., valI = ...
		String line = valE.toString();
		line  = line.replaceAll("\\.","");
		line  = line.replaceAll("\\,","");
		line  = line.replaceAll("\\:","");
		line  = line.replaceAll("\\;","");
		line  = line.replaceAll("\\'","");
		line  = line.replaceAll("\\?","");
		line  = line.replaceAll("\"","");

		StringTokenizer tokenizer = new StringTokenizer(line);



		while(tokenizer.hasMoreTokens())
		{
			String tmp =tokenizer.nextToken();
			if (! googleStopwords.contains(tmp)){


				word.set(tmp);
				//context.write(word, one);
				context.write(new Text(word+"\t"+filename), one);

			}
		}

	}

	public void run(Context context) throws IOException, InterruptedException {
		setup(context);
		while (context.nextKeyValue()) {
			map(context.getCurrentKey(), context.getCurrentValue(), context);
		}
		cleanup(context);
	}

}






