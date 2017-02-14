/*
 *  Copyright 2017 LunaMC.io
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.lunamc.common.login.encryption;

import io.lunamc.common.network.DecidedConnection;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;

public class DefaultEncryptionFactory implements EncryptionFactory {

    private static final int VERIFY_BYTES = 4;

    private final Random random = new Random();
    private final KeyPair keyPair;

    public DefaultEncryptionFactory() {
        this(generateKeyPair());
    }

    public DefaultEncryptionFactory(KeyPair keyPair) {
        this.keyPair = Objects.requireNonNull(keyPair, "keyPair must not be null");
    }

    @Override
    public Encryption create(DecidedConnection connection, String loginData) {
        byte[] verification = new byte[VERIFY_BYTES];
        random.nextBytes(verification);
        return new DefaultEncryption(verification);
    }

    private static KeyPair generateKeyPair() {
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    private class DefaultEncryption implements Encryption {

        private final byte[] verification;

        private DefaultEncryption(byte[] verification) {
            this.verification = Objects.requireNonNull(verification, "verification must not be null");
        }

        @Override
        public KeyPair getKeyPair() {
            return keyPair;
        }

        @Override
        public byte[] getVerifyBytes() {
            return verification;
        }
    }
}
