package cat.yoink.dream.impl.command;

import cat.yoink.dream.api.command.Command;
import cat.yoink.dream.api.command.CommandManager;
import cat.yoink.dream.api.util.LoggerUtil;

/**
 * @author yoink
 * @since 9/21/2020
 */
public class Help extends Command
{
	public Help(final String name, final String[] alias, final String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void onTrigger(final String arguments)
	{
		LoggerUtil.sendMessage("Dream b1");

		for (final Command command : CommandManager.INSTANCE.getCommands())
		{
			LoggerUtil.sendMessage(command.getName() + " - " + command.getUsage());
		}
	}
}
