package me.kailip.twitter;

import jdk.net.SocketFlow;
import me.kailip.Main;
import me.kailip.account.AccType;
import me.kailip.account.Account;
import me.kailip.account.AccountManager;
import twitter4j.Status;
import twitter4j.TwitterException;

public class TwitterBot extends Thread{
    public static int a = 0;
    @Override
    public void run() {
        while (Main.isStarted()) {
            try {
            for(Account account : AccountManager.getAccountList()) {
                if(account.getType() == AccType.MAIN) {
                    a++;
                    TwitterManager manager = new TwitterManager(account);
                    for(int a = 0;a < 5;a++) {
                        try {
                            Status status = manager.getTimeLine().get(a);
                            for(Account acc : AccountManager.getAccountList()) {
                                TwitterManager man = new TwitterManager(acc);
                                if (!status.getUser().isProtected()) {
                                    if (!man.isFavorite(status)) {
                                        man.favorite(status);
                                        Thread.sleep(200);
                                    }
                                }
                            }
                        } catch (TwitterException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            Thread.sleep(1000 * 300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
