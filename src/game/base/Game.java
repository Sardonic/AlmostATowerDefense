package game.base;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame {
	private final int width;
	private final int height;
	public static final long IDEAL_FPS = 60;
	private final long MS_BETWEEN_FRAMES = 1000 / IDEAL_FPS;
	private final long NS_TO_MS_QUOTIENT = 1000000;
	private final int NUM_BUFFERS = 2;
	private boolean quit;
	private BufferStrategy strategy;
	private Room room;
	
	private Game(int width, int height) {
		this.width = width;
		this.height = height;
		room  = new Room(width, height);
		room.addDefaultTowers();
		quit = false;
	}
	
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(width, height));
		setResizable(false);
		setIgnoreRepaint(true); // I'll handle the repaints myself, thank you.
		setBackground(Color.WHITE);
		
		// Exit cleanly when the window is closed.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				quit = true;
			}
		});
		
		// Leave the game when esc is pressed
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					quit = true;
				}
			}
		});
		
		add(room);
		addMouseListener(room);
		pack();
		setVisible(true);
		
		createBufferStrategy(NUM_BUFFERS);
		strategy = getBufferStrategy();
		
		mainLoop();
	}
	
	private void mainLoop() {
		// Run the game in its own thread or it refuses to close.
		
		Thread gameLoop = new Thread() {
			public void run() {
				runLoop();
			}
		};
		
		gameLoop.start();
	}
	
	private void runLoop() {
		while(!quit) {
			long preLoopTimeNs = System.nanoTime();
			
			update();
			render();
			
			// Figure out if we can let the CPU take a little nap before the next frame.
			long postLoopTimeNs = System.nanoTime();
			long elapsedMs = (postLoopTimeNs - preLoopTimeNs) / NS_TO_MS_QUOTIENT;
			long targetSleep = MS_BETWEEN_FRAMES - elapsedMs;
			
			// Do we need to kill some time?
			if (targetSleep > 0) {
				try {
					Thread.sleep(targetSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.exit(0);
	}

	private void update() {
		// Do the updates
		quit = room.mainLoop();
	}

	private void render() {
		// Draw to a buffer
		Graphics g = strategy.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		
		// Tell every unit to draw here.
		room.paintComponent(g);
		
		// Show what we've drawn
		strategy.show();
		
		// Drop the graphics resources we don't need anymore.
		g.dispose();
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Game(512, 512).init();
			}
		});
	}
}
