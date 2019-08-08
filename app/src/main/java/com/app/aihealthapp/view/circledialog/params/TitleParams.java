package com.app.aihealthapp.view.circledialog.params;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.app.aihealthapp.view.circledialog.res.values.CircleColor;
import com.app.aihealthapp.view.circledialog.res.values.CircleDimen;

/**
 * 标题参数
 * Created by hupei on 2017/3/30.
 */
public class TitleParams implements Parcelable {
    /**
     * 标题
     */
    public String text;
    /**
     * 标题高度
     */
    public int height = CircleDimen.TITLE_HEIGHT;
    /**
     * 标题字体大小
     */
    public int textSize = CircleDimen.TITLE_TEXT_SIZE;
    /**
     * 标题字体颜色
     */
    public int textColor = CircleColor.title;
    /**
     * 标题背景颜色
     */
    public int backgroundColor;

    public int gravity = Gravity.CENTER;

    public static final Creator<TitleParams> CREATOR = new Creator<TitleParams>() {
        @Override
        public TitleParams createFromParcel(Parcel in) {
            return new TitleParams(in);
        }

        @Override
        public TitleParams[] newArray(int size) {
            return new TitleParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeInt(this.height);
        dest.writeInt(this.textSize);
        dest.writeInt(this.textColor);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.gravity);
    }

    public TitleParams() {
    }

    protected TitleParams(Parcel in) {
        this.text = in.readString();
        this.height = in.readInt();
        this.textSize = in.readInt();
        this.textColor = in.readInt();
        this.backgroundColor = in.readInt();
        this.gravity = in.readInt();
    }

   /* public static final Creator<TitleParams> CREATOR = newCreator<TitleParams>() {
        @Override
        public TitleParams createFromParcel(Parcel source) {
            return new TitleParams(source);
        }

        @Override
        public TitleParams[] newArray(int size) {
            return new TitleParams[size];
        }
    };*/
}
