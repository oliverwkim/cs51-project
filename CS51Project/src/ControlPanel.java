import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ControlPanel extends JPanel {
	
	private static JTextField heightField;
	private static JTextField widthField;
	private static JTextField startXField;
	private static JTextField startYField;
	private static JTextField endXField;
	private static JTextField endYField;
	
	private static Grid grid = null;
	
	private static int oldHeight;
	private static int oldWidth;
	
	public static void main(String args[])
	{
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		
		JButton generateButton = new JButton("Generate Random Grid");
		JButton prevButton = new JButton("Use Previous Grid");

		JLabel heightLabel = new JLabel("Grid height");
		heightField = new JTextField("10");
		JLabel widthLabel = new JLabel("Grid width");
		widthField = new JTextField("10");
		
		JLabel startXLabel = new JLabel("Starting X");
		startXField = new JTextField("0");
		JLabel startYLabel = new JLabel("Starting Y");
		startYField = new JTextField("0");
		
		JLabel endXLabel = new JLabel("Ending X");
		endXField = new JTextField("5");
		JLabel endYLabel = new JLabel("Ending Y");
		endYField = new JTextField("5");
		
		generateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				int height = Integer.parseInt(heightField.getText());
				int width = Integer.parseInt(widthField.getText());
				int startX = Integer.parseInt(startXField.getText());
				int startY = Integer.parseInt(startYField.getText());
				int endX = Integer.parseInt(endXField.getText());
				int endY = Integer.parseInt(endYField.getText());
				GUIPanel panel = new GUIPanel(20,30,40, height, width, startX, startY, endX, endY, null);
				grid = panel.getGrid();
				
				oldHeight = height;
				oldWidth = width;
			}
		});
		
		prevButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				int height = Integer.parseInt(heightField.getText());
				int width = Integer.parseInt(widthField.getText());
				int startX = Integer.parseInt(startXField.getText());
				int startY = Integer.parseInt(startYField.getText());
				int endX = Integer.parseInt(endXField.getText());
				int endY = Integer.parseInt(endYField.getText());
				new GUIPanel(20,30,40, oldHeight, oldWidth, startX, startY, endX, endY, grid);
			}
		});
		
		
		p.add(generateButton);
		p.add(prevButton);
		p.add(heightLabel);
		p.add(heightField);
		p.add(widthLabel);
		p.add(widthField);
		
		p.add(startXLabel);
		p.add(startXField);
		p.add(startYLabel);
		p.add(startYField);
		
		p.add(endXLabel);
		p.add(endXField);
		p.add(endYLabel);
		p.add(endYField);
		
		f.setTitle("Pathfinding Control Panel");
		f.setContentPane(p);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
