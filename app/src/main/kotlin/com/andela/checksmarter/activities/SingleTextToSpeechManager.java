package com.andela.checksmarter.activities;

import android.content.Context;

import com.andela.checksmarter.utilities.texttospeech.TextToSpeechManager;

/**
 * Created by CodeKenn on 04/05/16.
 */
public class SingleTextToSpeechManager {
    private static TextToSpeechManager instance;

    public static TextToSpeechManager getInstance(Context context) {
        if (instance == null) {
            instance = new TextToSpeechManager(context, new String[]{});
        }
        return instance;
    }
}
