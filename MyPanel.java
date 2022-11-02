import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {
	
	public int windowW = 1200, windowH = 900;
	public int tableSize = 300, tablePadding = 100;
	int strokeSize = 2;
	public int square = 0;
	int lastSquare = 0;
	boolean O = false;
	public int clicked = 0;
	public int shrink = 28;
	
	int game[] = new int[10];
	public int winner = 0;
	
	public BufferedImage XImgBuffered;
	public Image XImg;
	
	public MyPanel() {
		super();
		this.setPreferredSize(new Dimension(windowW, windowH));
	}
	
	Graphics2D g2d;
	public void paint(Graphics g) {
		g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, windowW, windowH);
		paintTable();
		if(square != 0)
			paintSquare();
		paintClick();
	}
	
	void paintTable() {
		int x = windowW / 2, y = windowH / 2;
		
		g2d.setStroke(new BasicStroke(strokeSize));
		g2d.setColor(Color.BLACK);
		
		g2d.drawLine(x - tablePadding / 2, y - tableSize / 2, x - tablePadding / 2, y + tableSize / 2);
		g2d.drawLine(x + tablePadding / 2, y - tableSize / 2, x + tablePadding / 2, y + tableSize / 2);
		g2d.drawLine(x - 3 * tablePadding / 2, y - tableSize / 2, x - 3 * tablePadding / 2, y + tableSize / 2);
		g2d.drawLine(x + 3 * tablePadding / 2, y - tableSize / 2, x + 3 * tablePadding / 2, y + tableSize / 2);
		g2d.drawLine(x - tableSize / 2, y - tablePadding / 2, x + tableSize / 2, y - tablePadding / 2);
		g2d.drawLine(x - tableSize / 2, y + tablePadding / 2, x + tableSize / 2, y + tablePadding / 2);
		g2d.drawLine(x - tableSize / 2, y - 3 * tablePadding / 2, x + tableSize / 2, y - 3 * tablePadding / 2);
		g2d.drawLine(x - tableSize / 2, y + 3 * tablePadding / 2, x + tableSize / 2, y + 3 * tablePadding / 2);
	}
	
	void paintSquare() {
		int x = getSquarePos(true)[0];
		int y = getSquarePos(true)[1];
		
		if(lastSquare != -1 && game[square - 1] == 0) {
			g2d.setPaint(Color.WHITE);
			g2d.fillRect(x + strokeSize / 2, y + strokeSize / 2, tablePadding - strokeSize, tablePadding - strokeSize);
		}
		
		g2d.setPaint(new Color(230, 230, 230));
		
		x = getSquarePos(false)[0];
		y = getSquarePos(false)[1];
	
		lastSquare = square;
		
		if(game[square - 1] == 0)
			g2d.fillRect(x + strokeSize / 2, y + strokeSize / 2, tablePadding - strokeSize, tablePadding - strokeSize);
	}
	
	void paintClick() {
		g2d.setPaint(Color.BLACK);
		for(int i = 0; i < 9; i++) {
			if(game[i] == 1)
				g2d.drawImage(XImg, getSquarePos(i + 1)[0] + shrink / 2, getSquarePos(i + 1)[1] + shrink / 2, null);
			else if(game[i] == 2)
				g2d.drawOval(getSquarePos(i + 1)[0] + shrink / 2, getSquarePos(i + 1)[1] + shrink / 2, tablePadding - shrink, tablePadding - shrink);
		}
		if(clicked > 0 && game[clicked - 1] == 0) { 
			if(!O) game[clicked - 1] = 1;
			else game[clicked - 1] = 2;
			square = clicked;
			
			winner = checkWinner();
			if(winner > 0) {
				drawCenteredString("Player " + winner + " won!", new Rectangle(0, 0, windowW, 150), new Font("Sans Serif", Font.BOLD, 24));
				g2d.setPaint(Color.WHITE);
				g2d.fillRect(getSquarePos(false)[0] + strokeSize / 2, getSquarePos(false)[1] + strokeSize / 2, tablePadding - strokeSize, tablePadding - strokeSize);
			}
			if(winner == -1) {
				drawCenteredString("No player won!", new Rectangle(0, 0, windowW, 150), new Font("Sans Serif", Font.BOLD, 24));
				g2d.setPaint(Color.WHITE);
				g2d.fillRect(getSquarePos(false)[0] + strokeSize / 2, getSquarePos(false)[1] + strokeSize / 2, tablePadding - strokeSize, tablePadding - strokeSize);
			}
			
			g2d.setPaint(Color.BLACK);
			if(O == false)
				g2d.drawImage(XImg, getSquarePos(false)[0] + shrink / 2, getSquarePos(false)[1] + shrink / 2, null);
			else 
				g2d.drawOval(getSquarePos(false)[0] + shrink / 2, getSquarePos(false)[1] + shrink / 2, tablePadding - shrink, tablePadding - shrink);
			
			O = !O;
			clicked = 0;
		}
	}
	
	public void drawCenteredString(String text, Rectangle rect, Font font) {
		FontMetrics metrics = g2d.getFontMetrics(font);
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g2d.setFont(font);
		g2d.drawString(text, x, y);
	}
	
	int[] getSquarePos(boolean isLastSquare) {
		int x = windowW / 2 - tableSize / 2;
		int y = windowH / 2 - tableSize / 2;
		
		if(isLastSquare) {
			
			if(lastSquare % 3 == 2)
				x += tablePadding;
			else if(lastSquare % 3 == 0)
				x += tablePadding * 2;
			if((lastSquare - 1) / 3 == 1)
				y += tablePadding;
			else if((lastSquare - 1) / 3 == 2)
				y += tablePadding * 2;
			
		}
		else {
			
			if(square % 3 == 2)
				x += tablePadding;
			else if(square % 3 == 0)
				x += tablePadding * 2;
			if((square - 1) / 3 == 1)
				y += tablePadding;
			else if((square - 1) / 3 == 2)
				y += tablePadding * 2;
			
		}
		
		return new int[] { x, y };
	}
	
	int[] getSquarePos(int s) {
		int x = windowW / 2 - tableSize / 2;
		int y = windowH / 2 - tableSize / 2;
		
		if(s % 3 == 2)
			x += tablePadding;
		else if(s % 3 == 0)
			x += tablePadding * 2;
		if((s - 1) / 3 == 1)
			y += tablePadding;
		else if((s - 1) / 3 == 2)
			y += tablePadding * 2;
		
		return new int[] { x, y };
	}
	
	boolean isSquareClicked() {
		if(game[square] == 1)
			return true;
		return false;
	}
	
	public int checkWinner() {
		for(int i = 0; i < 3; i++)
			if(game[i] == game[i + 3] && game[i + 3] == game[i + 6])
				return game[i];
		for(int i = 0; i < 9; i += 3)
			if(game[i] == game[i + 1] && game[i + 1] == game[i + 2])
				return game[i];
		
		if(game[0] == game[4] && game[4] == game[8])
			return game[0];
		if(game[2] == game[4] && game[4] == game[6])
			return game[2];
		
		int c = 0;
		for(int i = 0; i < 9; i++)
			if(game[i] == 0) c++;
		if(c == 0)
			return -1;
		
		return 0;
	}
	
	void print(String message) {
		System.out.println(message);
	}
}
