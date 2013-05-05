import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ControlPanel extends JPanel {
	public static void main(String args[])
	{
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		
		JButton generateButton = new JButton("Generate Random Grid");
		generateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
					new GUIPanel(20,30,40);

			}
		});
		p.add(generateButton);
		f.setContentPane(p);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
