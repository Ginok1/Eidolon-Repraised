package elucent.eidolon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elucent.eidolon.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(method = "getJumpRidingScale", at = @At("HEAD"), cancellable = true)
    public void getJumpRidingScale(CallbackInfoReturnable<Float> ci) {
        if (ClientEvents.jumpTicks >= 5)
            ci.setReturnValue((ClientEvents.jumpTicks - 5 + Minecraft.getInstance().getFrameTime()) / 15.0f);
    }

    /*
    @Inject(method = "isRidingJumpable", at = @At("HEAD"), cancellable = true)
    public void isRidingJumpable(CallbackInfoReturnable<Boolean> ci) {
    	if (ClientEvents.jumpTicks >= 5 && ClientEvents.isInGui) ci.setReturnValue(true);
    }
     */
}
