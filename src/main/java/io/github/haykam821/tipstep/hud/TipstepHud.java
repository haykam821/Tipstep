package io.github.haykam821.tipstep.hud;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.data.server.BlockTagsProvider;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainer;
import net.minecraft.tag.Tag.TagEntry;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class TipstepHud extends DrawableHelper {
	public BlockHitResult getTrace(MinecraftClient client) {
		Entity cameraEntity = client.getCameraEntity();
		HitResult rayTrace = cameraEntity.rayTrace(client.interactionManager.getReachDistance(), 0, false);

		if (rayTrace.getType() == HitResult.Type.BLOCK) {
			return (BlockHitResult) rayTrace;
		}
		return null;
	}

	public String getBlockName(ItemStack stack) {
		Text blockName = new TranslatableText(stack.getTranslationKey());
		blockName = blockName.formatted(stack.getRarity().formatting);
		if (stack.hasCustomName()) {
			blockName = blockName.formatted(Formatting.ITALIC);
		}

		return blockName.asFormattedString();
	}

	public void render(int scaledWidth, MinecraftClient client) {
		BlockHitResult blockTrace = this.getTrace(client);
		if (blockTrace != null) {
			BlockPos pos = blockTrace.getBlockPos();
			BlockState blockState = client.world.getBlockState(pos);
			if (blockState == null) return;
			
			Block block = blockState.getBlock();
			ItemStack pickStack = block.getPickStack(client.world, pos, blockState);

			String blockName = this.getBlockName(pickStack);
			int blockNameWidth = client.textRenderer.getStringWidth(blockName) / 2;

			int centerX = scaledWidth / 2; 

			// Render
			DrawableHelper.fill(centerX - blockNameWidth - 2, 8, centerX + blockNameWidth + 2, 19, 0x40000000);
			this.drawCenteredString(client.textRenderer, blockName, centerX, 10, Formatting.WHITE.getColorValue());
			client.getItemRenderer().renderGuiItem(pickStack, centerX - 8, 22);
		}
	}
}