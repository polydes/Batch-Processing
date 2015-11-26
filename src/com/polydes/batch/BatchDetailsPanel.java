package com.polydes.batch;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.polydes.common.data.types.Types;
import com.polydes.common.sw.Resources;
import com.polydes.common.sw.Scenes;
import com.polydes.common.ui.propsheet.PropertiesSheetSupport;

import stencyl.core.engine.snippet.ISnippet;
import stencyl.core.lib.Folder;
import stencyl.core.lib.Game;
import stencyl.core.lib.ResourceTypes;
import stencyl.core.lib.scene.SceneModel;
import stencyl.sw.util.UI;
import stencyl.sw.util.dg.DialogPanel;

public class BatchDetailsPanel extends DialogPanel
{
	private static final Logger log = Logger.getLogger(BatchDetailsPanel.class);
	
	public String name;
	public Folder folder;
	public ISnippet snippet;
	
	public BatchDetailsPanel()
	{
		super(null);
		
		PropertiesSheetSupport sheet = new PropertiesSheetSupport(this, this);
		
		sheet.build()
			
			.header("Filters")
			
			.field("name")._string().add()
			
			.field("folder")._folder().type(ResourceTypes.scene).add()
			
			.header("Actions")
			
			.field("snippet").label("Add Behavior")._editor(Types._Snippet).add()
			
			.finish();
		
		JButton apply = new JButton("Apply");
		addGenericRow("", apply);
		
		apply.addActionListener((e) -> applyProcess());
		
		finishBlock();
	}
	
	public void applyProcess()
	{
		List<SceneModel> addToScenes =
			Game.getGame().getScenes().stream()
				.filter(scene -> Resources.isUnderFolder(scene, folder))
				.map(scene -> Scenes.ensureLoaded(scene))
				.filter(scene -> !Scenes.hasSnippet(scene, snippet))
				.collect(Collectors.toList());
		
		UI.Choice result = UI.showYesCancelPrompt(
			"Add Behavior to Scenes?",
			"Add " + snippet.getName() + " to " + addToScenes.size() + " scene(s)?"
		);
		
		if(result == UI.Choice.YES)
		{
			for(SceneModel scene : addToScenes)
			{
				log.debug("Adding " + snippet.getName() +  " to " + scene.getName() + ".");
				Scenes.addSnippet(scene, snippet);
				
				if(!Scenes.isNewScene(scene))
					Scenes.rewriteXml(scene, Scenes.Xml.Snippets);
			}
		}
	}
}
