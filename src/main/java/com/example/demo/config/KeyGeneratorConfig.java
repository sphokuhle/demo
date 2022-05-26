package com.example.demo.config;

import com.example.demo.service.KeycloakKeyService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

@Component
public class KeyGeneratorConfig {

//    @Value("${app.security.jwtPublicKey}")
//    private String certificateKey;
    @Autowired
    private KeycloakKeyService keycloakKeyService;

    public RSAPublicKey getParsedPublicKey() {
        try {
            byte[] encodedCert = keycloakKeyService.getPublicKeys().get("publicKey").getBytes("UTF-8");
            byte[] decodedCert = Base64.decodeBase64(encodedCert);
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            InputStream in = new ByteArrayInputStream(decodedCert);
            X509Certificate certificate = (X509Certificate)certFactory.generateCertificate(in);
            RSAPublicKey pubKey = (RSAPublicKey) certificate.getPublicKey();
            return pubKey;

        } catch (IOException | CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
