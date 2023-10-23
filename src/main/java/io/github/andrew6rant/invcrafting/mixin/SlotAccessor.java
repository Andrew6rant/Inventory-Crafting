package io.github.andrew6rant.invcrafting.mixin;

import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Slot.class)
public interface SlotAccessor {

    @Accessor("x")
    @Final
    @Mutable
    void setX(int value);

    @Accessor("y")
    @Final
    @Mutable
    void setY(int value);
}
