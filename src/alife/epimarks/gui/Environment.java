/**
 * 
 */
package alife.epimarks.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * @author lifeth
 *
 */
public class Environment extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(Individual individual){
		
		this.setTitle("Epigenetic Algorithm");

		WindowListener l = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		
		this.addWindowListener(l);
		this.pack();
		this.setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();		
		this.setSize(screenSize.width, screenSize.height);
		this.add(individual);
		individual.init();
	}
}
