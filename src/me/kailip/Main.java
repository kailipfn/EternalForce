package me.kailip;

import me.kailip.account.AccountManager;
import me.kailip.command.CommandHandler;
import me.kailip.twitter.TwitterBot;

public class Main {
    private static boolean started = false;
    public static void main(String[] args) {
        init();
    }

    public static void init() {
        AccountManager.init();
        setStarted(true);
        new CommandHandler().start();
        new TwitterBot().start();
    }

    public static void end() {
        setStarted(false);
        System.exit(0);
    }

    public static boolean isStarted() {
        return started;
    }

    public static void setStarted(boolean started) {
        Main.started = started;
    }
}
