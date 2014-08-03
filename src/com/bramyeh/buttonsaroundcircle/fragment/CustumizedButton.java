package com.bramyeh.buttonsaroundcircle.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class CustumizedButton extends Button {

	private Paint mPaint;
	private Paint mErasePaint;

	Bitmap mBitmap;
	Canvas mCanvas;
	RectF mRect;

	Boolean mTouchDown = false;

	private int[] mColorArray = { Color.CYAN, Color.BLUE, Color.YELLOW,
			Color.GREEN, Color.RED, Color.DKGRAY, Color.GRAY, Color.LTGRAY };

	private int mCurrentIndex = -1;
	private int mCurrentColor = Color.TRANSPARENT;

	public CustumizedButton(Context context) {
		super(context);
		init();
	}

	public CustumizedButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustumizedButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setAntiAlias(true);

		mErasePaint = new Paint();
		mErasePaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		mErasePaint.setAntiAlias(true);

		setBackgroundColor(Color.TRANSPARENT);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (w != oldw || h != oldh) {
			mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			mRect = new RectF(0.0f, 0.0f, (float) w, (float) w);
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		setMeasuredDimension(width, width);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = getWidth();
		int radiusInner = w / 4;

		mBitmap.eraseColor(Color.TRANSPARENT);

		float startAngle = 0;
		float offSetAngle = 360 / mColorArray.length;
		for (int idx = 0; idx < mColorArray.length; idx++) {
			mPaint.setColor(mColorArray[idx]);
			mCanvas.drawArc(mRect, startAngle, offSetAngle, true, mPaint);
			if (mTouchDown && idx == mCurrentIndex) {
				mPaint.setColor(0x99999999);
				mCanvas.drawArc(mRect, startAngle, offSetAngle, true, mPaint);
			}
			startAngle += offSetAngle;
		}

		float centerX = w / 2;
		float centerY = w / 2;

		mCanvas.drawCircle(centerX, centerY, radiusInner, mErasePaint);

		canvas.drawBitmap(mBitmap, 0, 0, null);
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getActionMasked();

		if (action == MotionEvent.ACTION_DOWN) {
			int w = getWidth();
			int y = getHeight();
			float centerX = w / 2;
			float centerY = y / 2;

			float targetX = event.getX();
			float targetY = event.getY();

			double angle = Math.atan2((targetY-centerY), (targetX-centerX)) / Math.PI * 180;
			if (angle < 0.0d) {
				angle += 360.0d;
			}

			//
			float startAngle = 0;
			float offSetAngle = 360 / mColorArray.length;
			for (int idx = 0; idx < mColorArray.length; idx++) {
				if (startAngle + offSetAngle > angle) {
					mCurrentIndex = idx;
					mCurrentColor = mColorArray[idx];
					break;
				}
				startAngle += offSetAngle;
			}

			mTouchDown = true;
			invalidate();

		} else if (action == MotionEvent.ACTION_UP) {
			mTouchDown = false;
			invalidate();
		}

		return super.onTouchEvent(event);
	}

	public int getCurrentColor() {
		return mCurrentColor;
	}
}
