package net.cherryzhang.sekuhara.BluetoothButtonAndChat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Cherry_Zhang on 2014-11-16.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter
{
    private static final String[] titles = { "密告する", "チャットする"};

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public TabsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int index)
    {
        switch (index) {
            case 0:
                return new SendBluetoothFragment();
            case 1:
                return new ClickHereToChat();
            default:
                return new Fragment(); //should never go here
        }
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
