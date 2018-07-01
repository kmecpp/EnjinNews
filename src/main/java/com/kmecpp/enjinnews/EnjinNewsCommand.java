package com.kmecpp.enjinnews;

import com.kmecpp.osmium.api.command.Command;
import com.kmecpp.osmium.api.command.CommandEvent;
import com.kmecpp.osmium.api.command.OsmiumCommand;

@Command(aliases = { "enjinnews", "enews", "news" },
		description = "Enjin news base command")
public class EnjinNewsCommand extends OsmiumCommand {

	@Override
	public void execute(CommandEvent e) {

	}

	@Command(aliases = "read")
	public void onRead(CommandEvent e) {

	}

}
