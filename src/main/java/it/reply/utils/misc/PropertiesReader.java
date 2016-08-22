package it.reply.utils.misc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

public class PropertiesReader {

	private static final org.apache.logging.log4j.Logger LOG = LogManager
			.getLogger(PropertiesReader.class);

	/**
	 * Loads the given property file.
	 * 
	 * @param path
	 *            The path of the file (from resources folder).
	 * @return the properties contained in the file.
	 * @throws IOException
	 *             if cannot access the file.
	 */
	public static Properties read(String path) throws IOException {
	  Properties properties = new Properties();
	  try (InputStream inputStream = PropertiesReader.class.getClassLoader()
				.getResourceAsStream(path)) {
  		properties.load(inputStream);
	  }
		if (LOG.isDebugEnabled())
			for (Entry<Object, Object> item : properties.entrySet()) {
				LOG.debug("[" + path + "] " + item.getKey() + " = "
						+ item.getValue());
			}
		return properties;
	}

}
