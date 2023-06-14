package com.owen1212055.customname.util;

import com.owen1212055.customname.CustomName;
import io.netty.buffer.Unpooled;
import io.papermc.paper.adventure.PaperAdventure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This classed is used for sending packets related to the interaction entity
 * sent to the client.
 */
public class SkeletonInteraction {

    private final CustomName customName;

    public SkeletonInteraction(CustomName customName) {
        this.customName = customName;
    }

    public Packet<ClientGamePacketListener> removePacket() {
        return new ClientboundRemoveEntitiesPacket(this.customName.getNametagId());
    }

    public Packet<ClientGamePacketListener> syncDataPacket() {
        List<SynchedEntityData.DataValue<?>> data = new ArrayList<>();
        data.add(ofData(Reflection.DATA_CUSTOM_NAME, Optional.ofNullable(PaperAdventure.asVanilla(this.customName.getName()))));

        byte value = (byte) (this.customName.isTargetEntitySneaking() ? 1 << 1 : 0);
        data.add(ofData(Reflection.DATA_SHARED_FLAGS_ID, value));

        return new ClientboundSetEntityDataPacket(this.customName.getNametagId(), data);
    }

    public Packet<?> initialSpawnPacket() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeVarInt(this.customName.getTargetEntity().getEntityId());
        buf.writeVarIntArray(new int[]{this.customName.getNametagId()});

        ClientboundSetEntityDataPacket initialCreatePacket = new ClientboundSetEntityDataPacket(this.customName.getNametagId(), List.of(
                ofData(Reflection.DATA_WIDTH_ID, 0f),
                ofData(Reflection.DATA_HEIGHT_ID, (float) this.customName.getEffectiveHeight()),
                ofData(Reflection.DATA_POSE, Pose.CROAKING),
                ofData(Reflection.DATA_CUSTOM_NAME_VISIBLE, true)
        ));
        Packet<ClientGamePacketListener> syncData = syncDataPacket();
        ClientboundSetEntityDataPacket afterCreateData = new ClientboundSetEntityDataPacket(this.customName.getNametagId(), List.of(
                ofData(Reflection.DATA_HEIGHT_ID, 99999999f)
        ));

        return new ClientboundBundlePacket(List.of(
                createPacket(), // Create entity
                initialCreatePacket,
                syncData,
                new ClientboundSetPassengersPacket(buf),
                afterCreateData
        ));
    }

    // int id, UUID uuid, double x, double y, double z, float pitch, float yaw, EntityType<?> entityType, int entityData, Vec3 velocity, double headYaw
    private Packet<ClientGamePacketListener> createPacket() {
        Location location = this.customName.getTargetEntity().getLocation();

        return new ClientboundAddEntityPacket(
                this.customName.getNametagId(),
                UUID.randomUUID(),
                location.x(),
                location.y() + this.customName.getPassengerOffset(), // Put the entity as close as possible to prevent lerping
                location.z(),
                0f,
                0f,
                EntityType.INTERACTION,
                0,
                Vec3.ZERO,
                0
        );
    }

    private static <T> SynchedEntityData.DataValue<T> ofData(EntityDataAccessor<T> data, T value) {
        return new SynchedEntityData.DataItem<>(data, value).value();
    }
}
