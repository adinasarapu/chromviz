package edu.ucsd.genome.chromviz.testExamples.IO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class ImagePanel extends JPanel   
{   
   	private static final long serialVersionUID = 1L;
	BufferedImage image;   
    double scale;   
    
    public ImagePanel()   
    {   
        loadImage();   
        scale = 1.0;   
        setBackground(Color.black);   
    }   
    
    protected void paintComponent(Graphics g)   
    {   
        super.paintComponent(g);   
        Graphics2D g2 = (Graphics2D)g;   
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);   
        int w = getWidth();   
        int h = getHeight();   
        int imageWidth = image.getWidth();   
        int imageHeight = image.getHeight();   
        double x = (w - scale * imageWidth)/2;   
        double y = (h - scale * imageHeight)/2;   
        AffineTransform at = AffineTransform.getTranslateInstance(x,y);   
        at.scale(scale, scale);   
        g2.drawRenderedImage(image, at);   
    }   
    
    /**  
     * For the scroll pane.  
     */  
    public Dimension getPreferredSize()   
    {   
        int w = (int)(scale * image.getWidth());   
        int h = (int)(scale * image.getHeight());   
        return new Dimension(w, h);   
    }   
    
    public void setScale(double s)   
    {   
        scale = s;   
        revalidate();      // update the scroll pane   
        repaint();   
    }   
    
    private void loadImage()   
    {   
       // String fileName = "images/karyotype_mouse_new.png"; 
        String fileName = "C:/Users/sdinasarapu/Desktop/karyotype_mouse_new.png"; 
        try  
        {   
            URL url = getClass().getResource(fileName);   
            image = ImageIO.read(url);   
        }   
        catch(MalformedURLException mue)   
        {   
            System.out.println("URL trouble: " + mue.getMessage());   
        }   
        catch(IOException ioe)   
        {   
            System.out.println("read trouble: " + ioe.getMessage());   
        }   
    }   
} 