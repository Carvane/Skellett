package me.limeglass.skellett.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettEffect;
import me.limeglass.skellett.utils.annotations.Patterns;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;

@Name("Baby adult")
@Description("Change the adult/baby state of entities. If this doesn't work you will need to use Age as this syntax is for only some mobs. Mainly zombies.")
@Patterns("(make|set|change) %entities% to [a[n]] (1¦baby|2¦adult)")
public class EffBabyAdult extends SkellettEffect {
	
	@Override
	protected void execute(Event event) {
		if (areNull(event)) return;
		if (patternMark == 1) {
			for (Entity entity : expressions.getAll(event, Entity.class)) {
				if (Ageable.class.isAssignableFrom(entity.getClass())) {
					((Ageable)entity).setBaby();
				} else if (Zombie.class.isAssignableFrom(entity.getClass())) {
					((Zombie)entity).setBaby(true);
				}
			}
		} else {
			for (Entity entity : expressions.getAll(event, Entity.class)) {
				if (Ageable.class.isAssignableFrom(entity.getClass())) {
					((Ageable)entity).setAdult();
				} else if (Zombie.class.isAssignableFrom(entity.getClass())) {
					((Zombie)entity).setBaby(false);
				}
			}
		}
	}

}
