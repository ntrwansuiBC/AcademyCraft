package cn.academy.block.block;

import cn.academy.block.container.ContainerMetalFormer;
import cn.academy.block.tileentity.TileMetalFormer;
import cn.academy.crafting.client.ui.GuiMetalFormer;
import cn.lambdalib2.registry.mc.gui.GuiHandlerBase;
import cn.lambdalib2.registry.mc.gui.RegGuiHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 */
public class BlockMetalFormer extends ACBlockContainer {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    @RegGuiHandler
    public static GuiHandlerBase guiHandler = new GuiHandlerBase() {
        @SideOnly(Side.CLIENT)
        @Override
        protected Object getClientContainer(EntityPlayer player, World world, int x, int y, int z) {
            ContainerMetalFormer container = (ContainerMetalFormer) getServerContainer(player, world, x, y, z);
            return container == null ? null : GuiMetalFormer.apply(container);
        }
        
        @Override
        protected Object getServerContainer(EntityPlayer player, World world, int x, int y, int z) {
            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
            return tile instanceof TileMetalFormer ? new ContainerMetalFormer((TileMetalFormer) tile, player) : null;
        }
    };
    
//    IIcon topIcon, bottomIcon;
//    IIcon sideIcons[] = new IIcon[4];

    public BlockMetalFormer() {
        super(Material.ROCK, guiHandler);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setHardness(3.0f);
        setHarvestLevel("pickaxe", 1);
    }
    
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerBlockIcons(IIconRegister ir)  {
//        sideIcons[0] = this.ricon(ir, "metal_former_front");
//        sideIcons[1] = this.ricon(ir, "metal_former_right");
//        sideIcons[2] = this.ricon(ir, "metal_former_back");
//        sideIcons[3] = this.ricon(ir, "metal_former_left");
//        topIcon      = this.ricon(ir, "metal_former_top");
//        bottomIcon   = this.ricon(ir, "metal_former_bottom");
//    }
    
//    @Override
//    @SideOnly(Side.CLIENT)
//    public IIcon getIcon(int side, int meta) {
//        // Fix for the mystery 32767 metadata passed in when crafting.
//        meta %= 4;
//
//        if(side == 1)
//            return topIcon;
//        if(side == 0)
//            return bottomIcon;
//
//        final int offsets[] = { 2, 3, 1, 0 };
//
//        return sideIcons[(offsets[meta] + side) % 4];
//    }
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMetalFormer();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

}