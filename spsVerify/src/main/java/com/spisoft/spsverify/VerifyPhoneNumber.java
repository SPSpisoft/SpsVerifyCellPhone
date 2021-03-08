package com.spisoft.spsverify;

import androidx.annotation.RequiresApi;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.spisoft.spcircleview.CircleView;
import com.spisoft.sprogressbutton.CircularProgressButton;
import com.spisoft.verifyedittextlibrary.VerifyEditText;
import com.spisoft.widget.countrycodepicker.CountryCodeDialog;
import com.spisoft.widget.countrycodepicker.CountryCodePicker;
import com.spisoft.widget.countrycodepicker.CountryUtils;
import com.timqi.sectorprogressview.ColorfulRingProgressView;
import com.timqi.sectorprogressview.SectorProgressView;

public class VerifyPhoneNumber extends RelativeLayout {

    private Context mContext;
    private View rootView;
    private MaterialCardView vLayBackground;
    private EditText vTxtPhoneNumber;
    private TextView vTxtCountryCode, vTxtCountry;
    private CircularProgressButton vButtonSendMobile;
    private MaterialCardView vIvCountryFlag, vBoxText;
    private ImageView vIvCountryFlag_;
    private VerifyEditText vVerifyText;
    private String CodeCostText = "";
    private View ViewBase;
    private RelativeLayout vlySetNumber, vlyVerify, vBtnBack, vBtnSendCode;
    private int CurrentMode = 0;
    private SectorProgressView vProgressExp;
    private ColorfulRingProgressView vProgressSendCode;
    private CountDownTimer countDownTimer = null;
    private ImageView vIconSendCode;
    private CircleView vCvSendCode;
    private int mVerifyTime;
    private TextView vTxtDescription;
    private String mTextMode_0, mTextMode_1, mTextMode_2, mTextMode_3, mTextMode_4, mTextMode_5, mTextMode_6, mTextMode_7, mTextMode_8, mTextMode_9;
    private OnSendPhoneNumberListener mSendNumberClickListener;
    private OnRegisterListener mOnRegisterClickListener;
    private boolean mCheckCellphone = true;
    private int mVerifyCodeLength;
    private OnRegisterCompletedListener mOnRegisterCompletedListener;
    private boolean countDownTimer_finish = true;

    public VerifyPhoneNumber(Context context) {
        super(context);
        init(context, null);
    }

    public VerifyPhoneNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VerifyPhoneNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VerifyPhoneNumber(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(final Context context, AttributeSet attrs) {

        mContext = context;
        rootView = inflate(context, R.layout.view_verify_cell_phone, this);

        ViewBase = rootView.findViewById(R.id.viewBase);
        vLayBackground = rootView.findViewById(R.id.layBack);
        vBtnBack = rootView.findViewById(R.id.btnBack);
        vBtnSendCode = rootView.findViewById(R.id.btnSendCode);
        vTxtPhoneNumber = rootView.findViewById(R.id.txtPhoneNumber);
        vTxtCountryCode = rootView.findViewById(R.id.tvCountryCode);
        vTxtCountry = rootView.findViewById(R.id.tvCountry);
        vButtonSendMobile = rootView.findViewById(R.id.circularButtonSend);
        vIvCountryFlag = rootView.findViewById(R.id.ivCountryFlag);
        vIvCountryFlag_ = rootView.findViewById(R.id.ivCountryFlag_);
        vVerifyText = rootView.findViewById(R.id.verifyText);
        vlySetNumber = rootView.findViewById(R.id.lySetNumber);
        vlyVerify = rootView.findViewById(R.id.lyVerify);
        vProgressExp = rootView.findViewById(R.id.cProgressExp);
        vProgressSendCode = rootView.findViewById(R.id.cProgressSendCode);
        vIconSendCode = rootView.findViewById(R.id.iconSendCode);
        vCvSendCode = rootView.findViewById(R.id.cvSendCode);
        vBoxText = rootView.findViewById(R.id.boxText);
        vTxtDescription = rootView.findViewById(R.id.txtDescription);

        vButtonSendMobile.setIndeterminateProgressMode(true);

        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerifyPhoneNumber, 0, 0);

            int atHeight = (int) typedArray.getDimension(R.styleable.VerifyPhoneNumber_SizeHeight, 140);
            int atWidth = (int) typedArray.getDimension(R.styleable.VerifyPhoneNumber_SizeWidth, 0);
            int atBtnWidth = (int) typedArray.getDimension(R.styleable.VerifyPhoneNumber_SizeBtnWidth, atHeight);
            RelativeLayout.LayoutParams paramsMain = (RelativeLayout.LayoutParams) ViewBase.getLayoutParams();
            paramsMain.height = atHeight;
            if(atWidth > 0) paramsMain.width = atWidth;
            ViewBase.setLayoutParams(paramsMain);

            RelativeLayout.LayoutParams paramsFlag = (RelativeLayout.LayoutParams) vIvCountryFlag.getLayoutParams();
            paramsFlag.width = atBtnWidth;
            vIvCountryFlag.setLayoutParams(paramsFlag);

            RelativeLayout.LayoutParams paramsBtnSend = (RelativeLayout.LayoutParams) vButtonSendMobile.getLayoutParams();
            paramsBtnSend.width = atBtnWidth;
            vButtonSendMobile.setLayoutParams(paramsBtnSend);

            RelativeLayout.LayoutParams paramsBtnSendCode = (RelativeLayout.LayoutParams) vBtnSendCode.getLayoutParams();
            paramsBtnSendCode.width = atHeight - 40;
            paramsBtnSendCode.height = atHeight - 40;
            vBtnSendCode.setLayoutParams(paramsBtnSendCode);

            RelativeLayout.LayoutParams paramsBtnBack = (RelativeLayout.LayoutParams) vBtnBack.getLayoutParams();
            paramsBtnBack.width = atHeight - 40;
            paramsBtnBack.height = atHeight - 40;
            vBtnBack.setLayoutParams(paramsBtnBack);

            float mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.VerifyPhoneNumber_CornerRadius, (atHeight-40)/2);
            vLayBackground.setRadius(mCornerRadius);
            vIvCountryFlag.setRadius(mCornerRadius);
            vButtonSendMobile.setRadius(mCornerRadius);
//            vBtnSendCode.setRadius(mCornerRadius);
//            vBtnBack.setRadius(mCornerRadius);

            int mTextPhoneSize = typedArray.getDimensionPixelSize(R.styleable.VerifyPhoneNumber_TextPhoneSize, 0);
            if(mTextPhoneSize == 0) mTextPhoneSize = atHeight/3;
            vTxtPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextPhoneSize);
            vTxtCountryCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextPhoneSize);
            vTxtCountry.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextPhoneSize/2);
            vTxtPhoneNumber.setHintTextColor(getResources().getColor(R.color.sps_grey_light));

            float mTextVerifySize = typedArray.getDimensionPixelSize(R.styleable.VerifyPhoneNumber_TextVerifySize, 0);
            if(mTextVerifySize == 0) mTextVerifySize = atHeight * 15;
            vVerifyText.setTextSize(mTextVerifySize);
            vVerifyText.setInputCount(6);

            int mViewMargin = (int) typedArray.getDimension(R.styleable.VerifyPhoneNumber_android_layout_margin, 20);
            LayoutParams paramsLayBackground = (LayoutParams) vLayBackground.getLayoutParams();
            paramsLayBackground.setMargins(mViewMargin,mViewMargin,mViewMargin,mViewMargin);
            vLayBackground.setLayoutParams(paramsLayBackground);

            mCheckCellphone = typedArray.getBoolean(R.styleable.VerifyPhoneNumber_CheckCellphone, true);
            mVerifyCodeLength= typedArray.getInt(R.styleable.VerifyPhoneNumber_VerifyCodeLength, 6);
            vVerifyText.setInputCount(mVerifyCodeLength);

            String mTitle = typedArray.getString(R.styleable.VerifyPhoneNumber_TextBtnSend);
            if(mTitle == null) mTitle = mContext.getString(R.string.BtnSend);
            vButtonSendMobile.setText(mTitle);

            mTextMode_0 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_0);
            if(mTextMode_0 == null) mTextMode_0 = mContext.getResources().getString(R.string.text_mode_0);

//            mTextMode_0_0 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_0_0);
//            if(mTextMode_0_0 == null) mTextMode_0_0 = mContext.getResources().getString(R.string.text_mode_0_0);

            mTextMode_1 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_1);
            if(mTextMode_1 == null) mTextMode_1 = mContext.getResources().getString(R.string.text_mode_1);

            mTextMode_2 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_2);
            if(mTextMode_2 == null) mTextMode_2 = mContext.getResources().getString(R.string.text_mode_2);

            mTextMode_3 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_3);
            if(mTextMode_3 == null) mTextMode_3 = mContext.getResources().getString(R.string.text_mode_3);

            mTextMode_4 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_4);
            if(mTextMode_4 == null) mTextMode_4 = mContext.getResources().getString(R.string.text_mode_4);

            mTextMode_5 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_5);
            if(mTextMode_5 == null) mTextMode_5 = mContext.getResources().getString(R.string.text_mode_5);

            mTextMode_6 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_6);
            if(mTextMode_6 == null) mTextMode_6 = mContext.getResources().getString(R.string.text_mode_6);

            mTextMode_7 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_7);
            if(mTextMode_7 == null) mTextMode_7 = mContext.getResources().getString(R.string.text_mode_7);

            mTextMode_8 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_8);
            if(mTextMode_8 == null) mTextMode_8 = mContext.getResources().getString(R.string.text_mode_8);

            mTextMode_9 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_9);
            if(mTextMode_9 == null) mTextMode_9 = mContext.getResources().getString(R.string.text_mode_9);

            final CountryCodePicker countryCodePicker = new CountryCodePicker(mContext);
            final CountryCodeDialog countryCodeDialog = new CountryCodeDialog(countryCodePicker);
            String locale = context.getResources().getConfiguration().locale.getCountry();
            countryCodePicker.setDefaultCountryUsingNameCodeAndApply(locale);
            vIvCountryFlag_.setImageResource(CountryUtils.getFlagDrawableResId(countryCodePicker.getmSelectedCountry()));
            vTxtCountryCode.setText(countryCodePicker.getSelectedCountryCodeWithPlus());
            vTxtCountry.setText(countryCodePicker.getSelectedCountryNameCode());
            CodeCostText = countryCodePicker.getSelectedCountryCodeWithPlus();
            countryCodeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    vIvCountryFlag_.setImageResource(CountryUtils.getFlagDrawableResId(countryCodePicker.getmSelectedCountry()));
                    vTxtCountryCode.setText(countryCodePicker.getSelectedCountryCodeWithPlus());
                    vTxtCountry.setText(countryCodePicker.getSelectedCountryNameCode());
                    CodeCostText = countryCodePicker.getSelectedCountryCodeWithPlus();
                    vTxtPhoneNumber.setHint("");
//                    vTxtPhoneNumber.setMaxLines(CodeCostText.length() + 11);
//                    Toast.makeText(mContext, countryCodePicker.getSelectedCountryCode(), Toast.LENGTH_SHORT).show();
                }
            });

            vIvCountryFlag_.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(CurrentMode == 0)
                        countryCodeDialog.show();
                }
            });

            vBtnSendCode.setClickable(true);
            vBtnSendCode.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(vVerifyText.getContent().trim().length() == mVerifyCodeLength) {
                        if (mOnRegisterClickListener != null)
                            mOnRegisterClickListener.onEvent(vVerifyText.getContent(), vTxtPhoneNumber.getText().toString(), vTxtCountryCode.getText().toString());
                    }else
                        SetMode(6);
                }
            });

            vBtnBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(CurrentMode == 4 || CurrentMode == 5 || CurrentMode == 6 || CurrentMode == 7){
                        Snackbar.make(vBtnBack, "[BACK] to edit validation number..", Snackbar.LENGTH_LONG)
                                .setAction("BACK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        SetMode(0);
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
                    }
                    else
                        SetMode(0);
                }
            });

        }

        vButtonSendMobile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckMobileNumber(vTxtPhoneNumber.getText().toString())) {
                    SetMode(1);
                    if(mSendNumberClickListener != null)
                        mSendNumberClickListener.onEvent(vTxtPhoneNumber.getText().toString(), vTxtCountryCode.getText().toString());
//                    postDelayed(new MyRunnable(4), 3000);
                }else {
                    SetMode(2);
//                    postDelayed(new MyRunnable(2), 3000);
                }
            }
        });

//        SetMode(0);
        vTxtDescription.setText(mTextMode_0);
    }

    private boolean CheckMobileNumber(String cellPhoneNumber) {
        if(!mCheckCellphone)
            return true;
        if(cellPhoneNumber.length() >= 10 && ((cellPhoneNumber.length() == 10 && cellPhoneNumber.substring(0,1).equals("9")) ||
                (cellPhoneNumber.length() == 11 && cellPhoneNumber.substring(0,2).equals("09"))))
            return true;
        return false;
    }

    public class MyRunnable implements Runnable {
        private int data;
        public MyRunnable(int mode) {
            this.data = mode;
        }

        @Override
        public void run() {
            SetMode(data);
        }
    }

    public void SetMode(int mode) {
        CurrentMode = mode;

        vTxtPhoneNumber.setEnabled(false);
        vVerifyText.SetEnableEditText(false);
        vButtonSendMobile.setProgress(0);
        vProgressSendCode.stopAnimateIndeterminate();
        vProgressSendCode.setStartAngle(0);

        YoYo.with(Techniques.BounceInUp)
                .duration(200)
                .repeat(0)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        YoYo.with(Techniques.Pulse)
                                .duration(1000)
                                .repeat(0)
                                .onEnd(new YoYo.AnimatorCallback() {
                                    @Override
                                    public void call(Animator animator) {
                                    }
                                }).playOn(vBoxText);
                    }
                }).playOn(vBoxText);

        switch (mode){
            case 0: //SetPhoneNumber
                vlySetNumber.setVisibility(VISIBLE);
                vlyVerify.setVisibility(GONE);
                vTxtPhoneNumber.setEnabled(true);
                vButtonSendMobile.setProgress(0);
                vTxtDescription.setText(mTextMode_0);
                break;
            case 1: //SendingPhoneNumber
                vlySetNumber.setVisibility(VISIBLE);
                vlyVerify.setVisibility(GONE);
                vButtonSendMobile.setProgress(50);
                vTxtDescription.setText(mTextMode_1);
                break;
            case 2: //FailPhoneNumber
                vTxtPhoneNumber.setEnabled(true);
                vTxtDescription.setText(mTextMode_2);
                vlySetNumber.setVisibility(VISIBLE);
                vlyVerify.setVisibility(GONE);
                vButtonSendMobile.setProgress(-1);
                postDelayed(new MyRunnable(0), 4000);
                break;
            case 3: //AcceptPhoneNumber
                vTxtDescription.setText(mTextMode_3);
                vlySetNumber.setVisibility(VISIBLE);
                vlyVerify.setVisibility(GONE);
                vButtonSendMobile.setProgress(100);
                postDelayed(new MyRunnable(4), 2000);
                break;
            case 4: //SetVerifyCode
                vTxtDescription.setText(mTextMode_4);
                vVerifyText.resetContent();
                vProgressExp.setPercent(0);
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);
                vVerifyText.SetEnableEditText(true);

                vProgressExp.stopAnimateIndeterminate();
                if(countDownTimer != null) countDownTimer.cancel();
                if(mVerifyTime == 0) mVerifyTime = 120;
                int FullTime = mVerifyTime * 1000;
                countDownTimer = new CountDownTimer(FullTime, 500) {

                    public void onTick(long millisUntilFinished) {
                        vProgressExp.setPercent((FullTime - millisUntilFinished) / 1000);
                    }

                    public void onFinish() {
                        countDownTimer_finish = true;
                        SetMode(6);
                        postDelayed(new MyRunnable(0), 3000);
                    }

                }.start();
                countDownTimer_finish = false;
                break;
            case 5: //SendingVerifyCode
                vTxtDescription.setText(mTextMode_5);
                vVerifyText.SetEnableEditText(false);
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);
                vProgressSendCode.animateIndeterminate();
                break;
            case 6: //FailVerifyCode
                vTxtDescription.setText(mTextMode_6);
                vIconSendCode.setImageResource(R.drawable.ic_baseline_thumb_down_alt_24);
                vCvSendCode.setBackgroundColor(Color.RED);
                vVerifyText.SetEnableEditText(true);
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);
                if(!countDownTimer_finish)
                    postDelayed(new MyRunnable(7), 3000);
                break;
            case 7: //SendingVerifyCode
                vTxtDescription.setText(mTextMode_7);
                vIconSendCode.setImageResource(R.drawable.ic_baseline_verified_user_24);
                vCvSendCode.setBackgroundColor(Color.GREEN);
                vVerifyText.SetEnableEditText(true);
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);
                break;
            case 8: //AccessVerifyCode
                vTxtDescription.setText(mTextMode_8);
                vCvSendCode.setBackgroundColor(Color.BLUE);
                vIconSendCode.setImageResource(R.drawable.ic_baseline_how_to_reg_24);
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);

                postDelayed(setCompleted, 2000);
                break;
            case 9: //ExpireVerifyCode
                vTxtDescription.setText(mTextMode_9);
                vIconSendCode.setImageResource(R.drawable.ic_baseline_thumb_down_alt_24);
                vCvSendCode.setBackgroundColor(Color.RED);
                vVerifyText.SetEnableEditText(false);
                vVerifyText.resetContent();
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);
                postDelayed(new MyRunnable(0), 2000);
                break;
        }
    }

    private final Runnable setCompleted = new Runnable() {
        @Override
        public void run() {
            if(mOnRegisterCompletedListener != null) mOnRegisterCompletedListener.onEvent();
        }
    };

    public VerifyPhoneNumber setVerifyTime(int verifyTimeSecond){
        this.mVerifyTime = verifyTimeSecond;
        return this;
    }

    public interface OnSendPhoneNumberListener {
        void onEvent(String myNumber, String myCountyCode);
    }

    public void SetOnSendPhoneNumberListener(OnSendPhoneNumberListener eventListener) {
        mSendNumberClickListener = eventListener;
    }

    public interface OnRegisterListener {
        void onEvent(String verifyCode, String myNumber, String myCountyCode);
    }

    public void SetOnRegisterListener(OnRegisterListener eventListener) {
        mOnRegisterClickListener = eventListener;
    }

    public interface OnRegisterCompletedListener {
        void onEvent();
    }

    public void SetOnRegisterCompletedListener(OnRegisterCompletedListener eventListener) {
        mOnRegisterCompletedListener = eventListener;
    }
}