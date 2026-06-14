package de.Cooltechno.idk.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.GsonConfigInstance;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.Color;
import java.nio.file.Path;

public class ModConfig {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("idk_config.json");

    public static final GsonConfigInstance<ModConfig> HANDLER = GsonConfigInstance.createBuilder(ModConfig.class)
            .setPath(CONFIG_PATH)
            .build();

    public boolean enabled = true;
    public float heartThreshold = 10.0f;
    public float maxOpacity = 0.45f;
    public int edgeColor = 0xFF0000;
    public int blendDepth = 40;

    public static boolean isEnabled() {
        return HANDLER.getConfig() != null && HANDLER.getConfig().enabled;
    }

    public static float getHeartThreshold() {
        return HANDLER.getConfig() != null ? HANDLER.getConfig().heartThreshold : 10.0f;
    }

    public static float getMaxOpacity() {
        return HANDLER.getConfig() != null ? HANDLER.getConfig().maxOpacity : 0.45f;
    }

    public static int getEdgeColor() {
        return HANDLER.getConfig() != null ? HANDLER.getConfig().edgeColor : 0xFF0000;
    }

    public static int getBlendDepth() {
        return HANDLER.getConfig() != null ? HANDLER.getConfig().blendDepth : 40;
    }

    public static Screen createScreen(Screen parent) {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> builder
                .title(Text.literal("Border Tint Config"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("General"))

                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Enable Screen Tint"))
                                .description(OptionDescription.of(Text.literal("Toggle the low health screen border tint.")))
                                .binding(true, () -> config.enabled, val -> config.enabled = val)
                                .controller(TickBoxControllerBuilder::create)
                                .build())

                        .option(Option.<Float>createBuilder()
                                .name(Text.literal("Heart Threshold"))
                                .description(OptionDescription.of(Text.literal("HP value where tint starts (1 heart = 2 HP)")))
                                .binding(10.0f, () -> config.heartThreshold, val -> config.heartThreshold = val)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(1.0f, 20.0f).step(0.5f))
                                .build())

                        .option(Option.<Float>createBuilder()
                                .name(Text.literal("Maximum Opacity"))
                                .description(OptionDescription.of(Text.literal("Lower this value to make the tint less intense.")))
                                .binding(0.45f, () -> config.maxOpacity, val -> config.maxOpacity = val)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.0f, 1.0f).step(0.05f))
                                .build())

                        .option(Option.<Color>createBuilder()
                                .name(Text.literal("Border Color"))
                                .description(OptionDescription.of(Text.literal("Choose a custom color tint for your screen edges.")))
                                .binding(new Color(0xFF0000), () -> new Color(config.edgeColor), val -> config.edgeColor = val.getRGB() & 0xFFFFFF)
                                .controller(ColorControllerBuilder::create)
                                .build())

                        .option(Option.<Integer>createBuilder()
                                .name(Text.literal("Border Size (Pixels)"))
                                .description(OptionDescription.of(Text.literal("Adjust how far inward from the edges the screen tint extends.")))
                                .binding(40, () -> config.blendDepth, val -> config.blendDepth = val)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(5, 150).step(5))
                                .build())
                        .build())
        ).generateScreen(parent);
    }
}