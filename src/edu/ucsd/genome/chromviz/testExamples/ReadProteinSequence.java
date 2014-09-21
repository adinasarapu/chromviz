package edu.ucsd.genome.chromviz.testExamples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReadProteinSequence {
	public ReadProteinSequence() {}
	public void getSequence()
	{
		StringBuffer buffer =null;// new StringBuffer();
		String line = null;
		String geneId = null;
		Map<String,StringBuffer> map = new HashMap<String,StringBuffer>();
		try {
			BufferedReader input = new BufferedReader(new FileReader("C:/Users/ashok/Desktop/sgmpseq.txt"));
			while((line = input.readLine()) != null)
			{	
				 buffer = new StringBuffer();
				if(line.substring(0,1).equals(">"))
				{	
					String[] str =line.split(":");
					geneId = str[1].substring(0,7);
					//System.out.println("Sequence ==> "+buffer);
					System.out.println("geneID ===>" +geneId);
					//map.put(geneId,buffer);
					//buffer = new StringBuffer();
				}
				if(!(line.substring(0,1).equals(">")))
				{						
					char ch[] = line.toCharArray();
					for(int j = 0; j < line.length(); j++)
					{
						buffer.append(ch[j]);
					}//for
					StringBuffer seq = new StringBuffer();
					seq.append(buffer);
						System.out.println("Sequence ==> "+seq);
						map.put(geneId,seq);
					
					}//if
				
			}//while
			
			input.close();
		}//try
		catch(IOException ie)
		{
			ie.printStackTrace();
		}
		//System.out.println("Sequence ==> "+buffer);
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key = it.next();
			System.out.println("Key ===>"+key);
			System.out.println("Value ===>" + map.get(key));
		}
		
	}

	public static void main(String[] args) {
		
ReadProteinSequence seq = new ReadProteinSequence();
seq.getSequence();
	}

}
