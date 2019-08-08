package com.app.aihealthapp.view.circledialog.params;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.app.aihealthapp.view.circledialog.res.values.CircleColor;
import com.app.aihealthapp.view.circledialog.res.values.CircleDimen;

/**
 * 输入框参数
 * Created by hupei on 2017/3/31.
 */
public class InputParams implements Parcelable {
    private static final int[] MARGINS = {50, 20, 50, 40};

    /**
     * 输入框与body视图的距离
     */
    public int[] margins = MARGINS;
    /**
     * 输入框的高度
     */
    public int inputHeight = CircleDimen.INPUT_HEIGHT;
    /**
     * 输入框提示语
     */
    public String hintText;
    /**
     * 输入框提示语颜色
     */
    public int hintTextColor = CircleColor.content;
    /**
     * 输入框背景资源文件
     */
    public int inputBackgroundResourceId;
    /**
     * 输入框边框线条粗细
     */
    public int strokeWidth = 1;
    /**
     * 输入框边框线条颜色
     */
    public int strokeColor = CircleColor.inputStroke;
    /**
     * 输入框的背景
     */
    public int inputBackgroundColor = Color.TRANSPARENT;
    /**
     * body视图的背景色
     */
    public int backgroundColor;
    /**
     * 输入框字体大小
     */
    public int textSize = CircleDimen.CONTENT_TEXT_SIZE;
    /**
     * 输入框字体颜色
     */
    public int textColor = CircleColor.title;

    public static final Creator<InputParams> CREATOR = new Creator<InputParams>() {
        @Override
        public InputParams createFromParcel(Parcel in) {
            return new InputParams(in);
        }

        @Override
        public InputParams[] newArray(int size) {
            return new InputParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.margins);
        dest.writeInt(this.inputHeight);
        dest.writeString(this.hintText);
        dest.writeInt(this.hintTextColor);
        dest.writeInt(this.inputBackgroundResourceId);
        dest.writeInt(this.strokeWidth);
        dest.writeInt(this.strokeColor);
        dest.writeInt(this.inputBackgroundColor);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.textSize);
        dest.writeInt(this.textColor);
    }

    public InputParams() {
    }

    protected InputParams(Parcel in) {
        this.margins = in.createIntArray();
        this.inputHeight = in.readInt();
        this.hintText = in.readString();
        this.hintTextColor = in.readInt();
        this.inputBackgroundResourceId = in.readInt();
        this.strokeWidth = in.readInt();
        this.strokeColor = in.readInt();
        this.inputBackgroundColor = in.readInt();
        this.backgroundColor = in.readInt();
        this.textSize = in.readInt();
        this.textColor = in.readInt();
    }

   /* public static final Creator<InputParams> CREATOR = newCreator<InputParams>() {
        @Override
        public InputParams createFromParcel(Parcel source) {
            return new InputParams(source);
        }

        @Override
        public InputParams[] newArray(int size) {
            return new InputParams[size];
        }
    };*/
}
