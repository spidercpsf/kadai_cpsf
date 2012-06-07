import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class AnimationFrame extends JFrame{
	AnimationPanel panel ;
	public AnimationFrame(int array[]){
		super("Merge Sort Animation");
		setSize(new Dimension(500, 500));
		panel= new AnimationPanel(array);
		add(panel);
		show();
	}
	public SortEventListener getListenr(){
		return panel;
	}
}
