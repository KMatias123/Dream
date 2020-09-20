package cat.yoink.dream.impl.module.render;

import cat.yoink.dream.Client;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.setting.SettingType;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class ClickGUI extends Module
{
	public ClickGUI(String name, String description, Category category)
	{
		super(name, description, category);

		setBind(Keyboard.KEY_RSHIFT);

		addSetting(new Setting.Builder(SettingType.ENUM)
				.setName("Color")
				.setModule(this)
				.setEnumValue("Red")
				.setEnumValues(new ArrayList<>(Arrays.asList("Red", "Green", "Blue")))
				.build());
	}

	@Override
	public void onEnable()
	{
		mc.displayGuiScreen(Client.clickGUI);
	}
}
