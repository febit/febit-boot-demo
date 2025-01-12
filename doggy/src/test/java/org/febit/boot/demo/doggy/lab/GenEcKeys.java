/*
 * Copyright 2023-present febit.org (support@febit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
