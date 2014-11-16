package net.cherryzhang.sekuhara.BluetoothButtonAndChat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.cherryzhang.sekuhara.BluetoothButtonAndChat.BluetoothChat.BluetoothChat;
import net.cherryzhang.sekuhara.R;

public class ClickHereToChat extends android.support.v4.app.Fragment
{

    Button startChatActivity;

    public ClickHereToChat()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_click_here_to_chat, container, false);
        startChatActivity = (Button) rootView.findViewById(R.id.b_goToBluetoothChat);

        startChatActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), BluetoothChat.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}
