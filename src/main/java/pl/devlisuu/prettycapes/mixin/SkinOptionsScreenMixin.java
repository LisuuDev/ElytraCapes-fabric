package pl.devlisuu.prettycapes.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.devlisuu.prettycapes.PrettyCapes;

@Mixin(SkinOptionsScreen.class)
public abstract class SkinOptionsScreenMixin extends Screen {

    protected SkinOptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At(value = "TAIL", shift = At.Shift.BEFORE))
    private void addCustomButton(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 24 * 0, 150, 20,
                Text.of(String.format("Cape Style: %s", PrettyCapes.capeStyle)), (button) -> {
            switch(PrettyCapes.capeStyle) {
                case ELYTRA:
                    PrettyCapes.capeStyle = PrettyCapes.CapeStyle.DEFAULT;
                    break;
                case DEFAULT:
                    PrettyCapes.capeStyle = PrettyCapes.CapeStyle.ELYTRA;
                    break;
            }

            button.setMessage(Text.of(String.format("Cape Style: %s", PrettyCapes.capeStyle)));
        }));
    }

    @ModifyVariable(method = "init", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/client/gui/screen/option/SkinOptionsScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;",
        shift = At.Shift.AFTER
    ), ordinal = 0)
    private int injected(int i) {
        if (i == 0) return i + 1;
        else return i;
    }
}
