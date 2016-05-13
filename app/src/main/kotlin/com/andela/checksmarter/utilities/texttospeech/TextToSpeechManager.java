package com.andela.checksmarter.utilities.texttospeech;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import com.andela.checksmarter.utilities.MsgBox;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by CodeKenn on 04/05/16.
 */
public class TextToSpeechManager {
    private String[] lists;
    private TextToSpeech tts;
    private int ttsResult;
    private Context context;

    private int counter = 0;
    private boolean speakAll = false;

    public TextToSpeechManager(Context context) {
        this.context = context;
        tts = new TextToSpeech(this.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    ttsResult = tts.setLanguage(Locale.US);
                }
            }
        });
    }

    public TextToSpeechManager(Context context, final String[] lists) {
        this(context);
        this.lists = lists;

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
            }

            @Override
            public void onDone(String utteranceId) {
                if (speakAll) {
                    counter++;
                    if (counter == TextToSpeechManager.this.lists.length) {
                        speakAll = false;
                        counter = 0;
                        return;
                    }
                    talk(TextToSpeechManager.this.lists[counter]);
                }
            }

            @Override
            public void onError(String utteranceId) {
            }
        });
    }

    public void talk(String text) {
        if (ttsResult != TextToSpeech.LANG_NOT_SUPPORTED && ttsResult != TextToSpeech.LANG_MISSING_DATA) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ttsGreater21(text);
            } else {
                ttsUnder20(text);
            }
        }
    }

    public void talk() {
        if (lists.length > 0) {
            speakAll = true;
            talk(lists[counter]);
        } else {
            MsgBox.INSTANCE.show(context, "None Selected");
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    public String[] getLists() {
        return lists;
    }

    public void setLists(String[] lists) {
        this.lists = lists;
    }
}
