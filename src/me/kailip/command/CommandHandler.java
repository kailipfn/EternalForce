package me.kailip.command;

import me.kailip.Main;
import me.kailip.account.AccType;
import me.kailip.account.Account;
import me.kailip.account.AccountManager;
import me.kailip.twitter.TwitterBot;
import me.kailip.utils.Logger;
import me.kailip.utils.Secret;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import org.apache.commons.lang3.math.NumberUtils;
import twitter4j.TwitterException;
import twitter4j.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandHandler extends Thread {
    public OAuthProvider provider = null;
    public OAuthConsumer consumer = null;
    public AccType type = null;
    public String authUrl;

    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (Main.isStarted()) {
            try {
                String cmd = br.readLine();
                if(provider != null && type != null) {
                    if (NumberUtils.isNumber(cmd)) {
                        if (cmd.length() == 7) {
                            try {
                                provider.retrieveAccessToken(consumer, cmd);
                                Account account = AccountManager.addAccount(consumer.getToken(),consumer.getTokenSecret(),type);
                                User user = account.getTwitter().verifyCredentials();
                                Logger.log( user.getName() + "(@" +user.getScreenName() + ")　を追加しました！");
                                this.provider = null;
                                this.consumer = null;
                                this.type = null;
                            } catch (OAuthMessageSignerException | OAuthNotAuthorizedException | OAuthExpectationFailedException | OAuthCommunicationException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if(provider != null) {
                    if(cmd.equals("main") || cmd.equals("sub")) {
                        Logger.log("このリンクを開き、認証後表示されたPINを入力してください。");
                        Logger.log(authUrl);
                    }
                    if(cmd.equals("main")) {
                        type = AccType.MAIN;
                    }
                    if(cmd.equals("sub")) {
                        type = AccType.SUB;
                    }

                }
                if(cmd.equals("add")) {
                    try {
                        OAuthConsumer consumer = new DefaultOAuthConsumer(
                                Secret.getConsumerKey,
                                Secret.getConsumerSecret);

                        OAuthProvider provider = new DefaultOAuthProvider(
                                "https://api.twitter.com/oauth/request_token",
                                "https://api.twitter.com/oauth/access_token",
                                "https://api.twitter.com/oauth/authorize");
                        authUrl = provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND);
                        this.consumer = consumer;
                        this.provider = provider;
                        Logger.log("TL読み取り用のアカウントならmain、ふぁぼ専用のアカウントならsubと入力してください。");
                    } catch (OAuthMessageSignerException | OAuthNotAuthorizedException | OAuthExpectationFailedException | OAuthCommunicationException e) {
                        e.printStackTrace();
                    }
                }
                if(cmd.contains("remove")) {
                    String[] args = cmd.split(" ");
                    if(AccountManager.getIdList().contains(args[1])) {
                        Account account = AccountManager.removeAccount(AccountManager.getTokenFromId(args[1]));
                        User user = account.getTwitter().verifyCredentials();
                        Logger.log( user.getName() + "(@" +user.getScreenName() + ")　を削除しました！");
                    }
                    else {
                        Logger.log(args[1] + "は追加されていません。");
                    }
                }
                if(cmd.equals("list")) {
                    for (Account str : AccountManager.getAccountList()) {
                        System.out.println(str.getId());
                    }
                }
                if(cmd.equals("get")) {
                    Logger.log(TwitterBot.a + "回いいねしました");
                }
                if(cmd.equals("end")) {
                    Logger.log("プログラムを終了しました。");
                    Main.end();
                }
            } catch (IOException | TwitterException e) {
                e.printStackTrace();
            }
        }
    }
}
