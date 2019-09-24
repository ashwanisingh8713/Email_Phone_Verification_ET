package com.mbtechpro.ashwani;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


public class EmailMobileTextChangedListener implements TextWatcher {

    private EditText mEmailOrMobile_Et;
    private EditText mPassword_Et;
    private String lastEnteredEmailOrMobile;
    private int cusrorPosition = 0;
    private TextView mTextViewError;
    private String mMobileCountryCode;
    private String defaultCountryCode = "+91";
    final private int mobileNumberLength = 10;

    private final String TAG = "Ashwani";

    public EmailMobileTextChangedListener(String mobileCountryCode, EditText emailOrMobile_Et, TextView textViewError) {
        this.mEmailOrMobile_Et = emailOrMobile_Et;
        this.mTextViewError = textViewError;
        this.mMobileCountryCode = mobileCountryCode;

        if(this.mMobileCountryCode == null) {
            this.mMobileCountryCode = defaultCountryCode;
        }
    }

    public EmailMobileTextChangedListener(String mobileCountryCode, EditText emailOrMobile_Et, EditText password_Et, TextView textViewError) {
        this.mEmailOrMobile_Et = emailOrMobile_Et;
        this.mPassword_Et = password_Et;
        this.mTextViewError = textViewError;
        this.mMobileCountryCode = mobileCountryCode;

        if(this.mMobileCountryCode == null) {
            this.mMobileCountryCode = defaultCountryCode;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            if(mTextViewError != null) {
                mTextViewError.setText("");
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mEmailOrMobile_Et.getText().toString().length() > 1) {
            mEmailOrMobile_Et.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_clear, 0);
        } else {
            mEmailOrMobile_Et.setCompoundDrawablesWithIntrinsicBounds(0,0,0, 0);
            if(mTextViewError != null) {
                mTextViewError.setText("");
            }
        }

        String wholeInput = mEmailOrMobile_Et.getText().toString();
        boolean containsWhitespace = StringUtils.containsWhitespace(wholeInput);

        wholeInput = StringUtils.trimAllWhitespace(wholeInput);
        String prefix = mMobileCountryCode;
        int prefixLen = prefix.length();


        int len = wholeInput.length();

        boolean hasPrefix = StringUtils.startsWithIgnoreCase(wholeInput, prefix);

        String finalText = wholeInput.replace(prefix, "");

        boolean digitsOnlyWithoutPrefix = TextUtils.isDigitsOnly(finalText);
        boolean digitsOnlyWholeInput = TextUtils.isDigitsOnly(wholeInput);

        if(hasPrefix && len == prefixLen) {
            mEmailOrMobile_Et.setText("");
            Log.i(TAG, "Law 2");
        }
        else if (digitsOnlyWholeInput  && !hasPrefix
                && ( finalText.startsWith("6")
                || finalText.startsWith("7")
                || finalText.startsWith("8")
                || finalText.startsWith("9")
                || finalText.startsWith("0")
                || finalText.startsWith("1")
                || finalText.startsWith("2")
                || finalText.startsWith("3")
                || finalText.startsWith("4")
                || finalText.startsWith("5"))
                && finalText.length() <= mobileNumberLength) {
            mEmailOrMobile_Et.getText().clear();
            mEmailOrMobile_Et.append(prefix + " "+wholeInput);
            lastEnteredEmailOrMobile = mEmailOrMobile_Et.getText().toString();
            Log.i(TAG, "Law 3");
        } else if(digitsOnlyWithoutPrefix && len == (mobileNumberLength+mMobileCountryCode.length()) && hasPrefix) {
            if(mPassword_Et != null) {
                mPassword_Et.requestFocus();
                CommonUtil.hideKeyboard(mEmailOrMobile_Et);
                Log.i(TAG, "Law 4");
            } else {
                CommonUtil.hideKeyboard(mEmailOrMobile_Et);
                Log.i(TAG, "Law 5");
            }
            mEmailOrMobile_Et.setFilters(new InputFilter[] { new InputFilter.LengthFilter((mobileNumberLength+mMobileCountryCode.length()+1)) });
        } else if(!digitsOnlyWithoutPrefix) {
            Log.i(TAG, "Law 6");
            lastEnteredEmailOrMobile = finalText;
            cusrorPosition = mEmailOrMobile_Et.getSelectionEnd();
            String etInput = mEmailOrMobile_Et.getText().toString();
            wholeInput = StringUtils.trimAllWhitespace(etInput);
            hasPrefix = StringUtils.startsWithIgnoreCase(etInput, prefix);

            if(hasPrefix) {
                Log.i(TAG, "Law 7 :: Input :: "+etInput);
                Log.i(TAG, "Law 7 :: finalText :: "+finalText);
                cusrorPosition = cusrorPosition-prefix.length()-1;
                mEmailOrMobile_Et.getText().clear();
                mEmailOrMobile_Et.append(finalText);
            }
            if(!containsWhitespace) {
                Log.i(TAG, "Law 8");
                mEmailOrMobile_Et.setSelection(cusrorPosition);
            }


            Log.i(TAG, "Law 8 #######################");

        }
        else {
            mEmailOrMobile_Et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(50) });
            Log.i(TAG, "Law 9");

            Log.i(TAG, "Law 9 #######################");
        }

    }
}
