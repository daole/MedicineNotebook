package com.dreamdigitizers.drugmanagement.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

public class DialogUtils {
	public static final int MAX_INDETERMINATE = 100;
	private static ProgressDialog progressDialog;
	
	public static void displayDialog(final Activity pActivity,
									 final String pTitle,
									 final String pMessage,
									 final boolean pIsTwoButton,
									 final String pPositiveButtonText,
									 final String pNegativeButtonText,
									 final IOnDialogButtonClickListener pListener) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(pActivity);
		alertDialogBuilder.setTitle(pTitle);
		alertDialogBuilder.setMessage(pMessage);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton(pPositiveButtonText, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface pDialog, int pWhich) {
				if (pListener != null) {
					pListener.onPositiveButtonClick(
							pActivity,
							pTitle,
							pMessage,
							pIsTwoButton,
							pPositiveButtonText,
							pNegativeButtonText);
				}
			}
		});
		if (pIsTwoButton) {
			alertDialogBuilder.setNegativeButton(pNegativeButtonText, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface pDialog, int pWhich) {
				if (pListener != null) {
					pListener.onNegativeButtonClick(
							pActivity,
							pTitle,
							pMessage,
							pIsTwoButton,
							pPositiveButtonText,
							pNegativeButtonText);
				}
				}
			});
		}
		final AlertDialog alertDialog = alertDialogBuilder.create();
		pActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				alertDialog.show();
			}
		});
	}
	
	public static void displayErrorDialog(final Activity pActivity,
										  final String pTitle,
										  final String pMessage,
										  final String pButtonText) {
		IOnDialogButtonClickListener dialogButtonClickListener = new IOnDialogButtonClickListener() {
			@Override
			public void onPositiveButtonClick(
					Activity pActivity,
					String pTitle,
					String pMessage,
					boolean pIsTwoButton,
					String pPositiveButtonText,
					String pNegativeButtonText) {
				
			}

			@Override
			public void onNegativeButtonClick(
					Activity pActivity,
					String pTitle,
					String pMessage,
					boolean pIsTwoButton,
					String pPositiveButtonText,
					String pNegativeButtonText) {
				
			}
		};
		DialogUtils.displayDialog(pActivity, pTitle, pMessage, false, pButtonText, null, dialogButtonClickListener);
	}
	
	public static void showProgressDialog(final Activity pActivity,
										  final int pStyle,
										  final String pTitle,
										  final String pMessage,
										  final boolean pIsCancelable,
										  final String pCancelButtonText,
										  final boolean pIsCanceledOnTouchOutside,
										  final boolean pIndeterminate,
										  final IOnProgressDialogCancelButtonClickListener pListener) {
		if(progressDialog == null) {
			progressDialog = new ProgressDialog(pActivity);
			progressDialog.setProgressStyle(pStyle);
			progressDialog.setTitle(pTitle);
			progressDialog.setMessage(pMessage);
			progressDialog.setCancelable(pIsCancelable);
			progressDialog.setCanceledOnTouchOutside(pIsCanceledOnTouchOutside);
			progressDialog.setIndeterminate(pIndeterminate);
			if(pIsCancelable && pListener != null) {
				progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, pCancelButtonText, new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface pDialog, int pWhich) {
						pListener.onCancelButtonClick(
								pActivity,
								pStyle,
								pTitle,
								pMessage,
								pIsCancelable,
								pCancelButtonText,
								pIsCanceledOnTouchOutside,
								pIndeterminate);
						DialogUtils.closeProgressDialog();
				    }
				});
			}
			if(!pIndeterminate) {
				progressDialog.setMax(DialogUtils.MAX_INDETERMINATE);
			}
			progressDialog.show();
		}
	}
	
	public static void closeProgressDialog() {
		if(DialogUtils.progressDialog != null) {
			DialogUtils.progressDialog.dismiss();
			DialogUtils.progressDialog = null;
		}
	}

	public interface IOnDialogButtonClickListener {
		void onPositiveButtonClick(Activity pActivity,
								   String pTitle,
								   String pMessage,
								   boolean pIsTwoButton,
								   String pPositiveButtonText,
								   String pNegativeButtonText);

		void onNegativeButtonClick(Activity pActivity,
								   String pTitle,
								   String pMessage,
								   boolean pIsTwoButton,
								   String pPositiveButtonText,
								   String pNegativeButtonText);
	}

	public interface IOnProgressDialogCancelButtonClickListener{
		void onCancelButtonClick(final Activity pActivity,
								 final int pStyle,
								 final String pTitle,
								 final String pMessage,
								 final boolean pIsCancelable,
								 final String pCancelButtonText,
								 final boolean pIsCanceledOnTouchOutside,
								 final boolean pIndeterminate);
	}
}
