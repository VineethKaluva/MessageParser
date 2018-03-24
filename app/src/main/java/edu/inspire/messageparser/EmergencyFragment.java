package edu.inspire.messageparser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class EmergencyFragment extends Fragment {

    MessageDBClass dbClass;
    ListView lv;
    TextView noMsg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msglistdisp, container, false);

        dbClass = new MessageDBClass(getActivity().getApplicationContext());
        Cursor cursor = dbClass.getDataFromEmerTab();
        noMsg = (TextView) view.findViewById(R.id.noMsg);
        lv = (ListView) view.findViewById(R.id.msgListDisp);
        int count = cursor.getCount();

        if (count == 0) {
            lv.setVisibility(View.GONE);
        } else if (count != 0) {
            noMsg.setVisibility(View.GONE);
            MessageAdapter adp = new MessageAdapter(getActivity().getApplicationContext(), cursor);


            lv.setAdapter(adp);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView address = (TextView) view.findViewById(R.id.addressDisp);
                TextView body = (TextView) view.findViewById(R.id.msgDisp);

                Intent intent  = new Intent(getActivity().getApplicationContext(), MessageViewActivity.class);
                intent.putExtra("smsaddress", address.getText().toString());
                intent.putExtra("smsbody", body.getText().toString());
                intent.putExtra("type", "emergency");
                getActivity().startActivity(intent);
            }
        });





        return view;
    }
}
