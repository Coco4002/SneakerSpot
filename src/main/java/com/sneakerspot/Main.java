package com.sneakerspot;

import com.sneakerspot.dao.DatabaseInitializer;
import com.sneakerspot.model.User;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();
    }
}