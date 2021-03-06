package cat.yoink.dream.impl.command;

import cat.yoink.dream.api.command.Command;
import cat.yoink.dream.api.command.CommandManager;
import cat.yoink.dream.api.util.LoggerUtil;

/**
 * @author yoink
 * @since 9/21/2020
 */
public class Prefix extends Command
{
	public Prefix(final String name, final String[] alias, final String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void onTrigger(final String arguments)
	{
		if (arguments.equals(""))
		{
			printUsage();
			return;
		}

		CommandManager.INSTANCE.setPrefix(arguments);
		LoggerUtil.sendMessage("Prefix set to " + arguments);
	}
}
