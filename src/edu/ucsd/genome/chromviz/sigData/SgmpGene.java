package edu.ucsd.genome.chromviz.sigData;


/** 
 * This container holds the rows from the sgmp.sgmp2gene table. 
 */

public class SgmpGene {

  // data
  public String entrezGeneName;
  public String species;
  public String sgmpID;
  public String entrezGeneID;
  public String entrezGeneSymbol;
  public String db;
  public String protAcc;
  public String spDisplayID;


 /**
  * Constructor holds 5 variables -- name, species and Entrez Gene details.
  * 
  *
  * @param nameVar		Entrez Gene Name
  * @param speciesVar   	Species name 
  * @param sgmpIdVar    	Entrez Gene ID 
  * @param entrezGeneIdVar	Entrez Gene Name
  * @param symbolVar    	Entrez Gene Symbol 
  * @param dbVar    		Protein Database 
  * @param protAccVar    	Protein Accession 
  * @param spDisplayIDVar    	SwissProt Entry ID 
  */
  public SgmpGene(String sgmpIdVar, String speciesVar,
		String entrezGeneIdVar, String nameVar, String symbolVar,
		String dbVar, String protAccVar, String spDisplayIDVar) {

    	entrezGeneName = nameVar;
    	species = speciesVar;
    	sgmpID = sgmpIdVar;
    	entrezGeneID = entrezGeneIdVar;
    	entrezGeneSymbol = symbolVar;
	db = dbVar;
	protAcc = protAccVar;
	spDisplayID = spDisplayIDVar;
  }
  
  	public String getEntrezGeneName()
  	{
		return entrezGeneName;
  	}
  	public String getSpecies()
  	{
		return species;
  	}
  	public String getSgmpID()
  	{
        	return sgmpID;
  	}
  	public String getEntrezGeneID()
  	{
        	return entrezGeneID;
  	}
  	public String getEntrezGeneSymbol()
  	{
        	return entrezGeneSymbol;
  	}
	public String getDb()
	{
		return db;
	}
	public String getProtAcc()
	{
		return protAcc;
	}
	public String getSpDisplayID()
	{
		return spDisplayID;
	}

/**
  * Checks validity of some parameters of SGMP Gene object 
  *
  * @return  False if sgmpID is null
  */
  public boolean validate() {
    if (sgmpID == null) return false;
    return true;
  }
}
