package net.cherryzhang.sekuhara.MagnetService;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.premnirmal.Magnet.IconCallback;
import com.premnirmal.Magnet.Magnet;

import net.cherryzhang.sekuhara.BluetoothButtonAndChat.BluetoothButtonAndMessagingActivity;
import net.cherryzhang.sekuhara.R;

import java.util.List;

/**
 * Created by Cherry_Zhang on 2014-11-15.
 */
public class ChatHeadService extends Service implements IconCallback
{

    private Magnet mMagnet;
    ImageView iconView;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        iconView = new ImageView(this);
        iconView.setImageResource(R.drawable.ic_launcher);
        YoYo.with(Techniques.RubberBand)
                .duration(700)
                .playOn(iconView);
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
        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(iconView);
    }

    @Override
    public void onIconClick(View icon, float iconXPose, float iconYPose)
    {
        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(icon);
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        // get the info from the currently running task
        List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(1);

        if (taskInfo.get(0).topActivity.getShortClassName().contentEquals(".BluetoothActivity.BluetoothActivity"))
        {
            Toast.makeText(this, "Do the bluetooth call", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), BluetoothButtonAndMessagingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Toast toast = Toast.makeText(this, "Do the bluetooth call", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundResource(R.drawable.custom_bkg_for_toast);
            toast.show();
        }
        else
        {
//            Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
            Intent intent = new Intent(getApplicationContext(), BluetoothButtonAndMessagingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Toast toast = Toast.makeText(this, "Do the bluetooth call", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundResource(R.drawable.custom_bkg_for_toast);
            toast.show();

        }
//        ComponentName componentInfo = taskInfo.get(0).topActivity;
//        componentInfo.getPackageName();
    }

    @Override
    public void onIconDestroyed()
    {

    }
}
