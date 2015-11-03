package com.dreamdigitizers.drugmanagement.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

public class DialogUtils {
	public static final int MAX_INDETERMINATE = 100;
	private static TimePickerDialog timePickerDialog;
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

	public static void displayTimePickerDialog(final Activity pActivity, final String pCancelButtonText, final boolean pIs24HourView, final IOnTimePickerDialogEventListener pListener) {
		if(DialogUtils.timePickerDialog == null) {
			Calendar calendar = Calendar.getInstance();
			DialogUtils.timePickerDialog = new TimePickerDialog(pActivity,
					new TimePickerDialog.OnTimeSetListener() {
						@Override
						public void onTimeSet(TimePicker pView, int pHourOfDay, int pMinute) {
							if(pListener != null) {
								pListener.onTimeSet(pHourOfDay, pMinute, pActivity, pCancelButtonText, pIs24HourView);
							}
							DialogUtils.closeTimePickerDialog();
						}
					},
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE),
					pIs24HourView);
			DialogUtils.timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, pCancelButtonText, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface pDialog, int pWhich) {
					if (pListener != null) {
						pListener.onCancel(pActivity, pCancelButtonText, pIs24HourView);
					}
					DialogUtils.closeTimePickerDialog();
				}
			});
			DialogUtils.timePickerDialog.show();
		}
	}

	public static void closeTimePickerDialog() {
		if(DialogUtils.timePickerDialog != null) {
			DialogUtils.timePickerDialog.dismiss();
			DialogUtils.timePickerDialog = null;
		}
	}
	
	public static void displayProgressDialog(final Activity pActivity,
											 final int pStyle,
											 final String pTitle,
											 final String pMessage,
											 final boolean pIsCancelable,
											 final String pCancelButtonText,
											 final boolean pIsCanceledOnTouchOutside,
											 final boolean pIndeterminate,
											 final IOnProgressDialogCancelButtonClickListener pListener) {
		if(DialogUtils.progressDialog == null) {
			DialogUtils.progressDialog = new ProgressDialog(pActivity);
			DialogUtils.progressDialog.setProgressStyle(pStyle);
			DialogUtils.progressDialog.setTitle(pTitle);
			DialogUtils.progressDialog.setMessage(pMessage);
			DialogUtils.progressDialog.setCancelable(pIsCancelable);
			DialogUtils.progressDialog.setCanceledOnTouchOutside(pIsCanceledOnTouchOutside);
			DialogUtils.progressDialog.setIndeterminate(pIndeterminate);
			if(pIsCancelable && pListener != null) {
				DialogUtils.progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, pCancelButtonText, new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface pDialog, int pWhich) {
						if(pListener != null) {
							pListener.onCancelButtonClick(
									pActivity,
									pStyle,
									pTitle,
									pMessage,
									pIsCancelable,
									pCancelButtonText,
									pIsCanceledOnTouchOutside,
									pIndeterminate);
						}
						DialogUtils.closeProgressDialog();
				    }
				});
			}
			if(!pIndeterminate) {
				DialogUtils.progressDialog.setMax(DialogUtils.MAX_INDETERMINATE);
			}
			DialogUtils.progressDialog.show();
		}
	}
	
	public static void closeProgressDialog() {
		if(DialogUtils.progressDialog != null) {
			DialogUtils.progressDialog.dismiss();
			DialogUtils.progressDialog = null;
		}
	}

	public interface IOnDialogButtonClickListener {
		void onPositiveButtonClick(final Activity pActivity,
								   final String pTitle,
								   final String pMessage,
								   final boolean pIsTwoButton,
								   final String pPositiveButtonText,
								   final String pNegativeButtonText);

		void onNegativeButtonClick(final Activity pActivity,
								   final String pTitle,
								   final String pMessage,
								   final boolean pIsTwoButton,
								   final String pPositiveButtonText,
								   final String pNegativeButtonText);
	}

	public interface IOnTimePickerDialogEventListener {
		void onTimeSet(final int pHourOfDay, final int pMinute, final Activity pActivity, final String pCancelButtonText, final boolean pIs24HourView);
		void onCancel(final Activity pActivity, final String pCancelButtonText, final boolean pIs24HourView);
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
