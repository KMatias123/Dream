package cat.yoink.dream;

import cat.yoink.dream.api.EventHandler;
import cat.yoink.dream.api.command.CommandManager;
import cat.yoink.dream.api.gui.clickgui.ClickGUI;
import cat.yoink.dream.api.module.ModuleManager;
import cat.yoink.dream.api.setting.SettingManager;
import cat.yoink.dream.api.util.font.CustomFontRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.awt.*;

/**
 * @author yoink
 * @since 9/20/2020
 */
@Mod(modid = "dream", name = "Dream", version = "b1")
public class Client
{
	public static ModuleManager moduleManager;
	public static SettingManager settingManager;
	public static CustomFontRenderer customFontRenderer;
	public static ClickGUI clickGUI;
	public static CommandManager commandManager;

	@Mod.EventHandler
	public void initialize(FMLInitializationEvent event)
	{
		commandManager = new CommandManager();
		settingManager = new SettingManager();
		moduleManager = new ModuleManager();
		customFontRenderer = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 19), true, false);
		clickGUI = new ClickGUI();

		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
}
