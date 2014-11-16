package net.cherryzhang.sekuhara.BluetoothButtonAndChat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import net.cherryzhang.sekuhara.MagnetService.ChatHeadService;
import net.cherryzhang.sekuhara.R;

/**
 * Created by Cherry_Zhang on 2014-11-16.
 */
public class BluetoothButtonAndMessagingActivity extends FragmentActivity
{

    ViewPager viewPager;
    TabsPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_and_messaging_activity);
        viewPager = (ViewPager)findViewById(R.id.pager);
        myPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip)findViewById(R.id.pager_title_strip);

        new LongOperation().execute();
    }

    private class LongOperation extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params) {
            //start the chathead
            startService(new Intent(BluetoothButtonAndMessagingActivity.this, ChatHeadService.class));
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast toast = Toast.makeText(BluetoothButtonAndMessagingActivity.this, "Press the little icon in order to report a chikan!", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundResource(R.drawable.custom_bkg_for_toast);
            toast.show();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


}
