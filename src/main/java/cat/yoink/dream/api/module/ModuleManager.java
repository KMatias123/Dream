package cat.yoink.dream.api.module;

import cat.yoink.dream.impl.module.combat.Criticals;
import cat.yoink.dream.impl.module.exploit.PacketMine;
import cat.yoink.dream.impl.module.misc.ChatSuffix;
import cat.yoink.dream.impl.module.misc.Timer;
import cat.yoink.dream.impl.module.movement.LongJump;
import cat.yoink.dream.impl.module.render.ClickGUI;
import cat.yoink.dream.impl.module.render.CustomFont;

import java.util.ArrayList;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class ModuleManager
{
	private final ArrayList<Module> modules = new ArrayList<>();

	public ModuleManager()
	{
		modules.add(new ClickGUI("ClickGUI", "Toggle modules by clicking on them", Category.RENDER));
		modules.add(new CustomFont("CustomFont", "Use a custom font render instead of Minecraft's default", Category.RENDER));
		modules.add(new PacketMine("PacketMine", "Mine blocks with packets", Category.EXPLOIT));
		modules.add(new Timer("Timer", "Speeds up your game", Category.MISC));
		modules.add(new Criticals("Criticals", "Deal critical hits without jumping", Category.COMBAT));
		modules.add(new LongJump("LongJump", "Jumps far", Category.MOVEMENT));
		modules.add(new ChatSuffix("ChatSuffix", "Adds a suffix to your chat messages", Category.MISC));
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
