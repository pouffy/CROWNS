package com.rae.crowns.content.nuclear;

import com.simibubi.create.content.redstone.rail.ControllerRailBlock;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public class AssemblyBlockGenerator extends SpecialBlockStateGen {

    @Override
    protected Property<?>[] getIgnoredProperties() {
        return new Property<?>[] { AssemblyBlock.TEMPERATURE };
    }

    @Override
    protected int getXRotation(BlockState state) {
        return switch (state.getValue(AssemblyBlock.AXIS)) {
            case X, Z ->  90;
            case Y -> 0;
        };
    }

    @Override
    protected int getYRotation(BlockState state) {
        return switch (state.getValue(AssemblyBlock.AXIS)) {
            case X -> 90;
            case Z, Y -> 0;
        };
    }

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        Direction.Axis axis = state.getValue(AssemblyBlock.AXIS);
        String name = state.getValue(AssemblyBlock.ACTIVITY).getSerializedName();
        String suffix = axis == Direction.Axis.Y ? "" : "_horizontal";
        return prov.models().getExistingFile(prov.modLoc("block/fuel_assembly/" + name + suffix));
    }
}
