package edu.ucsd.genome.chromviz.testExamples.IO;
import java.awt.*;   
import java.awt.event.*;   
import java.awt.geom.*;   
import java.awt.image.BufferedImage;   
import java.io.*;   
import java.net.*;   
import javax.imageio.ImageIO;   
import javax.swing.*;   
import javax.swing.event.*;   

  
public class ZoomTest   
{   
    public static void main(String[] args)   
    {   
        ImagePanel panel = new ImagePanel();   
        ImageZoom zoom = new ImageZoom(panel);   
        JFrame f = new JFrame();   
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        f.getContentPane().add(zoom.getUIPanel(), "North");   
        f.getContentPane().add(new JScrollPane(panel));   
        f.setSize(400,400);   
        f.setLocation(200,200);   
        f.setVisible(true);   
    }   
}   
    
  
    
