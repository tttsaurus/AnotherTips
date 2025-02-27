package com.tttsaurus.anothertips.core;

import com.tttsaurus.anothertips.AnotherTipsConfig;
import com.tttsaurus.anothertips.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.time.StopWatch;
import java.awt.*;
import java.util.Random;

public final class TipsManager
{
    private static boolean active = false;
    private static boolean listenChunkBuild = false;

    private static final Random random = new Random();
    private static final StopWatch stopWatch = new StopWatch();
    private static boolean roll = true;
    private static int lastTipIndex = -1;
    private static String currentTip;
    private static float currentWaitTime;
    private static String currentLang = "";

    //<editor-fold desc="getters & setters">
    public static boolean getActive() { return active; }
    public static void setActive(boolean flag) { active = flag; }

    public static boolean getListenChunkBuild() { return listenChunkBuild; }
    public static void setListenChunkBuild(boolean flag) { listenChunkBuild = flag; }

    public static void resetTimer()
    {
        if (stopWatch.isStarted())
        {
            stopWatch.stop();
            stopWatch.reset();
        }
    }
    //</editor-fold>

    private static float calcTipTime(String tip)
    {
        if (!AnotherTipsConfig.TIP_WAIT_TIME.containsKey(currentLang))
            return 1f;
        else
        {
            float timePerChar = AnotherTipsConfig.TIP_WAIT_TIME.get(currentLang);
            return tip.length() * timePerChar;
        }
    }

    private static void rollTip()
    {
        currentLang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();

        int length = AnotherTipsConfig.TIP_LANG_KEYS.size();

        if (length == 0)
            currentTip = "";
        else if (length == 1)
            currentTip = AnotherTipsConfig.TIP_LANG_KEYS.get(0);
        else
        {
            if (lastTipIndex == -1)
            {
                int index = random.nextInt(length);
                currentTip = I18n.format(AnotherTipsConfig.TIP_LANG_KEYS.get(index));
                lastTipIndex = index;
            }
            else
            {
                int index = random.nextInt(length);
                index = index == lastTipIndex ? index + 1 : index;
                index = index >= length ? 0 : index;
                currentTip = I18n.format(AnotherTipsConfig.TIP_LANG_KEYS.get(index));
                lastTipIndex = index;
            }
        }
    }

    public static void drawTips()
    {
        if (roll)
        {
            roll = false;
            rollTip();
            currentWaitTime = calcTipTime(currentTip);
        }

        if (!stopWatch.isStarted())
            stopWatch.start();

        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        //int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        RenderUtils.renderText("Tips", 20, height - 40, 1f, Color.YELLOW.getRGB(), true);
        RenderUtils.renderText(currentTip, 20, height - 30, 1f, Color.WHITE.getRGB(), true);

        if (stopWatch.getNanoTime() / 1E9d >= currentWaitTime)
        {
            stopWatch.stop();
            stopWatch.reset();
            stopWatch.start();
            roll = true;
        }
    }
}
