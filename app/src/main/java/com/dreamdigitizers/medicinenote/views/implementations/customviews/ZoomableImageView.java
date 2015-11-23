/**
 * This class was taken from:
 * http://www.c-sharpcorner.com/UploadFile/88b6e5/multi-touch-panning-pinch-zoom-image-view-in-android-using/
 */
package com.dreamdigitizers.medicinenote.views.implementations.customviews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class ZoomableImageView extends ImageView {
    private static final int MODE__NONE = 0;
    private static final int MODE__DRAG = 1;
    private static final int MODE__ZOOM = 2;

    private static final int CLICK_RADIUS = 3;

    private static final float SCALE__MIN = 1f;
    private static final float SCALE__MAX = 5f;

    private Matrix mMatrix;
    private PointF mStartPoint;
    private PointF mLastPoint;
    private ScaleGestureDetector mScaleDetector;
    private Animator mAnimator;

    private float[] mMatrixValues;
    private float mSavedScaleValue;
    private float mOriginalWidth;
    private float mOriginalHeight;
    private int mMode;
    private int mViewWidth;
    private int mViewHeight;
    private int mOldMeasuredWidth;
    private int mOldMeasuredHeight;
    private boolean mIsOpen;

    public ZoomableImageView(Context pContext) {
        super(pContext);
        this.initialize(pContext);
    }

    public ZoomableImageView(Context pContext, AttributeSet pAttrs) {
        super(pContext, pAttrs);
        this.initialize(pContext);
    }

    @Override
    protected void onMeasure(int pWidthMeasureSpec, int pHeightMeasureSpec) {
        super.onMeasure(pWidthMeasureSpec, pHeightMeasureSpec);

        this.mViewWidth = MeasureSpec.getSize(pWidthMeasureSpec);
        this.mViewHeight = MeasureSpec.getSize(pHeightMeasureSpec);
        if(this.mOldMeasuredWidth == this.mViewWidth && this.mOldMeasuredHeight == this.mViewHeight || this.mViewWidth == 0 || this.mViewHeight == 0) {
            return;
        }

        this.mOldMeasuredWidth = this.mViewWidth;
        this.mOldMeasuredHeight = this.mViewHeight;

        if(this.mSavedScaleValue == ZoomableImageView.SCALE__MIN) {
            Drawable drawable = this.getDrawable();
            if(drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
                return;
            }

            int bitmapWidth = drawable.getIntrinsicWidth();
            int bitmapHeight = drawable.getIntrinsicHeight();

            float scale;
            float scaleX = (float)this.mViewWidth / (float)bitmapWidth;
            float scaleY = (float)this.mViewHeight / (float)bitmapHeight;
            scale = Math.min(scaleX, scaleY);
            this.mMatrix.setScale(scale, scale);

            float redundantXSpace = (float)this.mViewWidth - (scale * (float)bitmapWidth);
            float redundantYSpace = (float)this.mViewHeight - (scale * (float)bitmapHeight);
            this.mOriginalWidth = this.mViewWidth - redundantXSpace;
            this.mOriginalHeight = this.mViewHeight - redundantYSpace;
            redundantXSpace /= (float)2;
            redundantYSpace /= (float)2;
            this.mMatrix.postTranslate(redundantXSpace, redundantYSpace);

            this.setImageMatrix(this.mMatrix);
        }

        this.translate();
    }

    public boolean isOpen() {
        return this.mIsOpen;
    }

    public void zoomImageFromThumb(final View pThumbnailView, final Bitmap pBitmap) {
        this.setImageBitmap(pBitmap);
        this.setVisibility(View.VISIBLE);
        pThumbnailView.setAlpha(0f);

        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZoomableImageView.this.zoomImageToThumbnail(pThumbnailView);
            }
        });

        this.mIsOpen = true;
    }
    public void zoomImageToThumbnail(final View pThumbnailView) {
        pThumbnailView.setAlpha(1f);
        ZoomableImageView.this.setVisibility(View.GONE);

        this.mIsOpen = false;
    }


    public void zoomImageFromThumb(final View pContainerView, final View pThumbnailView, final Bitmap pBitmap, final int pAnimationDuration) {
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
        }

        this.setImageBitmap(pBitmap);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();

        pThumbnailView.getGlobalVisibleRect(startBounds);
        pContainerView.getGlobalVisibleRect(finalBounds);

        final float startScale;
        if ((float)finalBounds.width() / finalBounds.height() > (float)startBounds.width() / startBounds.height()) {
            startScale = (float)startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            startScale = (float)startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        pThumbnailView.setAlpha(0f);
        this.setVisibility(View.VISIBLE);

        this.setPivotX(0f);
        this.setPivotY(0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(this, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(this, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(this, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(this, View.SCALE_Y, startScale, 1f));
        animatorSet.setDuration(pAnimationDuration);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ZoomableImageView.this.mAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                ZoomableImageView.this.mAnimator = null;
            }
        });
        animatorSet.start();
        this.mAnimator = animatorSet;

        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZoomableImageView.this.zoomImageToThumbnail(pThumbnailView, startBounds, startScale, pAnimationDuration);
            }
        });

        this.mIsOpen = true;
    }

    public void zoomImageToThumbnail(final View pThumbnailView, final Rect pStartBounds, float pStartScaleFinal, final int pAnimationDuration) {
        if (ZoomableImageView.this.mAnimator != null) {
            ZoomableImageView.this.mAnimator.cancel();
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(ZoomableImageView.this, View.X, pStartBounds.left))
                .with(ObjectAnimator.ofFloat(ZoomableImageView.this, View.Y, pStartBounds.top))
                .with(ObjectAnimator.ofFloat(ZoomableImageView.this, View.SCALE_X, pStartScaleFinal))
                .with(ObjectAnimator.ofFloat(ZoomableImageView.this, View.SCALE_Y, pStartScaleFinal));
        animatorSet.setDuration(pAnimationDuration);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pThumbnailView.setAlpha(1f);
                ZoomableImageView.this.setVisibility(View.GONE);
                ZoomableImageView.this.mAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                pThumbnailView.setAlpha(1f);
                ZoomableImageView.this.setVisibility(View.GONE);
                ZoomableImageView.this.mAnimator = null;
            }
        });
        animatorSet.start();
        ZoomableImageView.this.mAnimator = animatorSet;

        this.mIsOpen = false;
    }

    private void initialize(Context pContext) {
        this.mMatrix = new Matrix();
        this.mLastPoint = new PointF();
        this.mStartPoint = new PointF();
        this.mScaleDetector = new ScaleGestureDetector(pContext, new ScaleListener());
        this.mMatrixValues = new float[9];
        this.mSavedScaleValue = ZoomableImageView.SCALE__MIN;
        this.mMode = ZoomableImageView.MODE__NONE;

        //this.setClickable(true);
        this.setImageMatrix(this.mMatrix);
        this.setScaleType(ScaleType.MATRIX);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View pView, MotionEvent pEvent) {
                ZoomableImageView.this.handleTouch(pEvent);
                return true;
            }
        });
    }

    private void handleTouch(MotionEvent pEvent) {
        this.mScaleDetector.onTouchEvent(pEvent);

        PointF currentPoint = new PointF(pEvent.getX(), pEvent.getY());
        switch(pEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.mStartPoint.set(currentPoint);
                this.mLastPoint.set(this.mStartPoint);
                this.mMode = ZoomableImageView.MODE__DRAG;
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.mMode == MODE__DRAG) {
                    float deltaX = currentPoint.x - this.mLastPoint.x;
                    float deltaY = currentPoint.y - this.mLastPoint.y;
                    float translateX = this.calculateDragTranslate(deltaX, this.mViewWidth, this.mOriginalWidth * this.mSavedScaleValue);
                    float translateY = this.calculateDragTranslate(deltaY, this.mViewHeight, this.mOriginalHeight * this.mSavedScaleValue);
                    this.mMatrix.postTranslate(translateX, translateY);
                    this.translate();
                    this.mLastPoint.set(currentPoint);
                }
                break;
            case MotionEvent.ACTION_UP:
                this.mMode = ZoomableImageView.MODE__NONE;
                int diffX = (int)Math.abs(currentPoint.x - this.mStartPoint.x);
                int diffY = (int)Math.abs(currentPoint.y - this.mStartPoint.y);
                if (diffX <= ZoomableImageView.CLICK_RADIUS && diffY <= ZoomableImageView.CLICK_RADIUS) {
                    this.performClick();
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                this.mMode = ZoomableImageView.MODE__NONE;
                break;
        }
        this.setImageMatrix(this.mMatrix);
        this.invalidate();
    }

    private float calculateDragTranslate(float pDelta, float pViewSize, float pContentSize) {
        if(pContentSize <= pViewSize) {
            return 0;
        }
        return pDelta;
    }

    private void translate() {
        this.mMatrix.getValues(this.mMatrixValues);
        float translateX = this.mMatrixValues[Matrix.MTRANS_X];
        float translateY = this.mMatrixValues[Matrix.MTRANS_Y];
        translateX = this.calculateTranslate(translateX, this.mViewWidth, this.mOriginalWidth * this.mSavedScaleValue);
        translateY = this.calculateTranslate(translateY, this.mViewHeight, this.mOriginalHeight * this.mSavedScaleValue);

        if(translateX != 0 || translateY != 0) {
            this.mMatrix.postTranslate(translateX, translateY);
        }
    }

    private float calculateTranslate(float pTranslate, float pViewSize, float pContentSize) {
        float minTranslate;
        float maxTranslate;
        if(pContentSize <= pViewSize) {
            minTranslate = 0;
            maxTranslate = pViewSize - pContentSize;
        } else {
            minTranslate = pViewSize - pContentSize;
            maxTranslate = 0;
        }

        if (pTranslate < minTranslate) {
            return minTranslate - pTranslate;
        }
        if (pTranslate > maxTranslate) {
            return maxTranslate - pTranslate;
        }
        return 0;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector pDetector) {
            ZoomableImageView.this.mMode = ZoomableImageView.MODE__ZOOM;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector pDetector) {
            float scaleFactor = pDetector.getScaleFactor();
            float originalScale = ZoomableImageView.this.mSavedScaleValue;
            ZoomableImageView.this.mSavedScaleValue *= scaleFactor;

            if(ZoomableImageView.this.mSavedScaleValue > ZoomableImageView.SCALE__MAX) {
                ZoomableImageView.this.mSavedScaleValue = ZoomableImageView.SCALE__MAX;
                scaleFactor = ZoomableImageView.SCALE__MAX / originalScale;
            } else if(ZoomableImageView.this.mSavedScaleValue < ZoomableImageView.SCALE__MIN) {
                ZoomableImageView.this.mSavedScaleValue = ZoomableImageView.SCALE__MIN;
                scaleFactor = ZoomableImageView.SCALE__MIN / originalScale;
            }

            if(ZoomableImageView.this.mOriginalWidth * ZoomableImageView.this.mSavedScaleValue <= ZoomableImageView.this.mViewWidth ||
                    ZoomableImageView.this.mOriginalHeight * ZoomableImageView.this.mSavedScaleValue <= ZoomableImageView.this.mViewHeight) {
                ZoomableImageView.this.mMatrix.postScale(scaleFactor, scaleFactor, ZoomableImageView.this.mViewWidth / 2, ZoomableImageView.this.mViewHeight / 2);
            } else {
                ZoomableImageView.this.mMatrix.postScale(scaleFactor, scaleFactor, pDetector.getFocusX(), pDetector.getFocusY());
            }

            ZoomableImageView.this.translate();

            return true;
        }
    }
}