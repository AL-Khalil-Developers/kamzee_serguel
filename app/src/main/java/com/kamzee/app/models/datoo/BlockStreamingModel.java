package com.kamzee.app.models.datoo;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("BlockStreaming")
public class BlockStreamingModel extends ParseObject {

    public static final String BLOCKED_USER = "blockedUser";
    public static final String BLOCKED_USER_ID = "blockedUsersId";

    public static final String BLOCKER_USER = "blockedBy";
    public static final String OWNER_USER = "blockedBy";

    public static final String LIVE_STREAMING = "liveStreaming";

    public void setBlockedUser(User user) {

        put(BLOCKED_USER, user);
        put(BLOCKED_USER_ID, user.getObjectId());
    }

    public User getBlockedUser() {
        return (User) getParseObject(BLOCKED_USER);
    }

    public String getBlockedUserId() {
        return getString(BLOCKED_USER_ID);
    }

    public User getAuthor() {
        return (User) getParseObject(BLOCKER_USER);
    }

    public void setAuthor(User senderAuthor){
        put(BLOCKER_USER, senderAuthor);
    }

    public User getOwner() {
        return (User) getParseObject(OWNER_USER);
    }

    public void setOwner(User owner){
        put(OWNER_USER, owner);
    }

    public LiveStreamModel getLiveStreaming() {
        return (LiveStreamModel) getParseObject(LIVE_STREAMING);
    }

    public void setLiveStreaming(LiveStreamModel liveStreaming){
        put(LIVE_STREAMING, liveStreaming);
    }

    public static ParseQuery<BlockStreamingModel> getBlockStreamingQuery() {
        return  ParseQuery.getQuery(BlockStreamingModel.class);
    }
}
