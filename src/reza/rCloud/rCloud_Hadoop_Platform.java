 /**
  * @author Reza Shiftehfar
  * @version $Revision: 1.0 $ ($Date: 2012/12/22 $)
  */
package reza.rCloud;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 *     Platform.java is part of the rCloud framework. 
 *	   It required distributed version of ActorFoundry to compile/work.
 *     It provides interaction with the Hadoop cluster. 
 *     Required Hadoop settings are stored in config/rCloud_hadoop_config.properties  
 *
 * @author Reza Shiftehfar
 * @version $Revision: 1.0 $ ($Date: 2012/12/22 $)
 */
public class rCloud_Hadoop_Platform extends Actor {

//This is the location of the rCloud source folder within ActorFoundry directory:
final String RCLOUD_SOURCE_FOLDER= 
							java.io.File.separator  +
							"Users"					+ java.io.File.separator + 
							"rezashiftehfar"		+ java.io.File.separator + 
							"My_Software"			+ java.io.File.separator + 
							"ActorFoundry_dist_ver"	+ java.io.File.separator + 
							"src"					+ java.io.File.separator + 
							"reza"					+ java.io.File.separator + 
							"rCloud";

	//Default Constructor:
	public rCloud_Hadoop_Platform() {
	}

	/**
	 * initialize() method sets the required configuration parameters
	 * 		the required settings are stored in HADOOP_CONFIG_FILE
	 *
	 * returns true in case of success, otherwise returns false
	 */
	public boolean initialize(){
		FileReader reader = null;
		try {
			reader = new FileReader(HADOOP_CONFIG_FILE);
			Properties properties = new Properties();
			properties.load(reader);
			HADOOP_SOURCE_FOLDER = 	properties.getProperty("hadoop.source.folder");
			HADOOP_SOURCE_CORE_JAR 	 =  HADOOP_SOURCE_FOLDER + java.io.File.separator +
										properties.getProperty("hadoop.source.core.jar");
			HADOOP_SOURCE_CLI_JAR 	 =  HADOOP_SOURCE_FOLDER + java.io.File.separator +
										properties.getProperty("hadoop.source.cli.jar");
			HADOOP_SOURCE_LOGGING_JAR=  HADOOP_SOURCE_FOLDER + java.io.File.separator +
										properties.getProperty("hadoop.source.logging.jar");																
			HADOOP_EXECUTION_JARS 	 = 	HADOOP_SOURCE_CORE_JAR + "," +
										HADOOP_SOURCE_CLI_JAR  + "," +
										HADOOP_SOURCE_LOGGING_JAR ;
			HADOOP_FS_DEFAULT_NAME	 =	properties.getProperty("hadoop.fs.default.name");
			HADOOP_MAPRED_JOB_TRACKER=	properties.getProperty("hadoop.mapred.job.tracker");
			
			
		} catch (IOException ex) {
			return (initialized = false);	
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					return (initialized = false);
				}
		}		
		
		return (initialized = true);	
	}
	
    /** deleteDir(): Deletes all files and subdirectories under dir.
     *
     *  Returns true if all deletions were successful.
     *  If a deletion fails, the method stops attempting to delete and returns false.
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }   
        // The directory is now empty so delete it
        return dir.delete();
    }	
	
	/**
	 * compile(): This method receives a className and compiles the className.java file
	 *            and stores the result in the. 
	 *			  the log will be stored in JOB_FOLDER/log/error.txt
	 *
	 * className: Name of the class to be compiled
	 * returns true in case of success and false otherwise
	 */
	private boolean compile(String className) {
		//first delete the previously created classes ,if any:
		deleteDir(new File(HADOOP_JOB_COMPILED_OUTPUT_FOLDER_CLASSES));
		//next, create a new empty classes/ folder:
		new File(HADOOP_JOB_COMPILED_OUTPUT_FOLDER_CLASSES).mkdirs();
		//also create the error/ folder, if it doesn't exist:
		new File(HADOOP_JOB_COMPILATION_ERROR_LOG_FOLDER).mkdirs();

		//Finally, compile the given className:
		String fileToCompile =  RCLOUD_SOURCE_FOLDER	+ java.io.File.separator +  
								className+".java";

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		FileOutputStream errorStream = null;
		
		try{
			errorStream = new FileOutputStream(
									HADOOP_JOB_COMPILATION_ERROR_LOG_FOLDER +  
									java.io.File.separator + 
									"Errors.log");
									
		} catch(FileNotFoundException e){ 
			//if problem creating the file, default will be console
			//it will automatically set to null and no action is needed 
		}	
		
		// if we set the third parameter which is the error outputstream to null,
		//		it would be directed to the default system.err which is the console
		// 		we can also use "-verbose" to print out the compilation messages
		int compilationResult = 
				compiler.run(	null, null, errorStream, 
								"-classpath", HADOOP_SOURCE_CORE_JAR,
								"-d", HADOOP_JOB_COMPILED_OUTPUT_FOLDER_CLASSES,
								fileToCompile);
		if (compilationResult == 0) {
			//Compilation is successful:
			return true;
		} else {
			//Compilation Failed:
			return false;
		}	
	}
	
	/**
	 * add(): 	this method works as part of the make_JAR() method. Given a class file, 
	 *			it adds it to the target file. 
	 *			This way, multiple classes can be JARed into one output file.
	 *			in order to keep the structure of the JAR file, all files are structured
	 *			with respect to the classPathStartingFolder. This must match the package
	 *			name used within the file.
	 */
	private void add(File source, String classPathStartingFolder, JarOutputStream target) 
				throws IOException	{
				
	  BufferedInputStream in = null;
	  try
	  {
	    if (source.isDirectory())
	    {
	      String name = source.getPath().replace("\\", "/");	      
	      //remove the path before the classPathStartingFolder
	      if (!classPathStartingFolder.endsWith("/"))
	    		classPathStartingFolder += "/";	  
	      classPathStartingFolder = classPathStartingFolder.replace("\\", "/");
	      int locStartingFolderChar = name.indexOf(classPathStartingFolder);
	      name = name.substring(locStartingFolderChar + classPathStartingFolder.length());
	      
	      if (!name.isEmpty())
	      {
	        if (!name.endsWith("/"))
	          name += "/";
	        JarEntry entry = new JarEntry(name);
	        entry.setTime(source.lastModified());
	        target.putNextEntry(entry);
	        target.closeEntry();
	      }
	      for (File nestedFile: source.listFiles())
	        add(nestedFile, classPathStartingFolder, target);
	      return;
	    }

		String fileName = source.getPath().replace("\\", "/");
        //remove the path before the classPathStartingFolder
	    if (!classPathStartingFolder.endsWith("/"))
	    	classPathStartingFolder += "/";	  
	    classPathStartingFolder = classPathStartingFolder.replace("\\", "/");
	    int locStartingFolderChar = fileName.indexOf(classPathStartingFolder);
	    fileName = fileName.substring(locStartingFolderChar + classPathStartingFolder.length());

	    JarEntry entry = new JarEntry(fileName);
	    entry.setTime(source.lastModified());
	    target.putNextEntry(entry);
	    in = new BufferedInputStream(new FileInputStream(source));

	    byte[] buffer = new byte[1024];
	    while (true)
	    {
	      int count = in.read(buffer);
	      if (count == -1)
	        break;
	      target.write(buffer, 0, count);
	    }
	    target.closeEntry();
	  }
	  finally
	  {
	    if (in != null)
	      in.close();
	  }
	}
	
	/**
	 * makeJAR(): this method receives a className and makes a JAR file out of it
	 *			  It requires all the classes to be compiled before.
	 * 
	 * className: the name of the class to be JARed
	 * inputDirectory: the location of the className file(s)
	 * returns true in case of success. otherwise returns false
	 */
	private boolean makeJAR(String className, String outputDirectory) {
		//create the jar/ folder, if it doesn't exist:
		new File(HADOOP_JOB_COMPILED_OUTPUT_FOLDER_JAR).mkdirs();
	
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
				
		JarOutputStream target = null;						
		try {		
			target = new JarOutputStream(
						new FileOutputStream(
									HADOOP_JOB_COMPILED_OUTPUT_FOLDER_JAR 
									+ java.io.File.separator 
									+ className+".jar"
											)
						, manifest);
			add(new File(HADOOP_JOB_COMPILED_OUTPUT_FOLDER_CLASSES), 
					 HADOOP_JOB_COMPILED_OUTPUT_FOLDER_CLASSES, target);
		} catch (Exception e) { //in case of any error simply return false
			return false;
		} finally {
		    if (target != null)
		    	try{
		    		target.close();
		    	} catch (Exception e) { 
		    		return false; 
		    	}
		}
		
		return true;
	}
	
	/**
	 * run_Haoop_Job(): This method submits a Hadoop job to Hadoop cluster
	 *
	 * className: Name of the className which is also the name of the JAR file
	 *				e.g. Mapper_Reducer_Classes
	 * mapperClassName: Name of the mapper class defined in the className.java file
	 *					Note that both Mapper and Reducers are defined in one java file
	 *					e.g. a mapper name of Mapper_Class is equal to 
	 *						 reza.rCloud.Mapper_Reducer_Classes$Mapper_Class
	 * reducerClassName: Name of the Reducer class defined in the className.java file.
	 * mapOutputKeyClass, mapOutputValueClass, outputKeyClass, outputValueClass: 
	 *					type of the hadoop output key/values.
	 *									  e.g. org.apache.hadoop.io.IntWritable
	 * inputPath, outputPath: HDFS path of the input and output folders
	 * waitForJobToFinish: This specifies whether :
	 *						False: just submit the job and return without any waiting
	 *						True: submit the job and wait for it to finish
	 *								
	 * returns true if everything goes OK, otherwise returns false
	 */
	private boolean run_Hadoop_Job(	String jobName,
									String className, 
									String mapperClassName,	String reducerClassName,
									String mapOutputKeyClass, String mapOutputValueClass,									
									String outputKeyClass, String outputValueClass,
									String inputPath, String outputPath,
									boolean waitForJobToFinish){
		try{

			// create a configuration
			Configuration conf = new Configuration();
			conf.set("fs.default.name", HADOOP_FS_DEFAULT_NAME);
			conf.set("mapred.job.tracker", HADOOP_MAPRED_JOB_TRACKER);
			conf.set("mapred.jar", HADOOP_JOB_COMPILED_OUTPUT_FOLDER_JAR
										+ java.io.File.separator 
										+ className+".jar");
			conf.set("libjars", HADOOP_EXECUTION_JARS);
			
			//setting the names of the mapper and reducer in the className file
			//Mapper and Reducer has to be in one file 
			//(e.g. reza.rCloud.Mapper_Reducer_Classes$Mapper_Class
			// or as most of the time no packaging is used: Mapper_Reducer_Classes$Mapper_Class)
			conf.set("mapreduce.map.class", mapperClassName);
    		conf.set("mapreduce.reduce.class", reducerClassName);
    			
			// create a new job based on the configuration
			Job job = new Job(conf, jobName);
			job.setJarByClass(this.getClass());

			// key/value of your reducer output
			job.setMapOutputKeyClass(Class.forName(mapOutputKeyClass));
			job.setMapOutputValueClass(Class.forName(mapOutputValueClass));
			job.setOutputKeyClass(Class.forName(outputKeyClass));
			job.setOutputValueClass(Class.forName(outputValueClass));
		
			FileInputFormat.addInputPath(job, new Path(inputPath));
			// this deletes possible output paths to prevent job failures
			FileSystem fs = FileSystem.get(conf);
			Path out = new Path(outputPath);
			fs.delete(out, true);
			// finally set the empty out path
			FileOutputFormat.setOutputPath(job, new Path(outputPath));

			if (waitForJobToFinish) 
				return (job.waitForCompletion(true));
			else {
				job.submit();
				return true;
			}
		} catch (Exception e) { 		
			return false; 
		}
	}
	
	
	//This method is not working correctly!!!!!!!
	@message
	public void hadoop_job_isComplete(ActorName theClient, String theMethod, String jobName){
		try{
			Configuration conf = new Configuration();
			conf.set("fs.default.name", HADOOP_FS_DEFAULT_NAME);
			conf.set("mapred.job.tracker", HADOOP_MAPRED_JOB_TRACKER);
			Job job = new Job(conf, jobName);	
			if (job.isComplete())
				send(theClient, theMethod, "true");	
			else
				send(theClient, theMethod, "false");	
		} catch (Exception e) {
				e.printStackTrace();
				send(theClient, theMethod, "false");			
		}
	}	
	
	/**
	 *  hadoop_job_submitter(): this is the method that other actors can call to send a file
	 *						 containing the Mapper/Reducer classes, specify the required
	 *						 settings and submit the job to hadoop for running
	 *
	 * className: Name of the className which is also the name of the JAR file
	 *				e.g. Mapper_Reducer_Classes
	 * mapperClassName: Name of the mapper class defined in the className.java file
	 *					Note that both Mapper and Reducers are defined in one java file
	 *					e.g. a mapper name of Mapper_Class is equal to 
	 *						 reza.rCloud.Mapper_Reducer_Classes$Mapper_Class
	 *					Note that since both mapper and reducer classes has to be defined
	 *						 in a single file called (className.java), the full names of 
	 *						 mapper and reducer classes to be used are:
	 *						 className$mapperClassName    & className$reducerClassName
	 * reducerClassName: Name of the Reducer class defined in the className.java file.
	 * mapOutputKeyClass, mapOutputValueClass, outputKeyClass, outputValueClass: 
	 *					type of the hadoop output key/values.
	 *									  e.g. org.apache.hadoop.io.IntWritable
	 * inputPath, outputPath: HDFS path of the input and output folders
	 * waitForJobToFinish: This specifies whether :
	 *						False: just submit the job and return without any waiting
	 *						True: submit the job and wait for it to finish
	 */	
	@message
	public void hadoop_job_submitter(	
									String jobName,
									String className, 
									String theJavaSourceFile,
									String mapperClassName,	String reducerClassName,
									String mapOutputKeyClass, String mapOutputValueClass,									
									String outputKeyClass, String outputValueClass,
									String inputPath, String outputPath,
									String waitForJobToFinish){
		if (!initialized)
			initialize();
		
		//Continue only if the Hadoop configuration is successfully initialized:
		if (initialized) {
		
			//step 0: copy the received mapper/reducer source java file to disk:
		        PrintWriter outputStream = null;
		        try {
					outputStream = new PrintWriter(new FileWriter(
									RCLOUD_SOURCE_FOLDER 	+ java.io.File.separator +
									className 				+ ".java"));
					outputStream.println(theJavaSourceFile);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
		            if (outputStream != null) {
        		        outputStream.close();
            		}
				}
			System.out.println("File stored to disk");
			
			//step 1: compile the received class file dynamically:
			send(stdout, "println", "  * Platform Actor: " + 
										"compiling the Hadoop class files...");
			boolean compResult = compile(className);	

			//step 2: make a JAR file out of the compiled file:
			if (compResult) {
				send(stdout, "println", "  * Platform Actor: " + 
											"compiling the Hadoop class files finished");
				send(stdout, "println", "  * Platform Actor: " + 
											"Making the class JAR file...");
				compResult	= makeJAR(className, HADOOP_JOB_COMPILED_OUTPUT_FOLDER_JAR);
			}
			
			//step 3: Now let's run the Hadoop job:
			if (compResult) {
				send(stdout, "println", "  * Platform Actor: " + 
											"Making the class JAR file finished");											
				send(stdout, "println", "  * Platform Actor: " + 
											"Submitting the job to Hadoop...");
				boolean tempBool = false;
				if (waitForJobToFinish.equalsIgnoreCase("TRUE"))
					tempBool = true;
				compResult = run_Hadoop_Job(jobName, className,
													 className + "$" + mapperClassName, 
													 className + "$" + reducerClassName,
													 mapOutputKeyClass, 
													 mapOutputValueClass,
													 outputKeyClass, 
													 outputValueClass,
											inputPath, 
											outputPath,
											tempBool);

			}
			
			if (compResult) 
				send(stdout, "println", "  * Platform Actor: " + 
											"Job successfully submitted to Hadoop");			
			else 	//if any of the previous steps failed:
				send(stdout, "println", "**** Platform Actor: " + 
											"ERROR in preparing the given Hadoop job");	
																				
		} else {  //if initialization of hadoop configuration file failed:
				send(stdout, "println", "*** Platform Actor: Error reading " + 
											 "the required rCloud-Hadoop " + 
										     "configuration file: " + HADOOP_CONFIG_FILE);
				send(stdout, "println", "*** Plaftorm Actor: Platform Actor " + 
											 "won't continue with the Hadoop request");
		}	

	}


//these will be set from the values stored in the rCloud_Hadoop_Config.properties:
private String 	HADOOP_SOURCE_FOLDER, HADOOP_SOURCE_CORE_JAR, HADOOP_SOURCE_CLI_JAR, 
				HADOOP_SOURCE_LOGGING_JAR, HADOOP_EXECUTION_JARS, HADOOP_FS_DEFAULT_NAME,
				HADOOP_MAPRED_JOB_TRACKER;

//the following parameters do not need to be modified:
final String HADOOP_CONFIG_FILE = 	
							RCLOUD_SOURCE_FOLDER 	+ java.io.File.separator + 
							"config" 				+ java.io.File.separator + 
							"rCloud_Hadoop_Config.properties";

//These are default folder names for storing the temp/output results:
final String HADOOP_JOB_COMPILED_OUTPUT_FOLDER_CLASSES = 
							RCLOUD_SOURCE_FOLDER	+ java.io.File.separator +
							"classes";
final String HADOOP_JOB_COMPILED_OUTPUT_FOLDER_JAR =
							RCLOUD_SOURCE_FOLDER	+ java.io.File.separator +
							"jars";
final String HADOOP_JOB_COMPILATION_ERROR_LOG_FOLDER =
							RCLOUD_SOURCE_FOLDER	+ java.io.File.separator +
							"logs";
							
//this shows whether Hadoop required configurations are successfully configured or not:							
private boolean initialized = false;
}
