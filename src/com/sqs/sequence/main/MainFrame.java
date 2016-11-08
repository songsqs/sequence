package com.sqs.sequence.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sqs.sequence.panel.LineNumberHeaderView;
import com.sqs.sequence.panel.SequencePanel;
import com.sqs.sequence.svg.util.SvgUtil;

public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2684471702993396821L;
	
	private SequencePanel sequencePanel;
	private JTextArea inputArea;
	private JScrollPane inputJsp;
	private JScrollPane spJsp;
	
	private MenuBar menuBar;

	private File saveTo = null;

	private MenuListener menuListener = new MenuListener();

	// scale
	private static final double[] SCALE = { 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5,
			1.6, 1.7, 1.8, 1.9, 2.0 };
	private int currentScaleIndex = 9;

	public MainFrame(String title){
		super(title);
		
		init();

		addKeyListener();
	}
	
	private void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		sequencePanel = new SequencePanel();
		sequencePanel.setPreferredSize(new Dimension(700, 300));
		sequencePanel.setBackground(Color.CYAN);
		spJsp = new JScrollPane(sequencePanel);
		spJsp.setBackground(Color.CYAN);
		spJsp.setPreferredSize(new Dimension(600, 200));
		spJsp.setBorder(BorderFactory.createTitledBorder("展示区"));
		
		inputArea = new JTextArea(10, 10);
		inputJsp = new JScrollPane(inputArea);
		inputJsp.setPreferredSize(new Dimension(600, 150));
		inputJsp.setBorder(BorderFactory.createTitledBorder("请输入"));
		inputJsp.setRowHeaderView(new LineNumberHeaderView());

		this.getContentPane().add(spJsp, BorderLayout.CENTER);
		this.getContentPane().add(inputJsp, BorderLayout.SOUTH);

		addMenu();
	}

	private void addMenu() {
		menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");

		// save
		MenuShortcut saveMenuShorcut = new MenuShortcut(KeyEvent.VK_S, false);
		MenuItem saveMenuItem = new MenuItem("Save", saveMenuShorcut);
		saveMenuItem.addActionListener(menuListener);
		fileMenu.add(saveMenuItem);

		MenuItem saveAsPngMenuItem = new MenuItem("Save As PNG");
		saveAsPngMenuItem.setActionCommand("SaveAsPNG");
		saveAsPngMenuItem.addActionListener(menuListener);
		fileMenu.add(saveAsPngMenuItem);

		MenuItem saveAsSvgMenuItem = new MenuItem("Save As Svg");
		saveAsSvgMenuItem.setActionCommand("SaveAsSVG");
		saveAsSvgMenuItem.addActionListener(menuListener);
		fileMenu.add(saveAsSvgMenuItem);

		fileMenu.addSeparator();

		// open
		MenuShortcut openMenuShortcut = new MenuShortcut(KeyEvent.VK_O, false);
		MenuItem openMenuItem = new MenuItem("Open", openMenuShortcut);
		openMenuItem.addActionListener(menuListener);
		fileMenu.add(openMenuItem);

		MenuItem openFromSvg = new MenuItem("Open from Svg");
		openFromSvg.setActionCommand("OpenFromSVG");
		openFromSvg.addActionListener(menuListener);
		fileMenu.add(openFromSvg);

		Menu viewMenu = new Menu("View");

		// zoom in
		MenuShortcut zoomInMenuShortcut = new MenuShortcut(KeyEvent.VK_MINUS, false);
		MenuItem zoomInMenuItem = new MenuItem("Zoom In", zoomInMenuShortcut);
		zoomInMenuItem.setActionCommand("ZoomIn");
		zoomInMenuItem.addActionListener(menuListener);
		viewMenu.add(zoomInMenuItem);

		// zoom out
		MenuShortcut zoomOutMenuShortcut = new MenuShortcut(KeyEvent.VK_EQUALS, false);
		MenuItem zoomOutMenuItem = new MenuItem("Zoom Out", zoomOutMenuShortcut);
		zoomOutMenuItem.setActionCommand("ZoomOut");
		zoomOutMenuItem.addActionListener(menuListener);
		viewMenu.add(zoomOutMenuItem);

		menuBar.add(fileMenu);
		menuBar.add(viewMenu);

		this.setMenuBar(menuBar);
	}

	private void addKeyListener() {
		inputArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// System.out.println("KeyReleased:" + e);
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String text = inputArea.getText().trim();
					sequencePanel.drawSequence(text);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
	}

	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Save")) {
				if (saveTo == null) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Choose a file");
					int result = fileChooser.showOpenDialog(MainFrame.this);
					if (result != JFileChooser.APPROVE_OPTION) {
						return;
					}
					saveTo = fileChooser.getSelectedFile();
				}

				// save to file
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(saveTo))) {
					String text = MainFrame.this.inputArea.getText();
					if (text == null) {
						return;
					}
					bw.write(text.trim());
					bw.flush();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			} else if (e.getActionCommand().equals("Open")) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Choose a file");
				int result = fileChooser.showOpenDialog(MainFrame.this);
				if (result != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File openFile = fileChooser.getSelectedFile();

				// open file and set text
				try (BufferedReader br = new BufferedReader(new FileReader(openFile))) {
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line);
						sb.append("\n");
					}

					String text = sb.toString();
					MainFrame.this.inputArea.setText(text);
					MainFrame.this.sequencePanel.drawSequence(text);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			} else if (e.getActionCommand().equals("ZoomIn")) {
				System.out.println("Zoom In");
				currentScaleIndex--;
				if (currentScaleIndex < 0) {
					currentScaleIndex = 0;
				}
				System.out.println(currentScaleIndex);
				MainFrame.this.sequencePanel.setTransform(SCALE[currentScaleIndex], SCALE[currentScaleIndex]);
				MainFrame.this.sequencePanel.repaint();
			} else if (e.getActionCommand().equals("ZoomOut")) {
				System.out.println("Zoom Out");
				currentScaleIndex++;
				if (currentScaleIndex >= SCALE.length) {
					currentScaleIndex = SCALE.length - 1;
				}
				System.out.println(currentScaleIndex);
				MainFrame.this.sequencePanel.setTransform(SCALE[currentScaleIndex], SCALE[currentScaleIndex]);
				MainFrame.this.sequencePanel.repaint();
			} else if (e.getActionCommand().equals("SaveAsPNG")) {
				System.out.println("Save As PNG");
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Choose a file");
				int result = fileChooser.showOpenDialog(MainFrame.this);
				if (result != JFileChooser.APPROVE_OPTION) {
					return;
				}

				File saveAsPNGFile = fileChooser.getSelectedFile();

				BufferedImage bufferedImage = new BufferedImage(MainFrame.this.sequencePanel.getWidth(),
						MainFrame.this.sequencePanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = bufferedImage.createGraphics();
				sequencePanel.paint(g2d);
				try {
					ImageIO.write(bufferedImage, "PNG", saveAsPNGFile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if (e.getActionCommand().equals("SaveAsSVG")) {
				System.out.println("Save As Svg");
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setDialogTitle("Choose a file");
				int result = fileChooser.showOpenDialog(MainFrame.this);
				if (result != JFileChooser.APPROVE_OPTION) {
					return;
				}

				File saveAsSvgFile = fileChooser.getSelectedFile();
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(saveAsSvgFile))) {
					String svgXml = SvgUtil.generateSvgString(MainFrame.this.sequencePanel.getObjectBeanList(),
							MainFrame.this.sequencePanel.getLifelineBeanList(), MainFrame.this.inputArea.getText());
					bw.write(svgXml);
					bw.flush();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			} else if (e.getActionCommand().equals("OpenFromSVG")) {
				System.out.println("Open from Svg");
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setDialogTitle("Choose a file");
				int result = fileChooser.showOpenDialog(MainFrame.this);
				if (result != JFileChooser.APPROVE_OPTION) {
					return;
				}

				File svgFile = fileChooser.getSelectedFile();
				try (FileInputStream inputStream = new FileInputStream(svgFile)) {
					String text = SvgUtil.getSourceTextFromSvg(inputStream);
					MainFrame.this.inputArea.setText(text);
					MainFrame.this.sequencePanel.drawSequence(text);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame("时序图");
		mainFrame.setVisible(true);
		mainFrame.pack();

		// String testInput = "A->B:text\nA->A:aaaas\nB->C:中文测试\nC-->B:中文测试END";
		// mainFrame.sequencePanel.drawSequence(testInput);
	}
}
