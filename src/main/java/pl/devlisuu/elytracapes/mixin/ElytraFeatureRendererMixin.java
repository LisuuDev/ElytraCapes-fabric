package pl.devlisuu.elytracapes.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pl.devlisuu.elytracapes.config.CapeStyleEnum;
import pl.devlisuu.elytracapes.config.ConfigManager;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity> {

    @ModifyArgs(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private void forceElytraRendering(Args args, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        if(ConfigManager.getConfig().modEnabled) {
            if(ConfigManager.getConfig().style == CapeStyleEnum.ALWAYS_ELYTRA) {

                if (livingEntity instanceof PlayerEntity) {
                    AbstractClientPlayerEntity abstractPlayer = (AbstractClientPlayerEntity) livingEntity;

                    if (!abstractPlayer.isInvisible()
                            && abstractPlayer.isPartVisible(PlayerModelPart.CAPE)
                            && abstractPlayer.getSkinTextures().capeTexture() != null) {

                        // Item in chestplate slot will always be item in chest slot
                        args.set(0, abstractPlayer.getEquippedStack(EquipmentSlot.CHEST).getItem());
                    }
                }
            }else if(ConfigManager.getConfig().style == CapeStyleEnum.ALWAYS_NORMAL) {

                if (livingEntity instanceof PlayerEntity) {
                    AbstractClientPlayerEntity abstractPlayer = (AbstractClientPlayerEntity) livingEntity;

                    // Worst solution you can ever imagine (elytra check is negated by default)
                    if(abstractPlayer.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.BIG_DRIPLEAF) {
                        args.set(0, Items.SMALL_DRIPLEAF);
                    }else{
                        args.set(0, Items.BIG_DRIPLEAF);
                    }
                }
            }
        }
    }
}