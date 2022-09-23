package net.magnumvd.spyglasscontrols.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Shadow
    private MinecraftClient client;

    boolean smoothCamOld;

    @Inject(method = "updateMouse", at = @At("HEAD"))
    private void Head(CallbackInfo info) {

        smoothCamOld = this.client.options.smoothCameraEnabled;
        if (this.client.player != null) {
            this.client.options.smoothCameraEnabled = smoothCamOld || (this.client.options.getPerspective().isFirstPerson() && this.client.player.isUsingSpyglass());
        }
    }
    @Inject(method = "updateMouse", at = @At("TAIL"))
    private void Tail(CallbackInfo info) {

        this.client.options.smoothCameraEnabled = smoothCamOld;
    }
}
