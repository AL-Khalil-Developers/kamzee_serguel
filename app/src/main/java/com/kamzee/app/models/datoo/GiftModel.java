package com.kamzee.app.models.datoo;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Gift")
public class GiftModel extends ParseObject {

    public static final String SEND_GIFT_PARAM = "send_gift";
    public static final String CoinS_PARAM = "Coins";

    public static final String CoinS = "Coins";
    public static final String FILE = "file";
    public static final String FILE_SOUND = "sound";

    public int getCoins() {
        return getInt(CoinS);
    }

    public ParseFile getGiftFile() {
        try {
            return this.fetchIfNeeded().getParseFile(FILE);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ParseFile getGiftFileSound() {
        try {
            return this.fetchIfNeeded().getParseFile(FILE_SOUND);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static ParseQuery<GiftModel> getGiftQuery() {
        return  ParseQuery.getQuery(GiftModel.class);
    }
}
