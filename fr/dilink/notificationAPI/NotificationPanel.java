package fr.dilink.notificationAPI;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class NotificationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Notification notif;
	private String title = "";
	private String description = "";

	protected NotificationPanel(Notification notifI, String titleI, String descI) {
		this.notif = notifI;
		this.setLayout(null);
		this.title = titleI;
		this.description = descI;
		
		/**
		 * TODO: Fix that (change the position of the notif)
		 */
		@SuppressWarnings("unused")
		Shape shape = new RoundRectangle2D.Double(0, 0, notifI.getWidth(), notifI.getHeight(), 0, 0);
		//notifI.setShape(shape);
	}
	/*try {
	    // retrieve image
	    BufferedImage bi = getMyImage();
	    File outputfile = new File("saved.png");
	    ImageIO.write(bi, "png", outputfile);
	} catch (IOException e) {
	    ...
	}
	@Override
	public boolean imageUpdate( Image img, int flags, int x, int y, int w, int h ) {
		System.out.println("Image update: flags=" + flags + " x=" + x + " y=" + y + " w=" + w + " h=" + h);
		repaint();
		return true;
	}*/

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(notif.getIcon() != null) g.drawImage(notif.getIcon(), notif.getIconPosition().width, notif.getIconPosition().height, this);
		GraphicsUtil.drawString(g, title, notif.getTitleLocation(), notif.getTitleColor(), notif.getTitleFontStyle(), notif.getTitleFontHeight());
		GraphicsUtil.drawString(g, description, notif.getDescriptionLocation(), notif.getDescriptionColor(), notif.getDescriptionFontStyle(), notif.getDescriptionFontHeight());
	}

	public void updateAll() {
		this.setBackground(notif.getBackgroundColor());
		this.paint(getGraphics());
	}
}