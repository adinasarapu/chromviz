package edu.ucsd.genome.chromviz.sigUtility;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import edu.ucsd.genome.chromviz.sigData.KnownGeneNew;
import edu.ucsd.genome.chromviz.sigSql.KnownGeneListDAO;

public class TestClass {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, Exception {
		Map<String, List<String>> kg = KnownGeneListDAO.getLocusWithKnownGeneListWithMapID("mmu04144");
		Map<String, KnownGeneNew> kgMap = KnownGeneListDAO.getAllKnownGenesMap();
		Iterator<String> it = kg.keySet().iterator();
		while(it.hasNext()){
			String lid = it.next();
			System.out.print(lid);
			List<String> kgids = kg.get(lid);
			Iterator<String> it2 = kgids.iterator();
			while(it2.hasNext()){
				String kgid = it2.next();
				KnownGeneNew kgn = kgMap.get(kgid);
				if(kgn != null){
					System.out.println("\t\t\t\t\t"+kgid+"\t"+kgn.getKgID()+"\t"+kgn.getCdsStartPosition()+"\t"+kgn.getCdsEndPosition()+"\t"+kgn.getProteinID());
				}else{
					System.out.println("\t\t\t\t\t"+kgid+"\tNo details");
				}
			}
			System.out.println();
		}
	}

}
