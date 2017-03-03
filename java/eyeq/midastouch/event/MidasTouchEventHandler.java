package eyeq.midastouch.event;

import eyeq.util.oredict.CategoryTypes;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MidasTouchEventHandler {
    public static final String OREDICT_FRUIT_APPLE = CategoryTypes.PREFIX_FRUIT.getDictionaryName("apple");

    @SubscribeEvent
    public void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();
        if(world.isRemote || player.isCreative()) {
            return;
        }
        if(player.getHeldItemMainhand().isEmpty() && player.getHeldItemOffhand().isEmpty()) {
            world.setBlockState(event.getPos(), Blocks.GOLD_BLOCK.getDefaultState());
        }
    }

    @SubscribeEvent
    public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();
        if(world.isRemote || player.isCreative()) {
            return;
        }
        if(player.getHeldItemMainhand().isEmpty() && player.getHeldItemOffhand().isEmpty()) {
            world.setBlockState(event.getPos(), Blocks.GOLD_BLOCK.getDefaultState());
        }
    }

    @SubscribeEvent
    public void onEntityItemPickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if(player.getEntityWorld().isRemote || player.isCreative()) {
            return;
        }
        EntityItem entity = event.getItem();
        ItemStack itemStack = entity.getEntityItem();
        Item item = itemStack.getItem();

        ItemStack gold;
        if(item instanceof ItemBlock) {
            if(((ItemBlock) item).getBlock() == Blocks.GOLD_BLOCK) {
                return;
            }
            gold = new ItemStack(Blocks.GOLD_BLOCK, itemStack.getCount());
        } else if(item instanceof ItemArmor) {
            Item armor;
            switch(((ItemArmor) item).getEquipmentSlot()) {
            case FEET:
                armor = Items.GOLDEN_BOOTS;
                break;
            case LEGS:
                armor = Items.GOLDEN_LEGGINGS;
                break;
            case CHEST:
                armor = Items.GOLDEN_CHESTPLATE;
                break;
            case HEAD:
            default:
                armor = Items.GOLDEN_HELMET;
                break;
            }
            if(item == armor) {
                return;
            }
            gold = new ItemStack(armor, itemStack.getCount(), itemStack.getMetadata());
        } else {
            if(item == Items.GOLDEN_APPLE || item == Items.GOLD_INGOT) {
                return;
            }
            if(UOreDictionary.contains(itemStack, OREDICT_FRUIT_APPLE)) {
                gold = new ItemStack(Items.GOLDEN_APPLE, itemStack.getCount());
            } else {
                gold = new ItemStack(Items.GOLD_INGOT, itemStack.getCount());
            }
        }
        entity.setEntityItemStack(gold);
        entity.onCollideWithPlayer(event.getEntityPlayer());
        event.setCanceled(true);
    }
}
