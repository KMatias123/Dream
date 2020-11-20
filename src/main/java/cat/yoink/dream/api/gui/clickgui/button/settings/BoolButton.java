package cat.yoink.dream.api.gui.clickgui.button.settings;

import cat.yoink.dream.Client;
import cat.yoink.dream.api.gui.clickgui.button.SettingButton;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.util.font.FontUtil;

import java.awt.*;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class BoolButton extends SettingButton
{
	private final Setting setting;

	public BoolButton(final Module module, final Setting setting, final int X, final int Y, final int W, final int H)
	{
		super(module, X, Y, W, H);
		this.setting = setting;
	}

	@Override
	public void render(final int mX, final int mY)
	{
		if (setting.getBooleanValue())
		{
			drawButton(mX, mY);
			FontUtil.drawStringWithShadow(setting.getName(), (float) (getX() + 6), (float) (getY() + 4), new Color(255, 255, 255, 232).getRGB());
		}
		else
		{
			if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
			{
				Client.clickGUI.drawGradient(getX(), getY(), getX() + getW(), getY() + getH(), new Color(220, 220, 220, 232).getRGB(), new Color(218, 218, 218, 232).getRGB());
			}
			else
			{
				Client.clickGUI.drawGradient(getX(), getY(), getX() + getW(), getY() + getH(), new Color(240, 240, 240, 232).getRGB(), new Color(238, 238, 238, 232).getRGB());
			}

			FontUtil.drawString(setting.getName(), (float) (getX() + 6), (float) (getY() + 4), new Color(29, 29, 29, 232).getRGB());
		}
	}

	@Override
	public void mouseDown(final int mX, final int mY, final int mB)
	{
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY) && (mB == 0 || mB == 1))
		{
			setting.setBooleanValue(!setting.getBooleanValue());
		}
	}
}
