package com.rae.crowns.init.misc;

import com.simibubi.create.AllTags;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.world.item.Item;

import static com.rae.crowns.CROWNS.REGISTRATE;

@SuppressWarnings("ALL")
public class ItemInit {

    //to do list -> uranium ore (enrichment ?) + plutonium (created from 235) + depletion of fuel

    public static final ItemEntry<Item> URANIUM_INGOT = REGISTRATE
            .item("uranium_ingot", Item::new)
            .tag(AllTags.commonItemTag("ingots/uranium"), AllTags.commonItemTag("ingots"))
            .register();

    public static final ItemEntry<Item> RAW_URANIUM = REGISTRATE
            .item("raw_uranium", Item::new)
            .tag(AllTags.commonItemTag("raw_materials/uranium"), AllTags.commonItemTag("raw_materials"))
            .register();

    public static void register() {}

}
