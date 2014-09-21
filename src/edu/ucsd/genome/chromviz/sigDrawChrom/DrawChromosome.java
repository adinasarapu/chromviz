package edu.ucsd.genome.chromviz.sigDrawChrom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.ucsd.genome.chromviz.sigData.ChromosomeData;
import edu.ucsd.genome.chromviz.sigData.FileMetaData;
import edu.ucsd.genome.chromviz.sigData.GeneLineData;
import edu.ucsd.genome.chromviz.sigData.UserClass;
import edu.ucsd.genome.chromviz.sigUtility.CommonUtil;
import edu.ucsd.genome.chromviz.sigUtility.ReadFileData;

public class DrawChromosome extends JPanel {

	private static final long serialVersionUID = 1L;
	// selected chromosome list details from UCSC
	List<ChromosomeData> chrDataList;
	// user data with metadata
	ReadFileData fileData;
	List<UserClass> userClass;
	// this collectes all gene details with it their coordinates, irrespective
	// of chromosome
	List<GeneLineData> allGeneDataList;
	List<String> nameString;
	List<String> timeTitleList;
	List<String> ligandTitleList;
	FileMetaData fileMetaData;
	Map<String, List<List<String>>> arrayData;
	//File inFile = new File("images/karyotype_mouse_new.png");
	JFrame frame = new JFrame("Creating a Popup Menu");
	List<ChromosomeData> dlist = new ArrayList<ChromosomeData>();

	public void drawChromosome(List<ChromosomeData> chrDataList,
			ReadFileData fileData) {
		this.chrDataList = chrDataList;
		this.fileData = fileData;
		this.fileMetaData = fileData.getFileMetaData();
		this.timeTitleList = fileData.getFileMetaData().getTimeTitleList();
		this.ligandTitleList = fileData.getFileMetaData().getLigandTitleList();
		this.arrayData = fileData.getArrayData();
		addMouseListeners();
	}

	public void paintComponent(final Graphics g) {
		super.validate();// updateUI();
		super.paintComponent(g); // paints background
		allGeneDataList = new ArrayList<GeneLineData>();
		Collections.sort(chrDataList);
		for (final ChromosomeData chrData : chrDataList) {
			final Rectangle rect = chrData.getRectangle();
			g.setColor(Color.BLACK);
			g.drawRoundRect(chrData.getXPos(), chrData.getYPos(), (int) rect.getWidth(), (int) rect.getHeight(), 15, 15);
			for (int i = 0; i < chrData.getLinePosList().size(); i++) {
				final double genePos = chrData.getLinePosList().get(i);
				final Rectangle geneLine = new Rectangle(chrData.getXPos(),	chrData.getYPos() + (int) genePos, rect.width, 1);
				g.drawRect(chrData.getXPos(),	chrData.getYPos() + (int) genePos, rect.width, 0);
				// write each chromosome name
				g.drawString(chrData.getChromosomeName(), chrData.getXPos(),	chrData.getYPos() + rect.height + 30);
				// assign data (ucsc and user) to each gene
				GeneLineData geneData = new GeneLineData();
				// set ucsc data here
				geneData.setRectangle(geneLine);
				Double d1 = chrData.getTxStart().get(i);
				geneData.setTxStartPos(d1.intValue());
				Double d2 = chrData.getTxEnd().get(i);
				geneData.setTxEndPos(d2.intValue());
				geneData.setGeneName(chrData.getGeneName().get(i));
				geneData.setGeneSymbol(chrData.getGeneSymbol().get(i));
				geneData.setChromosomeName(chrData.getChromosomeName());
				String locusID = chrData.getLocusID(i);
				geneData.setGeneEntrezID(locusID);
				// set user data here
				if(arrayData != null){
					List<List<String>> arrayValues = arrayData.get(locusID);
					geneData.setUserGeneData(arrayValues);
				}
				allGeneDataList.add(geneData);				
			}
		}
	}

	/*
	 * 
	 */
	private void addMouseListeners() {
		setFocusable(true);
		requestFocusInWindow();
		this.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
			}

			public void mouseMoved(MouseEvent e) {
				verifyGene(e.getPoint(), false);
			}
		});
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				boolean isControl = e.isControlDown();

				verifyGene(e.getPoint(), true);
				selectChromosomeByClick(e.getPoint(), isControl);
			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		});
	}

	/*
	 * 
	 */
	private void verifyGene(Point point, boolean isPopup) {
		boolean insideGene = false;
		for (GeneLineData gene : allGeneDataList) {
			if (gene.getRectangle().contains(point)) {
				insideGene = true;
				if (!isPopup) {
					CommonUtil.addToolTipText(gene,fileMetaData, this);
				} else
					CommonUtil.addPopup(gene, this);
			} else {
			}
		}
		if (!insideGene) {
		}
	}

	/*
	 * 
	 */
	private void selectChromosomeByClick(Point point, boolean isControl) {
		for (ChromosomeData data : chrDataList) {
			Rectangle rect = new Rectangle(data.getXPos(), data.getYPos(), data.getRectangle().width, data.getRectangle().height);
			if (rect.contains(point)) {
				drawSingleChromosome(data, isControl);
			}
		}
	}

	/*
	 * 
	 */

	private void drawSingleChromosome(ChromosomeData data, boolean isControl) {

		frame.dispose();
		frame = new JFrame();
		final DrawSignleChromosome cs = new DrawSignleChromosome();
		JScrollPane scrollPane = new JScrollPane(cs);
		scrollPane.setMinimumSize(new java.awt.Dimension(300, 800));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane scroller = new JScrollPane(cs);
		frame.getContentPane().add(scroller, BorderLayout.CENTER);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		boolean duplicate = false;
		if (!isControl) {
			dlist.clear();
		} else {
			for (ChromosomeData d : dlist) {
				if (d.getChromosomeName().equals(data.getChromosomeName())) {
					duplicate = true;
				}
			}
		}
		if (!duplicate)
			dlist.add(data);
		cs.drawChromosome(dlist, fileData);
		frame.add(scrollPane);
		frame.pack();
		frame.setLocation(new Point(800, 5));
		frame.setVisible(true);
		if(dlist.size() == 1){
		frame.setSize(260 * dlist.size(), 800);
		}else{
			frame.setSize(260 * dlist.size()* 3/4, 800);
		}
		
		JButton zoomIn = new JButton("");
	
		JButton zoomOut = new JButton("");
		zoomIn.setToolTipText("Zoom In");
		zoomOut.setToolTipText("Zoom Out");
		zoomIn.setIcon(new ImageIcon("images/Zoom-In-icon.png"));
		zoomOut.setIcon(new ImageIcon("images/Zoom-Out-icon.png"));

		final JButton left = new JButton("");
		final JButton right = new JButton("");
		left.setIcon(new ImageIcon("images/Button-Previous-icon.png"));
		right.setIcon(new ImageIcon("images/Button-Next-icon.png"));
		
		final JButton undo = new JButton("");
		final JButton redo = new JButton("");
		undo.setIcon(new ImageIcon("images/Undo-icon.png"));
		redo.setIcon(new ImageIcon("images/Redo-icon.png"));

		cs.setLayout(null);
		cs.add(zoomIn, 0);
		cs.add(zoomOut, 1);
		cs.add(left, 2);
		cs.add(right, 3);
		cs.add(undo, 4);
		cs.add(redo, 5);
		zoomIn.setBounds(10, 10, 30, 20);
		zoomOut.setBounds(45, 10, 30, 20);
		left.setBounds(80, 10, 30, 20);
		right.setBounds(115, 10, 30, 20);
		undo.setBounds(150, 10, 30, 20);
		redo.setBounds(185, 10, 30, 20);
		cs.addRemoveButton();
		scrollPane.setViewportView(cs);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		left.setEnabled(false);
		if (timeTitleList == null || timeTitleList.size() <= 1) {
			right.setEnabled(false);
		}
		zoomIn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cs.zoomIn();
				cs.repaint();
				cs.setSize(200, 200);
			}
		});
		zoomOut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cs.zoomOut();
				cs.repaint();
				cs.setSize(200, 200);

			}
		});

		left.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean enabled = cs.decrement();
				cs.repaint();
				right.setEnabled(true);
				if (!enabled) {
					left.setEnabled(false);
				}

			}
		});
		right.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean enabled = cs.increment();
				cs.repaint();
				if (!enabled) {
					right.setEnabled(false);
				}
				left.setEnabled(true);

			}
		});
		redo.setEnabled(false);
		undo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cs.removeLast();
				redo.setEnabled(true);
			}
		});
		redo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cs.addLast();
			}
		});
	}

	public BufferedImage zoomIn(BufferedImage bi, int scale) {
		int width = bi.getWidth();
		int height = scale * bi.getHeight();

		BufferedImage biScale = new BufferedImage(width, height, bi.getType());

		// Cicla dando un valore medio al pixel corrispondente
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				biScale.setRGB(i, j, bi.getRGB(i / scale, j / scale));

		return biScale;
	}
}
