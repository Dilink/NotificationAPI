package fr.dilink.notificationAPI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.xml.stream.Location;

public class Notification extends JWindow implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private int update = -1;
	private Rectangle screenSize;

	private Timer speedDown;
	private Timer timeToGoDown;
	private NotificationPanel panel;

	public static final int LOCATION_TOP_LEFT = 0;
	public static final int LOCATION_TOP_RIGHT = 1;
	public static final int LOCATION_BOTTOM_LEFT = 2;
	public static final int LOCATION_BOTTOM_RIGHT = 3;

	private boolean isDraw = false;

	private Color colorTitle = Color.BLACK;
	private Color colorDescription = Color.BLACK;
	private Color colorBackground = null;
	private int iconWidth = 50;
	private int iconHeight = 50;
	private int iconPositionX = 10;
	private int iconPositionY = 10;
	private int titleLocationX = 65;
	private int titleLocationY = 0;
	private int descriptionLocationX = 65;
	private int descriptionLocationY = 0;
	private int titleFontHeight = 22;
	private int descriptionFontHeight = 18;
	private int titleFontStyle = Font.BOLD;
	private int descritpionFontStyle = Font.PLAIN;
	private int location = LOCATION_BOTTOM_RIGHT;
	private int speed = 20;
	private int timeDisplayed = 5000;
	private int notificationWidth = 250;
	private int notificationHeight = 100;
	//public int gifTimer = 1;
	private BufferedImage icon = null;
	private Clip alert_sound = null;
	private String title = "";
	private String description = "";

	public Notification(String titleI, String text) {
		//this.setTitle(title);
		this.setSize(notificationWidth, notificationHeight);
		//this.setResizable(false);
		//this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setAlwaysOnTop(true);
		//this.setUndecorated(true);
		//this.setType(Type.POPUP);
		this.setLocation(-10000 - notificationWidth, -10000 - notificationHeight);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
			}
		});

		this.screenSize = GraphicsUtil.getScreenBounds(null);

		this.descriptionLocationY = this.getTitleFontHeight() + 2;
		
		this.title = titleI;
		this.description = text;

		this.panel = new NotificationPanel(this, titleI, text);
		this.add(this.panel);


		this.isDraw = false;
	}
	
	/**
	 * TODO: Correct this feature
	 * @return Width of the title
	 */
	@Deprecated
	public int getTitleWidth() {
		FontMetrics fm = this.panel.getGraphics().getFontMetrics(this.panel.getFont());
		return fm.stringWidth(this.title);
	}
	
	/**
	 * TODO: Correct this feature
	 * @return Height of the title
	 */
	@Deprecated
	public int getTitleHeight() {
		FontMetrics fm = this.panel.getGraphics().getFontMetrics(this.panel.getFont());
		return fm.getHeight();
	}
	
	/**
	 * TODO: Correct this feature
	 * @return Width of the description
	 */
	@Deprecated
	public int getDescriptionWidth() {
		Font font = getFont();
		FontMetrics fm = this.getFontMetrics(font);
		return fm.stringWidth(this.description);
	}
	
	/**
	 * TODO: Correct this feature
	 * @return Height of the description
	 */
	@Deprecated
	public int getDescriptionHeight() {
		FontMetrics fm = this.panel.getGraphics().getFontMetrics(this.panel.getFont());
		return fm.getHeight();
	}

	/**
	 * Basic speed : 20ms
	 * 
	 * @param speedI speed time to disappear the notification (in ms)
	 * @return Current notification
	 */
	public Notification setSpeed(int speedI) {
		this.speed = speedI;
		return this;
	}

	/**
	 * @param colorD The hex value of a color for the current description notification
	 * @return Current notification
	 */
	public Notification setDescriptionColor(int colorD) {
		this.colorDescription = new Color(colorD);
		return this;
	}

	/**
	 * @param colorD The {@link Color} for the current description notification
	 * @return Current notification
	 */
	public Notification setDescriptionColor(Color colorD) {
		this.colorDescription = colorD;
		return this;
	}

	/**
	 * @return The description {@link Color} for the current notification
	 */
	public Color getDescriptionColor() {
		return this.colorDescription;
	}

	/**
	 * @param colorT The hex value of a color for the current title notification
	 * @return Current notification
	 */
	public Notification setTitleColor(int colorT) {
		this.colorTitle = new Color(colorT);
		return this;
	}

	/**
	 * @param colorT The {@link Color} for the current title notification
	 * @return Current notification
	 */
	public Notification setTitleColor(Color colorT) {
		this.colorTitle = colorT;
		return this;
	}

	/**
	 * @return The description {@link Color} for the current notification
	 */
	public Color getTitleColor() {
		return this.colorTitle;
	}

	/**
	 * @param colorBG The hex value of a color for the current background notification
	 * @return Current notification
	 */
	public Notification setBackgroundColor(int colorBG) {
		this.colorBackground = new Color(colorBG);
		return this;
	}

	/**
	 * @param colorBG The {@link Color} for the current background notification
	 * @return Current notification
	 */
	public Notification setBackgroundColor(Color colorBG) {
		this.colorBackground = colorBG;
		return this;
	}

	/**
	 * @return The background {@link Color} for the current notification
	 */
	public Color getBackgroundColor() {
		return this.colorBackground;
	}

	/**
	 * @param width The width for the current notification
	 * @param height The height for the current notification
	 * @return Current notification
	 */
	public Notification setIconSize(int width, int height) {
		this.iconWidth = width;
		this.iconHeight = height;
		if(this.icon != null) this.icon = GraphicsUtil.resizeImage(this.icon, iconWidth, iconHeight, BufferedImage.TYPE_INT_RGB);
		return this;
	}

	/**
	 * @param dim The {@link Dimension} for the current notification
	 * @return Current notification
	 */
	public Notification setIconSize(Dimension dim) {
		this.iconWidth = (int) dim.getWidth();
		this.iconHeight = (int) dim.getHeight();
		if(this.icon != null) this.icon = GraphicsUtil.resizeImage(this.icon, iconWidth, iconHeight, BufferedImage.TYPE_INT_RGB);
		return this;
	}
	
	/**
	 * @return The size of the icon
	 */
	public Dimension getIconSize() {
		return new Dimension(this.iconWidth, this.iconHeight);
	}

	/**
	 * Usage : notif.setIcon(Class.class, "icon.png");
	 * 
	 * @param cla The class to get the package
	 * @param location The location of the image inside the package of the Class
	 * @return Current notification
	 */
	public Notification setIcon(Class<?> cla, String location) {
		try {
			BufferedImage iconI = ImageIO.read(cla.getResourceAsStream(location));
			this.icon = GraphicsUtil.resizeImage(iconI, iconWidth, iconHeight, BufferedImage.TYPE_INT_RGB);
		} catch (IOException | IllegalArgumentException e) {
			System.err.println("Error: " + e.getLocalizedMessage() + " - Notification don't use any icon !");
		}
		return this;
	}

	/**
	 * Usage : notif.setIcon(ImageIO.read(Class.class.getResourceAsStream("icon.png")));
	 * in a try/catch
	 * 
	 * @param image A BufferedImage
	 * @return Current notification
	 */
	public Notification setIcon(BufferedImage image) {
		this.icon = GraphicsUtil.resizeImage(image, iconWidth, iconHeight, BufferedImage.TYPE_INT_RGB);
		return this;
	}

	/**
	 * @param x The distance from the left
	 * @param y The distance from the top
	 * @return Current notification
	 */
	public Notification setIconPosition(int x, int y) {
		this.iconPositionX = x;
		this.iconPositionY = y;
		return this;
	}

	/**
	 * @return The dimension of the positions x and y
	 */
	public Dimension getIconPosition() {
		return new Dimension(this.iconPositionX, this.iconPositionY);
	}

	/**
	 * @return The icon for the current notification
	 */
	public BufferedImage getIcon() {
		return this.icon;
	}

	/**
	 * Use .wav extension for the sound
	 * 
	 * @param cla The class to get the package
	 * @param location The location of the sound inside the package of the Class
	 * @return Current notification
	 */
	public Notification setSoundAlert(Class<?> cla, String location) {
			try {
				alert_sound = AudioSystem.getClip();
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(cla.getResourceAsStream(location));
				alert_sound.open(inputStream);
			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException | NullPointerException e) {
				System.err.println("Error: " + e.getLocalizedMessage() + " - Notification don't use any sound for the alert !");
			}
		return this;
	}
	
	/**
	 * @param loc The {@link Location} value of the notification
	 * @return Current notification
	 */
	public Notification setNotificationLocation(int loc) {
		this.location = loc;
		return this;
	}

	/**
	 * @return the {@link Location} of the current notification
	 */
	public int getNotificationLocation() {
		return this.location;
	}

	/**
	 * @return the {@link Dimension} of the notification title
	 */
	public Dimension getTitleLocation() {
		return new Dimension(this.titleLocationX, this.titleLocationY);
	}

	/**
	 * @return the {@link Dimension} of the notification description
	 */
	public Dimension getDescriptionLocation() {
		return new Dimension(this.descriptionLocationX, this.descriptionLocationY);
	}

	/**
	 * @param titleX The distance from the left
	 * @param titleY The distance from the top
	 * @return Current notification
	 */
	public Notification setTitleLocation(int titleX, int titleY) {
		this.titleLocationX = titleX;
		this.titleLocationY = titleY;
		return this;
	}

	/**
	 * @param dim The {@link Dimension} for the title location
	 * @return Current notification
	 */
	public Notification setTitleLocation(Dimension dim) {
		this.titleLocationX = dim.width;
		this.titleLocationY = dim.height;
		return this;
	}

	/**
	 * @param descX The distance from the left
	 * @param descY The distance from the top
	 * @return Current notification
	 */
	public Notification setDescriptionLocation(int descX, int descY) {
		this.descriptionLocationX = descX;
		this.descriptionLocationY = descY;
		return this;
	}

	/**
	 * @param dim The {@link Dimension} for the description location
	 * @return Current notification
	 */
	public Notification setDescriptionLocation(Dimension dim) {
		this.descriptionLocationX = dim.width;
		this.descriptionLocationY = dim.height;
		return this;
	}

	/**
	 * @param size The title {@link Font} height
	 * @return Current notification
	 */
	public Notification setTitleFontHeight(int size) {
		this.titleFontHeight = size;
		return this;
	}

	/**
	 * @return the title {@link Font} height
	 */
	public int getTitleFontHeight() {
		return this.titleFontHeight;
	}

	/**
	 * @param size The  description {@link Font} height
	 * @return Current notification
	 */
	public Notification setDescriptionFontHeight(int size) {
		this.descriptionFontHeight = size;
		return this;
	}

	/**
	 * @return the  description {@link Font} height
	 */
	public int getDescriptionFontHeight() {
		return this.descriptionFontHeight;
	}

	/**
	 * Basic style : Font.BOLD
	 * 
	 * @param style {@link Font} style
	 * @return Current notification
	 */
	public Notification setTitleFontStyle(int style) {
		this.titleFontStyle = style;
		return this;
	}

	/**
	 * Basic style : Font.BOLD
	 * 
	 * @return the title {@link Font} style
	 */
	public int getTitleFontStyle() {
		return this.titleFontStyle;
	}

	/**
	 * Basic style : Font.PLAIN
	 * 
	 * @param style {@link Font} style
	 * @return Current notification
	 */
	public Notification setDescritpionFontStyle(int style) {
		this.descritpionFontStyle = style;
		return this;
	}

	/**
	 * Basic style : Font.PLAIN
	 * 
	 * @return the description {@link Font} style
	 */
	public int getDescriptionFontStyle() {
		return this.descritpionFontStyle;
	}

	/**
	 * @param newTime the new time to display the current notification
	 * @return Current notification
	 */
	public Notification setDisplayedTime(int newTime) {
		this.timeDisplayed = newTime;
		return this;
	}

	/**
	 * @return the displayed time for the current notification
	 */
	public int getDisplayedTime() {
		return this.timeDisplayed;
	}

	/**
	 * @param timer the timer value for the GIF
	 * @return Current notification
	 */
	/*public Notification setGifTimer(int timer) {
		this.gifTimer = timer;
		return this;
	}*/

	/**
	 * @return The Timer of the GIF
	 */
	/*public int getGifTimer() {
		return this.gifTimer;
	}*/

	/**
	 * After a declaration of a new notification, used to draw it
	 * 
	 * @return Current notification
	 */
	public Notification draw() {
		this.isDraw = true;

		this.offsetY = this.getHeight() * GraphicsUtil.getDisplayedNotification();

		this.posX = (int) (this.screenSize.getWidth() - this.getWidth());
		this.posY = (int) (this.screenSize.getHeight() - this.getHeight() - offsetY);

		this.speedDown = new Timer(this.speed, this);
		this.timeToGoDown = new Timer(this.timeDisplayed, this);

		GraphicsUtil.increaseDisplayedNotifications();

		this.timeToGoDown.start();
		this.setVisible(true);
		this.panel.updateAll();

		if(this.alert_sound != null) {
			this.alert_sound.start();
		}
		
		this.update(getGraphics());
		return this;
	}

	int offsetY = 0;

	public void destroy() {
		isDraw = false;
		colorTitle = Color.BLACK;
		colorDescription = Color.BLACK;
		colorBackground = null;
		iconWidth = 50;
		iconHeight = 50;
		iconPositionX = 10;
		iconPositionY = 10;
		titleLocationX = 65;
		titleLocationY = 0;
		descriptionLocationX = 65;
		descriptionLocationY = 0;
		titleFontHeight = 22;
		descriptionFontHeight = 18;
		titleFontStyle = Font.BOLD;
		descritpionFontStyle = Font.PLAIN;
		location = LOCATION_BOTTOM_RIGHT;
		speed = 20;
		timeDisplayed = 5000;
		notificationWidth = 250;
		notificationHeight = 100;

		if(this.alert_sound != null) {
			this.alert_sound.stop();
		}
		
		GraphicsUtil.decreaseDisplayedNotifications();
		this.setVisible(false);
		this.dispose();
	}

	int posX = 0;
	int posY = 0;

	@Override
	public void update(Graphics g) {
		if(this.isDraw) {			
			this.update++;

			this.offsetY = this.getHeight() * GraphicsUtil.getDisplayedNotification();

			switch (this.getNotificationLocation()) {
			case LOCATION_TOP_LEFT:
				if(this.isVisible() && (this.getLocationOnScreen().y > -50)) {
					this.setLocation(0, (int) -(10 * this.update));
				} else {
					//System.out.println("Sys 1");
					this.destroy();
				}
				break;

			case LOCATION_TOP_RIGHT:
				if(this.isVisible() && (this.getLocationOnScreen().y > -50)) {
					this.setLocation((int) (this.screenSize.getWidth() - this.getWidth()), (int) -(10 * this.update));
				} else {
					//System.out.println("Sys 2");
					this.destroy();
				}
				break;

			case LOCATION_BOTTOM_LEFT:
				if(this.isVisible() && (this.getLocationOnScreen().y < this.screenSize.getHeight() + 50)) {
					this.setLocation(0, (int) (this.screenSize.getHeight() - this.getHeight() + (10 * this.update)));
				} else {
					//System.out.println("Sys 3");
					this.destroy();
				}
				break;

			case LOCATION_BOTTOM_RIGHT:
				if(this.isVisible() && (this.getLocationOnScreen().y < this.screenSize.getHeight() + 50)) {
					this.setLocation(this.posX, this.posY + (10 * this.update));
				} else {
					//System.out.println("Sys 4");
					this.destroy();
				}
				break;

			default:
				break;
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.timeToGoDown) {
			this.speedDown.start();
		}
		if(e.getSource() == this.speedDown) {
			this.update(this.getGraphics());
		}
	}
	/*public synchronized void playSound(final Class<?> cla, final String location) {
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(cla.getResourceAsStream(location));
					clip.open(inputStream);
					clip.start();
					clip.close();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}*/
}