package cat.yoink.dream.api.setting;

import cat.yoink.dream.api.module.Module;

import java.util.ArrayList;

/**
 * @author yoink
 * @since 9/20/2020
 */
public enum SettingManager
{
	INSTANCE;

	private final ArrayList<Setting> settings = new ArrayList<>();

	public void addSetting(final Setting setting)
	{
		settings.add(setting);
	}

	public ArrayList<Setting> getSettings(final Module module)
	{
		final ArrayList<Setting> sets = new ArrayList<>();

		for (final Setting setting : settings)
		{
			if (setting.getModule().equals(module))
			{
				sets.add(setting);
			}
		}

		return sets;
	}

	public Setting getSetting(final String moduleName, final String name)
	{
		for (final Setting setting : settings)
		{
			if (setting.getModule().getName().equalsIgnoreCase(moduleName) && setting.getName().equalsIgnoreCase(name))
			{
				return setting;
			}
		}

		return null;
	}

	public ArrayList<Setting> getSettings()
	{
		return settings;
	}
}
