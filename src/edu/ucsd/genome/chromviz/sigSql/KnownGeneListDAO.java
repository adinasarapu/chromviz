package edu.ucsd.genome.chromviz.sigSql;

import java.util.*;
import java.io.IOException;
import java.sql.*;

import edu.ucsd.genome.chromviz.sigDBConnection.DatabaseConnection;
import edu.ucsd.genome.chromviz.sigData.KnownGene;
import edu.ucsd.genome.chromviz.sigData.KnownGeneNew;
import edu.ucsd.genome.chromviz.sigData.LocusKnownGenes;
import edu.ucsd.genome.chromviz.sigKegg.KnownGeneObject;

/**
 * The Class <b>KnownGeneList</b> queries the knownGene table of mm9 database
 * and it creates list of <class>KnownGene</class> instances from each row of
 * database table.
 * 
 * @author srujana
 */

public class KnownGeneListDAO {

	private static final String SELECT_COLUMNS_FROM_KNOWNGENE_WITH_PROTEIN_ID;
	private static final String SELECT_COLUMNS_FROM_KNOWNGENE_WITH_ALL;
	private static final String SELECT_COLUMNS_FROM_KNOWNGENE_WITH_CHROMOSOME;
	private static final String SELECT_COLUMNS_FROM_KNOWNGENE_WITH_REFSEQID;
	private static final String SELECT_COLUMNS_FROM_KGXREF_WITH_ALL;
	private static final String SELECT_COLUMNS_FROM_KGKEGGPATHWAY_WITH_ENTREZID;
	private static final String SELECT_COLUMNS_FROM_KNOWNGENE_WITH_KGID;
	private static final String SELECT_COLUMNS_FROM_KEGGPATHWAY_LOCUSLIST_WITH_MAPID;
	private static final String SELECT_COLUMNS_FROM_ALL_KNOWNGENE;

	static {
		StringBuffer query = new StringBuffer(512);
		query.append("\n");
		query.append("SELECT	kx.refseq,\n"); // 1
		query.append("		kg.chrom,\n"); // 2
		query.append("		kg.strand,\n"); // 3
		query.append("		kg.txStart,\n"); // 4
		query.append("		kg.txEnd,\n"); // 5
		query.append("		kg.cdsStart,\n"); // 6
		query.append("		kg.cdsEnd,\n"); // 7
		query.append("		kg.exonCount,\n"); // 8
		query.append("		kg.exonStarts,\n"); // 9
		query.append("		kg.exonEnds,\n"); // 10
		query.append("		kg.proteinID,\n"); // 11
		query.append("		kg.alignID,\n"); // 12
		query.append("		kx.geneSymbol\n"); // 12
		query
				.append("	FROM knownGene kg JOIN kgXref kx ON kx.kgID = kg.name\n");
		int commonSqlLength = query.length();

		query.append(" WHERE kg.chrom = ?");
		// query.append(" WHERE kx.geneSymbol = ?");
		SELECT_COLUMNS_FROM_KNOWNGENE_WITH_CHROMOSOME = query.toString();

		query.delete(commonSqlLength, query.length());
		query.append(" WHERE kx.refseq = ?");
		// SELECT_COLUMNS_FROM_KNOWNGENE_WITH_KNOWN_GENE_NAMES =
		// query.toString();
		SELECT_COLUMNS_FROM_KNOWNGENE_WITH_REFSEQID = query.toString();
		query.delete(commonSqlLength, query.length());
		query.append("	WHERE	kg.proteinID = ?");
		SELECT_COLUMNS_FROM_KNOWNGENE_WITH_PROTEIN_ID = query.toString();

		query.delete(commonSqlLength, query.length());
		SELECT_COLUMNS_FROM_KNOWNGENE_WITH_ALL = query.toString();

		StringBuffer query1 = new StringBuffer(512);
		query1.append("\n");
		query1.append("SELECT	kx.geneSymbol,\n"); // 1
		query1.append("		kx.refseq \n"); // 2
		query1.append("	FROM kgXref kx\n");
		query1.append("	WHERE kx.refseq != ''");
		SELECT_COLUMNS_FROM_KGXREF_WITH_ALL = query1.toString();

		StringBuffer query2 = new StringBuffer(512);
		query2.append("\n");
		query2.append("	SELECT	path.kgID,\n"); // 1
		query2.append("		kg.chrom,\n"); // 2
		query2.append("		kg.strand,\n"); // 3
		query2.append("		kg.txStart,\n"); // 4
		query2.append("		kg.txEnd,\n"); // 5
		query2.append("		kg.cdsStart,\n"); // 6
		query2.append("		kg.cdsEnd,\n"); // 7
		query2.append("		kg.exonCount,\n"); // 8
		query2.append("		kg.exonStarts,\n"); // 9
		query2.append("		kg.exonEnds,\n"); // 10
		//query2.append("		kg.proteinID,\n"); // 11 // added now
		query2.append("		kg.alignID,\n"); // 12
		query2.append("		path.locusID,\n"); // 13
		query2.append("		path.mapID,\n"); // 14
		query2.append("		ref.refseq,\n"); //15
		query2.append("		ref.spDisplayID,\n"); //16
		query2.append("		ref.spID,\n"); //17
		query2.append("		ref.geneSymbol\n"); //18
		query2
				.append("	FROM (kgXref ref JOIN knownGene kg ON ref.kgID = kg.name) JOIN keggPathway path ON kg.name = path.kgID\n");
		query2.append("	WHERE ((locusID = ");
		SELECT_COLUMNS_FROM_KGKEGGPATHWAY_WITH_ENTREZID = query2.toString();

		StringBuffer query3 = new StringBuffer(512);
		query3.append("\n");
		query3.append("SELECT	kg.name,\n"); // 1 eg: "uc007aet.1"
		query3.append("		kg.chrom,\n"); // 2 eg: chr1 (1-19, M,X,Y); chrY_random (Y = Y,1,4,5,7,8,9,13,17,Un)   
		query3.append("		kg.strand,\n"); // 3 eg: + or -
		query3.append("		kg.txStart,\n"); // 4
		query3.append("		kg.txEnd,\n"); // 5
		query3.append("		kg.cdsStart,\n"); // 6
		query3.append("		kg.cdsEnd,\n"); // 7
		query3.append("		kg.exonCount,\n"); // 8
		query3.append("		kg.exonStarts,\n"); // 9
		query3.append("		kg.exonEnds,\n"); // 10
		query3.append("		kg.proteinID,\n"); // 11
		query3.append("		kg.alignID,\n"); // 12
		query3.append("		kp.locusID,\n"); // 13
		query3.append("		kp.mapID\n"); // 14
		query3
				.append("	FROM knownGene kg JOIN keggPathway kp ON kg.name = kp.kgID");
		query3.append("	WHERE	(kg.name = ");
		SELECT_COLUMNS_FROM_KNOWNGENE_WITH_KGID = query3.toString();

		
		StringBuffer query4 = new StringBuffer(512);
		query4.append("\n");
		query4.append("SELECT	locusID,\n"); // 1
		query4.append("		kgID\n"); // 2
		query4.append("	FROM keggPathway");
		query4.append("	WHERE	mapID = ?");
		SELECT_COLUMNS_FROM_KEGGPATHWAY_LOCUSLIST_WITH_MAPID = query4.toString();
		
		StringBuffer query5 = new StringBuffer(512);
		query5.append("\n");
		query5.append("SELECT	kg.name,\n"); // 1 eg: "uc007aet.1"
		query5.append("		kg.chrom,\n"); // 2 eg: chr1 (1-19, M,X,Y); chrY_random (Y = Y,1,4,5,7,8,9,13,17,Un)   
		query5.append("		kg.strand,\n"); // 3 eg: + or -
		query5.append("		kg.txStart,\n"); // 4
		query5.append("		kg.txEnd,\n"); // 5
		query5.append("		kg.cdsStart,\n"); // 6
		query5.append("		kg.cdsEnd,\n"); // 7
		query5.append("		kg.exonCount,\n"); // 8
		query5.append("		kg.exonStarts,\n"); // 9
		query5.append("		kg.exonEnds,\n"); // 10
		query5.append("		kg.proteinID,\n"); // 11
		query5.append("		kg.alignID\n"); // 12
		query5.append("	FROM knownGene kg");
		//query5.append("	WHERE	kg.name = ");
		SELECT_COLUMNS_FROM_ALL_KNOWNGENE = query5.toString();		
	}

	
	
	public static Map<String, List<String>> getLocusWithKnownGeneListWithMapID(String mapID) throws IOException, Exception{
		Map<String, List<String>> locusKGList = null;//new HashMap<String,List<String>>();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DatabaseConnection.getConnection("chromviz");
			pst = con
					.prepareStatement(SELECT_COLUMNS_FROM_KEGGPATHWAY_LOCUSLIST_WITH_MAPID);
			pst.setString(1, mapID.trim());
			rs = pst.executeQuery();
			List<String> locusKnownGeneList = new ArrayList<String>();
			List<String> locusList = new ArrayList<String>();
			while(rs.next()){
				String locusID = rs.getString(1);
				String kgID = rs.getString(2);
				locusKnownGeneList.add(locusID+"\t"+kgID);
				//System.out.println(locusID+"\t"+kgID);
				if(!locusList.contains(locusID)){	
					locusList.add(locusID);
				}				
			}
			//System.out.println(locusList.toString());
			locusKGList = getKnownGeneList(locusList, locusKnownGeneList);
			
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error number: " + e.getErrorCode());
		} finally {
			rs.close();
			pst.close();
			con.close();
		}
		return locusKGList;
	}
	
	private static Map<String, List<String>> getKnownGeneList(
			List<String> locusList, List<String> locusKnownGeneList) {
		Map<String, List<String>> locusKGList = new HashMap<String,List<String>>();
		Iterator<String> it = locusList.iterator();
		while(it.hasNext()){
			String lid = it.next();
			Iterator<String> it2 = locusKnownGeneList.iterator();
			List<String> list = new ArrayList<String>();
			while(it2.hasNext()){
				String[] both = it2.next().split("\t");
				//System.out.println(lid+"\t"+both[1]);
				if(lid.equals(both[0])){
					list.add(both[1]);					
				}
			}
			locusKGList.put(lid,list);
		}		
		return locusKGList;
	}
	
	public static Set<String> getKnownGeneIDWithEntrezID(String locusID)
			throws Exception {
		Set<String> ret = new HashSet<String>();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		// System.out.print("LocusID:" + locusID + "\t");
		try {
			con = DatabaseConnection.getConnection("chromviz");
			pst = con
					.prepareStatement(SELECT_COLUMNS_FROM_KGKEGGPATHWAY_WITH_ENTREZID);
			pst.setString(1, locusID);
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).indexOf(".2") == -1) {
					if (ret.add(rs.getString(1))) {
						// System.out.println("KgID:" + rs.getString(1));
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error number: " + e.getErrorCode());
		} finally {
			rs.close();
			pst.close();
			con.close();
		}
		return ret;

	} // method getKnownGene

	/**
	 * This method, getAllKnownGenes(), returns a List of
	 * <b>KnownGene</b>instances. It first checks the db connection and obtains
	 * user name and password from HGDBInfo instance.
	 * 
	 * @return List of KnownGenes instance
	 * 
	 * @throws Exception
	 * 
	 * @throws Exception
	 *             , SQLException if connection is null
	 */
	public static Map<String, String> getAllKgXrefGenesymbols()
			throws Exception {
		Map<String, String> xrefs = new HashMap<String, String>();

		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		try {

			con = DatabaseConnection.getConnection("chromviz");
			st = con.createStatement();
			rs = st.executeQuery(SELECT_COLUMNS_FROM_KGXREF_WITH_ALL);
			while (rs.next()) {
				xrefs.put(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException se) {
			System.err.println("Error message: " + se.getMessage());
			System.err.println("Error number: " + se.getErrorCode());
		} finally {
			rs.close();
			st.close();
			con.close();
		}
		return xrefs;

	} // method getKnownGene

	public static Map<String, KnownGeneNew> getAllKnownGenesMap() throws Exception {

		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		Map<String, KnownGeneNew> listRow = new HashMap<String, KnownGeneNew>();
		//List<KnownGene> listRow = new ArrayList<KnownGene>();
		try {
			con = DatabaseConnection.getConnection("chromviz");
			st = con.createStatement();
			rs = st.executeQuery(SELECT_COLUMNS_FROM_ALL_KNOWNGENE);
			while (rs.next()) {
				if(rs.getString(1) != null)
				listRow.put(rs.getString(1),buildAllKnownGeneFromResultSetRow(rs));
			}
		} catch (SQLException se) {
			System.err.println("Error message: " + se.getMessage());
			System.err.println("Error number: " + se.getErrorCode());
		} finally {
			rs.close();
			st.close();
			con.close();
		}
		return listRow;

	} // method getKnownGene
	
	private static KnownGeneNew buildAllKnownGeneFromResultSetRow(ResultSet rs)
			throws SQLException {
		KnownGeneNew kg = new KnownGeneNew();
		kg.setKgID(rs.getString(1));
		kg.setChromosome(rs.getString(2));
		kg.setStrand(rs.getString(3));
		kg.setTxStartPosition(rs.getInt(4));
		kg.setTxEndPosition(rs.getInt(5));
		kg.setCdsStartPosition(rs.getInt(6));
		kg.setCdsEndPosition(rs.getInt(7));
		kg.setExonCount(rs.getInt(8));
		kg.setExonStartsPosition(rs.getString(9));
		kg.setExonEndsPosition(rs.getString(10));
		kg.setProteinID(rs.getString(11));
		kg.setAlignID(rs.getString(12));
		return kg;
	} // method buildKnownGeneFromResultSetRow


	public static List<KnownGene> getAllKnownGenes() throws Exception {

		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		List<KnownGene> listRow = new ArrayList<KnownGene>();
		try {
			con = DatabaseConnection.getConnection("chromviz");
			st = con.createStatement();
			rs = st.executeQuery(SELECT_COLUMNS_FROM_KNOWNGENE_WITH_ALL);
			while (rs.next()) {
				listRow.add(buildKnownGeneFromResultSetRow(rs));
			}
		} catch (SQLException se) {
			System.err.println("Error message: " + se.getMessage());
			System.err.println("Error number: " + se.getErrorCode());
		} finally {
			rs.close();
			st.close();
			con.close();
		}
		return listRow;

	} // method getKnownGene

	//

	/**
	 * This method, getKnownGene(), returns a <b>KnownGene</b>instance. It first
	 * checks the db connection and obtains user name and password from HGDBInfo
	 * instance.
	 * 
	 * @return KnownGene instance
	 * 
	 * @throws Exception
	 * 
	 * @throws Exception
	 *             , SQLException if connection is null
	 */

	public static KnownGene getKnownGene(String gid) throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		KnownGene kg = null;
		try {
			con = DatabaseConnection.getConnection("chromviz");
			pst = con
					.prepareStatement(SELECT_COLUMNS_FROM_KNOWNGENE_WITH_PROTEIN_ID);
			pst.setString(1, gid);
			rs = pst.executeQuery();
			kg = buildKnownGeneFromResultSetRow(rs);
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error number: " + e.getErrorCode());
		} finally {
			rs.close();
			pst.close();
			con.close();
		}
		return kg;
	} // method getKnownGene

	public static List<KnownGene> getKnownGeneSequence(String kgid)
			throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<KnownGene> listRow = new ArrayList<KnownGene>();
		try {
			con = DatabaseConnection.getConnection("chromviz");
			pst = con
					.prepareStatement(SELECT_COLUMNS_FROM_KNOWNGENE_WITH_REFSEQID);

			pst.setString(1, kgid);
			rs = pst.executeQuery();
			while (rs.next()) {
				listRow.add(buildKnownGeneFromResultSetRow(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error number: " + e.getErrorCode());
		} finally {
			rs.close();
			pst.close();
			con.close();
		}
		return listRow;
	} // method getKnownGene

	/**
	 * This method returns <b>KnownGene</b>instance for each row
	 * 
	 * @return KnownGene instance
	 * 
	 * @throws SQLException
	 *             if connection is null
	 */
	public static List<KnownGene> getKnownGeneSequencedata(String[] kgname)
			throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<KnownGene> listRow = new ArrayList<KnownGene>();
		try {
			con = DatabaseConnection.getConnection("chromviz");
			pst = con
					.prepareStatement(SELECT_COLUMNS_FROM_KNOWNGENE_WITH_REFSEQID);
			for (int i = 0; i <= kgname.length; i++) {
				pst.setString(1, kgname[i]);
				rs = pst.executeQuery();
				while (rs.next()) {
					listRow.add(buildKnownGeneFromResultSetRow(rs));
				}
			}

		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error number: " + e.getErrorCode());
		} finally {
			rs.close();
			pst.close();
			con.close();
		}
		return listRow;
	} // method getKnownGene

	/**
	 * This method, getKnownChromGene(), returns a List of
	 * <b>KnownGene</b>instances. It first checks the db connection and obtains
	 * user name and password from HGDBInfo instance.
	 * 
	 * @return List of KnownGenes instance
	 * 
	 * @throws Exception
	 * 
	 * @throws Exception
	 *             , SQLException if connection is null
	 */

	public static List<KnownGene> getKnownChromGene(String chrom)
			throws Exception {

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		List<KnownGene> listRow = new ArrayList<KnownGene>();
		try {
			con = DatabaseConnection.getConnection("chromviz");
			pst = con
					.prepareStatement(SELECT_COLUMNS_FROM_KNOWNGENE_WITH_CHROMOSOME);
			pst.setString(1, chrom);
			rs = pst.executeQuery();
			while (rs.next()) {
				listRow.add(buildKnownGeneFromResultSetRow(rs));
			}
		} catch (SQLException se) {
			System.err.println("Error message: " + se.getMessage());
			System.err.println("Error number: " + se.getErrorCode());
		} finally {
			rs.close();
			pst.close();
			con.close();
		}
		return listRow;

	} // method getKnownGene

	private static KnownGene buildKnownGeneFromResultSetRow(ResultSet rs)
			throws SQLException {
		KnownGene kg = new KnownGene();
		kg.setKnownGeneID(rs.getString(1));
		kg.setChromosome(rs.getString(2));
		kg.setStrand(rs.getString(3));
		kg.setTxStartPosition(rs.getInt(4));
		kg.setTxEndPosition(rs.getInt(5));
		kg.setCdsStartPosition(rs.getInt(6));
		kg.setCdsEndPosition(rs.getInt(7));
		kg.setExonCount(rs.getInt(8));
		kg.setExonStartsPosition(rs.getString(9));
		kg.setExonEndsPosition(rs.getString(10));
		kg.setAlignID(rs.getString(11));
		kg.setLocusID(rs.getString(12));
		kg.setMapID(rs.getString(13));
		kg.setRefSeqID(rs.getString(14));
		kg.setSpDisplayID(rs.getString(15));
		kg.setSpID(rs.getString(16));
		kg.setGeneSymbol(rs.getString(17));
		return kg;
	} // method buildKnownGeneFromResultSetRow

	public static List<KnownGene> getKnownGeneDetails(KnownGeneObject[] kgids)
			throws Exception {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		List<KnownGene> listRow = new ArrayList<KnownGene>();
		try {
			con = DatabaseConnection.getConnection("chromviz");
			String q = getQueryString(kgids);
			String query = SELECT_COLUMNS_FROM_KNOWNGENE_WITH_KGID + q;
			System.out.println("query\t: " + query);
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				if (rs.getString(1) != null) {
					listRow.add(buildKnownGeneFromResultSetRow(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error number: " + e.getErrorCode());
		} finally {
			rs.close();
			st.close();
			con.close();
		}
		return listRow;
	} // method getKnownGene

	public static List<KnownGene> getDataValuesForKeggPathway(String keggPathID,
			List<String> geneIDList) throws Exception {
		List<KnownGene> ret = new ArrayList<KnownGene>();
		List<String> uni = new ArrayList<String>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		// System.out.print("LocusID:" + locusID + "\t");
		try {
			con = DatabaseConnection.getConnection("chromviz");
			// System.out.println("MapValues: "+map.keySet().toString());
			String q = getQueryString(geneIDList) + ") AND mapID='" + keggPathID
					+ "'";
			String query = SELECT_COLUMNS_FROM_KGKEGGPATHWAY_WITH_ENTREZID + q;
			//System.out.println("query\t: " + query);
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				KnownGene kgene = buildKnownGeneFromResultSetRow(rs);
					if (!uni.contains(kgene.getLocusID())) {
						ret.add(kgene);
						uni.add(kgene.getLocusID());
						// System.out.println("KgID:" + rs.getString(1));
					}
			}
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error number: " + e.getErrorCode());
		} finally {
			rs.close();
			st.close();
			con.close();
		}		
		return ret;
	}

	private static KnownGeneObject buildKnownGeneObjectFromResultSetRow(
			ResultSet rs) throws SQLException {
		KnownGeneObject kgo = new KnownGeneObject();
		kgo.setKnownGeneID(rs.getString(1));
		kgo.setLocusID(rs.getString(2));
		return kgo;
	}

	private static String getQueryString(KnownGeneObject[] kgids) {
		StringBuffer q = new StringBuffer();
		q.append("'" + kgids[0].getKnownGeneID() + "'");
		if (kgids.length > 1) {
			for (int i = 1; i < kgids.length; i++) {
				q.append(") OR (kg.name = '" + kgids[i].getKnownGeneID() + "'");
			}
			q.append(")");
		}
		return q.toString();
	}

	private static String getQueryString(List<String> geneList) {
		Object[] keys = geneList.toArray();
		StringBuffer q = new StringBuffer();
		q.append("'" + keys[0] + "'");
		if (keys.length > 1) {
			for (int i = 1; i < keys.length; i++) {
				q.append(") OR (locusID = '" + keys[i] + "'");
			}
			q.append(")");
		}
		return q.toString();
	}
}
