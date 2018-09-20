package cn.academy.tutorial;

import cn.academy.util.LocalHelper;
import cn.academy.Resources;
import cn.academy.tutorial.ACTutorial.Tag;
import cn.academy.tutorial.client.RecipeHandler;
import cn.lambdalib2.cgui.Widget;
import cn.lambdalib2.render.obj.ObjLegacyRender;
import cn.lambdalib2.util.Colors;
import cn.lambdalib2.util.HudUtils;
import cn.lambdalib2.util.RenderUtils;
import cn.lambdalib2.util.GameTimer;
import cn.lambdalib2.vis.CompTransform;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import cn.lambdalib2.render.legacy.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.Color;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public final class ViewGroups {

    // This class used a SideOnly hack to make user be able to specify display on init without considering side only issue.

    private static Random random = new Random();

    private static final ViewGroup nothing = new ViewGroup() {
        @Override
        public Tag getTag() {
            return null;
        }
    };

    public static ViewGroup drawsBlock(Block block) {
        return drawsBlock(block, 0);
    }

    public static ViewGroup drawsBlock(Block block, int meta) {
        if (client()) return drawsBlockImpl(block, meta);
        return nothing;
    }

    public static ViewGroup drawsItem(Item item) {
        return drawsItem(item, 0);
    }

    public static ViewGroup drawsItem(Item item, int metadata) {
        if (client()) return drawsItemImpl(item, metadata);
        return nothing;
    }

    public static ViewGroup recipes(Item item) {
        if (client()) return recipesImpl(item);
        return nothing;
    }

    public static ViewGroup recipes(Block block) {
        return recipes(Item.getItemFromBlock(block));
    }

    public static ViewGroup displayModel(
        String model,
        String textureName,
        CompTransform transform
    ) {
        if (client()) return displayModelImpl(model, textureName, transform);
        return nothing;
    }

    public static ViewGroup displayModel(String model, CompTransform transform) {
        return displayModel(model, model, transform);
    }

    public static ViewGroup displayIcon(
            String icon,
            float dx, float dy, 
            float scale, Color color) {
        ResourceLocation icon_res = Resources.getTexture(icon);
        return new ViewGroup() {
            @Override
            public Widget[] getSubViews() {
                return new Widget[] { withDraw(() -> {
                    RenderUtils.loadTexture(icon_res);
                    Colors.bindToGL(color);

                    glDepthFunc(GL_ALWAYS);
                    glPushMatrix();
                    glTranslatef(dx, dy, 0);
                    glScalef(scale, scale, 1);
                    HudUtils.rect(-.5, -.5, 1, 1);
                    glPopMatrix();
                    glDepthFunc(GL_LEQUAL);
                })};
            }

            @Override
            public Tag getTag() {
                return Tag.VIEW;
            }
        };
    }





    // --------- Internal

    @SideOnly(Side.CLIENT)
    private static ViewGroup recipesImpl(Item item) {
        return new ViewGroup() {
            Widget[] result = RecipeHandler.instance.recipeOfItem(item);
            ItemStack stack = new ItemStack(item, 1);

            @Override
            public Widget[] getSubViews() {
                return result;
            }

            @Override
            public Tag getTag() {
                return Tag.CRAFT;
            }

            @Override
            public String getDisplayText() {
                return localCraft(stack);
            }
        };
    }

    @SideOnly(Side.CLIENT)
    private static ViewGroup displayModelImpl(String mdl, String texture, CompTransform transform) {
        ObjLegacyRender model = Resources.getModel(mdl);
        ResourceLocation res_tex = Resources.getTexture("models/" + texture);
        return new ViewGroup() {
            @Override
            public Widget[] getSubViews() {
                return new Widget[] { withDraw(() -> {
                    glPushMatrix();
                    glRotated((GameTimer.getAbsTime() / 50.0) % 360.0, 0, 1, 0);
                    transform.doTransform();
                    RenderUtils.loadTexture(res_tex);
                    model.renderAll();
                    glPopMatrix();
                })};
            }

            @Override
            public Tag getTag() {
                return Tag.VIEW;
            }
        };
    }

    @SideOnly(Side.CLIENT)
    private static ViewGroup drawsBlockImpl(Block block, int metadata) {
        return new ViewGroup() {

            @Override
            public Widget[] getSubViews() {
                return new Widget[] { withDraw(() -> {
                    // TODO new block render routine
//                    final Minecraft mc = Minecraft.getMinecraft();
//                    final RenderBlocks renderer = RenderBlocks.getInstance();
//                    final Tessellator tes = Tessellator.instance;
//
//                    renderer.blockAccess = Minecraft.getMinecraft().theWorld;
//
//                    mc.world.setBlock(0, 0, 0, block, metadata, 0x00);
//                    RenderUtils.loadTexture(TextureMap.locationBlocksTexture);
//                    glCullFace(GL_BACK);
//                    glTranslated(0.15, 0.1, -1);
//                    glRotated((GameTimer.getAbsTime() / 80.0) % 360.0, 0, 1, 0);
//                    glScaled(.8, .8, .8);
//                    glTranslated(-.5, -.5, -.5);
//                    tes.startDrawingQuads();
//                    renderer.renderBlockAllFaces(block, 0, 0, 0);
//                    tes.draw();
//                    glCullFace(GL_FRONT);
                })};
            }

            @Override
            public Tag getTag() {
                return Tag.VIEW;
            }
        };
    }

    @SideOnly(Side.CLIENT)
    private static ViewGroup drawsItemImpl(Item item, int metadata) {
        ItemStack stack = new ItemStack(item, 1, metadata);
        return new ViewGroup() {

            @Override
            public Widget[] getSubViews() {
                return new Widget[] { withDraw(() -> {
                    // TODO new Item render routine
//                    glDepthFunc(GL_ALWAYS);
//                    RenderItem.renderInFrame = true;
//                    glTranslated(0.54, 0.5, 0);
//                    glScaled(-1/16.0, -1/16.0, 1);
//                    RenderUtils.loadTexture(TextureMap.locationItemsTexture);
//                    RenderUtils.renderItemInventory(stack);
//                    glDepthFunc(GL_LEQUAL);
                })};
            }

            @Override
            public Tag getTag() {
                return Tag.VIEW;
            }
        };
    }

    private static boolean client() {
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    }

    private static String localCraft(ItemStack stack) {
        return LocalHelper.root.getFormatted("ac.tutorial.crafting", stack.getDisplayName());
    }

    private ViewGroups() {}
}