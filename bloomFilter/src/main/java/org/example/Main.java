package org.example;

import java.io.*;
import java.nio.charset.Charset;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class Main {
    public static void main(String[] args) {


        BlackListedIpHandler blackListedIpHandler = new BlackListedIpHandler();
        System.out.println("127.0.0.1:  "+ blackListedIpHandler.mightContain("127.0.0.1"));
        System.out.println("75.245.10.1  : " + blackListedIpHandler.mightContain("75.245.10.1"));
        System.out.println("101.125.20.22:  "+ blackListedIpHandler.mightContain("101.125.20.22"));
        blackListedIpHandler.addToBlackList("127.0.0.1");
        System.out.println("127.0.0.1:  "+ blackListedIpHandler.mightContain("127.0.0.1"));

    }
}