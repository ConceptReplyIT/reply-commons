package it.prisma.utils.misc;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class EnumUtils {

	/**
	 * Lookup an enum entry by its name ignoring case.
	 * 
	 * @param clazz
	 *            the class of the enum.
	 * @param name
	 *            the name of the enum item.
	 * @return the matched enum entry.
	 * @throws NoSuchElementException
	 *             if the entry was not found.
	 */
	public static <E extends Enum<E>> E fromString(Class<E> clazz,
			String name) {
		for (E en : EnumSet.allOf(clazz)) {
			if (en.name().equalsIgnoreCase(name)) {
				return en;
			}
		}
		throw new NoSuchElementException("No enum found with name [" + name
				+ "] in class [" + clazz + "]");
	}
}
