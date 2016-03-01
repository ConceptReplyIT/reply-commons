package it.reply.utils;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SecurityHelper {

	/**
	 * Set in the default SSLContext the given key store and trust store. At
	 * least one between key and trust streams must non be null.
	 * 
	 * @see <a href="http://stackoverflow.com/questions/344748/how-to-use-a-file-in-a-jar
	 *      -as-javax-net-ssl-keystore">http://stackoverflow.com/questions/344748/how-to-use-a-file-in-a-jar
	 *      -as-javax-net-ssl-keystore</a>
	 * 
	 * @param keyStream
	 * @param keystoreType
	 * @param keyStorePassword
	 * @param trustStream
	 * @param trustType
	 * @throws Exception
	 */
	public static void setSSLContext(InputStream keyStream, String keystoreType, String keyStorePassword,
			InputStream trustStream, String trustType) throws Exception {
		KeyManager[] keyManagers = null;
		TrustManager[] trustManagers = null;

		if (keyStream == null && trustStream == null) {
			throw new IllegalArgumentException("At least one between key and trust streams must non be null.");
		}

		if (keyStream != null) {
			// Get keyStore
			KeyStore keyStore = KeyStore.getInstance(keystoreType);

			// if your store is password protected then declare it (it can be
			// null however)
			char[] keyPassword = keyStorePassword.toCharArray();

			// load the stream to your store
			keyStore.load(keyStream, keyPassword);

			// initialize a trust manager factory with the trusted store
			KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyFactory.init(keyStore, keyPassword);

			// get the trust managers from the factory
			keyManagers = keyFactory.getKeyManagers();
		}

		if (trustStream != null) {
			// Now get trustStore
			KeyStore trustStore = KeyStore.getInstance(trustType);

			// if your store is password protected then declare it (it can be
			// null however)
			// char[] trustPassword = password.toCharArray();

			// load the stream to your store
			trustStore.load(trustStream, null);

			// initialize a trust manager factory with the trusted store
			TrustManagerFactory trustFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustFactory.init(trustStore);

			// get the trust managers from the factory
			trustManagers = trustFactory.getTrustManagers();
		}

		// initialize an ssl context to use these managers and set as default
		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(keyManagers, trustManagers, null);
		SSLContext.setDefault(sslContext);
	}
}
