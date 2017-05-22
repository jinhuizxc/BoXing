package com.example.administrator.boxing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.nio.ByteBuffer;

/**
 * Created by jinhui on 2016/12/20.
 */

public class SinWave extends View implements Runnable {

    private static final String TAG = SinWave.class.getSimpleName();
    private Paint mPaint;        //画笔对象
    public static float amplifier = 80.0f;   //幅值--正弦波的高度
    public static float frequency = 40.0f;    //频率
    private static float phase = 45.0f;       //相位
    public static int width = 0;
    private int height = 0;


    float cy;


    public SinWave(Context context) {
        super(context);

        mPaint = new Paint();
        new Thread(this).start();
    }

    //不加这个构造方法会闪退！！！
    public SinWave(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        new Thread(this).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw =");
        height = this.getHeight();    //1158高度
        Log.i(TAG, "WIDTH =" + height);
        width = this.getWidth();    // 1080宽度

        //将画布置为白色
        canvas.drawColor(Color.WHITE);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(15);
        canvas.drawText("幅度值", 5, 15, mPaint);
        canvas.drawText("原点(0,0)", 5, getHeight() / 2, mPaint);
        canvas.drawText("(时间)", getWidth() - 40, getHeight() / 2, mPaint);
        canvas.drawLine(5, 0, 0, height, mPaint);    //纵坐标线
        canvas.drawLine(5, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);  //横坐标线

        cy = height / 2;
            //画图形
            for (int i = 0; i < width - 1; i++) {
                canvas.drawLine(
                        (float) i,
                        cy - amplifier * (float) (Math.sin(phase * 2 * (float) Math.PI / 360.0f + 2 * Math.PI * frequency * i / width)),
                        (float) (i + 1),
                        cy - amplifier * (float) (Math.sin(phase * 2 * (float) Math.PI / 360.0f + 2 * Math.PI * frequency * (i + 1) / width)),
                        mPaint);
            }
        }




    public float GetAmplifier() {
        return amplifier;
    }

    public float GetFrequency() {
        return frequency;
    }

    public float GetPhase() {
        return phase;
    }

    public void Set(float amplifier, float frequency, float phase) {
        this.frequency = frequency;
        this.amplifier = amplifier;
        this.phase = phase;
    }


    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted() || MainActivity.isPlaySound) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            //刷新界面，子线程中用，通过此方法调用onDraw()方法
            this.postInvalidate();

        }
    }


    /**
     * 生成正弦波
     *
     * @param wave
     * @param waveLen 每段正弦波的长度
     * @return
     * @parzzam length  总长度
     */
    public static byte[] sin(byte[] wave, int waveLen, int length) {
        for (int i = 0; i < length; i++) {
//            amplifier * (float) (Math.sin(phase * 2 * (float) Math.PI / 360.0f + 2 * Math.PI * frequency * i / width))
            wave[i] = (byte) (amplifier * (Math.sin(2 * Math.PI * ((i % waveLen) * 1.00 / waveLen))));
            Log.i(TAG, "WAVE = " + wave[i]);

        }
        return wave;
    }

}
