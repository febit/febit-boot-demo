package org.febit.boot.demo.doggy.lab;

import lombok.experimental.UtilityClass;

import java.security.Key;
import java.security.KeyPair;
import java.util.Base64;

@UtilityClass
public class GenUtils {

    static void println(String msg) {
        System.out.println(msg);
    }

    static  String encode(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    static void print(KeyPair pair, String algorithm) {
        println("Private Key: " + algorithm);
        println(encode(pair.getPrivate()));

        println("------------------------------");
        println("Public Key: " + algorithm);
        println(encode(pair.getPublic()));
        println("------------------------------");
    }

}
