package com.haibin.calendarviewproject.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

/**
 * 精美进度风格
 * Created by huanghaibin on 2018/2/8.
 */

public class ProgressMonthView extends MonthView {

    private Paint mProgressPaint = new Paint();
    private Paint mNoneProgressPaint = new Paint();
    private float mRadius;

    public ProgressMonthView(Context context) {
        super(context);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(dipToPx(context, 2.2f));
        mProgressPaint.setColor(0xBBf54a00);

        mNoneProgressPaint.setAntiAlias(true);
        mNoneProgressPaint.setStyle(Paint.Style.STROKE);
        mNoneProgressPaint.setStrokeWidth(dipToPx(context, 2.2f));
        mNoneProgressPaint.setColor(0x90CfCfCf);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 11 * 4;

    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, float x, float y, boolean hasScheme) {
        float cx = x + mItemWidth / 2;
        float cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, float x, float y) {
        float cx = x + mItemWidth / 2;
        float cy = y + mItemHeight / 2;

        int angle = 0;

        try {
            angle =  getAngle(Integer.parseInt(calendar.getScheme()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        RectF progressRectF = new RectF(cx - mRadius, cy - mRadius, cx + mRadius, cy + mRadius);
        canvas.drawArc(progressRectF, -90, angle, false, mProgressPaint);

        RectF noneRectF = new RectF(cx - mRadius, cy - mRadius, cx + mRadius, cy + mRadius);
        canvas.drawArc(noneRectF, angle - 90, 360 - angle, false, mNoneProgressPaint);

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, float x, float y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        float cx = x + mItemWidth / 2;

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }

    /**
     * 获取角度
     *
     * @param progress 进度
     * @return 获取角度
     */
    private static int getAngle(int progress) {
        return (int) (progress * 3.6);
    }


    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
