package cat.yoink.dream.api.gui.clickgui.button;

import cat.yoink.dream.Client;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.SettingManager;
import net.minecraft.client.Minecraft;

import java.awt.*;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class SettingButton
{
	public final Minecraft mc = Minecraft.getMinecraft();
	private final int H;
	private Module module;
	private int X;
	private int Y;
	private final int W;

	public SettingButton(final Module module, final int x, final int y, final int w, final int h)
	{
		this.module = module;
		X = x;
		Y = y;
		W = w;
		H = h;
	}

	public void render(final int mX, final int mY)
	{
	}

	public void mouseDown(final int mX, final int mY, final int mB)
	{
	}

	public void mouseUp(final int mX, final int mY)
	{
	}

	public void keyPress(final int key)
	{
	}

	public void close()
	{
	}

	public void drawButton(final int mX, final int mY)
	{
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
		{
			switch (SettingManager.INSTANCE.getSetting("ClickGUI", "Color").getEnumValue())
			{
				case "Red":
					Client.clickGUI.drawGradient(X, Y, X + W, Y + H, new Color(210, 30, 30, 232).getRGB(), new Color(206, 30, 30, 232).getRGB());
					break;
				case "Green":
					Client.clickGUI.drawGradient(X, Y, X + W, Y + H, new Color(20, 210, 20, 232).getRGB(), new Color(20, 206, 20, 232).getRGB());
					break;
				case "Blue":
					Client.clickGUI.drawGradient(X, Y, X + W, Y + H, new Color(20, 20, 210, 232).getRGB(), new Color(20, 20, 206, 232).getRGB());
					break;
				default:
					break;
			}
		}
		else
		{
			switch (SettingManager.INSTANCE.getSetting("ClickGUI", "Color").getEnumValue())
			{
				case "Red":
					Client.clickGUI.drawGradient(X, Y, X + W, Y + H, new Color(220, 30, 30, 232).getRGB(), new Color(216, 30, 30, 232).getRGB());
					break;
				case "Green":
					Client.clickGUI.drawGradient(X, Y, X + W, Y + H, new Color(30, 220, 30, 232).getRGB(), new Color(30, 216, 30, 232).getRGB());
					break;
				case "Blue":
					Client.clickGUI.drawGradient(X, Y, X + W, Y + H, new Color(30, 30, 220, 232).getRGB(), new Color(30, 30, 216, 232).getRGB());
					break;
				default:
					break;
			}
		}
	}

	public Module getModule()
	{
		return module;
	}

	public void setModule(final Module module)
	{
		this.module = module;
	}

	public int getX()
	{
		return X;
	}

	public void setX(final int x)
	{
		X = x;
	}

	public int getY()
	{
		return Y;
	}

	public void setY(final int y)
	{
		Y = y;
	}

	public int getW()
	{
		return W;
	}

	public int getH()
	{
		return H;
	}


	public boolean isHover(final int X, final int Y, final int W, final int H, final int mX, final int mY)
	{
		return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
	}
}