import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Arrays;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * The main thread will look like this:
 * Initialize grid
 * Node current = start;
 * g.turnOnFog, which will set everything not visible from the start to be not visible
 * while(current != goal){
 * 	g.getVision, which will expose any newly visible nodes
 * 	runLPAStar
 * 	constructPath
 * 	current = next node in the constructed path
 * 	}
 */

public class GUIPanel extends JPanel implements KeyListener {
	
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
	
	private void setPath (Node[] inputPath)
	{
		path = inputPath;
	}
	
	public void paintComponent(Graphics p)
	{
		super.paintComponent(p);
		for(int i = 0; i < gridY; i++)
		{
			for(int j = 0; j < gridX; j++)
			{

				p.setColor(Color.black);
				
				// draws Nodes
				p.drawOval(padding + margin * i, padding + margin * j, diameter, diameter);
				p.setColor(Color.gray);
				p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
				
				// draws horizontal connections between Nodes
				if(grid.getNode(i,j).connectionExists(grid.getNode(i+1, j)))
				{
					int x1 = padding + margin * i + diameter;
					int y1 = padding + margin * j + (diameter / 2);
					int x2 = padding + margin * (i + 1);
					int y2 = y1;
					p.drawLine(x1, y1, x2, y2);
				}
				
				// draws vertical connections between Nodes
				if(grid.getNode(i,j).connectionExists(grid.getNode(i,j+1))){
					int x1 = padding + margin * i + (diameter / 2);
					int y1 = padding + margin * j + diameter;
					int x2 = x1;
					int y2 = padding + margin * (j + 1);
					p.drawLine(x1, y1, x2, y2);
				}
				
				// draws north-eastern connections
				if(grid.getNode(i,j).connectionExists(grid.getNode(i+1,j+1))){
					int edge = (int) ((float) (diameter / 2) / Math.sqrt(2.0));
					int dist = (margin - 2 * edge);
					int x1 = edge + padding + (diameter / 2) + margin * i;
					int y1 = edge + padding + (diameter / 2) + margin * j;
					int x2 = edge + padding + (diameter / 2) + margin * i + dist;
					int y2 = edge + padding + (diameter / 2) + margin * j + dist;;
					p.drawLine(x1, y1, x2, y2);
				} 
				
				// draws north-western connections
				if(grid.getNode(i,j).connectionExists(grid.getNode(i-1,j+1))){
					int edge = (int) ((float) (diameter / 2) / Math.sqrt(2.0));
					int dist = (margin - 2 * edge);
					int x1 = padding + (diameter / 2) + margin * i - edge;
					int y1 = edge + padding + (diameter / 2) + margin * j;
					int x2 = padding + (diameter / 2) + margin * i - dist - edge;
					int y2 = edge + padding + (diameter / 2) + margin * j + dist;
					p.drawLine(x1, y1, x2, y2);
				}

				// colors in based on visibility
				if(grid.getNode(i,j).getVisibility())
				{
					p.setColor(Color.white);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
				}
				
				// colors start of path green
				if (path != null && path[0].equals(grid.getNode(i,j)))
				{
					p.setColor(Color.green);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
				} 
				// colors goal red
				else if (path != null && grid.getPos().equals(grid.getNode(i,j)))
				{
					p.setColor(Color.red);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
				} 
				// colors path blue
				else if (path != null && Arrays.asList(path).contains(grid.getNode(i,j)))
				{
					p.setColor(Color.blue);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		int padding = 10;
		int diameter = 30;
		int margin = diameter + 20;
		
		JFrame f = new JFrame();
		Grid g = new Grid(15,15);
		JPanel container = new JPanel(new BorderLayout());
		JButton generateButton = new JButton("Generate a new random map");
		
		// generate new random grid
		// set starting point
		// set ending point
		/*
		buttonContainer.add(generateButton);
		buttonContainer.add(start);
		buttonContainer.add(end);
		*/
		
		//g.createStandard();
		
		g.createRandom(new Point(14, 0), new Point(2, 12));
		Node current = g.getNode(14,0);
		Node end = g.getNode(2,12);
		g.turnOnFog(current, 2);
		g.setPos(current);
		
		//Node[] thisPath = LPAstar.algorithm(g, end, current, g.getVision(current, 2));
		Node[] thisPath= DStarLite.algorithm(g, end, current, g.getVision(current,2));
		GUIPanel map = new GUIPanel(g,thisPath,diameter,padding,margin);
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

		
		container.add(map);
		f.setContentPane(container);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		f.setSize(padding * 2 + margin * g.getX(), padding * 3 + margin * g.getY() + 100);
		Thread.sleep(250);
		while(!current.equals(end)){
			//current = thisPath[thisPath.length-2];
			current = thisPath[1];
			
			g.setPos(current);
			//thisPath = LPAstar.algorithm(g, end, current, g.getVision(current, 2));
			thisPath = DStarLite.algorithm(g, end, current, g.getVision(current, 2));
			map.setPath(thisPath);
			map.repaint();			
			Thread.sleep(500);
			if(thisPath.length == 1 && thisPath[0].equals(end)){
				current = end;
				g.setPos(current);
				g.getVision(current,  2);
				map.repaint();
				break;
			}
		}
		
	}

	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
