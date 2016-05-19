package raycasting.old;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import raycasting.gui.Rectangle;

@SuppressWarnings("serial")
public class RectanglesComponent extends JComponent {

	private final ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < rectangles.size(); i++) {
			Color color = new Color(rectangles.get(i).color.getRGB());
			g.setColor(color);
			//int x = rectangles.get(i).x;
			double x = rectangles.get(i).x;
			
			int y = rectangles.get(i).y;
			int width = rectangles.get(i).width;
			int height = rectangles.get(i).height;
			

			Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(color);
			g2.fill(rect);

//			g.fillRect(x, y, width, height);
//			g.drawLine(x, y, x, y+height);
		}
	}

	static Player player;
	static Map map;
	static RectanglesComponent comp;
	static JFrame testFrame;
	static int heightOfWindow = 1600;
	static int widthOfWindow = 1280;
	static KeyboardInput keyboard = new KeyboardInput();
	
	public static void main(String[] args) {
		initUI();
		initGame();
		int milliSecondsBetweenFrames = 1000 / World.FPS;
		
		while (true) {
			try {
				Thread.sleep(milliSecondsBetweenFrames);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gameUpdate();
		}

	}
	
	static void initUI() {
		testFrame = new JFrame();
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		comp = new RectanglesComponent();
		comp.setPreferredSize(new Dimension(widthOfWindow, heightOfWindow));
		testFrame.getContentPane().add(comp, BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel();
		JButton clearButton = new JButton("Turn 20 degrees");
		clearButton.addKeyListener(keyboard);
		buttonsPanel.add(clearButton);
		testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player.getLookingDirection().addDirection(new Direction(20));
				
			}
		});
		testFrame.pack();
		testFrame.setVisible(true);
	}

	static void initGame() {
		map = new Map();
		Map.generateMap(map, 3);
		player = new Player();
		player.setLookingDirection(new Direction(150));
		player.setPoint(new Point2D.Double(40, -42));
	}
	
	static void gameUpdate(){
		keyboard_check();
		drawWalls();
	}
	
	static void keyboard_check(){
		keyboard.poll();
		if(keyboard.keyDown(KeyEvent.VK_W))
			player.moveOneFrameForward();
		if(keyboard.keyDown(KeyEvent.VK_A))
			player.moveOneFrameLeft();
		if(keyboard.keyDown(KeyEvent.VK_S))
			player.moveOneFrameBackward();
		if(keyboard.keyDown(KeyEvent.VK_D))
			player.moveOneFrameRight();
		if(keyboard.keyDown(KeyEvent.VK_RIGHT))
			player.rotateOneFrameRight();
		if(keyboard.keyDown(KeyEvent.VK_LEFT))
			player.rotateOneFrameLeft();
		
	}
	
	static void drawWalls() {
		comp.clearRectangle();
		WallProperties[] walls = map.generateWallPropertiesArray(player);
		// Color[] colors = map
		for (int i = 0; i < walls.length; i++) {
			// System.out.println(distances[i]);
			if (walls[i] == null)
				continue;
			int width = widthOfWindow / walls.length;
			int x = widthOfWindow - 1 - (i * width);
			int y = heightOfWindow / 4;
			//int height = (int) (2.0 * 2500 / (walls[i].distance));
			int height = (int) (5000/walls[i].distance);
			Color color = walls[i].color;
			comp.addRectangle(x, y - height / 2, width, height, color);
		}
	}
	
	public void addRectangle(int x, int y, int width, int height, Color color) {
		rectangles.add(new Rectangle(x, y, width, height, color));
		repaint();
	}

	public void clearRectangle() {
		rectangles.clear();
		repaint();
	}
	
}