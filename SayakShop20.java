import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class SayakShop20 {
	private static JLabel picLabel = new JLabel();
	public static boolean isBW = false;
	public static boolean WBactive = false;
	public static boolean WBenable = false;
	public static int gradient = 0;
	public static JButton brightnessB;
	public static JButton writeB;
	public static JButton BlackWhite;
	public static JButton WhiteBall;
	public static JButton resetB;
	public static JButton TopBottom;
	public static JButton BottomTop;
	public static JButton LeftRight;
	public static JButton RightLeft;
	public static JButton DisableGradient;
	public static File f;
	public static BufferedImage newPicture = null;
	public static BufferedImage unModified = null;
	public static BufferedImage blackWhite = null;
	public static JFrame PreviewWindow = new JFrame("Image Preview");
	public static JFrame EditWindow = new JFrame("Image Editor");
	public static ImageIcon image;
	public static JSlider brightnessSlider = new JSlider(-100, 100, 0);
	public static JSlider contrastSlider = new JSlider(-60, 60, 0);
	public static JSlider saturationSlider = new JSlider(0, 100, 100);
	public static JSlider blueSlider = new JSlider(0, 100, 100);
	public static JSlider yellowSlider = new JSlider(0, 100, 100);
	public static JSlider redSlider = new JSlider(0, 100, 100);
	public static JSlider greenSlider = new JSlider(0, 100, 100);
	public static JSlider purpleSlider = new JSlider(0, 100, 100);
	public static int Xcord;
	public static int Ycord;
	public static int Ex;
	public static int Why;

	public static void setPicture(BufferedImage toBeSet) {
		newPicture = toBeSet;
	}

	public static void makeWindows() {

		PreviewWindow.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Xcord = e.getX();
				Ycord = e.getY();
				System.out.println(Xcord + " " + Ycord);

				if (WBactive) {
					Ex = Xcord;
					Why = Ycord;
					WBactive = false;
					WBenable = true;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});

		EditWindow.setLayout(new GridLayout(13, 2));
		brightnessB = new JButton("Apply Edits");
		DisableGradient = new JButton("Disable Grad");
		BottomTop = new JButton("Grad bottom/top");
		TopBottom = new JButton("Grad top/bottom");
		LeftRight = new JButton("Grad left/right");
		RightLeft = new JButton("Grad right/left");
		resetB = new JButton("Reset");
		writeB = new JButton("Save Image");
		BlackWhite = new JButton("Toggle B/W Mode");
		WhiteBall = new JButton("Select White Balance");
		JLabel label2 = new JLabel("Brightness");
		JLabel label3 = new JLabel("Contrast");
		JLabel label4 = new JLabel("Saturation");
		JLabel label5 = new JLabel("Blue");
		JLabel label6 = new JLabel("Yellow + Light Orange");
		JLabel label7 = new JLabel("Red + Dark Orange");
		JLabel label8 = new JLabel("Green");
		JLabel label9 = new JLabel("Purple + Pink");

		EditWindow.add(writeB);
		EditWindow.add(brightnessB);
		EditWindow.add(BlackWhite);
		EditWindow.add(WhiteBall);
		EditWindow.add(BottomTop);
		EditWindow.add(TopBottom);
		EditWindow.add(LeftRight);
		EditWindow.add(RightLeft);
		EditWindow.add(DisableGradient);
		EditWindow.add(resetB);
		EditWindow.add(label2);
		EditWindow.add(brightnessSlider);
		EditWindow.add(label3);
		EditWindow.add(contrastSlider);
		EditWindow.add(label4);
		EditWindow.add(saturationSlider);
		EditWindow.add(label5);
		EditWindow.add(blueSlider);
		EditWindow.add(label6);
		EditWindow.add(yellowSlider);
		EditWindow.add(label7);
		EditWindow.add(redSlider);
		EditWindow.add(label8);
		EditWindow.add(greenSlider);
		EditWindow.add(label9);
		EditWindow.add(purpleSlider);
	

		brightnessB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent buttonPress) {
				System.out.println("Edit Action Performed");
				resetImage();
				System.out.println(buttonPress);
				if (saturationSlider.getValue() != 100)
					changeSaturation();
				if (blueSlider.getValue() != 100)
					changeBlue();
				if (yellowSlider.getValue() != 100)
					changeYellow();
				if (redSlider.getValue() != 100)
					changeRed();
				if (greenSlider.getValue() != 100)
					changeGreen();
				if (purpleSlider.getValue() != 100)
					changePurple();
				changeBrightnessAndContrast();
				if (WBenable) {
					System.out.println("WB Activated");
					whiteBalance();
				}
				System.out.println(isBW);
				if (isBW) {
					System.out.println("BW Activated");
					activateBW();
				}

				if (gradient == 1)
					topBottom();
				if (gradient == 2)
					leftRight();
				if (gradient == 3)
					bottomTop();
				if (gradient == 4)
					rightLeft();
				System.out.println(gradient);
				refresh();
			}
		});
		writeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent buttonPress) {
				File outputfile = new File("newPictureX.jpg");

				try {
					ImageIO.write(newPicture, "jpg", outputfile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		BlackWhite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent buttonPress) {
				if (!isBW) {
					isBW = true;
					System.out.println("BW Toggled On");
					System.out.println(isBW);
				} else if (isBW) {
					isBW = false;
					System.out.println("BW Toggled Off");
					System.out.println(isBW);
				}
			}
		});

		resetB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent buttonPress) {
				resetImage();
				contrastSlider.setValue(0);
				brightnessSlider.setValue(0);
				saturationSlider.setValue(100);
				redSlider.setValue(100);
				blueSlider.setValue(100);
				yellowSlider.setValue(100);
				greenSlider.setValue(100);
				purpleSlider.setValue(100);
				WBenable = false;
				isBW = false;
				gradient = 0;
				refresh();
			}
		});

		BottomTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent buttonPress) {
				gradient = 3;
			}
		});

		TopBottom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent buttonPress) {
				gradient = 1;
			}
		});
		LeftRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent buttonPress) {
				gradient = 2;
			}
		});
		RightLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent buttonPress) {
				gradient = 4;
			}
		});
		DisableGradient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent buttonPress) {
				gradient = 0;
			}
		});

		WhiteBall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent buttonPress) {
				WBactive = true;
			}
		});

		try {
			// f=new File("SwissLines.jpg");
			JFileChooser ImagePick = new JFileChooser();
			ImagePick.showOpenDialog(null);
			f = ImagePick.getSelectedFile();

			BufferedImage inPicture = ImageIO.read(f);
			unModified = ImageIO.read(f);
			setPicture(inPicture);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		image = new ImageIcon(newPicture);
		picLabel.setIcon(image);
		PreviewWindow.add(picLabel);
		PreviewWindow.pack();
		PreviewWindow.setVisible(true);
		EditWindow.pack();
		EditWindow.setVisible(true);
	}

	public static void leftRight() {

		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(unModified.getRGB(x, y));
				int r1 = pixColor.getRed();
				int b1 = pixColor.getBlue();
				int g1 = pixColor.getGreen();

				Color pixColorN = new Color(newPicture.getRGB(x, y));
				int r2 = pixColorN.getRed();
				int b2 = pixColorN.getBlue();
				int g2 = pixColorN.getGreen();

				double frac = (double) x / (newPicture.getWidth());

				int r3 = (int) (r1 * frac + r2 * (1.0 - frac));
				int b3 = (int) (b1 * frac + b2 * (1.0 - frac));
				int g3 = (int) (g1 * frac + g2 * (1.0 - frac));

				Color outColor = new Color(r3, g3, b3);

				newPicture.setRGB(x, y, outColor.getRGB());

			}
		}
	}

	public static void rightLeft() {

		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(unModified.getRGB(x, y));
				int r1 = pixColor.getRed();
				int b1 = pixColor.getBlue();
				int g1 = pixColor.getGreen();

				Color pixColorN = new Color(newPicture.getRGB(x, y));
				int r2 = pixColorN.getRed();
				int b2 = pixColorN.getBlue();
				int g2 = pixColorN.getGreen();

				double frac = (double) x / (newPicture.getWidth());

				int r3 = (int) (r2 * frac + r1 * (1.0 - frac));
				int b3 = (int) (b2 * frac + b1 * (1.0 - frac));
				int g3 = (int) (g2 * frac + g1 * (1.0 - frac));

				Color outColor = new Color(r3, g3, b3);

				newPicture.setRGB(x, y, outColor.getRGB());

			}
		}
	}

	public static void topBottom() {

		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(unModified.getRGB(x, y));
				int r1 = pixColor.getRed();
				int b1 = pixColor.getBlue();
				int g1 = pixColor.getGreen();

				Color pixColorN = new Color(newPicture.getRGB(x, y));
				int r2 = pixColorN.getRed();
				int b2 = pixColorN.getBlue();
				int g2 = pixColorN.getGreen();

				double frac = (double) y / (newPicture.getHeight());

				int r3 = (int) (r1 * frac + r2 * (1.0 - frac));
				int b3 = (int) (b1 * frac + b2 * (1.0 - frac));
				int g3 = (int) (g1 * frac + g2 * (1.0 - frac));

				Color outColor = new Color(r3, g3, b3);

				newPicture.setRGB(x, y, outColor.getRGB());

			}
		}
	}

	public static void bottomTop() {

		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(unModified.getRGB(x, y));
				int r1 = pixColor.getRed();
				int b1 = pixColor.getBlue();
				int g1 = pixColor.getGreen();

				Color pixColorN = new Color(newPicture.getRGB(x, y));
				int r2 = pixColorN.getRed();
				int b2 = pixColorN.getBlue();
				int g2 = pixColorN.getGreen();

				double frac = (double) y / (newPicture.getHeight());

				int r3 = (int) (r2 * frac + r1 * (1.0 - frac));
				int b3 = (int) (b2 * frac + b1 * (1.0 - frac));
				int g3 = (int) (g2 * frac + g1 * (1.0 - frac));

				Color outColor = new Color(r3, g3, b3);

				newPicture.setRGB(x, y, outColor.getRGB());

			}
		}
	}

	public static void changeBrightnessAndContrast() {
		float ModBright = (float) brightnessSlider.getValue();
		float ModContra = (float) contrastSlider.getValue();
		System.out.println("BrightnessChangeMethod " + ModBright);
		float spacer = 1;
		RescaleOp modifier = new RescaleOp(ModContra / 100 + spacer, ModBright, null);
		modifier.filter(newPicture, newPicture);
	}

	public static void activateBW() {

		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(newPicture.getRGB(x, y));
				int greyLevel = (pixColor.getRed() + pixColor.getGreen() + pixColor.getBlue()) / 3;
				Color toBeSet = new Color(greyLevel, greyLevel, greyLevel);
				newPicture.setRGB(x, y, toBeSet.getRGB());
			}
		}
	}

	public static void changeSaturation() {
		resetImage();
		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(unModified.getRGB(x, y));

				float[] transformColor = new float[3];
				transformColor = Color.RGBtoHSB(pixColor.getRed(), pixColor.getGreen(), pixColor.getBlue(), null);
				float saturationScale = (float) transformColor[1] * saturationSlider.getValue() / 100;

				Color pixColorAlt = Color.getHSBColor(transformColor[0], saturationScale, transformColor[2]);

				newPicture.setRGB(x, y, pixColorAlt.getRGB());

			}
		}
	}

	public static void changeBlue() {

		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(newPicture.getRGB(x, y));
				int r = pixColor.getRed();
				int b = pixColor.getBlue();
				int g = pixColor.getGreen();

				float[] transformColor = new float[3];
				transformColor = Color.RGBtoHSB(r, g, b, null);
				float saturationScale = (float) transformColor[1] * blueSlider.getValue() / 100;

				pixColor = Color.getHSBColor(transformColor[0], saturationScale, transformColor[2]);

				if ((1.5 * b > (r + g)) && (b > r + 50)) {
					newPicture.setRGB(x, y, pixColor.getRGB());

				}
			}
		}
	}

	public static void changeGreen() {

		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(newPicture.getRGB(x, y));
				int r = pixColor.getRed();
				int b = pixColor.getBlue();
				int g = pixColor.getGreen();

				float[] transformColor = new float[3];
				transformColor = Color.RGBtoHSB(r, g, b, null);
				float saturationScale = (float) transformColor[1] * greenSlider.getValue() / 100;

				pixColor = Color.getHSBColor(transformColor[0], saturationScale, transformColor[2]);

				if ((1.5 * g > (r + b)) && (g > r + 25)) {
					newPicture.setRGB(x, y, pixColor.getRGB());

				}
			}
		}
	}

	public static void changeRed() {
		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(newPicture.getRGB(x, y));
				int r = pixColor.getRed();
				int b = pixColor.getBlue();
				int g = pixColor.getGreen();

				float[] transformColor = new float[3];
				transformColor = Color.RGBtoHSB(r, g, b, null);
				float saturationScale = (float) transformColor[1] * redSlider.getValue() / 100;

				pixColor = Color.getHSBColor(transformColor[0], saturationScale, transformColor[2]);

				if ((1.5 * r > (b + g)) && (r > g + 40)) {
					newPicture.setRGB(x, y, pixColor.getRGB());

				}

			}
		}
	}

	public static void changeYellow() {

		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(newPicture.getRGB(x, y));
				int r = pixColor.getRed();
				int b = pixColor.getBlue();
				int g = pixColor.getGreen();

				float[] transformColor = new float[3];
				transformColor = Color.RGBtoHSB(r, g, b, null);
				float saturationScale = (float) transformColor[1] * yellowSlider.getValue() / 100;

				pixColor = Color.getHSBColor(transformColor[0], saturationScale, transformColor[2]);

				if ((Math.abs(r - g) < 30) && ((r + g) / 2 + 20 > b))
					newPicture.setRGB(x, y, pixColor.getRGB());

			}
		}
	}

	public static void changePurple() {

		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(newPicture.getRGB(x, y));
				int r = pixColor.getRed();
				int b = pixColor.getBlue();
				int g = pixColor.getGreen();

				float[] transformColor = new float[3];
				transformColor = Color.RGBtoHSB(r, g, b, null);
				float saturationScale = (float) transformColor[1] * purpleSlider.getValue() / 100;

				pixColor = Color.getHSBColor(transformColor[0], saturationScale, transformColor[2]);

				if ((Math.abs(r - b) < 50) && ((r + b) / 2 + 50 > g))
					newPicture.setRGB(x, y, pixColor.getRGB());

			}
		}
	}

	public static void whiteBalance() {
		Color pixColorN = new Color(0, 0, 0);
		if (WBenable)
			pixColorN = new Color(unModified.getRGB(Ex, Why));

		int r2 = pixColorN.getRed();
		int b2 = pixColorN.getBlue();
		int g2 = pixColorN.getGreen();

		int max = Math.max(Math.max(r2, g2), b2);

		int rOff = Math.abs(max - r2);
		int gOff = Math.abs(max - g2);
		int bOff = Math.abs(max - b2);

		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(newPicture.getRGB(x, y));
				int r = pixColor.getRed() + rOff;
				int b = pixColor.getBlue() + bOff;
				int g = pixColor.getGreen() + gOff;
				if (r > 255)
					r = 255;
				if (b > 255)
					b = 255;
				if (g > 255)
					g = 255;
				Color outColor = new Color(r, g, b);

				newPicture.setRGB(x, y, outColor.getRGB());

			}
		}
	}

	public static void refresh() {
		System.out.println("REFRESH");
		image.setImage(newPicture);
		PreviewWindow.pack();
		PreviewWindow.setVisible(true);
		PreviewWindow.setSize(1200, 800);
	}

	public static void main(String[] args) {
		makeWindows();
	}

	public static void resetImage() {
		for (int y = 0; y < newPicture.getHeight(); y++) {
			for (int x = 0; x < newPicture.getWidth(); x++) {
				Color pixColor = new Color(unModified.getRGB(x, y));
				Color XX = new Color(pixColor.getRed(), pixColor.getGreen(), pixColor.getBlue());
				newPicture.setRGB(x, y, XX.getRGB());
			}
		}

	}

}
