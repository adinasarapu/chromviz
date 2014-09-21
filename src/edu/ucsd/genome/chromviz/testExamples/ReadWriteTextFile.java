package edu.ucsd.genome.chromviz.testExamples;

import java.io.*;

public class ReadWriteTextFile
{
	/**
	* Fetch the entire contents of a text file, and return it in a String.
	* This style of implementation does not throw Exceptions to the caller.
	*
	* @param aFile is a file which already exists and can be read.
	*
	*/
	static public String getContents(File aFile)
	{
		StringBuffer contents = new StringBuffer();
		try
		{
			//use buffering, reading one line at a time
			//FileReader always assumes default encoding is OK!
			BufferedReader input =  new BufferedReader(new FileReader(aFile));
			try
			{
				String line = new String(); 
				while (( line = input.readLine()) != null)
				{
					contents.append(line+"\n");
                                }
			}
                        finally
			{
				input.close();
                        }
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
                return contents.toString();
	}
}
