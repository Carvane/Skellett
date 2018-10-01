package me.limeglass.skellett.lang;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import me.limeglass.skellett.utils.Utils;
import me.limeglass.skellett.utils.annotations.AllChangers;
import me.limeglass.skellett.utils.annotations.Changers;
import me.limeglass.skellett.utils.annotations.Multiple;

public abstract class ChangeablePropertyExpression<F, T1, T2> extends SkellettPropertyExpression<F, T1> {
	
	@SuppressWarnings("unchecked")
	public Class<? extends T2> getChangeType() {
		return (Class<T2>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected T1[] get(Event event, F[] objects) {
		for (F f : objects) {
			for (T1 t : get(f)) {
				collection.add(t);
			}
		}
		return (T1[]) collection.toArray();
	}
	
	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		Class<? extends T2> expression = getChangeType();
		Class<?>[] returnable = (getClass().isAnnotationPresent(Multiple.class)) ? CollectionUtils.array(Utils.getArrayClass(expression)) : CollectionUtils.array(expression);
		if (getClass().isAnnotationPresent(AllChangers.class)) return returnable;
		if (!getClass().isAnnotationPresent(Changers.class)) return null;
		return (Arrays.asList(getClass().getAnnotation(Changers.class).value()).contains(mode)) ? returnable : null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (isNull(event) || delta == null) return;
		for (F f : getExpr().getArray(event)) {
			for (Object object : delta) {
				change(f, (T2)object, mode);
			}
		}
	}
	
	public abstract T1[] get(F f);
	
	public abstract void change(F f, T2 t, ChangeMode mode);

}