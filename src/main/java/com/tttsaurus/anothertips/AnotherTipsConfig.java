package com.tttsaurus.anothertips;

import net.minecraftforge.common.config.Configuration;
import java.util.*;

public class AnotherTipsConfig
{
    public static final List<String> TIP_LANG_KEYS = new ArrayList<>();
    public static final Map<String, Float> TIP_WAIT_TIME = new HashMap<>();
    public static boolean ENABLE_TEXT_BACKGROUND;

    public static Configuration CONFIG;

    public static void loadConfig()
    {
        try
        {
            CONFIG.load();

            AnotherTipsConfig.TIP_LANG_KEYS.clear();
            String[] TIP_LANG_KEYS = CONFIG.getStringList("Tip Lang Keys", "general", new String[0], "This is the pool of tips that will be displayed during the world loading screen");
            AnotherTipsConfig.TIP_LANG_KEYS.addAll(Arrays.asList(TIP_LANG_KEYS));

            AnotherTipsConfig.TIP_WAIT_TIME.clear();
            String[] TIP_WAIT_TIME = CONFIG.getStringList("Tip Wait Time", "general", new String[]{"en_us:0.1"}, "This is a map that determines the wait time for tips in different languages\nFor example, en_us:0.1 refers to \"wait 0.1s for each character for tips in English\"");
            for (String entry: TIP_WAIT_TIME)
            {
                String[] args = entry.split(":");
                if (args.length != 2) continue;

                float time = 0f;
                try
                {
                    time = Float.parseFloat(args[1]);
                }
                catch (Exception ignored) { continue; }

                AnotherTipsConfig.TIP_WAIT_TIME.put(args[0], time);
            }

            ENABLE_TEXT_BACKGROUND = CONFIG.getBoolean("Enable Text Background", "general", false, "A transparent box behind the text");
        }
        catch (Exception ignored) { }
        finally
        {
            if (CONFIG.hasChanged()) CONFIG.save();
        }
    }
}
