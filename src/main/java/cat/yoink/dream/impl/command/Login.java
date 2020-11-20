package cat.yoink.dream.impl.command;

import cat.yoink.dream.api.command.Command;
import cat.yoink.dream.api.util.LoggerUtil;
import cat.yoink.dream.api.util.SessionUtil;

/**
 * @author yoink
 * @since 9/21/2020
 */
public class Login extends Command
{
	public Login(final String name, final String[] alias, final String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void onTrigger(final String arguments)
	{
		final String[] split = arguments.split(" ");
		try
		{
			if (split[0].equals("") || split[1].equals(""))
			{
				printUsage();
				return;
			}
		}
		catch (final Exception ignored)
		{
			printUsage();
			return;
		}

		if (SessionUtil.login(split[0], split[1]))
		{
			LoggerUtil.sendMessage("Logged in to " + SessionUtil.getSession().getUsername());
		}
		else
		{
			LoggerUtil.sendMessage("Failed to log in");
		}
	}
}
