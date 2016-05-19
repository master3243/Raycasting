/*
 * Abdulrahman Alabdulkareem 782435
 * May 17, 2016
 */
package raycasting.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import raycasting.keyboard.ComponentKeyboardMapEditor;
import raycasting.old.Direction;
import raycasting.old.Player;

@SuppressWarnings("serial")
public class GUI  extends JComponent {
	
	private JFrame frame;

	public void initUI(int widthOfWindow, int heightOfWindow) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(widthOfWindow, heightOfWindow));
		frame.getContentPane().add(this, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public ComponentKeyboardMapEditor getComponentMapEditor(){
		return new ComponentKeyboardMapEditor(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawRectangles(g);
	}

	private final ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	
	public void drawRectangles(Graphics g){
		for (int i = 0; i < rectangles.size(); i++) {
			Color color = new Color(rectangles.get(i).color.getRGB());
//			g.setColor(color);
			double x = rectangles.get(i).x;
//			int x = rectangles.get(i).x;
			int y = rectangles.get(i).y;
			int width = rectangles.get(i).width;
			int height = rectangles.get(i).height;

//			g.fillRect(x, y, width, height);
		}
	}

	public void addRectangles(Rectangle rec){
		rectangles.add(rec);
	}
	
	public void addRectangles(Rectangle[] arr){
		for(Rectangle rec : arr)
			rectangles.add(rec);
	}
	
	public void clearRectangles(){
		rectangles.clear();
	}
	
	public static void main(String args[]){
		GUI gui = new GUI();
		Player player = new Player();
		gui.initUI(100, 120);
		ComponentKeyboardMapEditor keyboard = gui.getComponentMapEditor();
		keyboard.bindToMoveInDirection(player, KeyStroke.getKeyStroke("W"), new Direction(0));
		gui.addRectangles(new Rectangle(0, 0, 2, 2, new Color(10, 10, 10)));
		gui.repaint();
		java.util.Scanner sc = new java.util.Scanner(System.in);
		while(true){
			sc.nextLine();
			System.out.println(player.getPoint().getX());
			System.out.println(player.getPoint().getY());
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
