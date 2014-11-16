package net.cherryzhang.sekuhara.SendBluetoothActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import net.cherryzhang.sekuhara.R;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import java.util.Locale;

import jp.ne.docomo.smt.dev.aitalk.AiTalkTextToSpeech;
import jp.ne.docomo.smt.dev.aitalk.data.AiTalkSsml;
import jp.ne.docomo.smt.dev.common.exception.SdkException;
import jp.ne.docomo.smt.dev.common.exception.ServerException;

/**
 * 
 * @author dyoung
 * @author Matt Tyler
 */
public class MonitoringActivity extends Activity implements BeaconConsumer {
	protected static final String TAG = "MonitoringActivity";
    private BeaconManager beaconManager;
    private AitestAsyncTask task;
    TextToSpeech tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitoring);
		verifyBluetooth();

        tts = new TextToSpeech(MonitoringActivity.this,new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status != TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.JAPANESE);
                }
            }
        });

        beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);

        // By default the AndroidBeaconLibrary will only find AltBeacons.  If you wish to make it
        // find a different type of beacon, you must specify the byte layout for that beacon's
        // advertisement with a line like below.  The example shows how to find a beacon with the
        // same byte layout as AltBeacon but with a beaconTypeCode of 0xaabb
        //
        // beaconManager.getBeaconParsers().add(new BeaconParser().
        //        setBeaconLayout("m:2-3=aabb,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

	    beaconManager.bind(this);
	    
		//initializing simulated beacons
        beaconManager.setBeaconSimulator(new TimedBeaconSimulator() );
		((TimedBeaconSimulator) BeaconManager.getBeaconSimulator()).createTimedSimulatedBeacons();
	}
	
	public void onRangingClicked(View view) {
		Intent myIntent = new Intent(this, RangingActivity.class);
		this.startActivity(myIntent);
	}

	private void verifyBluetooth() {

		try {
			if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Bluetooth not enabled");			
				builder.setMessage("Please enable bluetooth in settings and restart this application.");
				builder.setPositiveButton(android.R.string.ok, null);
				builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						finish();
			            System.exit(0);					
					}					
				});
				builder.show();
			}			
		}
		catch (RuntimeException e) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Bluetooth LE not available");			
			builder.setMessage("Sorry, this device does not support Bluetooth LE.");
			builder.setPositiveButton(android.R.string.ok, null);
			builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					finish();
		            System.exit(0);					
				}
				
			});
			builder.show();
			
		}
		
	}	

    @Override 
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
        if (tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
    }

    private void logToDisplay(final String line) {
    	runOnUiThread(new Runnable() {
    	    public void run() {
    	    	EditText editText = (EditText)MonitoringActivity.this
    					.findViewById(R.id.monitoringText);
       	    	editText.append(line+"\n");            	    	    		
    	    }
    	});
    }

    private AiTalkSsml ssml;
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters params;
    MediaPlayer mp;

    // getting camera parameters
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }

    /*
    * Turning On flash
    */
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            try
            {
                camera.setPreviewTexture(new SurfaceTexture(0));
            }
            catch (Exception e)
            {
                Log.e("camera exception", e.getMessage());
            }
            camera.open();
            camera.startPreview();
//            params = camera.getParameters();
//            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.autoFocus(new Camera.AutoFocusCallback() {
                public void onAutoFocus(boolean success, Camera camera) {
                }
            });
            Camera.Parameters params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            camera.setParameters(params);

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);

            isFlashOn = true;

        }

    }

    @Override
    public void onBeaconServiceConnect()
    {
        beaconManager.setMonitorNotifier(new MonitorNotifier()
        {
            @Override
            public void didEnterRegion(Region region)
            {
                logToDisplay("I just saw a beacon named " + region.getUniqueId() + " for the first time!");

//                Camera camera = Camera.open();
//                Camera.Parameters p = camera.getParameters();
//                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//                camera.setParameters(p);
//                camera.startPreview();
                turnOnFlash();

                tts.speak("痴漢 痴漢 痴漢 痴漢 痴漢 痴漢 痴漢", TextToSpeech.QUEUE_FLUSH, null);
//                AuthApiKey.initializeAuth("564d35486f2f484f727031757844466f6669382f564a6e57316443324348793162397365697775682e6d35");
//                try
//                {
//                    ssml = new AiTalkSsml();
//                    ssml.startVoice("nozomi");
//                    ssml.addText("ああああああああああああああああああああああああああああああああああああああ");
////                    ssml.endVoice();
//                }
//                catch (Exception ex)
//                {
//                    Log.e("AAAAAAAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAAAA" + ex.getMessage());
//                }
//                AlertDialog.Builder dlg = new AlertDialog.Builder(MonitoringActivity.this);
//                task = new AitestAsyncTask(dlg, AitestAsyncTask.henkan_ssml_sound);
//                task.execute(ssml);
            }

            @Override
            public void didExitRegion(Region region)
            {
                logToDisplay("I no longer see a beacon named " + region.getUniqueId());
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region)
            {
                logToDisplay("I have just switched from seeing/not seeing beacons: " + state);
            }
        });

        try
        {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));

        }
        catch (RemoteException e)
        {
        }

    }

        /////////////////////////////////////////////////////////////////////////////

	
}

class AitestAsyncTask extends AsyncTask<Object, Integer, byte[]>
{
    // 警報ダイアログ
    private AlertDialog.Builder _dlg;
    // 変換タイプ
    private int _henkan;
    public static final int henkan_ssml_sound = 1;
    public static final int henkan_ssml_aikana = 2;
    public static final int henkan_ssml_jeitakana = 3;
    public static final int henkan_aikana_sound = 11;
    public static final int henkan_aikana_jeitakana = 13;
    public static final int henkan_jeitakana_sound = 21;
    // エラーフラグ
    private boolean isSdkException = false;
    private String exceptionMessage = null;

    // 非同期タスクのコンストラクタ
    public AitestAsyncTask(AlertDialog.Builder dlg, int henkan) {
        super();
        _dlg = dlg;
        _henkan = henkan;
    }

    // 非同期タスクのバックグラウンド実行部分
    @Override
    protected byte[] doInBackground(Object... params) {
        byte[] resultData = null;
        try {
            // 要求処理クラスを作成
            AiTalkTextToSpeech search = new AiTalkTextToSpeech();
            // 要求処理クラスにリクエストデータを渡し、レスポンスデータを取得する
            switch (_henkan){
                case henkan_ssml_sound:
                    resultData = search.requestAiTalkSsmlToSound(((AiTalkSsml)params[0]).makeSsml());
                    break;
                case henkan_ssml_aikana:
                    resultData = search.requestAiTalkSsmlToAikana(((AiTalkSsml)params[0]).makeSsml()).getBytes();
                    break;
                case henkan_ssml_jeitakana:
                    resultData = search.requestAiTalkSsmlToJeitakana(((AiTalkSsml)params[0]).makeSsml());
                    break;
                case henkan_aikana_sound:
                    resultData = search.requestAikanaToSound((String)params[0]);
                    break;
                case henkan_aikana_jeitakana:
                    resultData = search.requestAikanaToJeitakana((String)params[0]);
                    break;
                case henkan_jeitakana_sound:
                    resultData = search.requestJeitakanaToSound(((String)params[0]).getBytes("Shift_Jis"));
                    break;
                default:
                    return null;
            }
            // 音声変換の場合は、スピーカに出力
            switch (_henkan){
                case henkan_ssml_sound:
                case henkan_aikana_sound:
                case henkan_jeitakana_sound:
                    // 音声出力用バッファ作成
                    int bufSize = AudioTrack.getMinBufferSize(16000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
                    // ビッグエディアンをリトルエディアンに変換
                    search.convertByteOrder16(resultData);
                    // 音声出力
                    AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, bufSize, AudioTrack.MODE_STREAM);
                    at.play();
                    at.write(resultData, 0, resultData.length);
                    // 音声出力待ち
                    Thread.sleep(resultData.length/32);
                    break;
            }
        } catch (SdkException ex) {
            isSdkException = true;
            exceptionMessage = "ErrorCode: " + ex.getErrorCode() + "\nMessage: " + ex.getMessage();
        } catch (ServerException ex) {
            exceptionMessage = "ErrorCode: " + ex.getErrorCode() + "\nMessage: " + ex.getMessage();
        } catch (Exception ex){
            exceptionMessage = "ErrorCode: " + "**********" + "\nMessage: " + ex.getMessage();
        }
        return resultData;
    }

    @Override
    protected void onCancelled() {
    }

    @Override
    protected void onPostExecute(byte[] resultData) {
        if(resultData == null){
            // エラー表示
            if(isSdkException){
                _dlg.setTitle("SdkException 発生");

            }else{
                _dlg.setTitle("ServerException 発生");
            }
            _dlg.setMessage(exceptionMessage + " ");
            _dlg.show();

        }else{
            // 結果表示
            switch (_henkan){
                case henkan_ssml_aikana:
                    // ＡＩカナ結果の表示
                    break;
                case henkan_ssml_jeitakana:
                case henkan_aikana_jeitakana:
                    // Ｊｅｉｔａカナ結果の表示
                    try {
                    } catch (Exception ex){
                    }
                    break;
            }
        }
    }
}