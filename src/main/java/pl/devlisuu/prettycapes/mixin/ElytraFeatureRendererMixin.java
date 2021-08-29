package pl.devlisuu.prettycapes.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity> {

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private void forceElytraRendering(Args args, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        if(livingEntity instanceof PlayerEntity) {
            AbstractClientPlayerEntity abstractPlayer = (AbstractClientPlayerEntity)livingEntity;

            if(abstractPlayer.canRenderCapeTexture() && abstractPlayer.getCapeTexture() != null && abstractPlayer.isPartVisible(PlayerModelPart.CAPE)) {
                ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);

                if(!(abstractPlayer.isInvisible())) {
                    args.set(0, livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem());
                }else if(!(itemStack.isOf(Items.AIR))) {
                    args.set(0, livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem());
                }
            }
        }
    }
}