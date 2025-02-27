package com.tttsaurus.anothertips.mixin.early;

import com.tttsaurus.anothertips.core.TipsManager;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.ChunkRenderWorker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkRenderWorker.class)
public class ChunkRenderWorkerMixin
{
    @Inject(method = "processTask", at = @At("RETURN"))
    public void processTask(ChunkCompileTaskGenerator generator, CallbackInfo ci)
    {
        if (TipsManager.getListenChunkBuild())
        {
            TipsManager.setListenChunkBuild(false);
            TipsManager.resetTimer();
            TipsManager.setActive(false);
        }
    }
}
