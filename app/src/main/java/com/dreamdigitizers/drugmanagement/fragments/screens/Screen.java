package com.dreamdigitizers.drugmanagement.fragments.screens;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.dreamdigitizers.drugmanagement.activities.MyActivity;

public abstract class Screen extends Fragment {
	private static final String ERROR_MESSAGE__CONTEXT_NOT_IMPLEMENTS_INTERFACE = "Activity must implement IScreenActionsListener.";

	protected IScreenActionsListener mIScreenActionsListener;

	@Override
	public void onAttach(Context pContext) {
		super.onAttach(pContext);
		try {
			this.mIScreenActionsListener = (IScreenActionsListener)pContext;
			this.mIScreenActionsListener.onSetCurrentScreen(this);
		} catch (ClassCastException e) {
			throw new ClassCastException(Screen.ERROR_MESSAGE__CONTEXT_NOT_IMPLEMENTS_INTERFACE);
		}
	}
	
	@Override
    public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer, Bundle pSavedInstanceState) {
		View view = this.loadView(pInflater, pContainer);
		this.retrieveScreenItems(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle pSavedInstanceState) {
		super.onActivityCreated(pSavedInstanceState);
		if(pSavedInstanceState != null) {
			this.recoverInstanceState(pSavedInstanceState);
		}
		this.mapInformationToScreenItems();
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
		this.mIScreenActionsListener = null;
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

	public boolean onBackPressed() {
		return false;
	}
	
	protected abstract View loadView(LayoutInflater pInflater, ViewGroup pContainer);
	protected abstract void retrieveScreenItems(View pView);
	protected abstract void recoverInstanceState(Bundle pSavedInstanceState);
	protected abstract void mapInformationToScreenItems();

	public interface IScreenActionsListener {
		void onSetCurrentScreen(Screen pCurrentScreen);
		void onBackAction();
	}
}
