package com.mbtechpro.ashwani;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText emailOrMobile_Et;
    private EditText password_ET;

    private TextView emailMobileError_TV;
    private TextView passwordError_TV;
    private TextView output_TV;

    private Button submit_Btn;

    private boolean isUserEnteredEmail;
    private boolean isUserEnteredMobile;

    final String MOBILE_COUNTRY_CODE = "+91";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EditText
        emailOrMobile_Et = findViewById(R.id.emailMobile_ET);
        password_ET = findViewById(R.id.password_ET);

        // Inline Error TextView
        emailMobileError_TV = findViewById(R.id.emailMobileError_TV);
        passwordError_TV = findViewById(R.id.passwordError_TV);

        output_TV = findViewById(R.id.output_TV);

        submit_Btn = findViewById(R.id.submit_Btn);

        // Adding Text Change Listener to detect Email or Mobile at runtime
        emailOrMobile_Et.addTextChangedListener(new EmailMobileTextChangedListener(MOBILE_COUNTRY_CODE, emailOrMobile_Et, password_ET, emailMobileError_TV));

        // DrawableRight action
        emailOrMobile_Et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(emailOrMobile_Et.getCompoundDrawables()[DRAWABLE_RIGHT] != null
                            && event.getRawX() >= (emailOrMobile_Et.getRight() - emailOrMobile_Et.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //Clear text
                        emailOrMobile_Et.setText("");
                        return false;
                    }
                }
                return false;
            }
        });


        submit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CommonUtil.noConnectionSnackBar(submit_Btn, (AppCompatActivity)MainActivity.this);

                output_TV.setText("");
                emailMobileError_TV.setText("");
                passwordError_TV.setText("");

                String emailOrMobilee = emailOrMobile_Et.getText().toString();
                String mobile = "";
                String email = "";
                String passwd = password_ET.getText().toString();
                String prefix = MOBILE_COUNTRY_CODE;
                boolean hasPrefix = StringUtils.startsWithIgnoreCase(emailOrMobilee, prefix);
                emailOrMobilee = StringUtils.trimAllWhitespace(emailOrMobilee);
                if(hasPrefix) {
                    emailOrMobilee = StringUtils.delete(emailOrMobilee, prefix);
                    // This removed extra space and set values after triming space
                    emailOrMobile_Et.setText(MOBILE_COUNTRY_CODE+" "+emailOrMobilee);
                    emailOrMobile_Et.setSelection(emailOrMobilee.length()+MOBILE_COUNTRY_CODE.length()+1);
                } else {
                    // This removed extra space and set values after triming space
                    emailOrMobile_Et.setText(emailOrMobilee);
                    emailOrMobile_Et.setSelection(emailOrMobilee.length());
                }

                final String emailOrMobile = emailOrMobilee;


                if (CommonUtil.isEmpty(emailOrMobile)) {
                    emailMobileError_TV.setVisibility(View.VISIBLE);
                    emailMobileError_TV.setText("Please enter your Mobile Number/Email Address");
                    return;
                }
                else if(hasPrefix && !CommonUtil.isValidMobile(emailOrMobile)) {
                    emailMobileError_TV.setVisibility(View.VISIBLE);
                    emailMobileError_TV.setText("Please enter valid Mobile Number");
                    return;
                } else if(!hasPrefix && !CommonUtil.isValidEmail(emailOrMobile)) {
                    emailMobileError_TV.setVisibility(View.VISIBLE);
                    emailMobileError_TV.setText("Please enter valid Email Address");
                    return;
                }


                if (CommonUtil.isEmpty(passwd)) {
                    passwordError_TV.setVisibility(View.VISIBLE);
                    passwordError_TV.setText("Please enter your Password");
                    return;
                }

                if (CommonUtil.isValidMobile(emailOrMobile)) {
                    isUserEnteredMobile = true;
                    isUserEnteredEmail = false;
                    mobile = emailOrMobile;
                }

                if (CommonUtil.isValidEmail(emailOrMobile)) {
                    isUserEnteredEmail = true;
                    isUserEnteredMobile = false;
                    email = emailOrMobile;
                }

                if (!isUserEnteredMobile && !isUserEnteredEmail) {
                    emailMobileError_TV.setVisibility(View.VISIBLE);
                    emailMobileError_TV.setText("Please enter your Mobile Number/Email Address");
                    return;
                }


                if(isUserEnteredEmail) {
                    output_TV.setText("Entered email is :: "+email );
                }
                else if(isUserEnteredMobile) {
                    output_TV.setText("Entered mobile is :: "+mobile );
                }


                ////

            }
        });

    }
}
