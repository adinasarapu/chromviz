package edu.ucsd.genome.chromviz.sigSql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.genome.chromviz.sigDBConnection.DatabaseConnection;
import edu.ucsd.genome.chromviz.sigData.KEGGPathways;

/**
 * The Class <b>KEGGmmuPathwaygenes</b> queries the mmuPathwayGenes table of
 * kegg database and it creates list of <b>mmuPathwayGenes</b> instances from
 * each row of database table.
 * 
 * @author Srujana
 */
public class KEGGMousePathwaysDAO {

	private static final String SELECT_DISTINCT_PATHID_PATHNAME_FROM_MMUPATHWAYGENES;

	static {
		StringBuffer query = new StringBuffer(512);
		query.append("\n");
		query.append("SELECT distinct mpw.pathID,\n");
		query.append("		mpw.pathName\n");
		query.append("FROM mmuPathwayGenes mpw\n");
		SELECT_DISTINCT_PATHID_PATHNAME_FROM_MMUPATHWAYGENES = query.toString();
		// query.delete(commonSqlLength,query.length());
	}

	/**
	 * This method, getAllPathwayGenes(), returns a List of
	 * <b>mmuPathwaygenes</b>instances. It first checks the db connection and
	 * obtains user name and password from DatabaseConnection instance.
	 * 
	 * @return List of mmuPathwayGenes instance
	 * 
	 * @throws Exception
	 * 
	 * @throws SQLException
	 *             , SQLException if connection is null
	 */
	public static List<KEGGPathways> getAllPathways() throws Exception {
		ResultSet rs = null;
		Connection conn = null;
		Statement st = null;
		List<KEGGPathways> listRow = new ArrayList<KEGGPathways>();
		try {
			conn = DatabaseConnection.getConnection("chromviz");
			st = conn.createStatement();
			rs = st.executeQuery(SELECT_DISTINCT_PATHID_PATHNAME_FROM_MMUPATHWAYGENES);
			while (rs.next()) {
				listRow.add(buildPathwayGenesFromResultSetRow(rs));
			}
		} catch (SQLException e) {
			System.err.println("Error message 1: " + e.getMessage());
			System.err.println("Error number 1: " + e.getErrorCode());
		} finally {
			rs.close();
			st.close();

			conn.close();
		}
		return listRow;
	} // method getAllSgmpGenes

	private static KEGGPathways buildPathwayGenesFromResultSetRow(ResultSet rs)
			throws SQLException {
		KEGGPathways sg = new KEGGPathways();
		sg.setPathID(rs.getString(1));
		sg.setPathName(rs.getString(2));
		return sg;
	} // method buildSgmpGeneFromResultSetRow

}
