package io.github.haykam821.tipstep.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.haykam821.tipstep.hud.TipstepHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Shadow
	private MinecraftClient client;

	@Shadow
	private int scaledWidth;

	@Unique
	private final TipstepHud tipstepHud = new TipstepHud();

	@Inject(method = "render", at = @At("TAIL"))
	public void renderTipstepHud(CallbackInfo ci) {
		if (this.client.options.keyPlayerList.isPressed()) return;
		this.tipstepHud.render(this.scaledWidth, this.client);
	}
}