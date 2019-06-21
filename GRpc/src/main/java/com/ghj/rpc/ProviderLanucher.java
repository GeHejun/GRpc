package com.ghj.rpc;

import com.ghj.rpc.core.Provider;

import java.io.IOException;

public class ProviderLanucher {
    public static void main(String[] args) throws IOException {
        Provider provider = new Provider();
        provider.publish();
    }
}
