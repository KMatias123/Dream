package cat.yoink.dream.api.setting;

import cat.yoink.dream.api.module.Module;

import java.util.List;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class Setting
{
	private final String name;
	private final Module module;
	private final SettingType type;
	private boolean booleanValue;
	private int integerValue;
	private int minIntegerValue;
	private int maxIntegerValue;
	private String enumValue;
	private List<String> enumValues;

	public Setting(final String name, final Module module, final int intValue, final int intMinValue, final int intMaxValue)
	{
		this.name = name;
		this.module = module;
		this.integerValue = intValue;
		this.minIntegerValue = intMinValue;
		this.maxIntegerValue = intMaxValue;
		this.type = SettingType.INTEGER;
	}

	public Setting(final String name, final Module module, final boolean boolValue)
	{
		this.name = name;
		this.module = module;
		this.booleanValue = boolValue;
		this.type = SettingType.BOOLEAN;
	}

	public Setting(final String name, final Module module, final List<String> enumValues)
	{
		this.name = name;
		this.module = module;
		this.enumValue = enumValues.get(0);
		this.enumValues = enumValues;
		this.type = SettingType.ENUM;
	}

	public String getName()
	{
		return name;
	}

	public Module getModule()
	{
		return module;
	}

	public SettingType getType()
	{
		return type;
	}

	public boolean getBooleanValue()
	{
		return booleanValue;
	}

	public void setBooleanValue(final boolean booleanValue)
	{
		this.booleanValue = booleanValue;
	}

	public int getIntegerValue()
	{
		return integerValue;
	}

	public void setIntegerValue(final int integerValue)
	{
		this.integerValue = integerValue;
	}

	public int getMinIntegerValue()
	{
		return minIntegerValue;
	}

	public int getMaxIntegerValue()
	{
		return maxIntegerValue;
	}

	public String getEnumValue()
	{
		return enumValue;
	}

	public void setEnumValue(final String enumValue)
	{
		this.enumValue = enumValues.contains(enumValue) ? enumValue : this.enumValue; // only change value if list includes it.
	}

	public List<String> getEnumValues()
	{
		return enumValues;
	}
}
