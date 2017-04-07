package org.apache.http.ssl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.util.Args;

@NotThreadSafe
public class SSLContextBuilder {
    static final String TLS = "TLS";
    private final Set<KeyManager> keymanagers;
    private String protocol;
    private SecureRandom secureRandom;
    private final Set<TrustManager> trustmanagers;

    static class KeyManagerDelegate extends X509ExtendedKeyManager {
        private final PrivateKeyStrategy aliasStrategy;
        private final X509ExtendedKeyManager keyManager;

        KeyManagerDelegate(X509ExtendedKeyManager keyManager, PrivateKeyStrategy aliasStrategy) {
            this.keyManager = keyManager;
            this.aliasStrategy = aliasStrategy;
        }

        public String[] getClientAliases(String keyType, Principal[] issuers) {
            return this.keyManager.getClientAliases(keyType, issuers);
        }

        public Map<String, PrivateKeyDetails> getClientAliasMap(String[] keyTypes, Principal[] issuers) {
            Map<String, PrivateKeyDetails> validAliases = new HashMap();
            for (String keyType : keyTypes) {
                String[] aliases = this.keyManager.getClientAliases(keyType, issuers);
                if (aliases != null) {
                    for (String alias : aliases) {
                        validAliases.put(alias, new PrivateKeyDetails(keyType, this.keyManager.getCertificateChain(alias)));
                    }
                }
            }
            return validAliases;
        }

        public Map<String, PrivateKeyDetails> getServerAliasMap(String keyType, Principal[] issuers) {
            Map<String, PrivateKeyDetails> validAliases = new HashMap();
            String[] aliases = this.keyManager.getServerAliases(keyType, issuers);
            if (aliases != null) {
                for (String alias : aliases) {
                    validAliases.put(alias, new PrivateKeyDetails(keyType, this.keyManager.getCertificateChain(alias)));
                }
            }
            return validAliases;
        }

        public String chooseClientAlias(String[] keyTypes, Principal[] issuers, Socket socket) {
            return this.aliasStrategy.chooseAlias(getClientAliasMap(keyTypes, issuers), socket);
        }

        public String[] getServerAliases(String keyType, Principal[] issuers) {
            return this.keyManager.getServerAliases(keyType, issuers);
        }

        public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
            return this.aliasStrategy.chooseAlias(getServerAliasMap(keyType, issuers), socket);
        }

        public X509Certificate[] getCertificateChain(String alias) {
            return this.keyManager.getCertificateChain(alias);
        }

        public PrivateKey getPrivateKey(String alias) {
            return this.keyManager.getPrivateKey(alias);
        }

        public String chooseEngineClientAlias(String[] keyTypes, Principal[] issuers, SSLEngine sslEngine) {
            return this.aliasStrategy.chooseAlias(getClientAliasMap(keyTypes, issuers), null);
        }

        public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine sslEngine) {
            return this.aliasStrategy.chooseAlias(getServerAliasMap(keyType, issuers), null);
        }
    }

    static class TrustManagerDelegate implements X509TrustManager {
        private final X509TrustManager trustManager;
        private final TrustStrategy trustStrategy;

        TrustManagerDelegate(X509TrustManager trustManager, TrustStrategy trustStrategy) {
            this.trustManager = trustManager;
            this.trustStrategy = trustStrategy;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            this.trustManager.checkClientTrusted(chain, authType);
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (!this.trustStrategy.isTrusted(chain, authType)) {
                this.trustManager.checkServerTrusted(chain, authType);
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return this.trustManager.getAcceptedIssuers();
        }
    }

    public static SSLContextBuilder create() {
        return new SSLContextBuilder();
    }

    public SSLContextBuilder() {
        this.keymanagers = new LinkedHashSet();
        this.trustmanagers = new LinkedHashSet();
    }

    public SSLContextBuilder useProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public SSLContextBuilder setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(KeyStore truststore, TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmfactory.init(truststore);
        TrustManager[] tms = tmfactory.getTrustManagers();
        if (tms != null) {
            TrustManager tm;
            if (trustStrategy != null) {
                for (int i = 0; i < tms.length; i++) {
                    tm = tms[i];
                    if (tm instanceof X509TrustManager) {
                        tms[i] = new TrustManagerDelegate((X509TrustManager) tm, trustStrategy);
                    }
                }
            }
            for (TrustManager tm2 : tms) {
                this.trustmanagers.add(tm2);
            }
        }
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException {
        return loadTrustMaterial(null, trustStrategy);
    }

    public SSLContextBuilder loadTrustMaterial(File file, char[] storePassword, TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        Args.notNull(file, "Truststore file");
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream instream = new FileInputStream(file);
        try {
            trustStore.load(instream, storePassword);
            return loadTrustMaterial(trustStore, trustStrategy);
        } finally {
            instream.close();
        }
    }

    public SSLContextBuilder loadTrustMaterial(File file, char[] storePassword) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        return loadTrustMaterial(file, storePassword, null);
    }

    public SSLContextBuilder loadTrustMaterial(File file) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        return loadTrustMaterial(file, null);
    }

    public SSLContextBuilder loadTrustMaterial(URL url, char[] storePassword, TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        Args.notNull(url, "Truststore URL");
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream instream = url.openStream();
        try {
            trustStore.load(instream, storePassword);
            return loadTrustMaterial(trustStore, trustStrategy);
        } finally {
            instream.close();
        }
    }

    public SSLContextBuilder loadTrustMaterial(URL url, char[] storePassword) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        return loadTrustMaterial(url, storePassword, null);
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keystore, char[] keyPassword, PrivateKeyStrategy aliasStrategy) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmfactory.init(keystore, keyPassword);
        KeyManager[] kms = kmfactory.getKeyManagers();
        if (kms != null) {
            KeyManager km;
            if (aliasStrategy != null) {
                for (int i = 0; i < kms.length; i++) {
                    km = kms[i];
                    if (km instanceof X509ExtendedKeyManager) {
                        kms[i] = new KeyManagerDelegate((X509ExtendedKeyManager) km, aliasStrategy);
                    }
                }
            }
            for (KeyManager km2 : kms) {
                this.keymanagers.add(km2);
            }
        }
        return this;
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keystore, char[] keyPassword) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        return loadKeyMaterial(keystore, keyPassword, null);
    }

    public SSLContextBuilder loadKeyMaterial(File file, char[] storePassword, char[] keyPassword, PrivateKeyStrategy aliasStrategy) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
        Args.notNull(file, "Keystore file");
        KeyStore identityStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream instream = new FileInputStream(file);
        try {
            identityStore.load(instream, storePassword);
            return loadKeyMaterial(identityStore, keyPassword, aliasStrategy);
        } finally {
            instream.close();
        }
    }

    public SSLContextBuilder loadKeyMaterial(File file, char[] storePassword, char[] keyPassword) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
        return loadKeyMaterial(file, storePassword, keyPassword, null);
    }

    public SSLContextBuilder loadKeyMaterial(URL url, char[] storePassword, char[] keyPassword, PrivateKeyStrategy aliasStrategy) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
        Args.notNull(url, "Keystore URL");
        KeyStore identityStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream instream = url.openStream();
        try {
            identityStore.load(instream, storePassword);
            return loadKeyMaterial(identityStore, keyPassword, aliasStrategy);
        } finally {
            instream.close();
        }
    }

    public SSLContextBuilder loadKeyMaterial(URL url, char[] storePassword, char[] keyPassword) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
        return loadKeyMaterial(url, storePassword, keyPassword, null);
    }

    protected void initSSLContext(SSLContext sslcontext, Collection<KeyManager> keyManagers, Collection<TrustManager> trustManagers, SecureRandom secureRandom) throws KeyManagementException {
        KeyManager[] keyManagerArr;
        if (keyManagers.isEmpty()) {
            keyManagerArr = null;
        } else {
            keyManagerArr = (KeyManager[]) keyManagers.toArray(new KeyManager[keyManagers.size()]);
        }
        sslcontext.init(keyManagerArr, !trustManagers.isEmpty() ? (TrustManager[]) trustManagers.toArray(new TrustManager[trustManagers.size()]) : null, secureRandom);
    }

    public SSLContext build() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslcontext = SSLContext.getInstance(this.protocol != null ? this.protocol : TLS);
        initSSLContext(sslcontext, this.keymanagers, this.trustmanagers, this.secureRandom);
        return sslcontext;
    }
}
