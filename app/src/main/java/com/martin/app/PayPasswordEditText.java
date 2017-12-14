package com.martin.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * <pre>
 *     e-mail : ijingzema@gmail.com
 *     time   : 2017/12/05
 *     desc   : 设置密码控件
 *     version: 1.0
 *     Copyright: Copyright（c）2017
 * </pre>
 *
 * @author majingze
 */
public class PayPasswordEditText extends EditText {

    private int height, width;
    private int maxCount = 6;
    private float divideLineWStartX;
    private float startX;
    private float startY;
    private Paint divideLinePaint;
    private Paint circlePaint;
    private int length;
    private boolean isFirst = true;

    private PasswordInputListener passwordInputListener;

    public PayPasswordEditText(Context context) {
        super(context);
        init();
    }

    public PayPasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        divideLinePaint = new Paint();
        divideLinePaint.setColor(Color.parseColor("#000000"));
        divideLinePaint.setStyle(Paint.Style.STROKE);
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.FILL);
        this.setCursorVisible(false);
    }

    public void setPasswordInputListener(PasswordInputListener passwordInputListener) {
        this.passwordInputListener = passwordInputListener;
    }

    /**
     * 重置
     */
    public void reset() {
        isFirst = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;

        divideLineWStartX = w / maxCount;

        startX = w / maxCount / 2; //第一个圆心的x坐标
        startY = h / 2; //第一个圆心的y坐标

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //不再调用super.onDraw(canvas)方法，因为会导致父类文字继续显示
        for (int i = 0; i < maxCount - 1; i++) {
            canvas.drawLine((i + 1) * divideLineWStartX,
                    0,
                    (i + 1) * divideLineWStartX,
                    height,
                    divideLinePaint);
        }
        drawPsdCircle(canvas, length);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        length = text.length();
        postInvalidate();
        if (passwordInputListener != null) {
            if (length == maxCount) {
                if (isFirst) {
                    isFirst = false;
                    passwordInputListener.firstInputDone(text);
                } else {
                    passwordInputListener.secondInputDone(text);
                }
                this.setText("");
            }
        }
    }

    /**
     * 画密码实心圆
     *
     * @param canvas
     */
    private void drawPsdCircle(Canvas canvas, int textLength) {
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(startX + i * 2 * startX,
                    startY,
                    10,
                    circlePaint);
        }
    }

    public interface PasswordInputListener {

        /**
         * 第一次输入完成
         *
         * @param value 返回第一次输入的值
         */
        void firstInputDone(CharSequence value);

        /**
         * 第二次输入完成
         *
         * @param value 返回第二次输入的值
         */
        void secondInputDone(CharSequence value);
    }
}
