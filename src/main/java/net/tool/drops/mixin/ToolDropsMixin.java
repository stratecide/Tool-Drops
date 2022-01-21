package net.tool.drops.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.tool.drops.ToolDropsMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ToolDropsMixin {

    @Shadow
    public abstract Item getItem();

    @Inject(method = "damage(ILjava/util/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z", at = @At(value = "RETURN", ordinal = 2))
    public void damage(int amount, Random random, ServerPlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (player != null && cir.getReturnValue()) {
            LootTable lootTable = ToolDropsMod.lootTables.get(ToolDropsMod.identifierToString(Registry.ITEM.getId(getItem())));
            if (lootTable != null) {
                DamageSource source = DamageSource.GENERIC;
                LootContext.Builder builder = (new LootContext.Builder((ServerWorld) player.world)).random(random).parameter(LootContextParameters.THIS_ENTITY, player).parameter(LootContextParameters.ORIGIN, player.getPos()).parameter(LootContextParameters.DAMAGE_SOURCE, source).optionalParameter(LootContextParameters.KILLER_ENTITY, source.getAttacker()).optionalParameter(LootContextParameters.DIRECT_KILLER_ENTITY, source.getSource()).luck(player.getLuck());
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), player::dropStack);
            }
        }
    }
}
