import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public class Main {
	
	MyFrame frame;
	Point mousePos;
	int mouseSquare = 0;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Main() {
		initialize();
	}
	
	void initialize() {
		frame = new MyFrame();
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(frame.panel.winner == 0) {
					mousePos = MouseInfo.getPointerInfo().getLocation();
					SwingUtilities.convertPointFromScreen(mousePos, frame.panel);
					frame.panel.clicked = getMousePos(mousePos.x, mousePos.y);
					frame.panel.repaint();
				}
			}
		});
		
		try {
			int squareSide = (int) Math.sqrt(frame.panel.tableSize * frame.panel.tableSize / 9);
			frame.panel.XImgBuffered = ImageIO.read(Main.class.getResource("/res/X.png"));
			frame.panel.XImg = frame.panel.XImgBuffered.getScaledInstance(squareSide - frame.panel.shrink, squareSide - frame.panel.shrink, Image.SCALE_SMOOTH);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(frame.panel.winner == 0) {
					mousePos = MouseInfo.getPointerInfo().getLocation();
					SwingUtilities.convertPointFromScreen(mousePos, frame.panel);
					frame.panel.square = getMousePos(mousePos.x, mousePos.y);
					frame.panel.repaint();
				}
				else {
					frame.panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					timer.cancel();
				}
			}
		}, 500, 75);
	}
	
	int getMousePos(int x, int y) {
		int squareSide = (int) Math.sqrt(frame.panel.tableSize * frame.panel.tableSize / 9);
		int firstSquareX = frame.panel.windowW / 2 - 3 * squareSide / 2;
		int firstSquareY = frame.panel.windowH / 2 - 3 * squareSide / 2;
			
		if(x > firstSquareX && x < firstSquareX + squareSide && y > firstSquareY && y < firstSquareY + squareSide)
			mouseSquare = 1;
		else if(x > firstSquareX + squareSide && x < firstSquareX + 2 * squareSide && y > firstSquareY && y < firstSquareY + squareSide)
			mouseSquare = 2;
		else if(x > firstSquareX + 2 * squareSide && x < firstSquareX + 3 * squareSide && y > firstSquareY && y < firstSquareY + squareSide)
			mouseSquare = 3;
		else if(x > firstSquareX && x < firstSquareX + squareSide && y > firstSquareY + squareSide && y < firstSquareY + squareSide * 2)
			mouseSquare = 4;
		else if(x > firstSquareX + squareSide && x < firstSquareX + 2 * squareSide && y > firstSquareY + squareSide && y < firstSquareY + squareSide * 2)
			mouseSquare = 5;
		else if(x > firstSquareX + 2 * squareSide && x < firstSquareX + 3 * squareSide && y > firstSquareY + squareSide && y < firstSquareY + squareSide * 2)
			mouseSquare = 6;
		else if(x > firstSquareX && x < firstSquareX + squareSide && y > firstSquareY + 2 * squareSide && y < firstSquareY + 3 * squareSide)
			mouseSquare = 7;
		else if(x > firstSquareX + squareSide && x < firstSquareX + 2 * squareSide && y > firstSquareY + 2 * squareSide && y < firstSquareY + squareSide * 3)
			mouseSquare = 8;
		else if(x > firstSquareX + 2 * squareSide && x < firstSquareX + 3 * squareSide && y > firstSquareY + 2 * squareSide && y < firstSquareY + squareSide * 3)
			mouseSquare = 9;
		else 
			mouseSquare = 0;
		
		if(mouseSquare == 0)
			frame.panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		else
			frame.panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		return mouseSquare;
	}
	
	void print(String message) {
		System.out.println(message);
	}
}
