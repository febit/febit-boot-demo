package org.febit.boot.demo.doggy.lab;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECGenParameterSpec;

public class GenEcKeys {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        var generator = KeyPairGenerator.getInstance("EC");
        var spec = new ECGenParameterSpec("secp256r1");
        // var spec = Curve.P_256.toECParameterSpec();
        generator.initialize(spec);
        var pair = generator.generateKeyPair();
        GenUtils.print(pair, "EC");
    }
}
