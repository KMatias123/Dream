package cat.yoink.dream.api.module;

import cat.yoink.dream.impl.module.misc.*;

import java.util.ArrayList;

public class ModuleManager
{
	private final ArrayList<Module> modules = new ArrayList<>();

	public ModuleManager()
	{
		modules.add(new SampleModule("Name", "Description", Category.MISC));
	}

	public ArrayList<Module> getModules()
	{
		return modules;
	}
}
