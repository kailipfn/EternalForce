package me.kailip.account;

import me.kailip.utils.Secret;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Account {
    public String token;
    public String secret;
    public String id;
    public AccType type;

    public Account(String token,String secret,String id,AccType type) {
        this.token = token;
        this.secret = secret;
        this.id = id;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getSecret() {
        return secret;
    }

    public AccType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Twitter getTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(Secret.getConsumerKey)
                .setOAuthConsumerSecret(Secret.getConsumerSecret)
                .setOAuthAccessToken(getToken())
                .setOAuthAccessTokenSecret(getSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }
}
