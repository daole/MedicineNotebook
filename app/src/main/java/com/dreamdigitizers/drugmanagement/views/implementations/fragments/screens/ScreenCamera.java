package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import java.util.List;

@SuppressWarnings("deprecation")
public class ScreenCamera extends Screen implements IViewCamera, Camera.ShutterCallback, Camera.PictureCallback {
    private FrameLayout mFrmCameraPreview;
    private Button mBtnCapture;
    private TextView mLblNoCamera;
    private View mCovTop;
    private View mCovBottom;
    private CameraPreviewView mCameraPreviewView;

    private Camera mCamera;
    private OrientationEventListener mOrientationEventListener;
    private int mOrientation;

    private IPresenterCamera mPresenter;

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mPresenter = (IPresenterCamera)PresenterFactory.createPresenter(IPresenterCamera.class, this);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.mOrientation = Constants.ORIENTATION__UNDEFINED;
        this.mOrientationEventListener.enable();

        if(this.mCamera == null) {
            this.mCamera = this.getCameraInstance();
        }
        if (this.mCamera != null) {
            this.mFrmCameraPreview.setVisibility(View.VISIBLE);
            this.mBtnCapture.setVisibility(View.VISIBLE);
            this.mLblNoCamera.setVisibility(View.GONE);
            this.mCameraPreviewView.setCamera(this.mCamera, 0);
        } else {
            this.mFrmCameraPreview.setVisibility(View.GONE);
            this.mBtnCapture.setVisibility(View.GONE);
            this.mLblNoCamera.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        this.mOrientationEventListener.disable();
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
        this.mCovTop = pView.findViewById(R.id.covTop);
        this.mCovBottom = pView.findViewById(R.id.covBottom);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mCameraPreviewView = new CameraPreviewView(this.getContext());
        this.mFrmCameraPreview.addView(this.mCameraPreviewView);

        this.mBtnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenCamera.this.buttonCaptureClick();
            }
        });

        pView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                ScreenCamera.this.cropCameraPreview();
                ScreenCamera.this.getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        this.mOrientationEventListener = new OrientationEventListener(this.getContext(), SensorManager.SENSOR_DELAY_UI) {
            public void onOrientationChanged(int pOrientation) {
                ScreenCamera.this.handleOrientation(pOrientation);
            }
        };
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
        this.mPresenter.saveImage(pData, this.getRotationDegrees());
        this.mBtnCapture.setEnabled(true);
    }

    private void buttonCaptureClick() {
        if(this.mCamera != null) {
            this.mBtnCapture.setEnabled(false);
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
        if(this.mCamera != null) {
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        }

        if(this.mCameraPreviewView != null) {
            this.mCameraPreviewView.destroyDrawingCache();
        }
    }

    private void handleOrientation(int pOrientation) {
        if(pOrientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
            return;
        }

        if(pOrientation > Constants.ORIENTATION__REVERSED_LANDSCAPE - Constants.ORIENTATION_THRESHOLD
                && pOrientation <= Constants.ORIENTATION__REVERSED_LANDSCAPE + Constants.ORIENTATION_THRESHOLD) {
            this.mOrientation = Constants.ORIENTATION__REVERSED_LANDSCAPE;
        } else if(pOrientation > Constants.ORIENTATION__REVERSED_PORTRAIT - Constants.ORIENTATION_THRESHOLD
                && pOrientation <= Constants.ORIENTATION__REVERSED_PORTRAIT + Constants.ORIENTATION_THRESHOLD) {
            this.mOrientation = Constants.ORIENTATION__REVERSED_PORTRAIT;
        } else if(pOrientation > Constants.ORIENTATION__LANDSCAPE - Constants.ORIENTATION_THRESHOLD
                && pOrientation <= Constants.ORIENTATION__LANDSCAPE + Constants.ORIENTATION_THRESHOLD) {
            this.mOrientation = Constants.ORIENTATION__LANDSCAPE;
        } else {
            this.mOrientation = Constants.ORIENTATION__PORTRAIT;
        }
    }

    private int getRotationDegrees() {
        int degrees = 0;
        switch(this.mOrientation) {
            case Constants.ORIENTATION__PORTRAIT:
                degrees = 90;
                break;
            case Constants.ORIENTATION__REVERSED_LANDSCAPE:
                degrees = 180;
                break;
            case Constants.ORIENTATION__REVERSED_PORTRAIT:
                degrees = 270;
                break;
            case Constants.ORIENTATION__LANDSCAPE:
                degrees = 0;
                break;
            default:
                break;
        }

        return  degrees;
    }

    private void cropCameraPreview() {
        int coverHeight = (this.getView().getHeight() - this.getView().getWidth()) / 2;

        ViewGroup.LayoutParams params = this.mCovTop.getLayoutParams();
        params.height = coverHeight;
        this.mCovTop.setLayoutParams(params);

        params = this.mCovBottom.getLayoutParams();
        params.height = coverHeight;
        this.mCovBottom.setLayoutParams(params);
    }

    private static class CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback {
        private static final double ASPECT_TOLERANCE = 0.1;

        private Context mContext;
        private Camera mCamera;
        private int mCameraId;

        public CameraPreviewView(Context pContext) {
            this(pContext, null, 0);
        }

        public CameraPreviewView(Context pContext, Camera pCamera, int pCameraId) {
            super(pContext);

            this.mContext = pContext;
            this.setCamera(pCamera, pCameraId);
            SurfaceHolder surfaceHolder = this.getHolder();
            surfaceHolder.addCallback(this);
            surfaceHolder.setKeepScreenOn(true);
        }

        @Override
        public void surfaceCreated(SurfaceHolder pSurfaceHolder) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder pSurfaceHolder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder pSurfaceHolder, int pFormat, int pWidth, int pHeight) {
            this.refreshCamera(pSurfaceHolder, pWidth, pHeight);
        }

        public void setCamera(Camera pCamera, int pCameraId) {
            if(pCamera != null) {
                this.mCamera = pCamera;
                this.mCameraId = pCameraId;
                this.requestLayout();
            }
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
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }

            return result;
        }

        private void refreshCamera(SurfaceHolder pSurfaceHolder, int pWidth, int pHeight) {
            if(pSurfaceHolder.getSurface() == null) {
                return;
            }

            try {
                Camera.Parameters parameters = this.mCamera.getParameters();

                Camera.Size previewSize = null;
                List<Camera.Size> supportedPreviewSizes = this.mCamera.getParameters().getSupportedPreviewSizes();
                if (supportedPreviewSizes != null) {
                    if(pWidth > pHeight) {
                        previewSize = this.getOptimalSize(supportedPreviewSizes, pWidth, pHeight);
                    } else {
                        previewSize = this.getOptimalSize(supportedPreviewSizes, pHeight, pWidth);
                    }
                }
                if(previewSize != null) {
                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                }

                Camera.Size pictureSize = null;
                List<Camera.Size> supportedPictureSizes = this.mCamera.getParameters().getSupportedPictureSizes();
                if (supportedPictureSizes != null) {
                    if(pWidth > pHeight) {
                        pictureSize = this.getOptimalSize(supportedPictureSizes, pWidth, pHeight);
                    } else {
                        pictureSize = this.getOptimalSize(supportedPictureSizes, pHeight, pWidth);
                    }
                }
                if(pictureSize != null) {
                    parameters.setPictureSize(pictureSize.width, pictureSize.height);
                }

                List<String> focusModes = parameters.getSupportedFocusModes();
                if(focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                } else if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                }
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

                int orientation = this.getCameraDisplayOrientation();
                parameters.setRotation(orientation);

                this.mCamera.setDisplayOrientation(orientation);
                this.mCamera.setParameters(parameters);
                this.mCamera.setPreviewDisplay(pSurfaceHolder);
                this.mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Camera.Size getOptimalSize(List<Camera.Size> pSizes, int pWidth, int pHeight) {
            if(pSizes == null) {
                return null;
            }

            Camera.Size optimalSize = null;
            double minDiff = Double.MAX_VALUE;
            double targetRatio = (double)pWidth / pHeight;
            int targetHeight = pHeight;

            for(Camera.Size size : pSizes) {
                double ratio = (double)size.width / size.height;
                if(Math.abs(ratio - targetRatio) > CameraPreviewView.ASPECT_TOLERANCE) {
                    continue;
                }

                int newMinDiff = Math.abs(size.height - targetHeight);
                if(newMinDiff < minDiff) {
                    optimalSize = size;
                    minDiff = newMinDiff;
                }
            }

            if(optimalSize == null) {
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
