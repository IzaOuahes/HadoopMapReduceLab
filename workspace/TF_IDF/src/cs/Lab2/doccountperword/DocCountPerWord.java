package cs.Lab2.doccountperword;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

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
import cs.Lab2.wordcountperdoc.WordCountPerDoc;

public class DocCountPerWord extends Configured implements Tool {
	public static Path tfidfword =null  ;


	@Override
	public int run(String[] args) throws Exception {
		System.out.println(Arrays.toString(args));
		Configuration configuration = this.getConf();


		Job job = new Job(configuration, "DocCountPerWord");
		job.setNumReduceTasks(1);
		job.setJarByClass(DocCountPerWord.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(DocCountPerWordMapper.class);
		job.setReducerClass(DocCountPerWordReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);


		//Path outputtfidfwordcountperdoc = new Path(args[0]);
		//Path tfidfword = new Path(args[1]);

		FileInputFormat.addInputPath(job,WordCountPerDoc.outputtfidfwordcountperdoc);
		//FileOutputFormat.setOutputPath(job, tfidfword);

		//FileInputFormat.addInputPath(job, new Path("outputtfidfwordcountperdoc"));
		FileOutputFormat.setOutputPath(job, new Path("tfidfword"));
		FileSystem hdfs = FileSystem.get(getConf());
		if (hdfs.exists(new Path("tfidfword")))
			hdfs.delete(new Path("tfidfword"), true);
		job.waitForCompletion(true);

		return 0;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Arrays.toString(args));
		int res1 = ToolRunner.run(new Configuration(), new WordCount(), args);
		int res2 = ToolRunner.run(new Configuration(), new WordCountPerDoc(), args);
		int res3 = ToolRunner.run(new Configuration(), new DocCountPerWord(), args);

		System.exit(res3);
	}

}