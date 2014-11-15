package net.cherryzhang.sekuhara.MagnetService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;

import com.premnirmal.Magnet.IconCallback;
import com.premnirmal.Magnet.Magnet;

import net.cherryzhang.sekuhara.R;

/**
 * Created by Cherry_Zhang on 2014-11-15.
 */
public class ChatHeadService extends Service implements IconCallback
{

    private Magnet mMagnet;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ImageView iconView = new ImageView(this);
        iconView.setImageResource(R.drawable.ic_launcher);
        mMagnet = new Magnet.Builder(this)
                .setIconView(iconView)
                .setIconCallback(this)
                .setRemoveIconResId(R.drawable.trash)
                .setRemoveIconShadow(R.drawable.bottom_shadow)
                .setShouldFlingAway(true)
                .setShouldStickToWall(true)
                .setRemoveIconShouldBeResponsive(true)
                .build();
        mMagnet.show();
    }

    @Override
    public void onFlingAway()
    {

    }

    @Override
    public void onMove(float x, float y)
    {

    }

    @Override
    public void onIconClick(View icon, float iconXPose, float iconYPose)
    {
        mMagnet.destroy();
    }

    @Override
    public void onIconDestroyed()
    {

    }
}
