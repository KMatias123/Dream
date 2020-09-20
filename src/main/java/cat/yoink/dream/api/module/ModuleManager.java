package cat.yoink.dream.api.module;

import cat.yoink.dream.impl.module.exploit.PacketMine;
import cat.yoink.dream.impl.module.render.ClickGUI;
import cat.yoink.dream.impl.module.render.CustomFont;

import java.util.ArrayList;

public class ModuleManager
{
	private final ArrayList<Module> modules = new ArrayList<>();

	public ModuleManager()
	{
		modules.add(new ClickGUI("ClickGUI", "Toggle modules by clicking on them", Category.RENDER));
		modules.add(new CustomFont("CustomFont", "Use a custom font render instead of Minecraft's default", Category.RENDER));
		modules.add(new PacketMine("PacketMine", "Mine blocks with packets", Category.EXPLOIT));

	}

	public ArrayList<Module> getModules()
	{
		return modules;
	}

	public Module getModule(String name)
	{
		for (Module module : modules)
		{
			if (module.getName().equalsIgnoreCase(name)) return module;
		}

		return null;
	}

	public ArrayList<Module> getModules(Category category)
	{
		ArrayList<Module> mods = new ArrayList<>();

		for (Module module : modules)
		{
			if (module.getCategory().equals(category)) mods.add(module);
		}

		return mods;
	}
}
