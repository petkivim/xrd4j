package com.pkrete.xrd4j.client.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class offers some helper methods for SOAP client.
 *
 * @author Petteri Kivimäki
 */
public class ClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClientUtil.class);

    /**
     * Calling this method creates a trust manager that does not validate
     * certificate chains like the default. In practice, to access a HTTPS URL
     * without having the certificate in the truststore.
     *
     * This method is for testing purposes only, and it must not be used in
     * production. This method should only be used in scenarios, where it is
     * not possible to install the required certificates using keytool e.g.
     * local testing with temporary certificates.
     *
     * @throws CertificateException if there's an error
     * @throws KeyManagementException if there's an error
     * @throws NoSuchAlgorithmException if there's an error
     */
    static public void doTrustToCertificates() throws CertificateException, KeyManagementException, NoSuchAlgorithmException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                }
            }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier hv = new HostnameVerifier() {

            @Override
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    logger.warn("Warning: URL host \"{}\" is different to SSLSession host \"{}\".", urlHostName, session.getPeerHost());
                    return false;
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
        // Now you it's possible to acces a https URL without having the
        // certificate in the truststore
    }

    /**
     * This methods overrides the default host name verifier that can cause
     * problems with self signed or untrusted SSL certificates. The problem
     * occurs when the common name (CN) in the certificate doesn't
     * match the host name of service URL. The default host name verifier checks 
     * if a host name matches the names stored inside the server's X.509 
     * certificate. This custom verifier verifies that the host name is an 
     * acceptable match with the server's authentication scheme.  
     *
     * This approach poses some security risks. Therefore, be mindful of that
     * especially if you consider this solution for a production deployment.
     */
    public static void setCustomHostNameVerifier() {
        HostnameVerifier hv = new HostnameVerifier() {

            @Override
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    logger.warn("Warning: URL host \"{}\" is different to SSLSession host \"{}\".", urlHostName, session.getPeerHost());
                    return false;
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
}
