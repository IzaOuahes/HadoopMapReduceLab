package cs.Lab2.wordcountperdoc;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cs.Lab2.wordcount.WordCount;



public class WordCountPerDoc extends Configured implements Tool {

	public static Path outputtfidfwordcountperdoc =null  ;


	@Override
	public int run(String[] args) throws Exception {
		System.out.println(Arrays.toString(args));
		Configuration configuration = this.getConf();


		Job job = new Job(configuration, "WordCounPerDoct");
		job.setNumReduceTasks(1);
		job.setJarByClass(WordCountPerDoc.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(WordCountPerDocMapper.class);
		job.setReducerClass(WordCountPerDocReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);


		//Path outputtfidfwordcount = new Path(args[0]);
		//Path outputtfidfwordcountperdoc = new Path(args[1]);


		FileInputFormat.addInputPath(job, WordCount.outputtfidfwordcount);
		//FileOutputFormat.setOutputPath(job, outputtfidfwordcountperdoc);


		//FileInputFormat.addInputPath(job, new Path("outputtfidfwordcount"));
		FileOutputFormat.setOutputPath(job, new Path("outputtfidfwordcountperdoc"));

		FileSystem hdfs = FileSystem.get(getConf());
		if (hdfs.exists(new Path("outputtfidfwordcountperdoc")))
			hdfs.delete(new Path("outputtfidfwordcountperdoc"), true);
		job.waitForCompletion(true);

		return 0;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Arrays.toString(args));
		int res = ToolRunner.run(new Configuration(), new WordCountPerDoc(), args);

		System.exit(res);
	}


}