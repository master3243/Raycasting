/*
 * Abdulrahman Alabdulkareem 782435
 * May 17, 2016
 */
package raycasting.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GUI extends JComponent {

	public final JFrame frame;

	public final int widthOfWindow;
	public final int heightOfWindow;

	public GUI(int widthOfWindow, int heightOfWindow) {
		frame = new JFrame();
		this.widthOfWindow = widthOfWindow;
		this.heightOfWindow = heightOfWindow;

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(widthOfWindow, heightOfWindow));
		frame.getContentPane().add(this, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawRectangles(g);
		drawMoney(g);
	}

	private final ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

	public void drawRectangles(Graphics g) {
		for (int i = 0; i < rectangles.size(); i++) {

			Color color = new Color(rectangles.get(i).color.getRGB());
			double x = rectangles.get(i).x;
			double y = rectangles.get(i).y;
			double width = rectangles.get(i).width;
			double height = rectangles.get(i).height;

			Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(color);
			g2.fill(rect);

		}
	}
	
	public int moneyInBP = 0;
	public int moneyInBase = 0;
	private void drawMoney(Graphics g){
		((Graphics2D) g).setPaint(new Color(255, 0, 0));
		g.setFont(new Font("Haettenschweiler", Font.PLAIN, 20));
		g.drawString("BackPack: $" + moneyInBP, getWidth()-131, 50);
		g.drawString("Base: $" + moneyInBase, getWidth()-100, 70);
		
	}
	
	public void addRectangles(Rectangle rec) {
		rectangles.add(rec);
	}

	public void addRectangles(ArrayList<Rectangle> arr) {
		for (Rectangle rec : arr)
			rectangles.add(rec);
	}

	public void addRectangles(Rectangle[] arr) {
		for (Rectangle rec : arr)
			rectangles.add(rec);
	}

	public void clearRectangles() {
		rectangles.clear();
	}

}
