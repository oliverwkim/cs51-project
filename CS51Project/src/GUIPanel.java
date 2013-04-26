<<<<<<< HEAD
import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIPanel extends JPanel {
	
	private int gridX;
	private int gridY;
	private Node[] path;
	private Grid grid;
	
	private int diameter;
	private int margin;
	private int padding;
	
	public GUIPanel (Grid g, Node[] inputPath, int d, int p, int m)
	{
		gridX = g.getX();
		gridY = g.getY();
		path = inputPath;
		grid = g;
		diameter = d;
		margin = m;
		padding = p;
	}
	
	public void paintComponent(Graphics p)
	{
		super.paintComponent(p);
		for(int i = 0; i < gridY; i++)
		{
			for(int j = 0; j < gridX; j++)
			{
				p.drawOval(padding + margin * i, padding + margin * j, diameter, diameter);
				
				if(grid.getNode(i,j).connectionExists(grid.getNode(i+1, j)))
				{
					p.drawLine(padding + margin * i + diameter, padding + j * margin + (diameter / 2), padding + margin * i + margin, padding + j * margin + (diameter / 2));
				}
				
				if(grid.getNode(i,j).connectionExists(grid.getNode(i,j+1))){
					p.drawLine(padding + margin * i + (diameter / 2), padding + j * margin + diameter, padding + margin * i + (diameter / 2), padding + margin * j + margin);
				}
				
				if(grid.getNode(i,j).connectionExists(grid.getNode(i+1,j+1))){
					int edge = (int) ((float) (diameter / 2) / Math.sqrt(2.0));
					int dist = (margin - 2 * edge);
					int x1 = edge + padding + (diameter / 2) + margin * i;
					int y1 = edge + padding + (diameter / 2) + margin * j;
					int x2 = edge + padding + (diameter / 2) + margin * i + dist;
					int y2 = edge + padding + (diameter / 2) + margin * j + dist;;
					p.drawLine(x1, y1, x2, y2);
				} 
				
				if(grid.getNode(i,j).connectionExists(grid.getNode(i-1,j+1))){
					int edge = (int) ((float) (diameter / 2) / Math.sqrt(2.0));
					int dist = (margin - 2 * edge);
					int x1 = padding + (diameter / 2) + margin * i - edge;
					int y1 = edge + padding + (diameter / 2) + margin * j;
					int x2 = padding + (diameter / 2) + margin * i - dist - edge;
					int y2 = edge + padding + (diameter / 2) + margin * j + dist;
					p.drawLine(x1, y1, x2, y2);
				}
				
				if (path[0].equals(grid.getNode(i,j)))
				{
					p.setColor(Color.green);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
					p.setColor(Color.black);
				} 
				else if (path[path.length - 1].equals(grid.getNode(i,j)))
				{
					p.setColor(Color.red);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
					p.setColor(Color.black);
				} 
				else if (Arrays.asList(path).contains(grid.getNode(i,j)))
				{
					p.setColor(Color.blue);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
					p.setColor(Color.black);
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		int padding = 10;
		int diameter = 30;
		int margin = diameter + 20;
		
		JFrame f = new JFrame();
		Grid g = new Grid(15,15);
		g.createRandom(new Point(0,0), new Point(14,14)));
		f.setSize(padding * 2 + margin * g.getX(), padding * 3 + margin * g.getY());
		Node[] path = AStar.algorithm(g, g.getNode(0,0), g.getNode(14,14));
		f.setContentPane(new GUIPanel(g,path,diameter,padding,margin));
		f.setVisible(true);
	}
}
=======
import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIPanel extends JPanel {
	
	private int gridX;
	private int gridY;
	private Node[] path;
	private Grid grid;
	
	private int diameter;
	private int margin;
	private int padding;
	
	public GUIPanel (Grid g, Node[] inputPath, int d, int p, int m)
	{
		gridX = g.getX();
		gridY = g.getY();
		path = inputPath;
		grid = g;
		diameter = d;
		margin = m;
		padding = p;
	}
	
	public void paintComponent(Graphics p)
	{
		super.paintComponent(p);
		for(int i = 0; i < gridY; i++)
		{
			for(int j = 0; j < gridX; j++)
			{
				p.drawOval(padding + margin * i, padding + margin * j, diameter, diameter);
				
				if(grid.getNode(i,j).connectionExists(grid.getNode(i+1, j)))
				{
					p.drawLine(padding + margin * i + diameter, padding + j * margin + (diameter / 2), padding + margin * i + margin, padding + j * margin + (diameter / 2));
				}
				
				if(grid.getNode(i,j).connectionExists(grid.getNode(i,j+1))){
					p.drawLine(padding + margin * i + (diameter / 2), padding + j * margin + diameter, padding + margin * i + (diameter / 2), padding + margin * j + margin);
				}
				
				if(grid.getNode(i,j).connectionExists(grid.getNode(i+1,j+1))){
					int edge = (int) ((float) (diameter / 2) / Math.sqrt(2.0));
					int dist = (margin - 2 * edge);
					int x1 = edge + padding + (diameter / 2) + margin * i;
					int y1 = edge + padding + (diameter / 2) + margin * j;
					int x2 = edge + padding + (diameter / 2) + margin * i + dist;
					int y2 = edge + padding + (diameter / 2) + margin * j + dist;;
					p.drawLine(x1, y1, x2, y2);
				} 
				
				if(grid.getNode(i,j).connectionExists(grid.getNode(i-1,j+1))){
					int edge = (int) ((float) (diameter / 2) / Math.sqrt(2.0));
					int dist = (margin - 2 * edge);
					int x1 = padding + (diameter / 2) + margin * i - edge;
					int y1 = edge + padding + (diameter / 2) + margin * j;
					int x2 = padding + (diameter / 2) + margin * i - dist - edge;
					int y2 = edge + padding + (diameter / 2) + margin * j + dist;
					p.drawLine(x1, y1, x2, y2);
				}
				
				if (path[0].equals(grid.getNode(i,j)))
				{
					p.setColor(Color.green);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
					p.setColor(Color.black);
				} 
				else if (path[path.length - 1].equals(grid.getNode(i,j)))
				{
					p.setColor(Color.red);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
					p.setColor(Color.black);
				} 
				else if (Arrays.asList(path).contains(grid.getNode(i,j)))
				{
					p.setColor(Color.blue);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
					p.setColor(Color.black);
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		int padding = 10;
		int diameter = 30;
		int margin = diameter + 20;
		
		JFrame f = new JFrame();
		Grid g = new Grid(15,15);
		g.createRandom(new Point(0,0), new Point(7,8));
		f.setSize(padding * 2 + margin * g.getX(), padding * 3 + margin * g.getY());
		Node[] path = AStar.algorithm(g, g.getNode(0,0), g.getNode(7,8));
		f.setContentPane(new GUIPanel(g,path,diameter,padding,margin));
		f.setVisible(true);
	}
}
>>>>>>> 1bc41006ce9157b0383b0def95ec19487c0df9a2
