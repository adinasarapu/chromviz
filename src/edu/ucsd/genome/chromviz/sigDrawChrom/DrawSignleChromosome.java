package edu.ucsd.genome.chromviz.sigDrawChrom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.ucsd.genome.chromviz.sigData.ChromosomeData;
import edu.ucsd.genome.chromviz.sigData.FileMetaData;
import edu.ucsd.genome.chromviz.sigData.GeneLine;
import edu.ucsd.genome.chromviz.sigData.GeneLineData;
import edu.ucsd.genome.chromviz.sigData.UserClass;
import edu.ucsd.genome.chromviz.sigUtility.CommonUtil;
import edu.ucsd.genome.chromviz.sigUtility.ReadFileData;

public class DrawSignleChromosome extends JPanel {

	private static final long serialVersionUID = 1L;
	
	List<ChromosomeData> dataList;
	List<ChromosomeData> mdataList;
	List<GeneLineData> genesOfChromosome;
	List<UserClass> userClass;
	List<String> nameString;
	FileMetaData fileMetaData = null;
	List<String> timeTitleList = null;
	List<String> ligandTitleList = null;
	//Image bgImage;
	int zoomFactor = 1;
	int geneWidth = 1;
	int timePos = 0;
	float widthFactor = 1;
	int height=0;
	ReadFileData fileData;
	
	public void drawChromosome(List<ChromosomeData> chrData, ReadFileData fileData /*FileMetaData fileMetaData*/) {
		mdataList = new ArrayList<ChromosomeData>();
		this.dataList = chrData;
		if(fileData != null){
			this.fileData = fileData;
			this.fileMetaData = fileData.getFileMetaData(); 
			this.timeTitleList = fileData.getFileMetaData().getTimeTitleList();
			this.ligandTitleList = fileData.getFileMetaData().getLigandTitleList();
		} 
		addMouseListeners();
		addComponentListener(new ComponentListener() {
          @Override
          public void componentShown(ComponentEvent arg0) {
          }
          @Override
          public void componentResized(ComponentEvent arg0) {
            repaint();
          }
          @Override
          public void componentMoved(ComponentEvent arg0) {
          }
          @Override
          public void componentHidden(ComponentEvent arg0) {
          }
        });
	}
	
	
	public void removeLast(){
		  if((dataList.size()-1) >=0){
		  for(int i=0 ; i< dataList.size(); i++){
	        remove(6);
	      }
		  mdataList.add(dataList.remove(dataList.size()-1));
		  addRemoveButton();
		  repaint();
		  }
		}
		public void addLast(){
		  if((mdataList.size()-1) >=0){
		  for(int i=0 ; i< dataList.size(); i++){
	        remove(6);
	      }
		  dataList.add(mdataList.get(mdataList.size()-1));
		  mdataList.remove(mdataList.size()-1);
		  addRemoveButton();
		  repaint();
		  }
		}
		 
		 
	 public Dimension getPreferredSize(){
	   int size=  280;
	   if(dataList!= null)
	     size=  220*dataList.size();	
	   return new Dimension(size,(height)+200);
	 }
	      
	
	public void addRemoveButton() {
		if (dataList.size() == 0) {
			SwingUtilities.getWindowAncestor(this).dispose();
			return;
		}
		int index = 6;
		int curOffset = 75;
		for (ChromosomeData data : dataList) {
			//genesOfChromosome = new ArrayList<GeneLineData>();
			final Rectangle rect = data.getRectangle();
			BufferedImage image = new BufferedImage(rect.width, rect.height,	BufferedImage.TYPE_INT_RGB);
			image = zoomInChromosome(image, zoomFactor);
			height = image.getHeight();
			JButton remove = new JButton("");
			remove.setIcon(new ImageIcon("images/remove.png"));
			remove.setToolTipText("delete!");
			remove.setBounds((curOffset + 60) + zoomFactor, 35, 20, 18);
			remove.setActionCommand(data.getChromosomeName());
			remove.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String name = e.getActionCommand();
					int ind = 0;
					for (int i = 0; i < dataList.size(); i++) {
						if (dataList.get(i).getChromosomeName().equals(name)) {
							break;
						}
						ind++;
					}
					for (int i = 0; i < mdataList.size(); i++) {
						if (mdataList.get(i).getChromosomeName().equals(name)) {
							mdataList.remove(i);
						}
					}
					for (int i = 0; i < dataList.size(); i++) {
						remove(6);
					}
					dataList.remove(ind);
					addRemoveButton();
					repaint();
				}
			});
			//System.out.println("Adding index" + index);
			add(remove, index);
			index++;
			if(ligandTitleList != null){
				for (int i = 0; i < ligandTitleList.size(); i++) {
					curOffset = curOffset + 35;	
				}
			}else{
				curOffset = curOffset + 35;
			}				
			curOffset = curOffset + 50;
		}
	}

	
	public void paintComponent(final Graphics g) {
		super.validate();// updateUI();
		super.paintComponent(g); // paints background
		int cnt = 0;
		int curOffset = 75;//75 // distance b/w two chromosome images
		genesOfChromosome = new ArrayList<GeneLineData>();
		for (ChromosomeData chrData : dataList) {			
			final Rectangle rect = chrData.getRectangle();
			BufferedImage image = new BufferedImage(rect.width, rect.height,BufferedImage.TYPE_INT_RGB);
			image = zoomInChromosome(image, zoomFactor);
			height = image.getHeight();
			int offset = curOffset;
			
			// Draw Chromosome Name
			int fontSize = 20;
			g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
			String chrName = chrData.getChromosomeName();
			g.setColor(Color.BLACK);
			//g.drawString(chrName, (curOffset) + zoomFactor, 50);
			
			// Draw Chromosome and Ligand Name (duplicate chromosome according to ligands selected or one time if no user data)
			if (ligandTitleList != null || timeTitleList != null) { // or fileData != null
						g.drawString(chrName, (curOffset-5), 50);
						//g.drawString(chrName, (curOffset-5) + zoomFactor, 50);
				for (int i = 0; i < ligandTitleList.size(); i++) {
					fontSize = 13;
					g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
					g.setColor(Color.BLACK);
					g.drawString(ligandTitleList.get(i), offset + (35 * i), 70);
					//g.drawString(ligandTitleList.get(i), offset + (35 * i) + zoomFactor, 70);
					g.drawRoundRect(offset + (35 * i), 90, image.getWidth(),	image.getHeight(), 15, 15);
					curOffset = curOffset + 35;
				}
			} else { // ligandTitleList == null || timeTitleList == null || fileData == null
				g.drawString(chrName, (curOffset+10), 50);
				//g.drawString(chrName, (curOffset+10) + zoomFactor, 50);
				g.setColor(Color.BLACK);
				g.drawRoundRect(offset + 35, 90, image.getWidth(), image.getHeight(), 15, 15);
				curOffset = curOffset + 35;
			}
			curOffset = curOffset + 50;			
			// Draw Time Name/Title (if user data) 
			if (cnt == 0) {
				if (timeTitleList != null && timeTitleList.size() > 0) {
					//g.drawString(timeTitleList.get(timePos), 30 + zoomFactor, 70);
					g.drawString(timeTitleList.get(timePos), 30, 70);
				} 
			}
			
			// Draw Lines (Genes) on Chromosome (with or without user data)
			for (int i = 0; i < chrData.getLinePosList().size(); i++) {
				GeneLine gline = new GeneLine();
				gline.setChrData(chrData);
				final double genePos = chrData.getLinePosList().get(i);				
				gline.setGenePos(genePos);
				Double d1 = chrData.getTxStart().get(i);
				int lineStart = d1.intValue(); 
				gline.setLineStart(lineStart);
				Double d2 = chrData.getTxEnd().get(i);
				int lineEnd = d2.intValue();
				gline.setLineEnd(lineEnd);
				String geneName = chrData.getGeneName().get(i); 
				gline.setGeneName(geneName);
				String geneSymbol = chrData.getGeneSymbol().get(i);
				gline.setGeneSymbol(geneSymbol);
				String locusID = chrData.getLocusID(i);
				gline.setLocusID(locusID);
				//System.out.println("GeneID : "+locusID+"\tGeneSymbol : "+geneSymbol+"\t"+genePos);
				if (ligandTitleList != null) { // ) { // or fileData != null
					for (int k = 0; k < ligandTitleList.size(); k++) {
						addGeneLineAndSymbol(k, gline, curOffset, offset, g); 
						cnt++;
					}
				} else { // if the user selects pathway directly with out data
					addGeneLineAndSymbol(1, gline, curOffset, offset, g);
				}
			}			
		}
	}
	
	int prevy = 0;
	String prevSym = "";
	private void addGeneLineAndSymbol(int k, GeneLine gline, int curOffset, int offset,Graphics g) {
		
		ChromosomeData chrData = gline.getChrData();
		double width = chrData.getRectangle().getWidth();
		String chrName = chrData.getChromosomeName();
		
		final Rectangle line = new Rectangle(offset + (35 * k),	90 + (((int) gline.getGenePos()) * zoomFactor), (int) (width * widthFactor), geneWidth);
		GeneLineData gene = new GeneLineData();
		gene.setRectangle(line);
		gene.ligandNumber = k + 1;
		gene.setTxStartPos(gline.getLineStart());
		gene.setGeneName(gline.getGeneName());
		gene.setTxEndPos(gline.getLineEnd());
		gene.setGeneSymbol(gline.getGeneSymbol());
		gene.setChromosomeName(chrName);
		gene.setGeneEntrezID(gline.getLocusID());
		genesOfChromosome.add(gene); // this collects all genes including duplicate cases
		
		Map<String, List<List<String>>> arrayData = null;
		if (fileData != null) {
			arrayData = fileData.getArrayData();
		}
		
		List<List<String>> arrayValues = null;
		if (arrayData != null && arrayData.size() > 0) {
			arrayValues = arrayData.get(gline.getLocusID());
			if (arrayValues != null && arrayValues.size() > 0) {								
				gene.setUserGeneData(arrayValues);
				String value = arrayValues.get(timePos).get(k);
				double v = Double.parseDouble(value);
				if (v >= 2.0) {
					g.setColor(Color.RED);
				} else if (v <= 0.75) {
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.BLACK);
				}
			} else {
				g.setColor(Color.BLACK);
			}
		}
		
		g.fillRect(offset + (35 * k), 90 + (((int) gline.getGenePos()) * zoomFactor), (int) (width * widthFactor), geneWidth);
		
		g.setFont(new Font("TimesRoman", Font.BOLD, 12));
		int length = gline.getGeneSymbol().length();
		
		int cprevy = 90 + (((int) gline.getGenePos()) * zoomFactor);
		String curSymbol = gline.getGeneSymbol();
		if (cprevy != prevy && !curSymbol.equals(prevSym)) {
			/*if ((cprevy - 5) < prevy) {
				cprevy = prevy - 12;
			}*/
			prevy = cprevy;
			// System.out.println("Gene symbol" + gene.getGeneSymbol() + "  Location" + cprevy);
			g.setColor(Color.BLACK);
			String geneSymbol = gline.getGeneSymbol();
			if (geneSymbol.length() >= 4) {
				geneSymbol = geneSymbol.substring(0, 4);
			}
			g.drawString(geneSymbol, (offset - geneSymbol.length()*8) + (35 * k),	90 + (((int) gline.getGenePos()) * zoomFactor));
			prevSym = curSymbol;
		}		
	}


	public void zoomIn() {
		if (zoomFactor < 200)
			zoomFactor++;
		if (widthFactor < 2) {
			widthFactor = 1.0f;
			if (zoomFactor == 5) {
				geneWidth = 3;
			}
		}
	}

	public void zoomOut() {
		if (zoomFactor > 1) {
			zoomFactor--;
			if (zoomFactor == 2)
				widthFactor = 1;
			if (zoomFactor < 5) {
				geneWidth = 1;
			}
		}
	}
	
	public boolean increment() {
		if (timePos < timeTitleList.size() - 1) {
			timePos++;
			this.repaint();
		}
		if (timePos == timeTitleList.size() - 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean decrement() {
		if (timePos > 0) {
			timePos--;
			this.repaint();
		}
		if (timePos == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	private void verifyGene(Point point, boolean isPopup) {
		boolean insideGene = false;
		for (GeneLineData gene : genesOfChromosome) {
			if (gene.getRectangle().contains(point)) {
				insideGene = true;				
				if (!isPopup) {
					CommonUtil.addToolTipText(gene,fileMetaData, this);					
				} else
					CommonUtil.addPopup(gene, this);
			} else {
				// this.setToolTipText(null);
			}
		}
		if (!insideGene) {
			// this.setToolTipText(null);
		}
	}

	private void addMouseListeners() {
		
		this.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
			}

			public void mouseMoved(MouseEvent e) {
				verifyGene(e.getPoint(), false);
			}
		});
		
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				verifyGene(e.getPoint(), true);
				//selectChromosome(e.getPoint());
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
	
	public BufferedImage zoomInChromosome(BufferedImage bi, int scale) {
		int width = (int) (bi.getWidth() * widthFactor);
		int height = scale * bi.getHeight();
		BufferedImage biScale = new BufferedImage(width, height, bi.getType());
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				biScale.setRGB(i, j,
						bi.getRGB((int) (i / widthFactor), j / scale));
		return biScale;
	}	
}
