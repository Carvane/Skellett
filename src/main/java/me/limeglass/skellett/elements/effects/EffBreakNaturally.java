package me.limeglass.skellett.elements.effects;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettEffect;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("Break block naturally")
@Description("Break blocks naturally meaning they drop like a normal block would when broken. You can also define to remove the drops.")
@Patterns({
		"break %blocks% [naturally] [(with|using) %-itemtype%] [and] without drops",
		"[naturally] break %block% [(with|using) %-itemtype%] [and] without drops",
		"break %blocks% [naturally] [(with|using) %-itemtype%]",
		"[naturally] break %block% [(with|using) %-itemtype%]"})
public class EffBreakNaturally extends SkellettEffect {

	@Override
	protected void execute(Event event) {
		if (isNull(event, Block.class)) return;
		Block[] blocks = expressions.getAll(event, Block.class);
		if (matchedPattern <= 1) {
			for (Block block : blocks) {
				//Could be better particles.
				block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.55, 0.5), 50, 0.3, 0.45, 0.3, block.getState().getData());
				block.setType(Material.AIR);
			}
		} else if (isNull(event, ItemStack.class)) {
			for (Block block : blocks) {
				block.breakNaturally();
			}
		} else {
			for (Block block : blocks) {
				block.breakNaturally(expressions.getSingle(event, ItemType.class).getRandom());
			}
		}
	}

}