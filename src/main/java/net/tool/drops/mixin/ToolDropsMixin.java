package net.tool.drops.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.tool.drops.ToolDropsMod;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ToolDropsMixin {

    @Shadow
    public abstract String getTranslationKey();

    @Inject(method = "damage(ILjava/util/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z", at = @At(value = "RETURN", ordinal = 2))
    public void damage(int amount, Random random, ServerPlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (player != null && cir.getReturnValue()) {
            LootTable lootTable = ToolDropsMod.lootTables.get(this.getTranslationKey());
            if (lootTable != null) {
                DamageSource source = DamageSource.GENERIC;
                LootContext.Builder builder = (new LootContext.Builder((ServerWorld) player.world)).random(random).parameter(LootContextParameters.THIS_ENTITY, player).parameter(LootContextParameters.ORIGIN, player.getPos()).parameter(LootContextParameters.DAMAGE_SOURCE, source).optionalParameter(LootContextParameters.KILLER_ENTITY, source.getAttacker()).optionalParameter(LootContextParameters.DIRECT_KILLER_ENTITY, source.getSource()).luck(player.getLuck());
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), player::dropStack);
            }
        }
    }
}
