package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterCamera;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.utils.SoundUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewCamera;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("deprecation")
public class ScreenCamera extends Screen implements IViewCamera, Camera.ShutterCallback, Camera.PictureCallback {
    private FrameLayout mFrmCameraPreview;
    private Button mBtnCapture;
    private TextView mLblNoCamera;
    private CameraPreviewView mCameraPreviewView;

    private Camera mCamera;
    private IPresenterCamera mPresenter;

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.releaseCameraAndPreviewView();
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__camera, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mFrmCameraPreview = (FrameLayout)pView.findViewById(R.id.frmCameraPreview);
        this.mBtnCapture = (Button)pView.findViewById(R.id.btnCapture);
        this.mLblNoCamera = (TextView)pView.findViewById(R.id.lblNoCamera);
        if(this.mCamera == null) {
            this.mCamera = this.getCameraInstance();
        }
        if (this.mCamera != null) {
            this.mCameraPreviewView = new CameraPreviewView(this.getContext(), this.mCamera, 0);
            this.mFrmCameraPreview.addView(this.mCameraPreviewView);
        } else {
            this.mBtnCapture.setVisibility(View.GONE);
            this.mLblNoCamera.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mBtnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenCamera.this.buttonCaptureClick();
            }
        });

        this.mPresenter = (IPresenterCamera)PresenterFactory.createPresenter(IPresenterCamera.class, this);
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    public void onImageSaved(File pFile) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.BUNDLE_KEY__CAPTURED_PICTURE_FILE_PATH, pFile.getAbsolutePath());
        Screen screen = new ScreenCapturedPicturePreview();
        screen.setArguments(arguments);
        this.mScreenActionsListener.onChangeScreen(screen);
    }

    @Override
    public void onShutter() {
        SoundUtils.playCameraShutterSound(this.getContext());
    }

    @Override
    public void onPictureTaken(byte[] pData, Camera pCamera) {
        int orientation = this.mCameraPreviewView.getCameraDisplayOrientation();
        this.mPresenter.saveImage(pData, orientation);
    }

    private void buttonCaptureClick() {
        if(this.mCamera != null) {
            this.mCamera.takePicture(this, null, this);
        }
    }

    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e){
            e.printStackTrace();
        }
        return camera;
    }

    private void releaseCameraAndPreviewView() {
        if (this.mCamera != null) {
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        }

        if(this.mCameraPreviewView != null){
            this.mCameraPreviewView.destroyDrawingCache();
        }
    }

    private static class CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback {
        private static final double ASPECT_TOLERANCE = 0.1;

        private Context mContext;
        private Camera mCamera;
        private SurfaceHolder mSurfaceHolder;
        private Camera.Size mPreviewSize;
        private Camera.Size mPictureSize;
        private int mCameraId;

        public CameraPreviewView(Context pContext, Camera pCamera, int pCameraId) {
            super(pContext);

            this.mContext = pContext;
            this.mCamera = pCamera;
            this.mSurfaceHolder = this.getHolder();
            this.mSurfaceHolder.addCallback(this);
            this.mSurfaceHolder.setKeepScreenOn(true);
            this.mCameraId = pCameraId;

            this.requestLayout();
        }

        @Override
        protected void onMeasure(int pWidthMeasureSpec, int pHeightMeasureSpec) {
            int width = this.resolveSize(this.getSuggestedMinimumWidth(), pWidthMeasureSpec);
            int height = this.resolveSize(this.getSuggestedMinimumHeight(), pHeightMeasureSpec);

            List<Camera.Size> supportedPreviewSizes = this.mCamera.getParameters().getSupportedPreviewSizes();
            List<Camera.Size> supportedPictureSizes = this.mCamera.getParameters().getSupportedPictureSizes();
            if (supportedPreviewSizes != null){
                this.mPreviewSize = this.getOptimalSize(supportedPreviewSizes, width, height);
            }

            if (supportedPictureSizes != null){
                this.mPictureSize = this.getOptimalSize(supportedPictureSizes, width, height);
            }

            this.setMeasuredDimension(width, height);
        }

        @Override
        public void surfaceCreated(SurfaceHolder pSurfaceHolder) {
            this.startCameraPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder pSurfaceHolder) {
            this.stopCameraPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder pSurfaceHolder, int pFormat, int pWidth, int pHeight) {
            this.refreshCamera();
        }

        private void refreshCamera() {
            if (this.mSurfaceHolder.getSurface() == null){
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
                    parameters.setPreviewSize(this.mPreviewSize.width, this.mPreviewSize.height);
                }

                if(this.mPictureSize != null) {
                    parameters.setPictureSize(this.mPictureSize.width, this.mPictureSize.height);
                }

                int orientation = this.getCameraDisplayOrientation();
                parameters.setRotation(orientation);

                this.mCamera.setDisplayOrientation(orientation);
                this.mCamera.setParameters(parameters);
                this.startCameraPreview();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        private Camera.Size getOptimalSize(List<Camera.Size> pSizes, int pWidth, int pHeight) {
            if (pSizes == null) {
                return null;
            }

            Camera.Size optimalSize = null;
            double minDiff = Double.MAX_VALUE;
            double targetRatio = (double)pHeight / pWidth;
            int targetHeight = pHeight;

            for (Camera.Size size : pSizes){
                double ratio = (double)size.width / size.height;
                if (Math.abs(ratio - targetRatio) > CameraPreviewView.ASPECT_TOLERANCE) {
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

        public int getCameraDisplayOrientation() {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(this.mCameraId, info);

            int rotation = ((WindowManager)this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            int degrees = 0;
            switch(rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
                default:
                    break;
            }

            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }

            return result;
        }

        private void startCameraPreview() {
            try {
                this.mCamera.setPreviewDisplay(this.mSurfaceHolder);
                this.mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void stopCameraPreview() {
            this.mCamera.stopPreview();
        }
    }
}
