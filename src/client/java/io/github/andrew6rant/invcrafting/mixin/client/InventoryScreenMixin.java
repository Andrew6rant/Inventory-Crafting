package io.github.andrew6rant.invcrafting.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {
    public InventoryScreenMixin(PlayerEntity player) {
        super(player.playerScreenHandler, player.getInventory(), Text.translatable("container.crafting"));
    }

    @Inject(method = "drawBackground", at = @At(value = "INVOKE",
    target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", shift = At.Shift.AFTER))
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        int i = this.x;
        int j = this.y;
        if (this.client.player.getInventory().contains(Items.CRAFTING_TABLE.getDefaultStack())) {
            context.drawTexture(new Identifier("textures/gui/more_crafting_slots.png"), i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        } else {
            context.drawTexture(new Identifier("textures/gui/more_crafting_slots_disabled.png"), i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        }
    }
}
