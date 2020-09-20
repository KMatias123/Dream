package cat.yoink.dream;

import cat.yoink.dream.api.EventHandler;
import cat.yoink.dream.api.module.ModuleManager;
import cat.yoink.dream.api.setting.SettingManager;
import cat.yoink.dream.api.util.font.CustomFontRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.awt.*;

@Mod(modid = "dream", name = "Dream", version = "b1")
public class Client
{
	public static ModuleManager moduleManager;
	public static SettingManager settingManager;
	public static CustomFontRenderer customFontRenderer;

	@Mod.EventHandler
	public void initialize(FMLInitializationEvent event)
	{
		settingManager = new SettingManager();
		moduleManager = new ModuleManager();
		customFontRenderer = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 19), true, false);

		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
}
