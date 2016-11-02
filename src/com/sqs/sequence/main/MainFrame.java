package com.sqs.sequence.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sqs.sequence.panel.SequencePanel;

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

		this.getContentPane().add(spJsp, BorderLayout.CENTER);
		this.getContentPane().add(inputJsp, BorderLayout.SOUTH);

		addMenu();
	}

	private void addMenu() {
		menuBar = new MenuBar();
		Menu menu = new Menu("File");

		// save
		MenuShortcut saveMenuShorcut = new MenuShortcut(KeyEvent.VK_S, false);
		MenuItem saveMenuItem = new MenuItem("Save", saveMenuShorcut);
		saveMenuItem.addActionListener(menuListener);
		menu.add(saveMenuItem);

		// open
		MenuShortcut openMenuShotcut = new MenuShortcut(KeyEvent.VK_O, false);
		MenuItem openMenuItem = new MenuItem("Open", openMenuShotcut);
		openMenuItem.addActionListener(menuListener);
		menu.add(openMenuItem);

		menuBar.add(menu);

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
