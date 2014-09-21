package edu.ucsd.genome.chromviz.sigUtility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import edu.ucsd.genome.chromviz.sigData.FileMetaData;
import edu.ucsd.genome.chromviz.sigData.GeneLineData;
import edu.ucsd.genome.chromviz.sigSql.SgmpGeneListDAO;

public class CommonUtil {

	public static void addToolTipText(GeneLineData gene, FileMetaData fileMetaData,
			JPanel panel) {

		List<String> timeTitleList = fileMetaData.getTimeTitleList();
		List<String> ligandTitleList = fileMetaData.getLigandTitleList();
		String geneSymbol = gene.getGeneSymbol();
		//String geneName = gene.getGeneName();
		double txS = gene.getTxStartPos();
		double txE = gene.getTxEndPos();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html>");
		buffer.append("<b>");
		buffer.append("<font size=\"3\" color=\"red\">UCSC Browser ID:</font>");
		buffer.append("</b> ");
		buffer.append(geneSymbol);
		buffer.append("<br />");
		buffer.append("<b>");
		buffer.append("<font size=\"3\" color=\"red\">Gene coordinates:</font></b>");
		buffer.append(txS + " - " + txE);
		int commonLength = buffer.length();
		if (gene.getUserGeneData() != null) {
		List<List<String>> useData = gene.getUserGeneData();
		buffer.append("<br /><b><font size=\"3\" color=\"red\">Data:</font></b>");
		Iterator<String> itr = ligandTitleList.iterator();
		buffer.append("<tr>");
		buffer.append(" <td  border=\"1\">" + "   " + "</td>");
		while (itr.hasNext()) {
			String name = itr.next();
			buffer.append(" <td  border=\"1\">" + name + "</td>");
		}
		buffer.append("</tr>");
		//if (useData != null && useData.size() >= 1) {
			Iterator<List<String>> it1 = useData.iterator();
			int k = 0;
			while (it1.hasNext()) {
				List<String> dataList = it1.next();
				buffer.append("<tr>");
				Iterator<String> it2 = dataList.iterator();
				buffer.append(" <td  border=\"1\">" + timeTitleList.get(k)
						+ "</td>");
				k++;
				while (it2.hasNext()) {
					String v = it2.next();
					double ev = Double.parseDouble(v);
					if (ev >= 2.0) {
						buffer.append(" <td  border=\"1\" bgcolor=\"#FF0000\">"
								+ ev + "</td>");
					} else if (ev <= 0.75) {
						buffer.append(" <td  border=\"1\" bgcolor=\"#009900\">"
								+ ev + "</td>");
					} else {
						buffer.append(" <td  border=\"1\" bgcolor=\"#FFFFCC\">"
								+ ev + "</td>");
					}
				}
				buffer.append("</tr>");
			}

		} else {
			buffer.delete(commonLength, buffer.length());
		}
		String tooltiptext = buffer.toString() + "</html>";
		panel.setToolTipText(tooltiptext);
	}

	public static void addPopup(final GeneLineData gene, JPanel panel) {
		JPopupMenu menu = new JPopupMenu();
		menu.add("Open ...");
		menu.addSeparator();
		JMenuItem menuItem1 = new JMenuItem("Molecule Page", null);
		JMenuItem menuItem2 = new JMenuItem("UCSC Genome Browser", null);
		menuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String id = null;
				try {
					id = SgmpGeneListDAO
							.getSgmpIDWithGeneSymbol(gene.geneSymbol);
				} catch (Exception e) {
					e.printStackTrace();
				}
				openUrl("http://www.signaling-gateway.org/molecule/query?afcsid="
						+ id);
			}
		});
		menuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				openUrl("http://genome.ucsc.edu/cgi-bin/hgTracks?clade=mammal&org=Mouse&db=mm9&position="
						+ gene.getName()
						+ ":"
						+ gene.getTxStartPos()
						+ "-"
						+ gene.getTxEndPos());
			}
		});
		menu.add(menuItem1);
		menu.add(menuItem2);
		panel.setComponentPopupMenu(menu);
	}

	public static void openUrl(String url) {
		String os = System.getProperty("os.name");
		Runtime runtime = Runtime.getRuntime();
		// Block for Windows Platform
		if (os.startsWith("Windows")) {
			String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
			try {
				Process p = runtime.exec(cmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
