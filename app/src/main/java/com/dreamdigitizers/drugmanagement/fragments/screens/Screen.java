package com.dreamdigitizers.drugmanagement.fragments.screens;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.dreamdigitizers.drugmanagement.activities.MyActivity;

public abstract class Screen extends Fragment {
	protected float mDensity;
	
	@Override
	public void onAttach(Context pContext) {
		super.onAttach(pContext);
		DisplayMetrics metrics = pContext.getResources().getDisplayMetrics();
		this.mDensity = metrics.density;
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
	
	protected abstract View loadView(LayoutInflater pInflater, ViewGroup pContainer);
	protected abstract void retrieveScreenItems(View pView);
	protected abstract void recoverInstanceState(Bundle pSavedInstanceState);
	protected abstract void mapInformationToScreenItems();
}
