import java.util.Arrays;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUIPanel extends JPanel implements KeyListener {
	
	private int gridX;
	private int gridY;
	private Node[] path;
	private Grid grid;
	private Node current;
	private Node end;
	Timer timer;
	
	private int diameter;
	private int margin;
	private int padding;
	private String choice;
	
	public GUIPanel (int d, int p, int m, int h, int w, int startX, int startY, int endX, int endY, Grid g, String c) 	
	{
		diameter = d;
		margin = m;
		padding = p;
		choice = c;
		
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
		
		JFrame f = new JFrame();
		JPanel container = new JPanel();
		
		current = grid.getNode(startX, startY);
		end = grid.getNode(endX, endY);

		grid.turnOnFog(current, 2);
		grid.setPos(current);
		
		if(choice.equals("D*Lite"))
		{
			path = DStarLite.algorithm(grid, end, current, grid.getVision(current, 2));
		}
		else if (choice.equals("LPA*"))
		{
			path = LPAstar.algorithm(grid, end, current, grid.getVision(current, 2));
		}
		else
		{
			path = AStar.algorithm(grid, end, current);
		}
		
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.add(this);
		f.setContentPane(container);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		f.setSize(10 + padding + margin * gridX, padding * 2 + margin * gridY);
		
		ActionListener action = new ActionListener()
        {   
            public void actionPerformed(ActionEvent event)
            {
                if(current.equals(end))
                {
                    timer.stop();
                }
                else
                {
                	loop();
                	
            		if(path.length == 1 && path[0].equals(end)){
            			current = end;
            			grid.setPos(current);
            			grid.getVision(current,  2);
            			timer.stop();
            		}
                	
                }
            }
        };
        
		timer = new Timer(500, action);
		timer.start();
	}
	
	private void loop () {
		current = path[1];
		grid.setPos(current);
	
		if(choice.equals("D*Lite"))
		{
			path = DStarLite.algorithm(grid, end, current, grid.getVision(current, 2));
		}
		else if (choice.equals("LPA*"))
		{
			path = LPAstar.algorithm(grid, end, current, grid.getVision(current, 2));
		}
		else
		{
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
