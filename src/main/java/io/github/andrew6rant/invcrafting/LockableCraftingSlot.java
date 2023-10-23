package io.github.andrew6rant.invcrafting;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class LockableCraftingSlot extends Slot {
    private final PlayerEntity player;

    public LockableCraftingSlot(PlayerEntity player, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.player = player;
    }

    @Override
    public boolean isEnabled() {
        return player.getInventory().contains(Items.CRAFTING_TABLE.getDefaultStack());
    }
}
