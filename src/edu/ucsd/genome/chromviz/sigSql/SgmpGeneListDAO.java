package edu.ucsd.genome.chromviz.sigSql;

import java.util.*;
import java.io.IOException;
import java.sql.*;

import edu.ucsd.genome.chromviz.sigDBConnection.DatabaseConnection;
import edu.ucsd.genome.chromviz.sigData.SgmpGene;


	/**
	*	The Class <b>SgmpGeneList</b> queries the sgmp2gene table of sgmp database
	*	and it creates list of <b>SgmpGene</b> instances from each row of database table.
	*
	*	@author	Ashok Reddy, Srujana
	*/

public class SgmpGeneListDAO
{
	
	private static final String SELECT_COLUMNS_FROM_SGMPGENE_WITH_SGMPID;
	private static final String SELECT_COLUMNS_FROM_SGMPGENE_AND_SGMPPROT;
	private static final String SELECT_SGMPID_FROM_SGMP2GENE_WITH_GENESYMBOL;
 
	static
	{	
	StringBuffer query = new StringBuffer(512);
		query.append("\n");
		query.append("SELECT	sg.sgmpID,\n");				// 1
		query.append("		sg.species,\n");			// 2 
		query.append("		sg.entrezGeneID,\n");			// 3 
		query.append("		sg.entrezGeneName,\n");			// 4 
		query.append("		sg.entrezGeneSymbol,\n");		// 5 
		query.append("		sp.db,\n");				// 6 
		query.append("		sp.protAcc,\n");			// 7 
		query.append("		sp.spDisplayID\n");			// 8 
		query.append("	FROM	sgmp2gene sg, sgmp2prot sp\n");
		query.append("	WHERE	");
		int commonSqlLength = query.length();
		
		query.append("	sg.sgmpID = sp.sgmpID");
		query.append("	AND		");
		query.append("	sg.sgmpID=?");
		SELECT_COLUMNS_FROM_SGMPGENE_WITH_SGMPID = query.toString();
		
		query.delete(commonSqlLength,query.length());
		query.append("	sg.sgmpID = sp.sgmpID");
		SELECT_COLUMNS_FROM_SGMPGENE_AND_SGMPPROT = query.toString();
		
		
	}
	static
	{
	StringBuffer query = new StringBuffer(512);
	query.append("\n");
	query.append("SELECT	sgmpID\n");				// 1
	query.append("	FROM	sgmp2gene\n");
	query.append("	WHERE	");
	query.append("	entrezGeneSymbol=?");
	SELECT_SGMPID_FROM_SGMP2GENE_WITH_GENESYMBOL = query.toString();
	}
 	
	/**
	*	This method, getAllSgmpGenes(), returns a List of <b>SgmpGene</b>instances. It first checks the db connection
	*	and obtains user name and password from  DatabaseConnection instance.
	*
	*       @return List of SgmpGenes instance
	*
	*	@throws Exception
	*
	*       @throws SQLException, SQLException if connection is null
	*/     

	public static List<SgmpGene> getAllSgmpGenes() throws Exception
        {
                ResultSet rs = null;
                Connection conn = null;
                Statement st = null;
		        List<SgmpGene> listRow = new ArrayList<SgmpGene>();
                try
		{
                	conn = DatabaseConnection.getConnection("chromviz");
                	st = conn.createStatement();
                        rs = st.executeQuery(SELECT_COLUMNS_FROM_SGMPGENE_AND_SGMPPROT);
                        while (rs.next())
                        {
                		listRow.add(buildSgmpGeneFromResultSetRow(rs));
                        }
                }
                catch(SQLException e)
                {
                	System.err.println ("Error message 1: " + e.getMessage ());
                        System.err.println ("Error number 1: " + e.getErrorCode ());
                }
                finally
                {
                	rs.close();
                        st.close();
                        //DatabaseConnection.closeConnection();
                        conn.close();
                }
                return listRow;
        } // method getAllSgmpGenes
                //

	/**
	*     This method, getSgmpGeneInfo(), returns a <b>SgmpGene</b>instance. It first checks the db connection
	*     and obtains user name and password from DatabaseConnection instance.
	*
	*	@return	SgmpGene instance
	*	
	*	@throws	Exception
	*
	*	@throws	Exception, SQLException if connection is null
	*/
	

	
	public static SgmpGene getSgmpGeneInfo(String gid) throws Exception
	{
		ResultSet rs = null;
		//DatabaseConnection dataConnection = null;
  		Connection conn = null;
		PreparedStatement pst = null;
		SgmpGene sg = null;
		try 
		{
			conn = DatabaseConnection.getConnection("chromviz");
			pst = conn.prepareStatement(SELECT_COLUMNS_FROM_SGMPGENE_WITH_SGMPID);
			pst.setString(1,gid.trim());
        		rs = pst.executeQuery();
			while(rs.next())
			{
				sg = buildSgmpGeneFromResultSetRow(rs);	
			}
   		}
		catch(SQLException e)
		{
			System.err.println ("Error message 2: " + e.getMessage ());
       			System.err.println ("Error number 2: " + e.getErrorCode ());
			e.printStackTrace();
   		}
		finally
		{
			rs.close();
			pst.close();
            conn.close();
		}
		return sg;
	} // method getSgmpGene

	/**
        *     This method returns <b>SgmpGene</b>instance for each row
        *
        *       @return SgmpGene instance
	 * @throws Exception 
	 * @throws IOException 
	*
        *       @throws SQLException if connection is null
        */
	public static String getSgmpIDWithGeneSymbol(String geneSymbol) throws Exception {
		ResultSet rs = null;
		//DatabaseConnection dataConnection = null;
  		Connection conn = null;
		PreparedStatement pst = null;
		String ret = null;
		try 
		{
			conn = DatabaseConnection.getConnection("chromviz");
			pst = conn.prepareStatement(SELECT_SGMPID_FROM_SGMP2GENE_WITH_GENESYMBOL);
			pst.setString(1,geneSymbol);
        		rs = pst.executeQuery();
			while(rs.next())
			{
				ret = rs.getString(1);	
			}
   		}
		catch(SQLException e)
		{
			System.err.println ("Error message 2: " + e.getMessage ());
       			System.err.println ("Error number 2: " + e.getErrorCode ());
			e.printStackTrace();
   		}
		finally
		{
			rs.close();
			pst.close();
            conn.close();
		}
		return ret;
	}

	private static SgmpGene buildSgmpGeneFromResultSetRow(ResultSet rs)
		throws SQLException
	{
		SgmpGene sg = new SgmpGene(
			rs.getString(1),
			rs.getString(2),
			rs.getString(3),
			rs.getString(4),
			rs.getString(5),
			rs.getString(6),
			rs.getString(7),
			rs.getString(8));
		return sg;
	} // method buildSgmpGeneFromResultSetRow	


}//public class KnownSgmpList	
