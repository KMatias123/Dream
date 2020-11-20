package cat.yoink.dream.api.command;

import cat.yoink.dream.api.util.LoggerUtil;

/**
 * @author yoink
 * @since 9/21/2020
 */
public class Command
{
	private String name;
	private String[] alias;
	private String usage;

	public Command(final String name, final String[] alias, final String usage)
	{
		setName(name);
		setAlias(alias);
		setUsage(usage);
	}

	public void onTrigger(final String arguments)
	{
	}

	public void printUsage()
	{
		LoggerUtil.sendMessage("Usage: " + CommandManager.INSTANCE.getPrefix() + usage);
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String[] getAlias()
	{
		return alias;
	}

	public void setAlias(final String[] alias)
	{
		this.alias = alias;
	}

	public String getUsage()
	{
		return usage;
	}

	public void setUsage(final String usage)
	{
		this.usage = usage;
	}
}
