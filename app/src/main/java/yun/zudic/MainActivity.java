package yun.zudic;

import android.graphics.Color;
import android.os.AsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.util.Linkify;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static android.speech.tts.TextToSpeech.ERROR;

public class MainActivity extends Activity {
    public String[] words;
    LinearLayout container;
    TextView tv; Button bt;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)this.findViewById(R.id.click_btn);
        container = (LinearLayout)findViewById(R.id.child);

        b.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                container.removeAllViews();
                ZudictP s = new ZudictP();
                EditText sentences = (EditText)findViewById(R.id.sentence);
                String url = "https://zh.dict.naver.com/sentenceAnalysis.nhn?query=";

                NetworkTask networkTask = new NetworkTask(url, sentences.getText().toString());
                networkTask.execute();
            }
        });
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private String sentences;

        public NetworkTask(String url, String sentences){
            this.url = url;
            this.sentences = sentences;
        }

        @Override
        protected String doInBackground(Void... params){
            String result;
            ZudictP zd = new ZudictP();
            result = zd.request(url, sentences);

            return result;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            s = s.replace(" ","");
            String[] splited = new String[s.length()];
            String[] splited2 = new String[s.length()];
            String[] splited3 = new String[s.length()];
            int[] wordlength = new int[s.length()];
            int word = 0;

            int originalSentence = s.indexOf("originalSentence\":\"");
            int start, end;
            for(int i=0; i<s.length()-7; i++){
                if(s.substring(i, i+7).equals("word\":\"")){
                    start = i+7;
                    end = s.indexOf("\"}", start);
                    splited[word] = s.substring(start, end);
                    wordlength[word] = splited[word].length();
                    splited[word] = s.substring(originalSentence+19, originalSentence+19+wordlength[word]);
                    originalSentence += wordlength[word];
                    if(hanziPandog(splited[word].toCharArray()) == false){continue;}
                    word++;
                }
            }
            int start2, end2;
            int word2 = 0;
            for(int i=0; i<s.length()-9; i++){
                if(s.substring(i, i+9).equals("pinyin\":\"")){
                    start2 = i+9;
                    end2 = s.indexOf("\"", start2);
                    splited2[word2] = s.substring(start2, end2);
                    if(splited2[word2].length() <= 1){continue;}
                    word2++;
                }
            }
            ZhuyinConverter zc = new ZhuyinConverter();
            int start3, end3;
            int word3 = 0;
            for(int i=0; i<s.length()-12; i++){
                if(s.substring(i, i+12).equals("numPinyin\":\"")){
                    start3 = i+12;
                    end3 = s.indexOf("\"", start3);
                    splited3[word3] = s.substring(start3, end3);
                    if(splited3[word3].length() <= 1){continue;}
                    splited3[word3] = zc.Converter(splited3[word3]);
                    word3++;
                }
            }

            String[] combined = new String[word];
            for(int i=0; i<word; i++){
                combined[i] = splited[i] + " " + splited2[i] + " " + splited3[i];
            }wordComposer(combined, word, splited);
        }
    }

    public void wordComposer(String[] s, int word, String[] sp){
        words = new String[word];
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != ERROR){
                    tts.setLanguage(Locale.CHINESE);
                }
            }
        });

        for(int i=0; i<word; i++) {
            words[i] = s[i];
            final String temp = sp[i];

            tv = new TextView(this);
            tv.setText(words[i]);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(24);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
                @Override
                public String transformUrl(Matcher match, String url) {
                    return "";
                }
            };
            Pattern pattern = Pattern.compile(sp[i]);
            Linkify.addLinks(tv, pattern, "https://zh.dict.naver.com/#/search?query="+temp, null, mTransform);

            container.addView(tv);

            bt = new Button(this);
            bt.setText("듣기");
            bt.setTag(temp);
            bt.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tts.speak(temp, TextToSpeech.QUEUE_FLUSH, null);
                }
            });

            container.addView(bt);
        }
    }

    public boolean hanziPandog(char[] ch) {
        if (ch[0] < 0x4E00 || ch[0] > 0x9FD5) {
            return false;
        }else {return true;}
    }
}

