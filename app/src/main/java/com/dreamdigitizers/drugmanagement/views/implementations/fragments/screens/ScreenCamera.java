package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dreamdigitizers.drugmanagement.R;

import java.io.IOException;
import java.util.List;

public class ScreenCamera extends Screen {
    private FrameLayout mFrmCameraPreview;
    private Button mBtnCapture;

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__camera, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mFrmCameraPreview = (FrameLayout)pView.findViewById(R.id.frmCameraPreview);
        this.mBtnCapture = (Button)pView.findViewById(R.id.btnCapture);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mBtnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenCamera.this.buttonCaptureClick();
            }
        });
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    private void buttonCaptureClick() {

    }

    @SuppressWarnings("deprecation")
    private static class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private static final double ASPECT_TOLERANCE = 0.1;

        private Camera mCamera;
        private Camera.Size mPreviewSize;
        private SurfaceHolder mSurfaceHolder;
        private Context mContext;
        private View mCameraView;
        private List<Camera.Size> mSupportedPreviewSizes;


        public CameraPreview(Context pContext, Camera pCamera, View pCameraView) {
            super(pContext);

            this.mCameraView = pCameraView;
            this.mContext = pContext;
            this.mSurfaceHolder = this.getHolder();
            this.mSurfaceHolder.addCallback(this);
            this.mSurfaceHolder.setKeepScreenOn(true);

            this.setCamera(pCamera);
        }

        @Override
        protected void onMeasure(int pWidthMeasureSpec, int pHeightMeasureSpec) {
            int width = this.resolveSize(this.getSuggestedMinimumWidth(), pWidthMeasureSpec);
            int height = this.resolveSize(this.getSuggestedMinimumHeight(), pHeightMeasureSpec);
            this.setMeasuredDimension(width, height);

            if (this.mSupportedPreviewSizes != null){
                this.mPreviewSize = this.getOptimalPreviewSize(this.mSupportedPreviewSizes, width, height);
            }
        }

        @Override
        protected void onLayout(boolean pIsChanged, int pLeft, int pTop, int pRight, int pBottom) {
            if (pIsChanged) {
                int width = pRight - pLeft;
                int height = pBottom - pTop;

                int previewWidth = width;
                int previewHeight = height;

                if (this.mPreviewSize != null){
                    Display display = ((WindowManager)this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

                    switch (display.getRotation()) {
                        case Surface.ROTATION_0:
                            previewWidth = this.mPreviewSize.height;
                            previewHeight = this.mPreviewSize.width;
                            this.mCamera.setDisplayOrientation(90);
                            break;
                        case Surface.ROTATION_90:
                            previewWidth = this.mPreviewSize.width;
                            previewHeight = this.mPreviewSize.height;
                            break;
                        case Surface.ROTATION_180:
                            previewWidth = this.mPreviewSize.height;
                            previewHeight = this.mPreviewSize.width;
                            break;
                        case Surface.ROTATION_270:
                            previewWidth = this.mPreviewSize.width;
                            previewHeight = this.mPreviewSize.height;
                            this.mCamera.setDisplayOrientation(180);
                            break;
                    }
                }

                int scaledChildHeight = previewHeight * width / previewWidth;
                this.mCameraView.layout(0, height - scaledChildHeight, width, height);
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder pSurfaceHolder) {
            try {
                this.mCamera.setPreviewDisplay(pSurfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder pSurfaceHolder) {
            if (this.mCamera != null){
                this.mCamera.stopPreview();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder pSurfaceHolder, int pFormat, int pWidth, int pHeight) {
            if (mSurfaceHolder.getSurface() == null){
                return;
            }

            try {
                Camera.Parameters parameters = this.mCamera.getParameters();

                List<String> focusModes = parameters.getSupportedFocusModes();
                if(focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                }
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

                if(this.mPreviewSize != null) {
                    Camera.Size previewSize = this.mPreviewSize;
                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                }

                this.mCamera.setParameters(parameters);
                this.mCamera.startPreview();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        public void startCameraPreview() {
            try {
                this.mCamera.setPreviewDisplay(this.mSurfaceHolder);
                this.mCamera.startPreview();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        private void setCamera(Camera pCamera) {
            this.mCamera = pCamera;
            this.mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();

            this.requestLayout();
        }

        private Camera.Size getOptimalPreviewSize(List<Camera.Size> pSizes, int pWidth, int pHeight) {
            if (pSizes == null) {
                return null;
            }

            Camera.Size optimalSize = null;
            double minDiff = Double.MAX_VALUE;
            double targetRatio = (double)pHeight / pWidth;
            int targetHeight = pHeight;

            for (Camera.Size size : pSizes){
                double ratio = (double)size.width / size.height;
                if (Math.abs(ratio - targetRatio) > CameraPreview.ASPECT_TOLERANCE) {
                    continue;
                }

                int newMinDiff = Math.abs(size.height - targetHeight);
                if (newMinDiff < minDiff) {
                    optimalSize = size;
                    minDiff = newMinDiff;
                }
            }

            if (optimalSize == null) {
                minDiff = Double.MAX_VALUE;
                for (Camera.Size size : pSizes) {
                    int newMinDiff = Math.abs(size.height - targetHeight);
                    if (newMinDiff < minDiff) {
                        optimalSize = size;
                        minDiff = newMinDiff;
                    }
                }
            }

            return optimalSize;
        }
    }
}
