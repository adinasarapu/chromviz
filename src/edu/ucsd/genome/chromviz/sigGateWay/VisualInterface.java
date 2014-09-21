package edu.ucsd.genome.chromviz.sigGateWay;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import org.xml.sax.SAXException;

import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import edu.ucsd.genome.chromviz.sigData.ChromosomeData;
import edu.ucsd.genome.chromviz.sigData.FileMetaData;
import edu.ucsd.genome.chromviz.sigDrawChrom.ChromosomeIdeogram;
import edu.ucsd.genome.chromviz.sigDrawChrom.ItemListenerPathWayImpl;
import edu.ucsd.genome.chromviz.sigKegg.KeggPathwaysSOAP;
import edu.ucsd.genome.chromviz.sigKegg.KeggXMLPathwayEntry;
import edu.ucsd.genome.chromviz.sigKegg.KeggXMLPathwayRelation;
import edu.ucsd.genome.chromviz.sigKegg.ReadXMLFileDOMParser;
import edu.ucsd.genome.chromviz.sigMyGraph.MyGraph;
import edu.ucsd.genome.chromviz.sigUtility.ImagePanel;
import edu.ucsd.genome.chromviz.sigUtility.ReadFileData;

public class VisualInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	public final static String IMAGE_BASE = "images/";
	public final static String KEGG_BASE = "kegg_xml/";
	int gridIdx = 2;
	int selectedLigandNumber = -1;	

	public VisualInterface() {
		
		// get contentPane, set BorderLayout
		Container contentPane = getContentPane();
		
		// create GridBagLayout object
		GridBagLayout gbag = new GridBagLayout();
		// set gridbag layout to jpanel
		JPanel northPane = new JPanel();
		JPanel southPane = new JPanel();
		northPane.setLayout(gbag);
		
		// radio buttons to select either micro array data upload or direct 
		// pathway visualization
		JLabel firstRadioLabel = new JLabel("Microarray Data");
		JLabel secondRadioLabel = new JLabel("Select Pathway ");
		//JLabel thirdRadioLabel = new JLabel("Refresh All         ");
		firstRadioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		secondRadioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		//thirdRadioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JRadioButton firstRadioButton = new JRadioButton("");
		JRadioButton secondRadioButton = new JRadioButton("");
		//JRadioButton thirdRadioButton = new JRadioButton("");
		JButton refreshButton = new JButton("Refresh");
		//refreshButton.setIcon(new ImageIcon("images/reload.png"));
		
		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(firstRadioButton);
		group.add(secondRadioButton);
		//group.add(refresh);
		
		// if radio button select is 'firstRadioButton' 
		final JButton dataFileSelectionButton = new JButton("Select Data File");
		final JFrame dataSelectionFrame = new JFrame("Select Data Frame");
		
		// if radio button select is 'secondRadioButton'
		// KEGG Pathway 'Name with ID' List
		//final List<String> pathWayNames = getKEGGPathwayListFromLocalXMLFiles(KEGG_BASE);
		
		Map<String, List<String>> xml_data_map = ReadXMLFileDOMParser.getKEGGPathwayWithCompleteGeneListFromLocalXMLFiles(KEGG_BASE);
		Set<String> pathWayNames =  xml_data_map.keySet();
		final JComboBox pathSelectComboBox = new JComboBox(pathWayNames.toArray());		
		// ComboBox manipulation in such a way that it allows pathway search search with first letter
		initPathSelectComboBoxModel(pathWayNames, pathSelectComboBox);
		
		// following buttons are also for north pane of main frame
		JButton dnaSeqButton = new JButton("GENOMIC Sequence");
		JButton proteinSeqButton = new JButton("PROTEIN Sequence");
		JButton proteinListButton = new JButton("Protein List");
		JButton exportImageButton = new JButton("Export Image");
		JButton networkButton = new JButton("Pathway Network");
		JButton xmlButton = new JButton("XML");
		
		// this array is used to select column number in the data file
		final String[] dataColumnNumber = { "", "1", "2", "3", "4", "5", "6", "7", "8" }; 
		// this array used to select no. of ligands used in data 
		final String[] ligandNumber = { "", "1", "2", "3","4","5"};
		final JComboBox geneIdSelectionBox = new JComboBox(dataColumnNumber);
		final JComboBox ligandSelectBox = new JComboBox(ligandNumber);
		
		// after number of ligands and time points selection the following 'add'
		// and 'delete' buttons are used. 'add' button is used for every new
		// time point selection, gives no of drop-downs based on number of
		// lignads selected. 'Delete' needs to be implemented
		final JButton addButton = new JButton("Add");
		final JButton removeButton = new JButton("Delete"); 

		// Once selection is over then upload 'tab-limited' file by user
		final JButton uploadFileButton = new JButton("Upload");
		
		// List of Maps 1, 2 or 3 based on selected Ligand Number
		// Each Ligand Map has List of values based on added time points for each gene
		
		final List<JTextField> ligandTextField = new ArrayList<JTextField>();
		final List<JTextField> timeTextField = new ArrayList<JTextField>();
		
		// Read file meta data and array data
		final ReadFileData fileData = new ReadFileData();
		final List<List<Integer>> listOfIndexList = new ArrayList<List<Integer>>();
		final List<String> timeTitleList = new ArrayList<String>();
		final FileMetaData fileMetaData = new FileMetaData();
		fileData.setFileMetaData(fileMetaData);
		
		//JMenuBar mb = new JMenuBar();
		//JMenu file = new JMenu("File");
		//mb.add(file);
		//JMenuItem open = new JMenuItem("Open");
		//file.add(open);

		// add radio buttons (firstRadioButton and secondRadioButton; radioLabel1 and radioLabel2)
		// add dataFileSelectionButton and pathSelectComboBox
		// add dnaSeqButton, proteinSeqButton, proteinListButton, exportImageButton, networkButton and xmlButton
		addButtonsToNorthPane(northPane, firstRadioButton, secondRadioButton,refreshButton,
				firstRadioLabel, secondRadioLabel,/*  thirdRadioLabel,*/ dataFileSelectionButton,
				pathSelectComboBox, networkButton, dnaSeqButton,
				proteinSeqButton, xmlButton, proteinListButton, exportImageButton);
		
		// SouthPane is again divided into two panes by adding two scrollpanes  
		final JScrollPane scrollSouthPane1 = new JScrollPane();
		final JScrollPane scrollSouthPane2 = new JScrollPane();
		
		addScrollPanesToSouthPane(southPane, scrollSouthPane1, scrollSouthPane2);		
		
		/*
		 * ItemListener implementation
		 * 
		 */
		ItemListenerPathWayImpl itemListener = new ItemListenerPathWayImpl(
				fileData,pathSelectComboBox, xml_data_map, scrollSouthPane1,
				scrollSouthPane2, dnaSeqButton, proteinSeqButton, proteinListButton,
				exportImageButton, networkButton, xmlButton, refreshButton);		
		
		pathSelectComboBox.addItemListener(itemListener);

		// first radio button selection
		firstRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				dataFileSelectionButton.setEnabled(true);
				pathSelectComboBox.setEnabled(false);
			}
		});
		
		// second radio button selection
		secondRadioButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						pathSelectComboBox.setEnabled(true);
						dataFileSelectionButton.setEnabled(false);
					}
				});
		
		// third radio button selection
		refreshButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				geneIdSelectionBox.setSelectedIndex(0);
				ligandSelectBox.setSelectedIndex(0);
				
				pathSelectComboBox.setEnabled(false);
				dataFileSelectionButton.setEnabled(false);
				
				VisualInterface.addTitlePane(scrollSouthPane1);
				VisualInterface.addTextPane(scrollSouthPane2);
			}			
		});
	/*	thirdRadioButton
		.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				geneIdSelectionBox.setSelectedIndex(0);
				ligandSelectBox.setSelectedIndex(0);
				
				pathSelectComboBox.setEnabled(false);
				dataFileSelectionButton.setEnabled(false);
				
				VisualInterface.addTitlePane(scrollSouthPane1);
				VisualInterface.addTextPane(scrollSouthPane2);
			}
		});*/

		
		 // File Selection Panel
		final JPanel fileSelectPane = new JPanel();
		final GridBagConstraints gridcons = new GridBagConstraints();
		
		/*
		 * file selection button
		 * 
		 */
		dataFileSelectionButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataSelectionFrame.setVisible(true);
				dataSelectionFrame.setResizable(true);
				dataSelectionFrame.setMinimumSize((new java.awt.Dimension(400,	150)));
				//dataSelectionFrame.setMaximumSize(new java.awt.Dimension(600,1000));
				dataSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
				geneIdSelectionBox.setMaximumRowCount(6);
				geneIdSelectionBox.setToolTipText("Select GeneID Column Number");
				geneIdSelectionBox.setEnabled(true);
				
				ligandSelectBox.setMaximumRowCount(4);
				ligandSelectBox.setEnabled(true);

				addButtonsToFileSelectionPane(gridcons, fileSelectPane,
						geneIdSelectionBox, ligandSelectBox, uploadFileButton,
						addButton);
				
				final Container selectFrameContent = dataSelectionFrame.getContentPane();
				selectFrameContent.add(fileSelectPane);
			}
		});
		
		dataFileSelectionButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				
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
		
		dataSelectionFrame.addWindowListener(new WindowListener() {
			public void windowClosed(WindowEvent e) {
			geneIdSelectionBox.setSelectedIndex(0);
			ligandSelectBox.setSelectedIndex(0);
			ligandTextField.clear();
			VisualInterface.addTitlePane(scrollSouthPane1);
			VisualInterface.addTextPane(scrollSouthPane2);
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				//dataSelectionFrame.setSize((new java.awt.Dimension(300,150)));
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});	
		
		
		/*
		 * 
		 * 
		 */
		geneIdSelectionBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent ievent) {
				if (ievent.getStateChange() == ItemEvent.SELECTED) {
					uploadFileButton.setEnabled(true);
				}
			}
		});

		/*
		 * drop down box for ligand number selection
		 * 
		 */
		ligandSelectBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent ievent) {
				if (ievent.getStateChange() == ItemEvent.SELECTED) {
					selectedLigandNumber = ligandSelectBox.getSelectedIndex();
					ligandSelectBox.setEnabled(false);
					addButton.setEnabled(true);					
					for(int j = 0; j < selectedLigandNumber; j++){
						JTextField columnText = new JTextField(5);	
						columnText.setBackground(new Color(146, 223, 227));
						columnText.setText("L"+(j+1));
						gridcons.gridx = (j+1);
						gridcons.gridy = gridIdx;
						gridcons.anchor = GridBagConstraints.WEST;
						ligandTextField.add(columnText);
						fileSelectPane.add(columnText, gridcons);
					}
					gridIdx = gridIdx + selectedLigandNumber;
				}
			}
		}); 		
		
	
		/*
		 * adds drop down buttons for each time point selected
		 * 
		 * 
		 */
		addButton.addActionListener(new java.awt.event.ActionListener() {
			List<Integer> list = new ArrayList<Integer>();
			int x = 1;
			int width = 400;
			int height = 180;
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Add")) {
					uploadFileButton.setEnabled(true);					
					List<JComboBox> clmNumBoxes = new ArrayList<JComboBox>();
					dataSelectionFrame.setMinimumSize((new java.awt.Dimension(width+(80*selectedLigandNumber),height))); // default 400 by 150
					height = height+25;
					
					for(int i = 0; i < selectedLigandNumber; i++){						
						clmNumBoxes.add(new JComboBox(dataColumnNumber));												
					}
					
					final JTextField timeTitleTextField = new JTextField(4);
					timeTitleTextField.setBackground(new Color(146, 223, 227));
					timeTitleTextField.setToolTipText("Enter column title and press ENTER");				
					timeTitleTextField.setText("T"+x);
					x++;
					timeTextField.add(timeTitleTextField);
					//timeTitleList.add(timeTitleTextField.getText());
					//System.out.println("clmNumBoxes1 = "+clmNumBoxes.size());
					
					addLigandNumberOfComboBoxes(selectedLigandNumber, gridcons, gridIdx, clmNumBoxes, timeTitleTextField, addButton,uploadFileButton, removeButton, fileSelectPane);
					
					for(int j = 0; j < clmNumBoxes.size(); j++){						
						final JComboBox cb = clmNumBoxes.get(j); 
						cb
						.addItemListener(new java.awt.event.ItemListener() {
							public void itemStateChanged(ItemEvent ie) {
								if (ie.getStateChange()  == ItemEvent.SELECTED) {
									//System.out.println("what is this "+ (cb.getSelectedIndex() - 1));									
									list.add(cb.getSelectedIndex() - 1);									
									if(list.size() == selectedLigandNumber){ 
										listOfIndexList.add(list);
										//System.out.println("Inner list ("+selectedLigandNumber+") = "+list.toString());
										list = new ArrayList<Integer>();
									}
								} else{
									//System.out.print("select it!");
								}
							}
						});												
					}
	
					dataSelectionFrame.addWindowListener(new WindowListener() {
						public void windowClosed(WindowEvent e) {
							x = 1;	
						}

						@Override
						public void windowActivated(WindowEvent e) {
						}

						@Override
						public void windowClosing(WindowEvent e) {
						}

						@Override
						public void windowDeactivated(WindowEvent e) {
						}

						@Override
						public void windowDeiconified(WindowEvent e) {
						}

						@Override
						public void windowIconified(WindowEvent e) {
						}

						@Override
						public void windowOpened(WindowEvent e) {
						}
					});					
				}
			}
			
			private void addLigandNumberOfComboBoxes(int selectedLigandNumber,
					GridBagConstraints gridcons, int gridIdx_yy,
					List<JComboBox> clmNumBoxes,
					JTextField columnTextField, JButton addButton,
					JButton uploadFileButton, JButton removeButton, JPanel fileSelectPane) {

				gridcons.gridx = 0;
				gridcons.gridy = gridIdx;
				gridcons.fill = GridBagConstraints.HORIZONTAL;
				fileSelectPane.add(columnTextField, gridcons);

				for (int i = 0; i < selectedLigandNumber; i++) {
					gridcons.gridx = (i + 1);
					gridcons.gridy = gridIdx;
					//System.out.println("clmNumBoxes2 = "+clmNumBoxes.get(i));
					JComboBox box = clmNumBoxes.get(i);
					fileSelectPane.add(box, gridcons);
				}
				
				gridcons.gridx = selectedLigandNumber+1;
				gridcons.gridy = gridIdx;
				fileSelectPane.add(addButton, gridcons);

				gridcons.gridx = selectedLigandNumber+2;
				gridcons.gridy = gridIdx;
				fileSelectPane.add(uploadFileButton, gridcons);

				gridcons.gridx = selectedLigandNumber+3;
				gridcons.gridy = gridIdx;
				fileSelectPane.add(removeButton, gridcons);
				removeButton.setEnabled(false);
			
				gridIdx = gridIdx_yy+2;
			}
		});
		
//		removeButton.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				if (ae.getActionCommand().equals("Remove")) {
//					// selectionPane.remove(clmNumBox);
//					// selectionPane.remove(columnNameTextField);
//					selectionPane.remove(addButton);
//					selectionPane.remove(removeButton);
//					yy = yy - 4;
//					if (lists.size() != 0) {
//						lists.remove(lists.size() - 1);
//						gridcons.gridx = 3;
//						gridcons.gridy = yy;
//						selectionPane.add(removeButton, gridcons);
//						gridcons.gridx = 2;
//						gridcons.gridy = yy;
//						selectionPane.add(addButton, gridcons);
//						System.out.println("yy= " + yy);
//						if (selectionPane.contains(1, yy)) {
//							System.out.println("yy inside = " + yy);
//							selectionPane.remove(clmNumBox);
//							selectionPane.remove(columnNameTextField);
//							selectionPane.validate();
//						}
//						// addRemoveSelectionRow(yy);
//						// removeMethod(yy);
//						selectionPane.revalidate();
//						selectionPane.repaint();
//					}
//					System.out.println("Lists size in remove button listener "
//							+ lists.size());
//					yy = yy + 2;
//					if (lists.size() == 0) {
//						selectionPane.remove(removeButton);
//						gridcons.gridx = 3;
//						gridcons.gridy = 0;
//						selectionPane.add(addButton, gridcons);
//					}
//				}
//			}
//
//			private void removeMethod(int yyyy) {
//				System.out.println("inside remove method");
//				// selectionPane.remove(yyyy);
//				selectionPane.remove(clmNumBox);
//				// selectionPane.remove(yyyy);
//				selectionPane.remove(columnNameTextField);
//				selectionPane.validate();
//			}
//		});
		uploadFileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileopen = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("text files","txt");
				fileopen.addChoosableFileFilter(filter);
				int ret = fileopen.showDialog(null, "Select file");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fileopen.getSelectedFile();
					
					fileData.setLigandNumber(selectedLigandNumber);
					fileData.setTimePointsSelected(timeTitleList.size());
					
					fileMetaData.setLigandsSelected(selectedLigandNumber);
					fileMetaData.setTimePointsSelected(timeTitleList.size());
					addLigandTitleListForFileMetaData();				
					addTimeTitleListForFileMetaData();
					
					Map<String, List<List<String>>> mapFileData = new HashMap<String, List<List<String>>>();
					collectAllLigandDataForAGivenTime(mapFileData, file, listOfIndexList);
					fileData.setArrayData(mapFileData);
			}
			pathSelectComboBox.setEnabled(true);
			dataSelectionFrame.dispose();
			}

			private void addLigandTitleListForFileMetaData() {
				Iterator<JTextField> it = ligandTextField.iterator();
				List<String> ligandTitleList = new ArrayList<String>();
				while(it.hasNext()){
					JTextField tf = it.next();
					ligandTitleList.add(tf.getText());
				}
				fileMetaData.setLigandTitleList(ligandTitleList);
			}
			
			private void addTimeTitleListForFileMetaData() {
				Iterator<JTextField> it = timeTextField.iterator();
				List<String> timeTitleList = new ArrayList<String>();
				while(it.hasNext()){
					JTextField tf = it.next();
					timeTitleList.add(tf.getText());
				}
				fileMetaData.setTimeTitleList(timeTitleList);
			}

			private void collectAllLigandDataForAGivenTime(Map<String, List<List<String>>> mapFileData,
					File file, List<List<Integer>> indexlist) {
				try {
					BufferedReader in = new BufferedReader(new FileReader(file));
					String str = null;
					while ((str = in.readLine()) != null) {
						if (str.contains("\t")) {
							String[] valStrArray = str.split("\t");
							String geneidValue = valStrArray[geneIdSelectionBox.getSelectedIndex() - 1];
							List<List<String>> list_out = new ArrayList<List<String>>();
							Iterator<List<Integer>> it1 = indexlist.iterator();														
							while(it1.hasNext()){
								List<Integer> list1 = it1.next();
								Iterator<Integer> it2 = list1.iterator();
								List<String> list_in = new ArrayList<String>();
								while(it2.hasNext()){									
									Integer value1 = it2.next();
									list_in.add(valStrArray[value1]);									
								}
								//System.out.println("list_in size :"+list_in.toString());
								list_out.add(list_in);
							}
							//System.out.println("list_out size :"+list_out.toString());
							mapFileData.put(geneidValue, list_out);
						} else {
							//System.out.println("Data should be in tab limited format!");
						}						
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		dnaSeqButton.addActionListener(new java.awt.event.ActionListener() {
			StringBuffer buffer = null;

			public void actionPerformed(ActionEvent ae) {
				JTextArea dnaSeq = new JTextArea();
				String ID = ItemListenerPathWayImpl.getPathID();
				if (ae.getActionCommand().equals("GENOMIC Sequence")) {
					try {
						if (buffer == null) {
							buffer = KeggPathwaysSOAP.getDnaSeqFromKEGG(ID);
						}

					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (ServiceException e) {
						e.printStackTrace();
					}
					dnaSeq.append(buffer.toString());
					scrollSouthPane2.setViewportView(dnaSeq);
				}
			}
		});
		proteinSeqButton.addActionListener(new java.awt.event.ActionListener() {
			StringBuffer buffer = null;

			public void actionPerformed(ActionEvent ae) {
				JTextArea sequences = new JTextArea();
				String pathID = ItemListenerPathWayImpl.getPathID();
				if (ae.getActionCommand().equals("PROTEIN Sequence")) {
					try {
						if (buffer == null) {
							buffer = KeggPathwaysSOAP
									.getProteinSeqFromKEGG(pathID);
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (ServiceException e) {
						e.printStackTrace();
					}
					sequences.append(buffer.toString());
					scrollSouthPane2.setViewportView(sequences);
				}
			}
		});
		proteinListButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						JTextArea ta = new JTextArea();
						StringBuffer buffer = new StringBuffer();
						if (ae.getActionCommand().equals("Protein List")) {
							
							List<ChromosomeData> chromData = ChromosomeIdeogram
									.getProteinList();
							for (ChromosomeData data : chromData) {
								List<String> genes = data.getGeneSymbol();
								String chr = data.getChromosomeName();
								buffer.append(chr);
								buffer.append("\t");
								for (String gene : genes) {
									if (buffer.indexOf(gene) == -1) {
										buffer.append(gene);
										buffer.append(",  ");
									}
								}
								buffer.append("\n");
							}
						}
						ta.append(buffer.toString());
						//System.out.println(buffer.toString());
						scrollSouthPane2.setViewportView(ta);
						buffer.delete(0, buffer.length());
					}// action performed
				});
		exportImageButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (ae.getActionCommand().equals("Export Image")) {
							JFileChooser jfc = new JFileChooser();
							int result = jfc.showSaveDialog(scrollSouthPane1);
							if (result == JFileChooser.CANCEL_OPTION)
								return;
							File file = jfc.getSelectedFile();
							int w = scrollSouthPane1.getWidth();
							int h = scrollSouthPane1.getHeight();
							BufferedImage exportImg = new BufferedImage(w, h,
									BufferedImage.TYPE_INT_BGR);
							Graphics2D g2 = exportImg.createGraphics();
							scrollSouthPane1.paint(g2);
							g2.dispose();
							try {
								ImageIO.write(exportImg, "png", file);
								exportImg.flush();
							} catch (IOException ie) {
							}
						}// if
					}
				});

		xmlButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String pathName = ItemListenerPathWayImpl.getPathName();
				String pathID = ItemListenerPathWayImpl.getPathID();
				if (ae.getActionCommand().equals("XML")) {
					JFileChooser jfc = new JFileChooser();
					int result = jfc.showSaveDialog(scrollSouthPane1);
					if (result == JFileChooser.CANCEL_OPTION)
						return;
					File file = jfc.getSelectedFile();
					try {
						KeggPathwaysSOAP.exportPathwayDataAsXML(pathName,
								pathID, file);
					} catch (ServiceException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		networkButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String pathID = ItemListenerPathWayImpl.getPathID();
				List<KeggXMLPathwayRelation> relations = null;
				// now different networks for different pathways
				if (ae.getActionCommand().equals("Pathway Network")) {
					//System.out.println(pathID + " Graph is Loading, PLease Wait ....................");
					if (relations == null) {
						relations = ReadXMLFileDOMParser.getKeggXMLPathwayRelations(pathID);
					}
					//System.out.println("relationsSize: " + relations.size());
					MyGraph.createGraph(relations);
				}
			}
		});
		contentPane.add(northPane, BorderLayout.WEST);
		contentPane.add(southPane, BorderLayout.SOUTH);

		// set Icon
		setIconImage(Toolkit.getDefaultToolkit().createImage(
				"IMAGE_BASE/bio_flygen.gif"));

		// open button
		ImageIcon openImage = new ImageIcon("IMAGE_BASE/open_folder.gif");
		JButton openButton = new JButton();
		openButton.setIcon(openImage);
		openButton.setText("My Web Page.");
		openButton.setBorder(null);
		openButton.setMinimumSize(new Dimension(30, 30));
		openButton.setMaximumSize(new Dimension(30, 30));
		openButton.setPreferredSize(new Dimension(30, 30));
		openButton.setToolTipText("Open Help File");
		// openButton ActionListener
		openButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openButton_actionPerformed(e);
			}
		});
		// exit button
		ImageIcon exitImage = new ImageIcon("IMAGE_BASE/exit.gif");
		JButton exitButton = new JButton();
		exitButton.setIcon(exitImage);
		exitButton.setText("Exit");
		exitButton.setBorder(null);
		exitButton.setMinimumSize(new Dimension(30, 30));
		exitButton.setMaximumSize(new Dimension(30, 30));
		exitButton.setToolTipText("Exit ChromViz Tool");
		// exitButton ActionListener
		exitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitButton_actionPerformed(e);
			}
		});
		
		dataSelectionFrame.addWindowListener(new WindowListener() {
			public void windowClosed(WindowEvent e) {
				//yy = 2;
				fileSelectPane.removeAll();
				//System.out.println("File selection window closed!");						
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
		
	} // end of visualinterface class constructor
		

	

	Vector<String> model = new Vector<String>();
	private boolean hide_flag = false;
	JTextField field = null; // pathway selection, in drop-down combo-box
	
	/*
	 * 
	 * 
	 */
	private void initPathSelectComboBoxModel(Set<String> pathWays,
			final JComboBox pathSelectComboBox) {
		pathSelectComboBox.setMaximumRowCount(10);
		pathSelectComboBox.setSelectedIndex(0);
		pathSelectComboBox.setEditable(true);

		field = (JTextField) pathSelectComboBox.getEditor()
				.getEditorComponent();
		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				keyTypedInCombo(e, pathSelectComboBox);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				keyPressedInCombo(e, pathSelectComboBox);
			}
		});

		Object[] v = pathWays.toArray();
		for (int i = 0; i < v.length; i++) {
			model.addElement((String) v[i]);
		}
		// Collections.sort(model);
		setModel(new DefaultComboBoxModel(model), "", pathSelectComboBox);
	}

	/*
	 * 
	 * 
	 */
	private void keyTypedInCombo(final KeyEvent ke, final JComboBox comboBox) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String text = field.getText();
				// System.out.println(String.format("|typed|%02d|%s",0,text));
				if (text.length() == 0) {
					comboBox.hidePopup();
					setModel(new DefaultComboBoxModel(model), "", comboBox);
				} else {
					DefaultComboBoxModel m = getSuggestedModel(model, text);
					if (m.getSize() == 0 || hide_flag) {
						comboBox.hidePopup();
						hide_flag = false;
					} else {
						setModel(m, text, comboBox);
						comboBox.showPopup();
					}
				}
			}
		});
	}

	/*
	 * 
	 */
	private void keyPressedInCombo(KeyEvent ke, final JComboBox comboBox) {
		String text = field.getText();
		int code = ke.getKeyCode();
			if (code == KeyEvent.VK_ENTER) {
			if (!model.contains(text)) {
				model.addElement(text);
				Collections.sort(model);
				setModel(getSuggestedModel(model, text), text, comboBox);
			}
			hide_flag = true; // combo.hidePopup();
		} else if (code == KeyEvent.VK_ESCAPE) {
			hide_flag = true; // combo.hidePopup();
		} else if (code == KeyEvent.VK_RIGHT) {
			for (int i = 0; i < model.size(); i++) {
				String str = model.elementAt(i);
				if (str.startsWith(text)) {
					comboBox.setSelectedIndex(-1);
					field.setText(str);
					return;
				}
			}
		}
	}

	/*
	 * 
	 */
	@SuppressWarnings({ })
	private static DefaultComboBoxModel getSuggestedModel(
			java.util.List<String> list, String text) {
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		for (String s : list) {
			if (s.toUpperCase().startsWith(text.toUpperCase()))
				m.addElement(s);
		}
		return m;
	}
	
	/*
	 * 
	 * 
	 */
	@SuppressWarnings({ })
	private void setModel(DefaultComboBoxModel mdl, String str,
			final JComboBox comboBox) {
		comboBox.setModel(mdl);
		comboBox.setSelectedIndex(-1);
		field.setText(str);
	}
	
	/*
	 * 
	 * 
	 */
	protected void addButtonsToFileSelectionPane(GridBagConstraints gridcons,
			JPanel selectionPane, JComboBox geneIdSelectionBox,
			JComboBox ligandSelectBox, JButton uploadButton, JButton addButton) {
		
		JLabel labelColumnName = new JLabel("Gene Identifier");
		JLabel labelColumnNumber = new JLabel("Column");
		JLabel labelNoOfLegands = new JLabel("Ligand");
		
		selectionPane.setMaximumSize(new java.awt.Dimension(600,1000));
		selectionPane.setMinimumSize(new java.awt.Dimension(500, 200));
		selectionPane.validate();

		GridBagLayout gridBag = new GridBagLayout();
		selectionPane.setBackground(Color.white);
		
		selectionPane.setLayout(gridBag);
		
		// add labelColumnName at 0,0
		gridcons.gridx = 0;
		gridcons.gridy = 0;
		gridcons.anchor = GridBagConstraints.WEST;
		gridcons.insets = new Insets(0, 0, 2, 5);
		selectionPane.add(labelColumnName, gridcons);
		labelColumnName.setVisible(true);

		// add labelColumnNumber at 1,0
		gridcons.gridx = 1;
		gridcons.gridy = 0;
		gridcons.anchor = GridBagConstraints.WEST;
		gridcons.insets = new Insets(0, 0, 2, 5);
		//gridcons.insets = new Insets(0, 10, 0, 0);
		selectionPane.add(labelColumnNumber, gridcons);
		labelColumnNumber.setVisible(true);

		// add uploadButton at 2,0
		gridcons.gridx = 2;
		gridcons.gridy = 0;
		gridcons.anchor = GridBagConstraints.WEST;
		gridcons.insets = new Insets(0, 0, 2, 5);
		//gridcons.insets = new Insets(2, 2, 2, 2);
		selectionPane.add(labelNoOfLegands, gridcons);
		uploadButton.setEnabled(true);

		// add addButton at 3,0
		gridcons.gridx = 4;
		gridcons.gridy = 1;
		gridcons.anchor = GridBagConstraints.WEST;
		gridcons.insets = new Insets(0, 0, 2, 5);
		//gridcons.insets = new Insets(2, 2, 2, 2);
		selectionPane.add(uploadButton, gridcons);
		addButton.setEnabled(true);

		// Input file GeneID Column Number
		//JTextField rowIdentifierText = new JTextField(4); 
		String[] geneIdentifier = { "Entez ID", "ENSEMBL ID"};
		JComboBox identifierSelectBox = new JComboBox(geneIdentifier);
		
		// add labelGeneId at 0,1
		gridcons.gridx = 0;
		gridcons.gridy = 1;
		gridcons.anchor = GridBagConstraints.WEST;
		//rowIdentifierText.setText("Gene ID");
		gridcons.insets = new Insets(0, 0, 2, 5);
		//rowIdentifierText.setEditable(true);
		//selectionPane.add(rowIdentifierText, gridcons);
		selectionPane.add(identifierSelectBox, gridcons);
		
		// add gIdBox at 1,1
		gridcons.gridx = 1;
		gridcons.gridy = 1;
		gridcons.anchor = GridBagConstraints.WEST;
		gridcons.insets = new Insets(0, 0, 2, 5);
		selectionPane.add(geneIdSelectionBox, gridcons);
		
		// add legandsBox at 3,1
		gridcons.gridx = 2;
		gridcons.gridy = 1;
		gridcons.anchor = GridBagConstraints.WEST;
		gridcons.insets = new Insets(0, 0,2, 5);
		selectionPane.add(ligandSelectBox, gridcons);
		
		// add label NoOfLegands at 2,1
		gridcons.gridx = 3;
		gridcons.gridy = 1;
		gridcons.anchor = GridBagConstraints.WEST;
		gridcons.insets = new Insets(0, 0, 2, 5);
		selectionPane.add(addButton, gridcons);

		//selectionPane.add(uploadButton, gridcons);
		// removeButton.setVisible(false);
		// southPanel.revalidate();

		uploadButton.setEnabled(false);
		addButton.setEnabled(false);
		addButton.setToolTipText("Add another column");
		//selectionPane.validate();
		//removeButton.setToolTipText("Remove current column");

	}

	/*
	 * toDo: dimentions should be changed accoring to users screen size 
	 */
	
	private void addScrollPanesToSouthPane(JPanel southPane,
			JScrollPane scrollPane1, JScrollPane scrollPane2) {
		
		southPane.setMaximumSize(new java.awt.Dimension(900, 600));
		southPane.setMinimumSize(new java.awt.Dimension(900, 600));
		southPane.setPreferredSize(new java.awt.Dimension(900, 530));

		// Size scrollPane1 - leftPane size doesn't matter since it's the
		// scrollable view
		scrollPane1.setMaximumSize(new java.awt.Dimension(900, 400));
		scrollPane1.setMinimumSize((new java.awt.Dimension(900, 400)));
		scrollPane1.setPreferredSize(new java.awt.Dimension(900, 300));
		// scrollPane1.setBorder(BorderFactory.createLineBorder(Color.black));
		scrollPane1
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane1
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		scrollPane2.setMaximumSize(new java.awt.Dimension(900, 300));
		scrollPane2.setMinimumSize((new java.awt.Dimension(900, 300)));
		scrollPane2.setPreferredSize(new java.awt.Dimension(900, 200));
		scrollPane2
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		southPane.setLayout(new GridBagLayout());
		southPane.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));
		southPane.add(scrollPane2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));
		// important to call revalidated here so southPane will notify
		// scrollPaneDown to update
		addTitlePane(scrollPane1);
		addTextPane(scrollPane2);
		southPane.revalidate();
	}
	
	/*
	 * 
	 * 
	 */
	private static void addTitlePane(JScrollPane scrollSouthPane1){
		ImagePanel ip = new ImagePanel();
		//ip.setToolTipText("ChromViz 1.0 is a visualization tool for the high-throughput Data on Chromosomes");
		//ip.setMinimumSize(new java.awt.Dimension(100, 100));
		ip.setOpaque(true);
		scrollSouthPane1.setViewportView(ip);
	}
	
	/*
	 * 
	 * 
	 */	
	public static void addTextPane(JScrollPane scrollSouthPane2){
			
		JTextField textField2 = new JTextField();
		Font font2 = new Font("Verdana", Font.BOLD, 18);
		textField2.setFont(font2);
		textField2.setHorizontalAlignment(JTextField.CENTER);
		textField2.setForeground(Color.DARK_GRAY);
		textField2.setText("ChromViz 1.0 - a visualization tool for the high-throughput data on Chromosomes");
		textField2.setEditable(false);
		textField2.setOpaque(false); 
		textField2.setBorder(BorderFactory.createEmptyBorder()); 
		scrollSouthPane2.setViewportView(textField2);
	}
	
	/*
	 * 
	 * 
	 */
	private void addButtonsToNorthPane(JPanel northPane,
			JRadioButton firstRadioButton, JRadioButton secondRadioButton, JButton refreshButton,
			JLabel labelMicroArray, JLabel labelPathway, /*JLabel labelRefresh,*/
			JButton dataSelectButton, JComboBox comboBoxPathwayList,
			JButton networkButton, JButton dnaSeqButton, JButton proteinSeqButton,
			JButton xmlButton, JButton proteinListButton, JButton exportImageButton) {
		// create gridbag constraints object
		GridBagConstraints gcons = new GridBagConstraints();
		// for all buttons display horizontal filling
		gcons.fill = GridBagConstraints.BOTH;// HORIZONTAL;
		//gcons.weightx = 0;
		//gcons.weighty = 0;
		// display firstRadioButton at x,y coordinates0,0
		gcons.gridx = 0;
		gcons.gridy = 0;
		//gcons.anchor = GridBagConstraints.WEST;
		gcons.insets = new Insets(2, 2, 2, 2);
		northPane.add(firstRadioButton, gcons);
		// display secondRadioButton at x,y coordinates 0,1
		gcons.gridx = 0;
		gcons.gridy = 1;
		//gcons.anchor = GridBagConstraints.WEST;
		gcons.insets = new Insets(2, 2, 2, 2);
		northPane.add(secondRadioButton, gcons);
		
		gcons.gridx = 2;
		gcons.gridy = 2;
		//gcons.anchor = GridBagConstraints.WEST;
		gcons.insets = new Insets(2, 2, 2, 2);
		refreshButton.setEnabled(false);
		northPane.add(refreshButton, gcons);
		
		
		// display labelMicroArray at x,y coordinates 2,0
		gcons.gridx = 2;
		gcons.gridy = 0;
		//gcons.anchor = GridBagConstraints.WEST;
		gcons.insets = new Insets(2, 2, 2, 2);
		northPane.add(labelMicroArray, gcons);
		
		// display selectButton at x,y coordinates 3,0
		gcons.gridx = 3;
		gcons.gridy = 0;
		//gcons.anchor = GridBagConstraints.WEST;
		gcons.insets = new Insets(2, 2, 2, 2);
		northPane.add(dataSelectButton, gcons);
		dataSelectButton.setEnabled(false);
		//uploadButton.setEnabled(false);

		// display labelPathway at x,y coordinates 2,1
		gcons.gridx = 2;
		gcons.gridy = 1;
		//gcons.anchor = GridBagConstraints.WEST;
		gcons.insets = new Insets(2, 2, 2, 2);
		northPane.add(labelPathway, gcons);

		/*gcons.gridx = 2;
		gcons.gridy = 2;
		gcons.anchor = GridBagConstraints.WEST;
		gcons.insets = new Insets(2, 2, 2, 2);
		northPane.add(labelRefresh, gcons);*/
		
		
		// display combobox at x,y cordinates 3,1
		gcons.gridx = 3;
		gcons.gridy = 1;
		gcons.insets = new Insets(2, 2, 2, 2);
		northPane.add(comboBoxPathwayList, gcons);
		comboBoxPathwayList.setEnabled(false);
		// display networkButton at x,y cordinates 6,0
		gcons.gridx = 6;
		gcons.gridy = 0;
		networkButton.setEnabled(false);
		gcons.insets = new Insets(0, 0, 0, 0);
		northPane.add(networkButton, gcons);
		// display dnaSeqButton at x,y cordinates 5,0
		gcons.gridx = 5;
		gcons.gridy = 0;
		gcons.insets = new Insets(0, 0, 0, 0);
		dnaSeqButton.setEnabled(false);
		northPane.add(dnaSeqButton, gcons);
		// display proteinSeqButton at x,y cordinates 5,1
		gcons.gridx = 5;
		gcons.gridy = 1;
		proteinSeqButton.setEnabled(false);
		northPane.add(proteinSeqButton, gcons);
		// display xmlButton at x,y cordinates 6,1
		gcons.gridx = 6;
		gcons.gridy = 1;
		xmlButton.setEnabled(false);
		northPane.add(xmlButton, gcons);
		// display proteinListButton at x,y cordinates 5,2
		gcons.gridx = 5;
		gcons.gridy = 2;
		proteinListButton.setEnabled(false);
		northPane.add(proteinListButton, gcons);
		// display exportImageButton at x,y cordinates 6,2
		gcons.gridx = 6;
		gcons.gridy = 2;
		gcons.insets = new Insets(0, 0, 0, 0);
		exportImageButton.setEnabled(false);
		northPane.add(exportImageButton, gcons);
		/*VisualInterface.addTitlePane(scrollSouthPane1);
		VisualInterface.addTextPane(scrollSouthPane2); */
		gcons.gridx = 7;
		gcons.gridy = 0;
		gcons.insets = new Insets(0, 0, 0, 0);
		//exportImageButton.setEnabled(false);
		
		JTextField textField1 = new JTextField();
		textField1.setHorizontalAlignment(JTextField.RIGHT);
		textField1.setText(" ");
		textField1.setEditable(false);		
		textField1.setOpaque(false); 
		textField1.setBorder(BorderFactory.createEmptyBorder());
		northPane.add(textField1, gcons);
		
		gcons.gridx = 8;
		gcons.gridy = 1;
		JTextField textField = new JTextField();
		Font font = new Font("Verdana", Font.BOLD, 30);
		textField.setFont(font);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setForeground(Color.MAGENTA);
		textField.setText("           ChromViz 1.0 ");
		textField.setEditable(false);
		textField.setOpaque(false); 
		textField.setBorder(BorderFactory.createEmptyBorder()); 
		northPane.add(textField, gcons);

	}

	/*
	 * 
	 * 
	 */
	protected List<String> getAllDataListForASelectedLigand(String[] str,
			List<Integer> listOfLegand1) {
		List<String> ret = new ArrayList<String>();
		Iterator<Integer> it = listOfLegand1.iterator();
		while (it.hasNext()) {
			int n = it.next();
			ret.add(str[n]);
		}
		return ret;
	}

	/*
	 * 
	 * 
	 */
	public static int[] convertIntegers(List<Integer> numbers) {
		int[] numArray = new int[numbers.size()];
		for (int i = 0; i < numArray.length; i++) {
			numArray[i] = numbers.get(i).intValue();
		}
		return numArray;
	}

	/*
	 * open a help file
	 */
	void openButton_actionPerformed(ActionEvent e) {
		Runtime r = Runtime.getRuntime();
		try {
			// open the text document
			String[] cmd = { "firefox", "www.flygenics.com" };
			Process p = r.exec(cmd);
			//System.out.println("" + p);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/*
	 * 
	 * exit the program and close the database
	 */
	void exitButton_actionPerformed(ActionEvent e) {
		// exit the program and close
		// DatabaseConnection.closeConnection();
		//System.out.println("Goodbye, -Exit Button");
		System.exit(0);
	}

	/*
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.JFrame#processWindowEvent(java.awt.event.WindowEvent)
	 */
	protected void processWindowEvent(WindowEvent we) {
		// super.processWindowEvent(WindowEvent we);
		if (we.getID() == WindowEvent.WINDOW_CLOSING) {
			// DatabaseConnection.closeConnection();
			//System.out.println("Goodbye, -Closing Window");
			System.exit(0);
		}
	}

}
