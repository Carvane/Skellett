package me.limeglass.skellett.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import me.limeglass.skellett.lang.SkellettPropertyCondition.PropertyMode;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PropertyConditionType {
	public PropertyMode value();
}