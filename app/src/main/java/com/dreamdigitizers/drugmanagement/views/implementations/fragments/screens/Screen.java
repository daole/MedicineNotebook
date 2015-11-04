package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.utils.DialogUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IView;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.MyFragment;

public abstract class Screen extends MyFragment implements IView {
	protected static final String BUNDLE_KEY__ROW_ID = "row_id";

	private static final String ERROR_MESSAGE__CONTEXT_NOT_IMPLEMENTS_INTERFACE = "Activity must implement IOnScreenActionsListener.";

	protected IOnScreenActionsListener mScreenActionsListener;

	@Override
	public void onAttach(Context pContext) {
		super.onAttach(pContext);

		try {
			this.mScreenActionsListener = (IOnScreenActionsListener)pContext;
		} catch (ClassCastException e) {
			throw new ClassCastException(Screen.ERROR_MESSAGE__CONTEXT_NOT_IMPLEMENTS_INTERFACE);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		this.mScreenActionsListener.onSetCurrentScreen(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();

		InputMethodManager imm = (InputMethodManager)this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(this.getView().getWindowToken(), 0);
	}

	@Override
	public void onDetach() {
		super.onDetach();

		this.mScreenActionsListener = null;
	}

	@Override
	public Context getViewContext() {
		return this.getContext();
	}

	@Override
	public void showMessage(int pStringResourceId) {
		Toast.makeText(this.getActivity(), pStringResourceId, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showConfirmation(int pStringResourceId, DialogUtils.IOnDialogButtonClickListener pListener) {
		String title = this.getString(R.string.title__error_dialog);
		String positiveButtonText = this.getString(R.string.btn__ok);
		String negativeButtonText = this.getString(R.string.btn__no);
		String message = this.getString(pStringResourceId);
		DialogUtils.displayDialog(this.getActivity(), title, message, true, positiveButtonText, negativeButtonText, pListener);
	}

	@Override
	public void showError(int pStringResourceId) {
		String title = this.getString(R.string.title__error_dialog);
		String buttonText = this.getString(R.string.btn__ok);
		String message = this.getString(pStringResourceId);
		DialogUtils.displayErrorDialog(this.getActivity(), title, message, buttonText);
	}
	
	protected void addChildToViewGroup(ViewGroup pParent, View pChild, int pPosition) {
		if(pPosition >= 0) {
			pParent.addView(pChild, pPosition);
		} else {
			pParent.addView(pChild);
		}
	}
	
	protected void replaceViewInViewGroup(ViewGroup pParent, View pChild, int pPosition) {
		if(pPosition < 0) {
			pPosition = 0;
		}
		
		int childCount = pParent.getChildCount();
		if(pPosition >= childCount) {
			pPosition = childCount - 1;
		}
		
		pParent.removeViewAt(pPosition);
		pParent.addView(pChild, pPosition);
	}

	public interface IOnScreenActionsListener {
		void onSetCurrentScreen(Screen pCurrentScreen);
		void onChangeScreen(Screen pScreen);
		void onBack();
	}
}
