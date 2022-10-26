package org.example;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.*;
import java.nio.charset.Charset;

public class BlackListedIpHandler {

    File blacklistedIps = new File("BlacklistedIps.dat");
    BloomFilter<String> blackListedIps = null;

    public BlackListedIpHandler() {

        if(blacklistedIps.exists())
        {
            readFromFile(blacklistedIps);
        }
        else {
            initializeBloomFilter(blacklistedIps);
        }

    }

    private void readFromFile(File blacklistedIps) {
        System.out.println("Reading from file");
        try (FileInputStream fis = new FileInputStream(blacklistedIps);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
             blackListedIps = (BloomFilter<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void initializeBloomFilter(File blacklistedIps) {
        System.out.println("Initializing");
        blackListedIps
                = BloomFilter.create(
                Funnels.stringFunnel(
                        Charset.forName("UTF-8")),
                10000);

        // Add the data sets
        blackListedIps.put("192.170.0.1");
        blackListedIps.put("75.245.10.1");
        blackListedIps.put("10.125.22.20");
        updateFile(blacklistedIps);
    }

    private void updateFile(File blacklistedIps) {
        try (FileOutputStream fos = new FileOutputStream(blacklistedIps);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(blackListedIps);
            oos.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean mightContain(String ip) {
      return blackListedIps.mightContain(ip);
   }

    public boolean addToBlackList(String ip){
        boolean added = blackListedIps.put(ip);
        updateFile(blacklistedIps);
        return  added;
    }

}
