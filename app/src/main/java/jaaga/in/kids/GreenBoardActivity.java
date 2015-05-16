package jaaga.in.kids;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by DELL on 16-05-2015.
 */
public class GreenBoardActivity extends Activity {
    String AtoZ[];
    LayoutInflater mLayoutInflater;
    ViewPager mViewPager;
    TextToSpeech mTextToSpeech;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.green_board);

        AtoZ = getResources().getStringArray(R.array.alphabets_large);
        text = (TextView) findViewById(R.id.alphabets_id);
        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTextToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new MyPageAdapter());
    }
    public class MyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return AtoZ.length;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View page = mLayoutInflater.inflate(R.layout.page, null);
            ((TextView)page.findViewById(R.id.alphabets_id)).setText(AtoZ[position]);
            ((TextView)page.findViewById(R.id.alphabet_small_id)).setText(AtoZ[position].toLowerCase());
            //Add the page to the front of the queue
            ((ViewPager) container).addView(page, 0);
            return page;
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            //See if object from instantiateItem is related to the given view
            //required by API
            return arg0==(View)arg1;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
            object=null;
        }
    }
    @Override
    public void onPause(){
        if(mTextToSpeech !=null){
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onPause();
    }
    public void speakText(View view){
        String toSpeak = text.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,
                Toast.LENGTH_SHORT).show();
        mTextToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }
}
