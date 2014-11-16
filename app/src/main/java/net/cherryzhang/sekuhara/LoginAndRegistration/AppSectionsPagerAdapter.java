package net.cherryzhang.sekuhara.LoginAndRegistration;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class AppSectionsPagerAdapter extends FragmentPagerAdapter
{

    public AppSectionsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new SummaryOfAppFragment1();
            case 1:
                return new SummaryOfAppFragment2();
            case 2:
                //the third screen (index 2) will be the login and registration screen
                return new LoginAndRegistrationFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Section " + (position + 1);
    }
}
