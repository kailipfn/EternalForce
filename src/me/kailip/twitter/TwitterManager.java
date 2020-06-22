package me.kailip.twitter;

import me.kailip.account.Account;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

public class TwitterManager {
    public Twitter twitter;
    public TwitterManager(Account account) {
        this.twitter = account.getTwitter();
    }

    public boolean isFavorite(Status status) {
        return status.isFavorited();
    }

    public void favorite(Status status) throws TwitterException {
        twitter.createFavorite(status.getId());
    }

    public ResponseList<Status> getTimeLine() throws TwitterException {
        return twitter.getHomeTimeline();
    }
}
