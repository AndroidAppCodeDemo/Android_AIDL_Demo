package com.ryg.sayhi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.ryg.sayhi.aidl.IMyService;
import com.ryg.sayhi.aidl.Student;

import java.util.ArrayList;
import java.util.List;


public class MyService extends Service {
    private final static String TAG = "MyService";

    private boolean mCanRun = true;
    private List<Student> mStudents = new ArrayList<Student>();

    //这里实现了aidl中的抽象函数
    private final IMyService.Stub mBinder = new IMyService.Stub() {

        @Override
        public List<Student> getStudent() throws RemoteException {
            synchronized (mStudents) {
                return mStudents;
            }
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            synchronized (mStudents) {
                if (!mStudents.contains(student)) {
                    mStudents.add(student);
                }
            }
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }

    };

    @Override
    public void onCreate() {
        Thread thr = new Thread(null, new ServiceWorker(), "BackgroundService");
        thr.start();
        //
        synchronized (mStudents) {
            for (int i = 1; i < 6; i++) {
                Student student = new Student();
                student.name = "student" + i;
                student.age = i * 5;
                mStudents.add(student);
            }
        }
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, String.format("on bind,intent = %s", intent.toString()));
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mCanRun = false;
        super.onDestroy();
    }


    class ServiceWorker implements Runnable {
        long counter = 0;

        @Override
        public void run() {
            // do background processing here.....
            while (mCanRun) {
                Log.d("scott", "" + counter);
                counter++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}