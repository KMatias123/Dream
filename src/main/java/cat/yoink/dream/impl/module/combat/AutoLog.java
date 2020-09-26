package cat.yoink.dream.impl.module.combat;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.setting.SettingType;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoLog extends Module
{
    private final Setting health = new Setting.Builder(SettingType.INTEGER)
            .setName("Health")
            .setModule(this)
            .setIntegerValue(10)
            .setMinIntegerValue(1)
            .setMaxIntegerValue(30)
            .build();

    public AutoLog(String name, String description, Category category)
    {
        super(name, description, category);

        addSetting(health);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
        if (mc.player == null || mc.world == null) return;

        if (mc.player.getHealth() <= health.getIntegerValue())
        {
            disable();
            mc.world.sendQuittingDisconnectingPacket();
            mc.loadWorld(null);
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }
}
