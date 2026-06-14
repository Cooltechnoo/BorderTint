package de.Cooltechno.idk.client;

import de.Cooltechno.idk.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class IdkClient implements ClientModInitializer {
    private static boolean configLoaded = false;

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;

            if (player == null || player.isCreative() || player.isSpectator()) {
                return;
            }

            if (!configLoaded) {
                try {
                    ModConfig.HANDLER.load();
                } catch (Exception e) {
                    if (ModConfig.HANDLER.getConfig() != null) {
                        ModConfig.HANDLER.getConfig().enabled = true;
                        ModConfig.HANDLER.getConfig().heartThreshold = 10.0f;
                        ModConfig.HANDLER.getConfig().maxOpacity = 0.45f;
                        ModConfig.HANDLER.getConfig().edgeColor = 0xFF0000;
                        ModConfig.HANDLER.getConfig().blendDepth = 40;
                    }
                }
                configLoaded = true;
            }

            if (!ModConfig.isEnabled()) {
                return;
            }

            float currentHp = player.getHealth();
            float threshold = ModConfig.getHeartThreshold();

            if (currentHp < threshold) {
                float intensity = 1.0f - (currentHp / threshold);
                float maxAlpha = ModConfig.getMaxOpacity();

                int screenWidth = drawContext.getScaledWindowWidth();
                int screenHeight = drawContext.getScaledWindowHeight();

                int blendDepth = ModConfig.getBlendDepth();
                int rawColor = ModConfig.getEdgeColor();

                int red = (rawColor >> 16) & 0xFF;
                int green = (rawColor >> 8) & 0xFF;
                int blue = rawColor & 0xFF;

                int steps = Math.max(10, blendDepth / 3);
                int pixelsPerStep = Math.max(1, blendDepth / steps);

                for (int i = 0; i < steps; i++) {
                    float layerProgress = 1.0f - ((float) i / steps);
                    float smoothFactor = layerProgress * layerProgress;

                    int calculatedAlpha = (int) (intensity * maxAlpha * smoothFactor * 255.0f);
                    if (calculatedAlpha <= 0) continue;

                    int tintColor = (calculatedAlpha << 24) | (red << 16) | (green << 8) | blue;

                    int offset = i * pixelsPerStep;
                    int nextOffset = offset + pixelsPerStep;

                    drawContext.fill(offset, offset, screenWidth - offset, nextOffset, tintColor);
                    drawContext.fill(offset, screenHeight - nextOffset, screenWidth - offset, screenHeight - offset, tintColor);
                    drawContext.fill(offset, nextOffset, nextOffset, screenHeight - nextOffset, tintColor);
                    drawContext.fill(screenWidth - nextOffset, nextOffset, screenWidth - offset, screenHeight - nextOffset, tintColor);
                }
            }
        });
    }
}