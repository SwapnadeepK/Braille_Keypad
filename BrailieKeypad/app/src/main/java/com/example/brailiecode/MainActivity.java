package com.example.brailiecode;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
public class MainActivity extends AppCompatActivity {

    private static final Map<String, Character> dotPatterns = new HashMap<>();

    static {
        dotPatterns.put("1", 'a');
        dotPatterns.put("12", 'b');
        dotPatterns.put("14", 'c');
        dotPatterns.put("145", 'd');
        dotPatterns.put("15", 'e');
        dotPatterns.put("124", 'f');
        dotPatterns.put("1245", 'g');
        dotPatterns.put("125", 'h');
        dotPatterns.put("24", 'i');
        dotPatterns.put("245", 'j');
        dotPatterns.put("13", 'k');
        dotPatterns.put("123", 'l');
        dotPatterns.put("134", 'm');
        dotPatterns.put("1345", 'n');
        dotPatterns.put("135", 'o');
        dotPatterns.put("1234", 'p');
        dotPatterns.put("12345", 'q');
        dotPatterns.put("1235", 'r');
        dotPatterns.put("234", 's');
        dotPatterns.put("2345", 't');
        dotPatterns.put("136", 'u');
        dotPatterns.put("1236", 'v');
        dotPatterns.put("2456", 'w');
        dotPatterns.put("1346", 'x');
        dotPatterns.put("13456", 'y');
        dotPatterns.put("1356", 'z');
        dotPatterns.put("356", '0');
        dotPatterns.put("2", '1');
        dotPatterns.put("23", '2');
        dotPatterns.put("25", '3');
        dotPatterns.put("256", '4');
        dotPatterns.put("26", '5');
        dotPatterns.put("235", '6');
        dotPatterns.put("2356", '7');
        dotPatterns.put("236", '8');
        dotPatterns.put("35", '9');
        dotPatterns.put("0", ' ');
    }

    private TextView textViewPattern;
    private TextView textViewResult;

    private TextToSpeech textToSpeech;

    private String currentPattern = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPattern = findViewById(R.id.textView);
        textViewResult = findViewById(R.id.textView2);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                 if (status == TextToSpeech.SUCCESS) {
                    // Set language to English (UK)
                    int result = textToSpeech.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Language not supported, handle error if needed
                    }
                }
                if(status!=TextToSpeech.ERROR)
                {
                    textToSpeech.setLanguage(Locale.UK);
                }

            }
        });

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // Called when TTS starts speaking
                Log.d("TTS", "onStart: " + utteranceId);
            }

            @Override
            public void onDone(String utteranceId) {
                // Called when TTS finishes speaking
                Log.d("TTS","onDone:"+ utteranceId);
            }

            @Override
            public void onError(String utteranceId) {
                // Called if there is an error with TTS
                Log.d("TTS", "onStop: " + utteranceId);
            }
        });

        Button buttonMatch = findViewById(R.id.buttonMatch);
        buttonMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchPattern();
            }
        });

        Button buttonDot1 = findViewById(R.id.buttonDot1);
        buttonDot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDotToPattern("1");
            }
        });

        Button buttonDot2 = findViewById(R.id.buttonDot2);
        buttonDot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDotToPattern("2");
            }
        });

        Button buttonDot3 = findViewById(R.id.buttonDot3);
        buttonDot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDotToPattern("3");
            }
        });

        Button buttonDot4 = findViewById(R.id.buttonDot4);
        buttonDot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDotToPattern("4");
            }
        });

        Button buttonDot5 = findViewById(R.id.buttonDot5);
        buttonDot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDotToPattern("5");
            }
        });

        Button buttonDot6 = findViewById(R.id.buttonDot6);
        buttonDot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDotToPattern("6");
            }
        });

        Button buttonDotSpace = findViewById(R.id.buttonDotSpace);
        buttonDotSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDotToPattern("0");
            }
        });

        Button buttonSpeak = findViewById(R.id.buttonDotSpeak);
        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakText();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the TextToSpeech resources
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    private void addDotToPattern(String dot) {
        currentPattern += dot;
        textViewPattern.setText(currentPattern);
    }

    private void matchPattern() {
        Character result = dotPatterns.get(currentPattern);
        if (result != null) {
            String currentResult = textViewResult.getText().toString();
            currentResult += result;
            textViewResult.setText(currentResult);
        }
        currentPattern = "";
        textViewPattern.setText("");
    }

    private void speakText() {
        String text = textViewResult.getText().toString();
        if (!text.isEmpty()) {
            // Set utterance ID for tracking TTS events
            String utteranceId = "ID";

            // Speak the text
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
        }
    }
}