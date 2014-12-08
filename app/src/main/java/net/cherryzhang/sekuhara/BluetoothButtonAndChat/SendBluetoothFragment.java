package net.cherryzhang.sekuhara.BluetoothButtonAndChat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import net.cherryzhang.sekuhara.R;

public class SendBluetoothFragment extends Fragment
{
    Button callForHelp;

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
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "The alarm is going to sound!", Toast.LENGTH_SHORT);
                            View view = toast.getView();
                            view.setBackgroundResource(R.drawable.custom_bkg_for_toast);
                            toast.show();
                        } else {
                            // something went wrong
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                            View view = toast.getView();
                            view.setBackgroundResource(R.drawable.custom_bkg_for_toast);
                            toast.show();
                        }
                    }
                });
            }
        });

        return rootView;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//        Log.e("SendBluetoothFragment ", "oncreateoptionsmenu");
//        super.onCreateOptionsMenu(menu, inflater);
//    }
}

