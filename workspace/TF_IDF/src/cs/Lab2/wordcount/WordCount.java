package cs.Lab2.wordcount;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;


public class WordCount extends Configured implements Tool {
	//final ne peut pas être modifié
	public static Path inputFilePath =null  ;
	public static Path outputtfidfwordcount =null  ;

	public int run(String[] args) throws Exception {

		if (args.length != 2) {

			System.out.println("Usage: [input] [output]");

			System.exit(-1);

		}


		// Création d'un job en lui fournissant la configuration et une description textuelle de la tâche

		Job job = Job.getInstance(getConf());

		job.setJobName("WordCount");


		// On précise les classes MyProgram, Map et Reduce

		job.setJarByClass(WordCount.class);

		job.setMapperClass(WordCountMapper.class);

		job.setReducerClass(WordCountReducer.class);


		// Définition des types clé/valeur de notre problème

		job.setMapOutputKeyClass(Text.class);

		job.setMapOutputValueClass(IntWritable.class);


		job.setOutputKeyClass(TextInputFormat.class);

		job.setOutputValueClass(TextOutputFormat.class);

		WordCount.inputFilePath  = new Path(args[0]);
		WordCount.outputtfidfwordcount = new Path(args[1]);


		// On accepte une entree recursive
		FileInputFormat.setInputDirRecursive(job, true);

		FileInputFormat.addInputPath(job, inputFilePath);
		FileOutputFormat.setOutputPath(job, outputtfidfwordcount);

		FileSystem fs = FileSystem.newInstance(getConf());

		if (fs.exists(outputtfidfwordcount)) {
			fs.delete(outputtfidfwordcount, true);
		}
		job.waitForCompletion(true);
		return 0;
	}


	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new WordCount(), args);

		System.exit(res);

	}

}

