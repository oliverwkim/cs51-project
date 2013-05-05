import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	
	private static String choice = "D*Lite";
	
	public static void main(String args[])
	{
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
				
		JButton generateButton = new JButton("Generate Random Grid");
		final JButton prevButton = new JButton("Use Previous Grid");
		prevButton.setEnabled(false);

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
		
		JLabel algLabel = new JLabel("Algorithms");
		String[] algorithms = {"D*Lite", "LPA*", "A*"};
		JComboBox algList = new JComboBox<String>(algorithms);
		
		// determines user's choice of algorithm
		algList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				JComboBox cb = (JComboBox)e.getSource();
				choice = (String)cb.getSelectedItem();
			}
		});
		
		// generates new, random grid if generateButton is pressed
		generateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				int height = Integer.parseInt(heightField.getText());
				int width = Integer.parseInt(widthField.getText());
				int startX = Integer.parseInt(startXField.getText());
				int startY = Integer.parseInt(startYField.getText());
				int endX = Integer.parseInt(endXField.getText());
				int endY = Integer.parseInt(endYField.getText());
				GUIPanel panel = new GUIPanel(20,30,40, height, width, startX, startY, endX, endY, null, choice);
				grid = panel.getGrid();
				prevButton.setEnabled(true);
				
				oldHeight = height;
				oldWidth = width;
			}
		});
		
		// re-generates the most recently generated random grid
		prevButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				int startX = Integer.parseInt(startXField.getText());
				int startY = Integer.parseInt(startYField.getText());
				int endX = Integer.parseInt(endXField.getText());
				int endY = Integer.parseInt(endYField.getText());
				new GUIPanel(20,30,40, oldHeight, oldWidth, startX, startY, endX, endY, grid, choice);

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
		
		p.add(algLabel);
		p.add(algList);
				
		f.setTitle("Pathfinding Control Panel");
		f.setContentPane(p);
		f.pack();
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
