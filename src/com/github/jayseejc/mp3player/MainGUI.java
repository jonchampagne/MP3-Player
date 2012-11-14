package com.github.jayseejc.mp3player;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.net.URI;
import java.net.URL;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;


public class MainGUI {

	String args[]=new String[]{""};
	JFrame frame;
	AdvancedPlayer p;
	Thread player;
	
	/*
	 * Default construction with no arguments
	 */
	/**
	 * @wbp.parser.entryPoint
	 */
	public MainGUI(){
		initialize();
	}
	/*
	 * Grab the main arguments if we can
	 */
	public MainGUI(String[] inargs) {
		args=inargs;
		initialize();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	
	public void setVisible(boolean in) throws NullPointerException{
		frame.setVisible(true);
	}
	
	public boolean isVisible(){
		return frame.isVisible();
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				play();
			}
		});
		btnPlay.setBounds(10, 11, 89, 23);
		frame.getContentPane().add(btnPlay);
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		btnPause.setBounds(10, 45, 89, 23);
		frame.getContentPane().add(btnPause);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		btnStop.setBounds(10, 79, 89, 23);
		frame.getContentPane().add(btnStop);
		
		makePlayer();
		
	}
	private void makePlayer() {
		try {
			p=new AdvancedPlayer(new FileInputStream(args[0]));
			player=new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						p.play();
					} catch (JavaLayerException e) {
						// TODO Auto-generated catch block
						System.err.println("Could not play "+p.getPlayBackListener().toString());
					}
					p.close();
				}
			});
		} catch (FileNotFoundException | JavaLayerException e) {
			System.err.println("Could not open "+args[0]);
		}
	}
	@SuppressWarnings("deprecation")
	private void play() {
		player.resume();
		if(!player.isAlive()){
			player.start();
		}
	}
	@SuppressWarnings("deprecation")
	private void pause() {
		player.suspend();
	}
	@SuppressWarnings("deprecation")
	protected void stop() {
		player.stop();
		makePlayer();
	}
}
