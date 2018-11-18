package com.fei.upb;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StaticDictionary {

    private static final Set<String> passwords = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private static Boolean DICTIONARIES_LOADED_FLAG = false;
    private static String DICTIONARY_DIRECTORY = "src/main/resources/dicts";
    private static File DICTIONARY_DIRECTORY_FILE;

    static {
        DICTIONARY_DIRECTORY_FILE = new File(DICTIONARY_DIRECTORY);
    }

    public static void loadDirectory(File directory) {
        if(!directory.isDirectory() && directory.listFiles() != null && directory.listFiles().length > 0) {
            return;
        }

        List<File> dictionaries = Arrays.asList(directory.listFiles());
        dictionaries.forEach(StaticDictionary::loadDictionary);
        DICTIONARIES_LOADED_FLAG = true;
    }

    private static void loadDictionary(File dictionary) {
        try {
            FileInputStream fstream = new FileInputStream(dictionary);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            br.lines().parallel().forEach(passwords::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isPasswordSecure(String password) {
        if(!isDictionaryLoaded()) {
            loadDirectory(DICTIONARY_DIRECTORY_FILE);
        }

        return !passwords.contains(password);
    }

    public static boolean isDictionaryLoaded() {
        return DICTIONARIES_LOADED_FLAG;
    }
}
