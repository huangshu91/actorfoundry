/**
 * @author Reza Shiftehfar
 * @version $Revision: 1.0 $ ($Date: 2012/12/22 $)
 */
package reza.rCloud;

import java.io.BufferedReader;
import java.io.FileReader;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Reza Shiftehfar
 * @version $Revision: 1.0 $ ($Date: 2012/12/22 $)
 */
public class Requester extends Actor {

	public Requester() {
	}
	
	/**
	 * fileToString(): Since ActorFoundry only sends String messages, if a file needs to be
	 *					sent to other Actors has to be converted to String first.
	 * fileName: name/path of the file to be converted to String
	 * returns String: the String representation of the fileName
	 *					returns empty string in case of an error
	 */
	public String fileToString(String fileName){
        BufferedReader inputStream = null;
		StringBuilder theFile = new StringBuilder(); 
        
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            String l;
            while ((l = inputStream.readLine()) != null) {
				theFile.append(l);
            }
        } catch (Exception e) {
        	return "";
        } finally {
            if (inputStream != null) {          	
                try {
					inputStream.close();
				} catch (Exception e) {
					return "";
				}
            }
        }
        
        return theFile.toString();					
	}

	@message
	public void start(ActorName platform) {
		send(stdout, "println", "Requester: sending of job Started... ");		
		
		//reading in the content of the mapper/reducer file:
		String fileName = "/Users/rezashiftehfar/My_Software/ActorFoundry_dist_ver/src/reza/rCloud/Mapper_Reducer_Classes_orig.java1";
		
		String hadoopMapperRedcuerFileContent = fileToString(fileName);
		if (!hadoopMapperRedcuerFileContent.isEmpty())
			send(platform, "hadoop_job_submitter"
								, "rCLOUD-HADOOP TEST JOB" 		// job name
								, "Mapper_Reducer_Classes" 		//mapper/reducer java file name
								, fileToString(fileName)		// the mapper/reducer file itself
								, "Mapper_Class" 				//mapper class
								, "Reducer_Class" 				//reducer class
								, "org.apache.hadoop.io.Text"		//MapOutputKey class
								, "org.apache.hadoop.io.IntWritable"//MapOutputValue class
								, "org.apache.hadoop.io.Text"		//outputKey class
								, "org.apache.hadoop.io.IntWritable"//outputValue class
								, "project_test/input"		//HDFS address of input files
								, "project_test/output"	    //HDFS address of output files
								, "true");					//true: wait for job to finish
	
		else 
			send(stdout, "println", "*** Requester Actor: Error reading content of the "+
									"mapper/reducer source file: "+fileName);							
		/*
		try{
			Thread.sleep(100000);
		} catch (Exception e) {
		}
		send(platform, "hadoop_job_isComplete", self(), "print_Status" ,"rCLOUD-HADOOP TEST JOB");
		*/
		send(stdout, "println", "Requester: sending of job Finished. ");		
	}
	
	@message
	public void print_Status(String status){
		send(stdout, "println", "Requester: status of isComplete is: "+status);				
	}

}
