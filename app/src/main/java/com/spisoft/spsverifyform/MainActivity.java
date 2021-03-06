package com.spisoft.spsverifyform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.spisoft.spsverify.VerifyPhoneNumber;

public class MainActivity extends AppCompatActivity {

    private VerifyPhoneNumber verifyPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyPhoneNumber = findViewById(R.id.verifyPhoneNumber);
        verifyPhoneNumber.setVerifyTime(100);
        verifyPhoneNumber.setPaddingLR(20);
        verifyPhoneNumber.SetOnSendPhoneNumberListener(new VerifyPhoneNumber.OnSendPhoneNumberListener() {
            @Override
            public void onEvent(String myNumber, String myCountyCode) {
                verifyPhoneNumber.postDelayed(new MRunnable(3), 4000);
            }
        });

        verifyPhoneNumber.SetOnRegisterListener(new VerifyPhoneNumber.OnRegisterListener() {
            @Override
            public void onEvent(String verifyCode, String myNumber, String myCC) {
                if(verifyCode.equals("111111")){
                    verifyPhoneNumber.postDelayed(new MRunnable(8), 2000);
                }else {
                    verifyPhoneNumber.SetMode(5, null);
                    verifyPhoneNumber.postDelayed(new MRunnable(6), 4000);
                }
            }
        });

        verifyPhoneNumber.SetOnRegisterCompletedListener(new VerifyPhoneNumber.OnRegisterCompletedListener() {
            @Override
            public void onEvent() {
                finish();
            }
        });
    }

    public class MRunnable implements Runnable {
        private int data;
        public MRunnable(int mode) {
            this.data = mode;
        }

        @Override
        public void run() {
            verifyPhoneNumber.SetMode(data, null);
        }
    }
}