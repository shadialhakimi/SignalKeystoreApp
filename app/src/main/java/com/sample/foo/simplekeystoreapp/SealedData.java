package com.sample.foo.simplekeystoreapp;

import android.os.Build;
//import android.security.keystore.KeyGenParameterSpec;
//import android.security.keystore.KeyProperties;
import android.util.Base64;

//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.sample.foo.simplekeystoreapp.JsonUtils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;


public class SealedData {

    @SuppressWarnings("unused")
    private static final String TAG = SealedData.class.getSimpleName();

    @JsonProperty
    @JsonSerialize(using = ByteArraySerializer.class)
    @JsonDeserialize(using = ByteArrayDeserializer.class)
    public byte[] iv;

    @JsonProperty
    @JsonSerialize(using = ByteArraySerializer.class)
    @JsonDeserialize(using = ByteArrayDeserializer.class)
    public byte[] data;

    SealedData( byte[] iv, byte[] data) {
        this.iv   = iv;
        this.data = data;
    }

    @SuppressWarnings("unused")
    public SealedData() {}

    public String serialize() {
        try {
            return JsonUtils.toJson(this);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public static SealedData fromString(String value) {
        try {
            return JsonUtils.fromJson(value, SealedData.class);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static class ByteArraySerializer extends JsonSerializer<byte[]> {
        @Override
        public void serialize(byte[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(Base64.encodeToString(value, Base64.NO_WRAP | Base64.NO_PADDING));
        }
    }

    private static class ByteArrayDeserializer extends JsonDeserializer<byte[]> {

        @Override
        public byte[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return Base64.decode(p.getValueAsString(), Base64.NO_WRAP | Base64.NO_PADDING);
        }
    }

}