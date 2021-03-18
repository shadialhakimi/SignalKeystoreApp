package com.sample.foo.simplekeystoreapp;

//import androidx.annotation.NonNull;

import com.sample.foo.simplekeystoreapp.Hex;

import java.io.IOException;

public class DatabaseSecret {

    private final byte[] key;
    private final String encoded;

    public DatabaseSecret(byte[] key) {
        this.key = key;
        this.encoded = Hex.toStringCondensed(key);
    }

    public DatabaseSecret(String encoded) throws IOException {
        this.key     = Hex.fromStringCondensed(encoded);
        this.encoded = encoded;
    }

    public String asString() {
        return encoded;
    }

    public byte[] asBytes() {
        return key;
    }
}
