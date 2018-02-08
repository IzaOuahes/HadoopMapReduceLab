package cs.Lab2.wordcountperdoc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class WordCountPerDocMapper extends Mapper<LongWritable, Text, Text, Text> {
	private final static IntWritable ONE = new IntWritable(1);


	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] words =  value.toString().split("\t");
		/* System.out.println(words[0]);
       System.out.println(words[1]);
       System.out.println(words[2]);
		 */

		context.write(new Text(words[1]), new Text(words[0]+"\t"+words[2]));
	}

}
