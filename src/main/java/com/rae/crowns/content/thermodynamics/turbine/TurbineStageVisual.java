package com.rae.crowns.content.thermodynamics.turbine;

import com.rae.crowns.init.client.PartialModelInit;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;

import java.util.function.Consumer;

public class TurbineStageVisual extends KineticBlockEntityVisual<TurbineStageBlockEntity> {

    protected final RotatingInstance turbine;

    public TurbineStageVisual(VisualizationContext context, TurbineStageBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        var axis = rotationAxis();
        turbine = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(PartialModelInit.TURBINE_STAGE))
                .createInstance();

        turbine.setup(TurbineStageVisual.this.blockEntity)
                .setPosition(getVisualPosition())
                .rotateToFace(axis)
                .setChanged();
    }

    @Override
    public void update(float pt) {
        turbine.setup(blockEntity)
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(turbine);
    }

    @Override
    protected void _delete() {
        turbine.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(turbine);
    }
}
