package net.cherryzhang.sekuhara.ReceiverActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import net.cherryzhang.sekuhara.R;

import java.util.Locale;

public class ReceiverActivity extends Activity
{
    private int REQUEST_ENABLE_BT = 1;
    TextToSpeech tts;
    TextView tvtv;

    Handler handler;

    final Runnable r = new Runnable()
    {
        public void run()
        {
            //parse query
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Receive");
            query.getInBackground("5xZ2q7kB01", new GetCallback<ParseObject>()
            {
                @Override
                public void done(ParseObject parseObject, com.parse.ParseException e)
                {
                    if (e == null) {
                        // object will be your game score
                        if (parseObject.getBoolean("isCalled") == true)
                        {
                            tts.speak("痴漢があります。痴漢があります。痴漢があります。痴漢があります。", TextToSpeech.QUEUE_FLUSH, null);
                            tvtv.setText("痴漢があります!!!");
                            handler.removeCallbacks(r);
                        }
                    } else {
                        // something went wrong
                    }
                    handler.postDelayed(r, 3000);
                }
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        tvtv = (TextView) findViewById(R.id.tvtv);

        Parse.initialize(this,
                "TsVbzF7jXzY1C0o86V2xxAxgSxvy4jmbyykOabPl",
                "VzamwWm4WswbDFxrxos2oSerQ2Av4RM6J5mNnNgr");

        tts = new TextToSpeech(ReceiverActivity.this,new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status != TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.JAPANESE);
                }
            }
        });

        handler = new Handler();
        handler.postDelayed(r, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receiver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
