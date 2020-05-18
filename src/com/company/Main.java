package com.company;

import database.ConnectionManager;

public class Main {

    public static void main(String[] args) {
        // try it :(3
        ConnectionManager.getInstance().getConnection();

        ConnectionManager.getInstance().closeConnection();
    }
}
