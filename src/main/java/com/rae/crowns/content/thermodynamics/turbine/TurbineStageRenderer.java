package com.rae.crowns.content.thermodynamics.turbine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.rae.crowns.init.client.PartialModelInit;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import com.simibubi.create.content.kinetics.flywheel.FlywheelBlockEntity;
import com.simibubi.create.content.kinetics.flywheel.FlywheelRenderer;
import com.simibubi.create.content.kinetics.flywheel.FlywheelVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class TurbineStageRenderer extends KineticBlockEntityRenderer<TurbineStageBlockEntity> {
    public TurbineStageRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    protected void renderSafe(TurbineStageBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (VisualizationManager.supportsVisualization(be.getLevel()))
            return;

        BlockState blockState = be.getBlockState();

        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        renderTurbine(be, ms, light, blockState, vb);

        //super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        //BlockState state = be.getBlockState();
        //FlywheelRenderer
        //Direction direction =  Direction.fromAxisAndDirection(((TurbineStageBlock)state.getBlock()).getRotationAxis(state), Direction.AxisDirection.POSITIVE);
        //VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
        //ms.pushPose();
        //SuperByteBuffer memoryRoll =
        //        CachedBuffers.partialFacing(PartialModelInit.TURBINE_STAGE, be.getBlockState(), direction.getOpposite());
        //standardKineticRotationTransform(memoryRoll, be, light).renderInto(ms, vb);
        //ms.popPose();
    }

    private void renderTurbine(TurbineStageBlockEntity be, PoseStack ms, int light, BlockState blockState, VertexConsumer vb) {
        SuperByteBuffer turbine = CachedBuffers.block(blockState);
        standardKineticRotationTransform(turbine, be, light);
        turbine.renderInto(ms, vb);
    }
}
