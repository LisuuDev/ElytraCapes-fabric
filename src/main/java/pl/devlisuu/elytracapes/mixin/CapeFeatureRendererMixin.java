package pl.devlisuu.elytracapes.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pl.devlisuu.elytracapes.config.CapeStyleEnum;
import pl.devlisuu.elytracapes.config.ConfigManager;

@Mixin(CapeFeatureRenderer.class)
public abstract class CapeFeatureRendererMixin {

    @ModifyArgs(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private void forceCapeRendering(Args args, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l) {
        if(ConfigManager.getConfig().style == CapeStyleEnum.ALWAYS_NORMAL) {
            args.set(0, Items.BIG_DRIPLEAF);
        }
    }

    @Inject(method = "render*", at = @At("HEAD"), cancellable = true)
    public void cancelCapeRendering(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        if(ConfigManager.getConfig().modEnabled) {
            if(ConfigManager.getConfig().style == CapeStyleEnum.ALWAYS_ELYTRA
                    || ConfigManager.getConfig().style == CapeStyleEnum.DISABLE_CAPES) {
                ci.cancel();
            }
        }
    }
}