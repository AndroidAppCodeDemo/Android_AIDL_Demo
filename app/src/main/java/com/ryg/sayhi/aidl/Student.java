package com.ryg.sayhi.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 通过AIDL传输非基本类型的对象，被传输的对象需要序列化
 */
public final class Student implements Parcelable {


    public String name;
    public int age;

    public Student() {
    }

    public static final Parcelable.Creator<Student> CREATOR = new
            Parcelable.Creator<Student>() {

                public Student createFromParcel(Parcel in) {
                    return new Student(in);
                }

                public Student[] newArray(int size) {
                    return new Student[size];
                }

            };

    private Student(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);

        dest.writeInt(age);
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("name: ");
        sb.append(name);
        sb.append(" age: ");
        sb.append(age);
        return sb.toString();
    }

}  