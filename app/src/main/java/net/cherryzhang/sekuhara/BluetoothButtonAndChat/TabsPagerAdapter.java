package net.cherryzhang.sekuhara.BluetoothButtonAndChat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.ViewGroup;

import net.cherryzhang.sekuhara.BluetoothButtonAndChat.BluetoothChat.BluetoothChat;

/**
 * Created by Cherry_Zhang on 2014-11-16.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter
{
    private static final String[] titles = { "密告する", "チャットする"};
    BluetoothChat bluetoothChat;
    Menu menu;

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public TabsPagerAdapter(FragmentManager fm, Menu menu)
    {
        super(fm);
        this.menu = menu;
    }

    @Override
    public Fragment getItem(int index)
    {
        switch (index) {
            case 0:
                return new SendBluetoothFragment();
            case 1:
                bluetoothChat = new BluetoothChat();
                return bluetoothChat;
            default:
                return new Fragment(); //should never go here
        }
    }

    public Fragment getBlueToothInstance()
    {
        return bluetoothChat;
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
