package io.github.andrew6rant.invcrafting.mixin;

import io.github.andrew6rant.invcrafting.LockableCraftingSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<RecipeInputInventory> {
    @Mutable @Shadow @Final private RecipeInputInventory craftingInput;

    //@Shadow @Final private PlayerEntity owner;

    //@Unique public Slot slot_test;

    public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @ModifyConstant(method = "<init>",
            constant = {
                    @Constant(intValue = 2,
                            ordinal = 0),
                    @Constant(intValue = 2,
                            ordinal = 1)})
    private int invcrafting$craftingInvSize(int value) {
        return 3;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V",
            constant = @Constant(intValue = 2, ordinal = 2))
    public int invcrafting$makeForLoopNotRun(int constant) {
        // Vanilla Minecraft adds the 2x2 crafting slots inm a loop. Instead of modifying it, I will stop the loop from running
        // and add slots individually (since I need to add slots both before and after the player inventory
        return -1;
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(target = "Lnet/minecraft/screen/PlayerScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
                    value = "INVOKE", ordinal = 0, shift = At.Shift.BEFORE))
    private void invcrafting$swapCraftingInput(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        //this.craftingInput = new CraftingInventory((PlayerScreenHandler)(Object)this, 3, 3);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(target = "Lnet/minecraft/screen/PlayerScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
                    value = "INVOKE", ordinal = 0, shift = At.Shift.AFTER))
    private void invcrafting$swapCraftingInput2(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        this.addSlot(new Slot(this.craftingInput, 0, 98 + 0 * 18, 18 + 0 * 18));
        this.addSlot(new Slot(this.craftingInput, 1, 98 + 1 * 18, 18 + 0 * 18));
        this.addSlot(new LockableCraftingSlot(owner, this.craftingInput, 2, 98 + 2 * 18, 18 + 0 * 18));
        this.addSlot(new Slot(this.craftingInput, 3, 98 + 0 * 18, 18 + 1 * 18));
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void invcrafting$PlayerScreenHandler(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        this.addSlot(new Slot(this.craftingInput, 4, 98 + 1 * 18, 18 + 1 * 18));
        this.addSlot(new LockableCraftingSlot(owner, this.craftingInput, 5, 98 + 2 * 18, 18 + 1 * 18));
        this.addSlot(new LockableCraftingSlot(owner, this.craftingInput, 6, 98 + 0 * 18, 18 + 2 * 18));
        this.addSlot(new LockableCraftingSlot(owner, this.craftingInput, 7, 98 + 1 * 18, 18 + 2 * 18));
        this.addSlot(new LockableCraftingSlot(owner, this.craftingInput, 8, 98 + 2 * 18, 18 + 2 * 18));
    }

    /*
    @Inject(method = "clearCraftingSlots", at = @At(value = "HEAD"))
    public void invcrafting$clearCraftingSlots(CallbackInfo ci) {
        if (this.owner.getInventory().contains(Items.CRAFTING_TABLE.getDefaultStack())) {
            if (slot_test != null) {
                ((SlotAccessor)slot_test).setX((98 + 2 * 18) + 25);
                System.out.println("success!!!");
            } else {
                System.out.println("null??");

            }
        }
    }*/

    /**
     * @author Andrew6rant (Andrew Grant)
     * @reason I made the inventory craftin grid 3x3 (+ output slot)
     * It returns 10 always even if the 2x2 grid is valid because the disabled slots are still there
     */
    @Overwrite
    public int getCraftingSlotCount() {
        //if (player.getInventory().contains(Items.CRAFTING_TABLE.getDefaultStack())) {
        //}
        return 10;
    }
}
