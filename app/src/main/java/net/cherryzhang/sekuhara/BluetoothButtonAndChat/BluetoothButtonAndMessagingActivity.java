package net.cherryzhang.sekuhara.BluetoothButtonAndChat;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.Parse;

import net.cherryzhang.sekuhara.BluetoothButtonAndChat.BluetoothChat.BluetoothChat;
import net.cherryzhang.sekuhara.BluetoothButtonAndChat.BluetoothChat.DeviceListActivity;
import net.cherryzhang.sekuhara.MagnetService.ChatHeadService;
import net.cherryzhang.sekuhara.R;

/**
 * Created by Cherry_Zhang on 2014-11-16.
 */
public class BluetoothButtonAndMessagingActivity extends FragmentActivity implements ActionBar.TabListener , ViewPager.OnPageChangeListener
{
    ViewPager viewPager;
    TabsPagerAdapter myPagerAdapter;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_and_messaging_activity);

        myPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), menu);
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip)findViewById(R.id.pager_title_strip);
        pagerTitleStrip.setTextColor(Color.GRAY);

        Parse.initialize(this,
                "TsVbzF7jXzY1C0o86V2xxAxgSxvy4jmbyykOabPl",
                "VzamwWm4WswbDFxrxos2oSerQ2Av4RM6J5mNnNgr");
        new LongOperation().execute();
    }

    private class LongOperation extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params) {
            //start the chathead
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

    @Override
    protected void onPause()
    {
        super.onPause();
        startService(new Intent(BluetoothButtonAndMessagingActivity.this, ChatHeadService.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(BluetoothChat.D) Log.d("BluetoothChat", "onActivityResult " + resultCode);
        switch (requestCode) {
            case BluetoothChat.REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    myPagerAdapter.bluetoothChat.connectDevice(data, true);
                }
                break;
            case BluetoothChat.REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    myPagerAdapter.bluetoothChat.connectDevice(data, false);
                }
                break;
            case BluetoothChat.REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    myPagerAdapter.bluetoothChat.setupChat(BluetoothChat.rootView);
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d("BluetoothChat", "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
//                finish();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat_and_button, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        int page = viewPager.getCurrentItem();
        Log.e("onPrepareOptionsMenu page", "" +page);
        switch (page)
        {
            case 0:
                menu.setGroupVisible(R.id.chatGroup,false);
                break;
            case 1:
                menu.setGroupVisible(R.id.chatGroup,true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent serverIntent = null;
        switch (item.getItemId()) {
            case R.id.secure_connect_scan:
                // Launch the DeviceListActivity to see devices and do scan
                serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, BluetoothChat.REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            case R.id.insecure_connect_scan:
                // Launch the DeviceListActivity to see devices and do scan
                serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, BluetoothChat.REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
            case R.id.discoverable:
                // Ensure this device is discoverable by others
                myPagerAdapter.bluetoothChat.ensureDiscoverable();
                return true;
        }
        return true;
    }

    @Override
    public void onPageScrolled(int i, float v, int i2)
    {
    }

    @Override
    public void onPageSelected(int currentFragmentIndex)
    {
        Log.e("onPageScrollStateChanged ","" +currentFragmentIndex);
        invalidateOptionsMenu();
    }

    @Override
    public void onPageScrollStateChanged(int currentFragmentIndex)
    {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
    {
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft)
    {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft)
    {

    }
}
