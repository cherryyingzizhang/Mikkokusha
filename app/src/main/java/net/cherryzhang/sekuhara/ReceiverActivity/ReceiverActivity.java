package net.cherryzhang.sekuhara.ReceiverActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;

import net.cherryzhang.sekuhara.R;

import java.util.Locale;

public class ReceiverActivity extends Activity
{
    private int REQUEST_ENABLE_BT = 1;
    TextToSpeech tts;

    Handler handler;

    final Runnable r = new Runnable()
    {
        public void run()
        {
            //parse query
            handler.postDelayed(this, 10000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

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
