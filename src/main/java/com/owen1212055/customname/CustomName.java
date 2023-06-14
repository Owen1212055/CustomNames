package com.owen1212055.customname;

import com.owen1212055.customname.util.SkeletonInteraction;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CustomName {

    private final SkeletonInteraction interaction = new SkeletonInteraction(this);

    // Target entity constants
    private final double effectiveHeight;
    private final Entity targetEntity;
    private final double passengerOffset;

    // Custom name constants
    private final int nametagEntityId;

    // States
    private boolean targetEntitySneaking;
    @Nullable
    private Component name;
    private boolean hidden;

    CustomName(Entity entity) {
        this.nametagEntityId = Bukkit.getUnsafe().nextEntityId();
        this.targetEntity = entity;

        net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        double ridingOffset = nmsEntity.getPassengersRidingOffset();
        double nametagOffset = nmsEntity.getNameTagOffsetY();

        // First, negate the riding offset to get to the bounding of the entity's bounding box
        // Negate the natural nametag offset of interaction entities (0.5)
        // Add the actual offset of the nametag
        this.effectiveHeight = -ridingOffset - 0.5 + nametagOffset;
        this.passengerOffset = ridingOffset;
    }

    public void setName(Component name) {
        this.name = name;
        this.syncData();
    }

    public void setTargetEntitySneaking(boolean targetEntitySneaking) {
        this.targetEntitySneaking = targetEntitySneaking;
        this.syncData();
    }

    public void sendToClient(@NotNull Player entity) {
        if (this.hidden) {
            return;
        }

        ((CraftPlayer) entity).getHandle().connection.send(this.interaction.initialSpawnPacket());
    }

    public void removeFromClient(@NotNull Player entity) {
        ((CraftPlayer) entity).getHandle().connection.send(this.interaction.removePacket());
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
        this.runOnTrackers((player) -> {
            if (hidden) {
                this.removeFromClient(player);
            } else {
                this.sendToClient(player);
            }
        });
    }

    @Nullable
    public Component getName() {
        return this.name;
    }

    public int getNametagId() {
        return this.nametagEntityId;
    }

    public Entity getTargetEntity() {
        return this.targetEntity;
    }

    public boolean isTargetEntitySneaking() {
        return this.targetEntitySneaking;
    }

    public double getEffectiveHeight() {
        return this.effectiveHeight;
    }

    public double getPassengerOffset() {
        return this.passengerOffset;
    }

    // Utilities
    private void syncData() {
        if (this.hidden) {
            return;
        }

        Packet<ClientGamePacketListener> dataPacket = this.interaction.syncDataPacket();
        this.runOnTrackers((player) -> {
            ((CraftPlayer) player).getHandle().connection.send(dataPacket);
        });
    }

    private void runOnTrackers(Consumer<Player> consumer) {
        for (Player player : this.targetEntity.getTrackedPlayers()) {
            consumer.accept(player);
        }
    }

}
