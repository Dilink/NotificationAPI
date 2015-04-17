package fr.dilink.notificationAPI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class GraphicsUtil
{
	private static int displayedNotification = 0;

	public static int getDisplayedNotification() {
		//System.out.println("Nbr: " + displayedNotification);
		return displayedNotification;
	}
	public static void increaseDisplayedNotifications() {
		displayedNotification += 1;
		//System.out.println("Add notification");
	}
	public static void decreaseDisplayedNotifications() {
		if(displayedNotification > 0) {			
			displayedNotification -= 1;
		} else {
			displayedNotification = 0;
		}
		System.out.println("Remove notification" + " - Now: "+displayedNotification);
	}

	public static Rectangle getScreenBounds(Window wnd) {
		Rectangle sb;
		Insets si = getScreenInsets(wnd);

		if(wnd == null) {
			sb = GraphicsEnvironment
					.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice()
					.getDefaultConfiguration()
					.getBounds(); 
		} else {
			sb = wnd
					.getGraphicsConfiguration()
					.getBounds(); 
		}

		sb.x += si.left;
		sb.y += si.top;
		sb.width -= si.left + si.right;
		sb.height -= si.top + si.bottom;
		return sb;
	}
	public static Insets getScreenInsets(Window wnd) {
		Insets si;

		if(wnd == null) { 
			si = Toolkit.getDefaultToolkit().getScreenInsets(GraphicsEnvironment
					.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice()
					.getDefaultConfiguration()); 
		} else { 
			si = wnd.getToolkit().getScreenInsets(wnd.getGraphicsConfiguration()); 
		}
		return si;
	}
	public static BufferedImage resizeImage(BufferedImage originalImage, int IMG_WIDTH, int IMG_HEIGHT, int type) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		return resizedImage;
	}
	public static Dimension getStringSize(JComponent c, String text) {
		FontMetrics metrics = c.getFontMetrics(c.getFont());
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);
		return new Dimension(adv + 2, hgt + 2);
	}
	public static void drawString(Graphics g, String text, int x, int y) {
		drawString(g, text, x, y, Color.BLACK, Font.PLAIN, 14);
	}
	public static void drawString(Graphics g, String text, int x, int y, int style, int size) {
		drawString(g, text, x, y, Color.BLACK, style, size);
	}
	public static void drawString(Graphics g, String text, Dimension dim) {
		drawString(g, text, dim.width, dim.height, Color.BLACK, Font.PLAIN, 14);
	}
	public static void drawString(Graphics g, String text, Dimension dim, int style, int size) {
		drawString(g, text, dim.width, dim.height, Color.BLACK, style, size);
	}
	public static void drawString(Graphics g, String text, Dimension dim, Color color) {
		drawString(g, text, dim.width, dim.height, color, Font.PLAIN, 14);
	}
	public static void drawString(Graphics g, String text, Dimension dim, Color color, int style, int size) {
		drawString(g, text, dim.width, dim.height, color, style, size);
	}
	public static void drawString(Graphics g, String text, int x, int y, Color color, int style, int size) {
		g.setColor(color);
		g.setFont(new Font("Dialog", style, size));
		//System.out.println("Font: " + g.getFont());
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight() - 2);
	}
}