package com.polydes.batch;

import javax.swing.JPanel;
import javax.swing.JWindow;

import stencyl.core.lib.Game;
import stencyl.sw.SW;
import stencyl.sw.ext.BaseExtension;
import stencyl.sw.ext.OptionsPanel;

public class BatchProcessing extends BaseExtension
{
	private static BatchProcessing instance;
	private BatchMainPage mainPage;
	
	public static BatchProcessing get()
	{
		return instance;
	}
	
	@Override
	public void onStartup()
	{
		super.onStartup();
		
		isInMenu = true;
		menuName = "Batch Processing";
		
		isInGameCenter = true;
		gameCenterName = "Batch Processing";
		
		instance = this;
	}
	
	private JPanel getMainPage()
	{
		if(mainPage == null)
			mainPage = new BatchMainPage();
		return mainPage;
	}
	
	@Override
	public void onActivate()
	{
		JWindow newDisplay = new JWindow(SW.get());
		newDisplay.add(getMainPage());
	}
	
	@Override
	public JPanel onGameCenterActivate()
	{
		return getMainPage();
	}
	
	@Override
	public void onDestroy()
	{
		instance = null;
	}
	
	@Override
	public void onGameSave(Game game)
	{
	}
	
	@Override
	public void onGameOpened(Game game)
	{
	}

	@Override
	public void onGameClosed(Game game)
	{
		if(mainPage != null)
			mainPage.removeAll();
		mainPage = null;
	}
	
	@Override
	protected boolean hasOptions()
	{
		return false;
	}
	
	@Override
	public OptionsPanel onOptions()
	{
		return null;
	}
	
	@Override
	public void onInstall()
	{
	}
	
	@Override
	public void onUninstall()
	{
	}
}