package com.tttsaurus.anothertips.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event, Logger logger)
    {

    }
    public void init(FMLInitializationEvent event, Logger logger)
    {
        logger.info("Another Tips starts initializing.");
    }
}
