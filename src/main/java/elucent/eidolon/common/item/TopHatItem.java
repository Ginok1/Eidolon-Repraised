package elucent.eidolon.common.item;

import elucent.eidolon.Eidolon;
import elucent.eidolon.client.ClientRegistry;
import elucent.eidolon.common.item.model.TopHatModel;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TopHatItem extends ArmorItem {
    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

    public static class Material implements ArmorMaterial {
        @Override
        public int getDurabilityForType(Type slot) {
            return MAX_DAMAGE_ARRAY[slot.getSlot().getIndex()] * 7;
        }

        @Override
        public int getDefenseForType(@NotNull Type slot) {
            return 1;
        }

        @Override
        public int getEnchantmentValue() {
            return 12;
        }

        @Override
        public @NotNull SoundEvent getEquipSound() {
            return ArmorMaterials.LEATHER.getEquipSound();
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(Items.BLACK_WOOL));
        }

        @Override
        public @NotNull String getName() {
            return Eidolon.MODID + ":top_hat";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }

        public static final Material INSTANCE = new Material();
    }

    String loreTag = null;

    public Item setLore(String tag) {
        this.loreTag = tag;
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        if (this.loreTag != null) {
            tooltip.add(Component.literal(""));
            tooltip.add(Component.literal(String.valueOf(ChatFormatting.DARK_PURPLE) + ChatFormatting.ITALIC + I18n.get(this.loreTag)));
        }
    }

    public TopHatItem(Properties builderIn) {
        super(Material.INSTANCE, Type.HELMET, builderIn);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public @NotNull TopHatModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                float pticks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
                ClientRegistry.TOP_HAT_MODEL.slot = getEquipmentSlot();
                ClientRegistry.TOP_HAT_MODEL.copyFromDefault(_default);
                ClientRegistry.TOP_HAT_MODEL.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return ClientRegistry.TOP_HAT_MODEL;
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return Eidolon.MODID + ":textures/entity/hat.png";
    }
}
