package net.cherryzhang.sekuhara.BluetoothButtonAndChat;

import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import net.cherryzhang.sekuhara.R;

public class SendBluetoothFragment extends Fragment
{
    Button callForHelp;
    // List of URIs to provide to Android Beam
    private Uri[] mFileUris = new Uri[10];
    NfcAdapter mNfcAdapter;
    // Flag to indicate that Android Beam is available
    boolean mAndroidBeamAvailable  = false;

    public SendBluetoothFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_button, container, false);
        callForHelp = (Button) rootView.findViewById(R.id.callForHelp);

        callForHelp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Receive");
                query.getInBackground("5xZ2q7kB01", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            // object will be your game score
                            object.put("isCalled", true);
                            object.saveInBackground();
                        } else {
                            // something went wrong
                        }
                    }
                });
            }
        });

        return rootView;
    }

    private class FileUriCallback implements
            NfcAdapter.CreateBeamUrisCallback {
        public FileUriCallback() {
        }
        /**
         * Create content URIs as needed to share with another device
         */
        @Override
        public Uri[] createBeamUris(NfcEvent event) {
            return mFileUris;
        }
    }
}

