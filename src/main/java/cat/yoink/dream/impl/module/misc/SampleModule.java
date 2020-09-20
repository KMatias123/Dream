package cat.yoink.dream.impl.module.misc;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.setting.SettingType;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

public class SampleModule extends Module
{
	private final Setting test = new Setting.Builder(SettingType.BOOLEAN)
			.setName("testSetting1")
			.setModule(this)
			.setBooleanValue(true)
			.build();

	private final Setting test2 = new Setting.Builder(SettingType.INTEGER)
			.setName("testSetting2")
			.setModule(this)
			.setIntegerValue(5)
			.setMinIntegerValue(3)
			.setMaxIntegerValue(10)
			.build();

	private final Setting test3 = new Setting.Builder(SettingType.ENUM)
			.setName("testSetting3")
			.setModule(this)
			.setEnumValue("Test")
			.setEnumValues(new ArrayList<>(Arrays.asList("Test", "Test2", "Test3")))
			.build();

	public SampleModule(String name, String description, Category category)
	{
		super(name, description, category);

		setBind(Keyboard.KEY_R);

		addSetting(test);
		addSetting(test2);
		addSetting(test3);
	}

	@Override
	public void onEnable()
	{
		System.out.println("SampleModule.onEnable");

		System.out.printf("Setting 1: %s, %s, %s, %s %n", test.getName(), test.getModule().getName(), test.getType(), test.getBooleanValue());
		System.out.printf("Setting 2: %s, %s, %s, %s, %s, %s %n", test2.getName(), test2.getModule().getName(), test2.getType(), test2.getIntegerValue(), test2.getMinIntegerValue(), test2.getMaxIntegerValue());
		System.out.printf("Setting 3: %s, %s, %s, %s, %s %n", test3.getName(), test3.getModule().getName(), test3.getType(), test3.getEnumValue(), Arrays.asList(test3.getEnumValues().toArray()));
	}

	@Override
	public void onDisable()
	{
		System.out.println("SampleModule.onDisable");
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event)
	{
		System.out.println(event.getEntity().getName() + " just died");
	}
}
