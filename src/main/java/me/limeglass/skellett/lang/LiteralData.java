package me.limeglass.skellett.lang;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.lang.Literal;

public class LiteralData {

	private Map<String, Integer> syntax = new HashMap<String, Integer>();
	private Literal<?>[] literals;
	public boolean nullable;

	public LiteralData(Literal<?>[] literals, String pattern) {
		Matcher matcher = Pattern.compile("\\%([^\\%]+)\\%").matcher(pattern);
		int i = 0;
		while (matcher.find()) {
			String literal = matcher.group(1);
			while ((literal.startsWith("-")) || (literal.startsWith("~")) || (literal.startsWith("*"))) {
				if (literal.startsWith("-")) nullable = true;
				literal = literal.substring(1, literal.length());
			}
			if (literal.endsWith("s")) literal = literal.substring(0, literal.length() - 1);
			if (literal.contains("/")) literal = "object";
			literal = literal + "0";
			if (syntax.containsKey(literal)) {
				while (syntax.containsKey(literal)) {
					char lastChar = literal.charAt(literal.length() - 1);
					if (lastChar >= '0' && lastChar <= '9') {
						literal = literal.replace(lastChar, '\0');
						literal = literal + ((int)lastChar + 1);
					}
				}
			}
			syntax.put(literal, i);
			i++;
		}
		this.literals = literals;
	}
	
	/**
	 * Check if any of the literals can be null
	 * 
	 * @return boolean
	*/
	public boolean isNullable() {
		return nullable;
	}
	
	/**
	 * Get the count of literals registered to this LiteralData.
	 * 
	 * @return int
	*/
	@Nullable
	public int size() {
		return syntax.size();
	}
	
	/**
	 * Grabs the literal associated with the raw string input.
	 * Recommended to not use this as there could be return errors.
	 * @see LiteralData#getSingle(Event, Class, int)
	 * @see LiteralData#getExpression(Class, int)
	 * 
	 * @return Literal<?>
	*/
	@Nullable
	public Literal<?> getRaw(String input) {
		return literals[syntax.get(input)];
	}
	
	/**
	 * Get a Literal by index.
	 * 
	 * @param index Grabs the literal in the calling storage index.
	 * @return Literal<?>
	*/
	@Nullable
	public Literal<?> get(int index) {
		if (index > literals.length) return null;
		return literals[index];
	}
	
	/**
	 * Get an array of all literals.
	 * 
	 * @return Literal<?>[]
	*/
	@Nullable
	public Literal<?>[] getLiterals() {
		return literals;
	}
	
	/**
	 * Get a map containing all literals and the index number of that literal.
	 * 
	 * @return Map<String, Integer>
	*/
	@Nullable
	public Map<String, Integer> getLiteralsMap() {
		return syntax;
	}
	
	/**
	 * Grabs a {@link Literal} from the data with a given index.
	 * 
	 * @param type The type's class you want to be returned.
	 * @param index Grabs the literal in the calling storage index.
	 * @retrun Literal<?>
	*/
	@Nullable
	public <T> Literal<?> getLiteral(Class<T> type, int index) {
		return literals[syntax.get(type.getSimpleName().toLowerCase() + index)];
	}
	
	/**
	 * Grabs the first literal in the syntax.
	 * 
	 * @param event The event
	 * @return Object
	*/
	@Nullable
	public Object getFirst(Event event) {
		return (literals != null && literals.length > 0) ? literals[0].getSingle(event) : null;
	}
	
	/**
	 * Grabs all of the values of the first literal in the syntax.
	 * 
	 * @param event The event
	 * @return Object
	*/
	@Nullable
	public Object getAllOfFirst(Event event) {
		return (literals != null && literals.length > 0) ? literals[0].getArray(event) : null;
	}

	/**
	 * Grabs the single value of a literal in the data index.
	 * See {@link LiteralData#getAll(Event, Class, int)} for an example and more information.
	 * 
	 * @param event The event
	 * @param type The type's class you want to be returned.
	 * @param index Grabs the value of which literal in the storage.
	 * @see LiteralData#getAll(Event, Class, int)
	*/
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> T getSingle(Event event, Class<T> type, int index) {
		Literal<?> literal = getLiteral(type, index);
		if (literal == null) return null;
		return (syntax.containsKey(type.getSimpleName().toLowerCase() + index)) ? (T) literal.getSingle(event) : null;
	}
	
	/**
	 * Grabs the single value of the first literal.
	 * 
	 * @param event The event
	 * @param type The type's class you want to be returned
	 * @return T
	*/
	public <T> T getSingle(Event event, Class<T> type) {
		return getSingle(event, type, 0);
	}
	
	/**
	 * Grabs a value of an literal as an integer.
	 * 
	 * @param event The event
	 * @param index Grabs the value of which literal in the storage.
	 * @return T
	*/
	public Integer getInt(Event event, int index) {
		return getSingle(event, Number.class, index).intValue();
	}
	
	/**
	 * Grabs a value of the first literal as an integer.
	 * 
	 * @param event The event
	 * @return T
	*/
	public Integer getInt(Event event) {
		return getInt(event, 0);
	}
	
	/**
	 * Same as {@link LiteralData#getAll(Event, Class)} just returning in a {@link List}
	 * 
	 * @param event The event
	 * @param type The type's class you want to be returned
	 * @see LiteralData#getAll(Event, Class)
	*/
	public <T> List<T> getList(Event event, Class<T> type) {
		return Arrays.asList(getAll(event, type));
	}
	
	/**
	 * Same as {@link LiteralData#getAll(Event, Class, int)} just returning in a {@link List}
	 * 
	 * @param event The event
	 * @param type The type's class you want to be returned
	 * @param index Grabs the values of which literal in the storage
	 * @see LiteralData#getAll(Event, Class, int)
	*/
	public <T> List<T> getList(Event event, Class<T> type, int index) {
		return Arrays.asList(getAll(event, type, index));
	}
	
	/**
	 * Grabs all values of a literal in the data index.
	 * 
	 * @param event The event
	 * @param type The type's class you want to be returned
	 * @param index Grabs the values of which literal in the storage
	*/
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> T[] getAll(Event event, Class<T> type, int index) {
		return (syntax.containsKey(type.getSimpleName().toLowerCase() + index)) ? (T[]) getLiteral(type, index).getArray(event) : null;
	}
	
	/**
	 * Grabs all values of the first literal.
	 * 
	 * @param event The event
	 * @param type The type's class you want to be returned
	 * @see LiteralData#getAll(Event, Class, int)
	*/
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> T[] getAll(Event event, Class<T> type) {
		return (syntax.containsKey(type.getSimpleName().toLowerCase() + 0)) ? (T[]) getLiteral(type, 0).getArray(event) : null;
	}
	
	/**
	 * Grabs the size of the values in an literal of the array index.
	 * 
	 * @param event The event.
	 * @param type The type's class you want to be returned.
	 * @param index The index of the literal array to check.
	*/
	@Nullable
	public <T> int getSize(Event event, Class<T> type, int index) {
		return (syntax.containsKey(type.getSimpleName().toLowerCase() + index)) ? getLiteral(type, index).getArray(event).length : null;
	}
	
	/**
	 * Grabs the size of the values in the first literal.
	 * 
	 * @param event The event
	 * @param type The type's class you want to be returned
	*/
	@Nullable
	public <T> int getSize(Event event, Class<T> type) {
		return (syntax.containsKey(type.getSimpleName().toLowerCase() + 0)) ? getLiteral(type, 0).getArray(event).length : null;
	}
	
	/**
	 * Get a map containing all literals and their single values in Object form.
	 * 
	 * @param event The event
	 * @return Map<Literal<?>, Object>
	*/
	@Nullable
	public <T> Map<Literal<?>, Object> getMap(Event event) {
		Map<Literal<?>, Object> data = new HashMap<Literal<?>, Object>();
		Arrays.asList(literals).forEach(literal -> data.put(literal, literal.getSingle(event)));
		return (data.isEmpty()) ? null : data;
	}
	
	/**
	 * Get a map containing all literals and their multiple values in Object form.
	 * 
	 * @param event The event
	 * @return Map<Literal<?>, Object[]>
	*/
	@Nullable
	public <T> Map<Literal<?>, Object[]> getAllMap(Event event) {
		Map<Literal<?>, Object[]> data = new HashMap<Literal<?>, Object[]>();
		Arrays.asList(literals).forEach(literal -> data.put(literal, literal.getArray(event)));
		return (data.isEmpty()) ? null : data;
	}
	
	/**
	 * Get a map containing all literals and their multiple values in an assigned type.
	 * 
	 * @param event The event
	 * @param type The type's class you want to be returned
	 * @return Map<Literal<?>, T[]>
	*/
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> Map<Literal<?>, T[]> getAllMapOf(Event event, Class<T>... types) {
		int i = 0;
		Map<Literal<?>, T[]> data = new HashMap<Literal<?>, T[]>();
		for (Class<T> type : types) {
			for (String string : syntax.keySet()) {
				if (string.contains(type.getSimpleName().toLowerCase()) && getLiteral(type, i) != null) {
					data.put(getLiteral(type, i), getAll(event, type, i));
				}
			}
			i++;
		}
		return (data.isEmpty()) ? null : data;
	}
	
	public String toString(Event event, boolean debug) {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getName());
		for (Literal<?> literal : literals)
			builder.append(literal.toString(event, debug));
		return builder.toString();
	}
}
