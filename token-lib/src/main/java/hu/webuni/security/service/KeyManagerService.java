package hu.webuni.security.service;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Service("KeyManagerService")
public class KeyManagerService {

    @Value("${hu.webuni.secret.dir}")
    private String directory;

    @Value("${hu.webuni.secret.pub.key}")
    private String publicKey;

    @Value("${hu.webuni.secret.secret.key}")
    private String privateKey;


    /**
     * Ezzel kell inicializálni a kulcsokat, de csak 1X
     * @throws Exception
     */
    public void init() throws Exception {
        File rsaKeyPairsDir = new File(System.getProperty("user.home"), directory);
        File publicKeyFile = new File(rsaKeyPairsDir, publicKey);
        if (!publicKeyFile.exists()) {
            if (!rsaKeyPairsDir.exists()) {
                rsaKeyPairsDir.mkdir();
            }
            generateRSAKeyPairs(rsaKeyPairsDir);
        }

    }

    public Algorithm getVerifier() throws Exception {
        return Algorithm.RSA256(readPublicKey(), null);
    }

    public Algorithm getSigner() throws Exception {
        return Algorithm.RSA256(null, readPrivateKey());
    }


    private void generateRSAKeyPairs(File rsaKeyPairsDir) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair keyPair = kpg.generateKeyPair();
        Key pub = keyPair.getPublic();
        Key pvt = keyPair.getPrivate();
        try (OutputStream out = new FileOutputStream(new File(rsaKeyPairsDir, privateKey))) {
            out.write(pvt.getEncoded());
        }
        try (OutputStream out2 = new FileOutputStream(new File(rsaKeyPairsDir, publicKey))) {
            out2.write(pub.getEncoded());
        }
    }

    public RSAPrivateKey readPrivateKey() throws Exception {
        File keyFile = new File(System.getProperty("user.home"), directory+"/"+privateKey);
        try (InputStream inputStream = new FileInputStream(keyFile)) {
            byte[] keyBytes = toBytes(inputStream);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
        }

    }

    public RSAPublicKey readPublicKey() throws Exception {
        File keyFile = new File(System.getProperty("user.home"), directory+"/"+publicKey);
        try (InputStream inputStream = new FileInputStream(keyFile)) {
            byte[] keyBytes = toBytes(inputStream);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
        }
    }

    public byte[] toBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        StreamUtils.copy(inputStream, buffer);
        byte[] bytes = buffer.toByteArray();
        inputStream.close();
        buffer.close();
        return bytes;
    }
}
