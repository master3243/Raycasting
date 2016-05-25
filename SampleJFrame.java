/*
 * Abdulrahman Alabdulkareem 782435
 * May 26, 2016
 */
package raycasting;

import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.geom.Rectangle2D;
	import java.util.ArrayList;

	import javax.swing.JComponent;
	import javax.swing.JFrame;

	import raycasting.gui.Rectangle;

	public class SampleJFrame extends JComponent  {
		
		public JFrame frame;
		
		public static void main(String args[]) {
			SampleJFrame test = new SampleJFrame();
			test.init();
		}
		
		public void init(){
			frame = new JFrame();
			
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setPreferredSize(new Dimension(500, 500));
			frame.getContentPane().add(this, BorderLayout.CENTER);
			
			frame.pack();
			frame.setVisible(true);
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Color color = new Color(100, 0, 0);
			
			Rectangle2D rect = new Rectangle2D.Double(5, 50, 100, 200);
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(color);
			g2.fill(rect);
			
			g2.setPaint(new Color(0, 100, 0));
			rect = new Rectangle2D.Double(20, 80, 130, 200);
			g2.fill(rect);
			
			g2.setPaint(new Color(0, 100, 100));
			rect = new Rectangle2D.Double(10, 30, 200, 50);
			g2.fill(rect);
//			
		}

	}
