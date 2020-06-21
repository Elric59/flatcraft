package dut.flatcraft;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import dut.flatcraft.resources.ExecutableAndTransformableResource;
import dut.flatcraft.resources.ExecutableResource;
import dut.flatcraft.resources.Resource;
import dut.flatcraft.resources.TransformableResource;
import dut.flatcraft.tools.Tool;
import dut.flatcraft.tools.ToolType;

/**
 * Utility class to easily access the images to use in the game.
 * 
 * @author leberre
 *
 */
public class MineUtils {

	private MineUtils() {
		// to prevent the creation of an instance of that class
	}

	public static final int DEFAULT_IMAGE_SIZE = 40;

	private static final Map<String, ImageIcon> cachedImages = new HashMap<>();
	private static final Map<String, Resource> cachedResources = new HashMap<>();
	private static final Map<String, Tool> cachedTools = new HashMap<>();

	public static final ImageIcon AIR = scaled("/textures/air.png");
	public static final ImageIcon PLAYER = scaled("/textures/player.png");

	/**
	 * Create a scaled up version of the original icon, to have a MineCraft effect.
	 *
	 * @param imageName the local name of the texture file (from which we can deduce
	 *                  the complete file name).
	 * @return an ImageIcon scaled up to 40x40.
	 */

	public static ImageIcon getImage(String localName) {
		ImageIcon cached = cachedImages.get(localName);
		if (cached == null) {
			String absoluteName = "/textures/default_" + localName + ".png";
			try {
				cached = new ImageIcon(ImageIO.read(MineUtils.class.getResource(absoluteName))
						.getScaledInstance(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE, Image.SCALE_DEFAULT));
			} catch (IOException e) {
				cached = new ImageIcon();
			}
			cachedImages.put(localName, cached);
		}
		return cached;
	}

	/**
	 * Create a scaled up version of the original icon, to have a MineCraft effect.
	 * 
	 * @param imageName the name of the texture file.
	 * @return an ImageIcon scaled up to 40x40.
	 */
	public static ImageIcon scaled(String imageName) {
		try {
			return new ImageIcon(ImageIO.read(MineUtils.class.getResource(imageName))
					.getScaledInstance(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			return new ImageIcon();
		}
	}

	/**
	 * Create a new scaled up version of the original icon, over an already scaled
	 * image (e.g. STONE).
	 *
	 * @param background a scaled up background image
	 * @param imageName  the new image to put on top of the background.
	 * @return an image consisting of imageName with the given background.
	 */
	public static ImageIcon overlay(String backgroundName, String foregroundName) {
		ImageIcon background = getImage(backgroundName);
		ImageIcon foreground = getImage(foregroundName);
		BufferedImage merged = new BufferedImage(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = merged.getGraphics();
		g.drawImage(background.getImage(), 0, 0, null);
		g.drawImage(foreground.getImage(), 0, 0, null);
		return new ImageIcon(merged);
	}

	/**
	 * Create a JButton without borders, to be used typically in a
	 * {@see GridLayout}.
	 * 
	 * @param icon the ImageIcon to be seen on the button.
	 * @return a button displaying icon, with no borders.
	 */
	public static JButton noBorderButton(ImageIcon icon) {
		JButton button = new JButton(icon);
		button.setBorder(BorderFactory.createEmptyBorder());
		return button;
	}

	/**
	 * Create a JToggleButton without borders, to be used typically in a
	 * {@see GridLayout}.
	 * 
	 * @param icon1 the ImageIcon to be seen first on the button.
	 * @param icon2 the ImageIcon to be seen once the button is pushed.
	 * @return a button displaying icon, with no borders.
	 */
	public static JToggleButton toggleNoBorderButton(ImageIcon icon1, ImageIcon icon2) {
		JToggleButton button = new JToggleButton(icon1);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setSelectedIcon(icon2);
		return button;
	}

	/**
	 * Create a JScrollPane which is incremented by 1/4 of a tile when scrolling
	 * once.
	 * 
	 * @param comp a component to be decorated with scrollbars.
	 * @return a JScrollPane with scroll speed adaptated to tiles.
	 */
	public static JScrollPane scrollPane(JComponent comp) {
		JScrollPane scroller = new JScrollPane(comp);
		scroller.getVerticalScrollBar().setUnitIncrement(DEFAULT_IMAGE_SIZE);
		scroller.getHorizontalScrollBar().setUnitIncrement(DEFAULT_IMAGE_SIZE);
		return scroller;
	}

	/**
	 * Utility method to simplify the creation of a resource if the localName of the
	 * resource is also the local name of its image.
	 * 
	 * @param localName a local name
	 * @param hardness  the hardness of the resource
	 * @param toolType  the type of tool required to dig it
	 * @return
	 */
	public static final Resource makeResource(String localName, int hardness, ToolType toolType) {
		return new Resource(localName, getImage(localName), hardness, toolType);
	}

	public static final Resource getResourceByName(String resourceName) {
		String key = resourceName.toLowerCase();
		Resource resource = cachedResources.get(key);
		if (resource != null) {
			return resource;
		}
		switch (key) {
		case "air":
			resource = new Resource("air", MineUtils.AIR, 10, ToolType.NO_TOOL);
			break;
		case "tree":
			resource = makeResource("tree", 10, ToolType.NO_TOOL);
			break;
		case "leaves":
			resource = new Resource("leaves", getImage("leaves_simple"), 1, ToolType.NO_TOOL);
			break;
		case "treetop":
			Resource digTree = getResourceByName("tree");
			resource = new TransformableResource("treetop", getImage("tree_top"), digTree, 10, ToolType.NO_TOOL);
			break;
		case "water":
			resource = makeResource("water", 1, ToolType.NO_TOOL);
			break;
		case "junglegrass":
			resource = makeResource("junglegrass", 1, ToolType.NO_TOOL);
			break;
		case "grass":
			resource = makeResource("grass", 1, ToolType.NO_TOOL);
			break;
		case "dirt":
			resource = makeResource("dirt", 1, ToolType.NO_TOOL);
			break;
		case "brick":
			resource = makeResource("brick", 3, ToolType.MEDIUM_TOOL);
			break;
		case "wood":
			resource = new Resource("wood", getImage("pine_wood"), 1, ToolType.NO_TOOL);
			break;
		case "stick":
			resource = makeResource("stick", 1, ToolType.NO_TOOL);
			break;
		case "lava":
			resource = makeResource("lava", 100000, ToolType.HARD_TOOL);
			break;
		case "coal_lump":
			resource = makeResource("coal_lump", 20, ToolType.MEDIUM_TOOL);
			break;
		case "coal":
			Resource lump = getResourceByName("coal_lump");
			resource = new TransformableResource("coal", overlay("stone", "mineral_coal"), lump, 20,
					ToolType.MEDIUM_TOOL);
			break;
		case "iron_lump":
			resource = makeResource("iron_lump", 30, ToolType.MEDIUM_TOOL);
			break;
		case "iron":
			lump = getResourceByName("iron_lump");
			resource = new TransformableResource("iron", overlay("stone", "mineral_iron"), lump, 30,
					ToolType.MEDIUM_TOOL);
			break;
		case "gold_lump":
			resource = makeResource("gold_lump", 40, ToolType.HARD_TOOL);
			break;
		case "gold":
			lump = getResourceByName("gold_lump");
			resource = new TransformableResource("gold", overlay("stone", "mineral_gold"), lump, 40,
					ToolType.HARD_TOOL);
			break;
		case "diamond_ore":
			resource = makeResource("diamond", 40, ToolType.HARD_TOOL);
			break;
		case "diamond":
			lump = getResourceByName("diamond_ore");
			resource = new TransformableResource("diamond", overlay("stone", "mineral_diamond"), lump, 40,
					ToolType.HARD_TOOL);
			break;
        case "crystal_shard":
            resource = makeResource("crystal_shard", 40, ToolType.MAGIC_TOOL);
                break;
        case "crystal_ore":
            resource = makeResource("crystal_shard", 40, ToolType.MAGIC_TOOL);
            break;
        case "crystal_tree":
            lump = getResourceByName("crystal_ore");
            resource =new TransformableResource("crystal_shard", overlay("tree","crystal_ore") , lump, 40, ToolType.MAGIC_TOOL);
            break;
        case "crystal_leaves":
            lump = getResourceByName("crystal_ore");
            resource =new TransformableResource("crystal_shard", overlay("leaves","crystal_ore") , lump, 40, ToolType.MAGIC_TOOL);
            break;
		case "stone":
			Resource cobble = getResourceByName("cobble");
			resource = new TransformableResource("stone", getImage("stone"), cobble, 10, ToolType.MEDIUM_TOOL);
			break;
		case "crystal":
			resource = makeResource("crystal", 10, ToolType.MAGIC_TOOL);
			break;
        case "crystal_brick":
            resource = makeResource("crystal_brick", 10, ToolType.MAGIC_TOOL);
            break;
		case "cobble":
			resource = makeResource("cobble", 10, ToolType.MEDIUM_TOOL);
			break;
		case "steel_lingot":
			resource = makeResource("steel_ingot", 10, ToolType.MEDIUM_TOOL);
			break;
		case "gold_lingot":
			resource = makeResource("gold_ingot", 10, ToolType.HARD_TOOL);
			break;
		case "copper_lump":
			resource = makeResource("copper_lump", 30, ToolType.MEDIUM_TOOL);
			break;
		case "copper":
			lump = getResourceByName("copper_lump");
			resource = new TransformableResource("copper", overlay("stone", "mineral_copper"), lump, 30,
					ToolType.MEDIUM_TOOL);
			break;
		case "copper_lingot":
			resource = makeResource("copper_ingot", 10, ToolType.HARD_TOOL);
			break;
		case "chest":
			resource = new ExecutableResource("chest", getImage("chest_front"), 100000, ToolType.NO_TOOL);
			break;
        case "skull":
            resource = makeResource("skull", 10, ToolType.NO_TOOL);
            break;
        case "skull_shard":
            resource = makeResource("skull_shard", 10, ToolType.MAGIC_TOOL);
            break;
        case "skull_stone":
            resource = makeResource("skull_stone", 10, ToolType.MAGIC_TOOL);
            break;
        case "skull_stonebrick":
            resource = makeResource("skull_stonebrick", 10, ToolType.MAGIC_TOOL);
            break;
        case "monster":
            lump = getResourceByName("skull");
            resource = new ExecutableAndTransformableResource("monster", overlay("ice", "monster"), lump, 5,
                    ToolType.SWORD_TOOL);
                break;
		default:
			throw new IllegalArgumentException(resourceName + " is not a correct resource name");
		}
		cachedResources.put(key, resource);
		return resource;
	}

	/**
	 * Utility method to simplify the creation of a tool.
	 * 
	 * @param localName a local name
	 * @param life      the initial capacity of the tool
	 * @param toolType  the type of tool
	 * @param decrement how much the tool capacity is decremented each time it is
	 *                  used
	 * @return a new tool
	 */
	public static final Tool makeTool(String localName, int life, ToolType toolType, int decrement) {
		return new Tool(localName, getImage("tool_" + localName), life, toolType, decrement);
	}

	public static final Tool createToolByName(String toolName) {
		String key = toolName.toLowerCase();
		Tool tool = cachedTools.get(key);
		if (tool != null) {
			return tool;
		}
		switch (key) {
		case "woodpick":
			tool = makeTool("woodpick", 100, ToolType.MEDIUM_TOOL, 5);
			break;
		case "stonepick":
			tool = makeTool("stonepick", 100, ToolType.MEDIUM_TOOL, 10);
			break;
		case "steelpick":
			tool = makeTool("steelpick", 100, ToolType.HARD_TOOL, 20);
			break;
		case "woodaxe":
			tool = makeTool("woodaxe", 100, ToolType.NO_TOOL, 1);
			break;
		case "stoneaxe":
			tool = makeTool("stoneaxe", 100, ToolType.NO_TOOL, 2);
			break;
		case "steelaxe":
			tool = makeTool("steelaxe", 100, ToolType.NO_TOOL, 2);
			break;
		case "diamondshovel":
			tool=makeTool("diamondshovel",300,ToolType.VERY_HARD_TOOL,30);
			break;
		case "diamondpick":
			tool=makeTool("diamondpick",300,ToolType.VERY_HARD_TOOL,40);
			break;
		case "crystalaxe":
			tool = makeTool("crystalaxe", 400, ToolType.MAGIC_TOOL, 60);
			break;
		case "crystalpick":
			tool = makeTool("crystalpick", 400, ToolType.MAGIC_TOOL, 60);
			break;
        case "crystalshovel":
            tool = makeTool("crystalshovel", 400, ToolType.MAGIC_TOOL, 60);
            break;
        case "crystalsword":
                tool = makeTool("crystalsword", 400, ToolType.SWORD_TOOL, 1);
                break;
        case "skullpick":
            tool = makeTool("skullpick", 2000, ToolType.BONE_TOOL, 30);
            break;
        case "magicstaff":
            tool = makeTool("magicstaff", 1000, ToolType.MAGIC_TOOL, 60);
            break;
		default:
			throw new IllegalArgumentException(toolName + " is not a correct tool name");
		}
		cachedTools.put(key, tool);
		return tool;
	}

	public static void fillRulesFromFile(String filename, Map<String, String> rules) {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(MineUtils.class.getResource(filename).openStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] pieces = line.split("=");
				rules.put(pieces[0], pieces[1]);
			}
		} catch (IOException ioe) {
			Logger.getAnonymousLogger().log(Level.INFO, "Rules file " + filename + " not found", ioe);
		}
	}
}
