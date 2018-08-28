package me.limeglass.skellett.elements.conditions;

import java.io.File;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.skellett.lang.SkellettCondition;
import me.limeglass.skellett.utils.annotations.Patterns;

@Name("File existance")
@Description("Test if a file exists on the server system. The string being a path string.")
@Patterns("[file] %string% (1¦does|2¦does(n't| not)) exist")
public class CondFileExists extends SkellettCondition {
	
	public boolean check(Event event) {
		if (areNull(event)) return !isNegated();
		File file = new File(expressions.getSingle(event, String.class));
        return (file.exists()) ? isNegated() : !isNegated();
	}

}
