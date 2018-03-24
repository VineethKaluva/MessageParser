package edu.inspire.messageparser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


public class MessageViewActivity extends AppCompatActivity {

    Bundle extras;
    Toolbar toolbar;
    TextView textView;
    MessageDBClass db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgview);
        stopService(new Intent(MessageViewActivity.this, NotificationService.class));
        db= new MessageDBClass(MessageViewActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.textView);
        extras = getIntent().getExtras();
        if(extras != null){


            String type = extras.getString("type");

            getSupportActionBar().setTitle(extras.getString("smsaddress"));

            textView.setText(extras.getString("smsbody"));

            if(type.equals("emotional")){
                db.updateUnreadtoRead(extras.getString("smsaddress"), "emotional");

            } else if(type.equals("happy")){
                db.updateUnreadtoRead(extras.getString("smsaddress"), "happy");
            } else  if(type.equals("emergency")){
                db.updateUnreadtoRead(extras.getString("smsaddress"), "emergency");
            }






        }


    }
}

