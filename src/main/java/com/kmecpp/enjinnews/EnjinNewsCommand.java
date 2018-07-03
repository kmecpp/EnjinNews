package com.kmecpp.enjinnews;

import com.kmecpp.osmium.api.command.Command;
import com.kmecpp.osmium.api.command.OsmiumCommand;

@Command(aliases = { "enjinnews", "enews", "en" },
		description = "Enjin news base command")
public class EnjinNewsCommand extends OsmiumCommand {

	@Override
	public void configure() {
		enableCommmandList("EnjinNews Commands");

		add("read");
		add("list");
		add("sync");

	}

	//	@Override
	//	public void configure() {
	//		//		registerArg("name");
	//	}
	//
	//	@Override
	//	public void execute(CommandEvent e) {
	//
	//	}
	//
	//	@Command(aliases = "read")
	//	public void read(CommandEvent e) {
	//
	//	}

}
