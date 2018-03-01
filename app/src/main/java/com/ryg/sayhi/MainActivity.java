package com.ryg.sayhi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.ryg.sayhi.aidl.IMyService;
import com.ryg.sayhi.aidl.Student;

public class MainActivity extends Activity {


    private IMyService mIMyService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIMyService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //通过服务端onBind方法返回的binder对象得到IMyService的实例，得到实例就可以调用它的方法了
            mIMyService = IMyService.Stub.asInterface(service);
            try {
                Student student = mIMyService.getStudent().get(0);
                showDialog(student.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 绑定Service
                if (view.getId() == R.id.button1) {
                    Intent intentService = new Intent(MainActivity.this, MyService.class);
                    intentService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.this.bindService(intentService, mServiceConnection, BIND_AUTO_CREATE);
                }

            }
        });
    }


    public void showDialog(String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Title")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        if (mIMyService != null) {
            unbindService(mServiceConnection);
        }
        super.onDestroy();
    }
}
