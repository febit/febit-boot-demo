package org.febit.boot.demo.doggy.lab;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class GenRsaKeys {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        var generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        var pair = generator.generateKeyPair();
        GenUtils.print(pair, "RSA");
    }
}
