import java.util.ArrayList;
import java.util.Arrays;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/*
 * For use by the ControlPanel class, to display the grids
 */

public class GUIPanel extends JPanel {
	
	private int gridX;
	private int gridY;
	private Node[] path;
	private ArrayList<Node> traversed;
	private Grid grid;
	private Node current;
	private Node end;
	Timer timer;
	
	private int counter;
	
	private int diameter;
	private int margin;
	private int padding;
	private String choice;
	private int totalCost = 0;
	
	JFrame f;
	
	public GUIPanel (int d, int p, int m, int h, int w, int startX, int startY, int endX, int endY, Grid g, String c) 	
	{
		diameter = d;
		margin = m;
		padding = p;
		choice = c;
		
		traversed = new ArrayList<Node>();
		
		// checks to see if it received a pre-defined grid as input
		if (g == null)
		{
			grid = new Grid(h,w);
			grid.createRandom();			
		}
		else {
			grid = g;
			grid.resetGrid();
		}
		
		gridX = grid.getX();
		gridY = grid.getY();
		
		f = new JFrame();
		JPanel container = new JPanel();
		
		current = grid.getNode(startX, startY);
		end = grid.getNode(endX, endY);

		grid.turnOnFog(current, 2);
		grid.setPos(current);
				
		if(choice.equals("D*Lite"))
		{
			path = DStarLite.algorithm(grid, end, current);
			if (path == null)
			{
				JOptionPane.showMessageDialog(f,"No path found!","No path found",JOptionPane.ERROR_MESSAGE);
			}
			counter = path.length - 2;
			grid.resetGrid();
			grid.turnOnFog(current, 2);
			f.setTitle(choice);
		}
		else if (choice.equals("LPA*"))
		{
			path = LPAstar.algorithm(grid, end, current, grid.getVision(current, 2));
			if (path == null)
			{
				JOptionPane.showMessageDialog(f,"No path found!","No path found",JOptionPane.ERROR_MESSAGE);
			}
			f.setTitle(choice);
		}
		else
		{
			path = AStar.algorithm(grid, end, current);
			if (path == null)
			{
				JOptionPane.showMessageDialog(f,"No path found!","No path found",JOptionPane.ERROR_MESSAGE);
			}
			f.setTitle(choice);
		}
		
		this.setPreferredSize(new Dimension(10 + padding + margin * gridX, padding * 2 + margin * gridY));
		
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.add(this);
		
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(container);
		
		f.add(jsp);
		f.setVisible(true);
		f.setSize(600,600);
		
		ActionListener action = new ActionListener()
        {   
            public void actionPerformed(ActionEvent event)
            {
            	// if it reaches the end of the path, stop repeating
                if(current.equals(end))
                {
                	// sums the edge costs between the traversed nodes, returns the total cost
                	for(int i = 1; i < traversed.size(); i++)
                	{
                		totalCost += grid.getEdgeLength(traversed.get(i), traversed.get(i-1));
                	}
                	totalCost += grid.getEdgeLength(traversed.get(traversed.size() - 1), end);
                	repaint();
                    timer.stop();
                }
                else
                {
                	loop();
                	if(path == null){
                		repaint();
                		timer.stop();
            			JOptionPane.showMessageDialog(f,"No path found!","No path found",JOptionPane.ERROR_MESSAGE);
                	}

        
                	else if(path.length == 1 && path[0].equals(end)){
                		// sums the edge costs between the traversed nodes, returns the total cost
                    	for(int i = 1; i < traversed.size(); i++)
                    	{
                    		totalCost += grid.getEdgeLength(traversed.get(i), traversed.get(i-1));
                    	}
                    	totalCost += grid.getEdgeLength(traversed.get(traversed.size() - 1), end);
                    	
            			current = end;
            			grid.setPos(current);
            			grid.getVision(current, 2);
            			repaint();
            			timer.stop();
            		}
                	
                }
            }
        };
        
		timer = new Timer(500, action);
		
		// checks if path exists
		if(path != null)
			timer.start();
	}
		
	private void loop () {
		if(choice.equals("D*Lite"))
		{
			if(counter <= 0 && !path[0].equals(end)){
    			current = path[0];
    			grid.setPos(current);
    			grid.getVision(current, 2);
    			repaint();
    			timer.stop();
    			JOptionPane.showMessageDialog(f,"No path found!","No path found",JOptionPane.ERROR_MESSAGE);
    		}
			else{
				traversed.add(path[counter + 1]);
				current = path[counter--];
				grid.getVision(current, 2);
				grid.setPos(current);
			}
			
		}
		else if (choice.equals("LPA*"))
		{
			traversed.add(path[path.length- 1]);
			current = path[path.length-2];
			grid.setPos(current);
			path = LPAstar.algorithm(grid, end, current, grid.getVision(current, 2));
		}
		else
		{
			traversed.add(path[path.length - 1]);
			current = path[path.length-2];
			grid.setPos(current);
			grid.getVision(current, 2);
			path = AStar.algorithm(grid, end, current);
			
		}
		
		this.setPath(path);
		this.repaint();			

	}
	
	public Grid getGrid ()
	{
		return grid;
	}
	
	private void setPath (Node[] inputPath)
	{
		path = inputPath;
	}
	
	public void paintComponent(Graphics p)
	{
		super.paintComponent(p);
		p.setColor(Color.black);
		
		if(totalCost > 0)
			p.drawString("Total Cost: " + totalCost, 25, 25);

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
				
				// colors goal green
				if (path != null && end.equals(grid.getNode(i,j)))
				{
					p.setColor(Color.green);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
				} 
				// colors current position red
				else if (path != null && grid.getPos().equals(grid.getNode(i,j)))
				{
					p.setColor(Color.red);
					p.fillOval(padding + margin * i, padding + margin * j, diameter, diameter);
				} 
				// colors traversed nodes black
				else if (traversed != null && traversed.contains(grid.getNode(i,j)))
				{
					p.setColor(Color.black);
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
}
