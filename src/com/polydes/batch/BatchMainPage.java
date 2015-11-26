package com.polydes.batch;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import stencyl.sw.lnf.Theme;

public class BatchMainPage extends JPanel
{
	public BatchMainPage()
	{
		super(new BorderLayout());
		
		setBackground(Theme.BG_COLOR);
		
		JPanel mainContent = new JPanel();
		add(mainContent, BorderLayout.CENTER);
		
		mainContent.setBackground(null);
		
		mainContent.add(new BatchDetailsPanel());
	}
}
