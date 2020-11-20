package cat.yoink.dream.api.module;

/**
 * @author yoink
 * @since 9/20/2020
 */
public enum Category
{
	COMBAT("Combat"),
	EXPLOIT("Exploit"),
	RENDER("Visuals"),
	MOVEMENT("Movement"),
	COMPONENT("Component"),
	MISC("Miscellaneous");

	private String name;

	Category(final String name)
	{
		setName(name);
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}
}
