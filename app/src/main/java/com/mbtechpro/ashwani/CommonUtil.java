package com.mbtechpro.ashwani;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ashwanisingh.
 */

public class CommonUtil {


    /**
     * Hides the keyboard displayed for the SearchEditText.
     * @param view The view to detach the keyboard from.
     */
    public static void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }


    public static void noConnectionSnackBar(View view, final AppCompatActivity context) {
        if(view == null) {
            return;
        }
        Snackbar mSnackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout mSnackbarView = (Snackbar.SnackbarLayout) mSnackbar.getView();
        mSnackbarView.setBackgroundColor(Color.BLACK);
        View snackView = context.getLayoutInflater().inflate(R.layout.thp_noconnection_snackbar, null);
        snackView.findViewById(R.id.action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(intent);
            }
        });
        mSnackbarView.addView(snackView);
        mSnackbar.show();
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
                .append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }


    // Validation Email
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Validation Name
    public static boolean isValidName(String name) {
        // TODO Auto-generated method stub
        boolean valid_firstname;
        if (name == null) {
            // edt.setError("Accept Alphabets Only.");
            valid_firstname = false;
        } else valid_firstname = name.matches("[a-zA-Z ]+");
        return valid_firstname;
    }

    public static boolean isEmpty(String string) {
        return string == null || TextUtils.isEmpty(string.trim());
    }

    public static boolean isValidMobile(String mobile) {
        String MOBILE_PATTERN = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";
        Pattern pattern = Pattern.compile(MOBILE_PATTERN);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static String getDeviceId(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.i("Dev_Ashwani", ""+android_id);

        return android_id;
    }





}
