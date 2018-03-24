package edu.inspire.messageparser;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class MessageAdapter extends CursorAdapter {

    public  MessageAdapter(Context context, Cursor cursor){
        super(context, cursor, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.row, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView addressDisp = (TextView) view.findViewById(R.id.addressDisp);
        TextView msgDisp = (TextView) view.findViewById(R.id.msgDisp);
        String status = cursor.getString(cursor.getColumnIndex(MessageDBClass.STATUS));

        if(status.equalsIgnoreCase("Read")){
            addressDisp.setTextColor(context.getResources().getColor(R.color.readMsgColor));
            msgDisp.setTextColor(context.getResources().getColor(R.color.readMsgColor));
        } else if(status.equalsIgnoreCase("Unread")){
            addressDisp.setTextColor(context.getResources().getColor(R.color.unreadMsgCOlor));
            msgDisp.setTextColor(context.getResources().getColor(R.color.unreadMsgCOlor));
        }


        addressDisp.setText(cursor.getString(cursor.getColumnIndex(MessageDBClass.ADDRESS)));
        msgDisp.setText(cursor.getString(cursor.getColumnIndex(MessageDBClass.SMSBODY)));






    }
}
