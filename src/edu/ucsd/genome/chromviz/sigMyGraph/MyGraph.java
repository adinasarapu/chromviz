package edu.ucsd.genome.chromviz.sigMyGraph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import edu.ucsd.genome.chromviz.sigDrawChrom.ItemListenerPathWayImpl;
import edu.ucsd.genome.chromviz.sigKegg.KeggXMLPathwayEntry;
import edu.ucsd.genome.chromviz.sigKegg.KeggXMLPathwayRelation;

public class MyGraph {
	
	//int re = 1;
	public static void createGraph(List<KeggXMLPathwayRelation> relations) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		final mxGraph graph = new mxGraph();
		graph.setCellsDisconnectable(false);
		Object parent = graph.getDefaultParent();
		List<String> lst1 = new ArrayList<String>();
		List<String> lst2 = new ArrayList<String>();
		Map<String, Object> vrtx = new LinkedHashMap<String, Object>();
		graph.getModel().beginUpdate();
		Iterator<KeggXMLPathwayRelation> relIt = relations.iterator();
		String pathID = ItemListenerPathWayImpl.getPathID();
		while (relIt.hasNext()) {
			KeggXMLPathwayRelation relation = relIt.next();
			KeggXMLPathwayEntry from = relation.getNodeFrom(); // KeggXMLPathwayEntry
			KeggXMLPathwayEntry to = relation.getNodeTo(); // KeggXMLPathwayEntry
			String relationType = relation.getRelationType();
			//System.out.println(re + "fromNode: " + from.getNodeDisplayName() + "\ntoNode: "	+ to.getNodeDisplayName() + "\n relationType: "		+ relationType);
			List<String> relationSubTypes = relation
					.getRelationSubTypes();
			Iterator<String> it = relationSubTypes.iterator();
			while (it.hasNext()) {
				String relationSubTypeName = it.next();
				//System.out.println("relationSubTypeName : "	+ relationSubTypeName);
			}
			String id1 = from.getNodeDisplayName();
			String id2 = to.getNodeDisplayName();
			//re++;
			if (vrtx.get(id1) == null) {
				Object v1 = graph.insertVertex(parent, null, id1, 0, 0,
						70, 35,
						"shape=ellipse;perimeter=ellipsePerimeter");
				vrtx.put(id1, v1);
			}
			if (vrtx.get(id2) == null) {
				Object v2 = graph.insertVertex(parent, null, id2, 0, 0,
						70, 35,
						"shape=ellipse;perimeter=ellipsePerimeter");
				vrtx.put(id2, v2);
			}
			lst1.add(id1);
			lst2.add(id2);
		}

		for (int i = 0; i < lst1.size(); i++) {
			String s1 = lst1.get(i);
			String s2 = lst2.get(i);
			graph.insertEdge(parent, null, "Edge", vrtx.get(s1),
					vrtx.get(s2));
		}
		graph.getModel().endUpdate();
		mxGraphLayout layout = new mxHierarchicalLayout(graph);
		layout.execute(parent);
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		frame.setSize(new Dimension(900, 700));
		frame.setTitle(pathID + " - Signaling Network");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(graphComponent, BorderLayout.CENTER);
	}
}
