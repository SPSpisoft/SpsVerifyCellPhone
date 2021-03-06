package com.spisoft.spsverify;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
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

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

import static androidx.core.content.ContextCompat.getSystemService;

public class VerifyPhoneNumber extends RelativeLayout {

    private Context mContext;
    private View rootView;
    private MaterialCardView vLayBackground;
    private EditText vTxtPhoneNumber;
    private TextView vTxtCountryCode, vTxtCountry;
    private CircularProgressButton vButtonSendMobile;
    private MaterialCardView vIvCountryFlag, vBoxText;
    private ShapedImageView vIvCountryFlag_;
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
    private String mTextMode_0, mTextMode_1, mTextMode_2, mTextMode_3, mTextMode_4, mTextMode_5, mTextMode_6, mTextMode_7, mTextMode_8, mTextMode_9, mTextMode_10;
    private OnSendPhoneNumberListener mSendNumberClickListener;
    private OnRegisterListener mOnRegisterClickListener;
    private boolean mCheckCellphone = true;
    private int mVerifyCodeLength;
    private OnRegisterCompletedListener mOnRegisterCompletedListener;
    private boolean countDownTimer_finish = true;
    private Vibrator vibrator;
    private ImageView vIcon;
    private OnChangePhoneNumberListener mOnChangePhoneNumberListener;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
        vIcon = rootView.findViewById(R.id.vIcon);

        vButtonSendMobile.setIndeterminateProgressMode(true);

        vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerifyPhoneNumber, 0, 0);

            int atHeight = (int) typedArray.getDimension(R.styleable.VerifyPhoneNumber_SizeHeight, 140);
            int atWidth = (int) typedArray.getDimension(R.styleable.VerifyPhoneNumber_SizeWidth, 0);
            int atBtnWidth = (int) typedArray.getDimension(R.styleable.VerifyPhoneNumber_SizeBtnWidth, atHeight*5/6);
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
            vIvCountryFlag_.setShapeRadius(mCornerRadius);
            vButtonSendMobile.setRadius(mCornerRadius);
//            vBtnSendCode.setRadius(mCornerRadius);
//            vBtnBack.setRadius(mCornerRadius);

//            vLayBackground.setBackgroundColor(typedArray.getColor(R.styleable.VerifyPhoneNumber_BackColor, Color.WHITE));
//            vLayBackground.setCardBackgroundColor( Color.WHITE);

            int mTextPhoneSize = typedArray.getDimensionPixelSize(R.styleable.VerifyPhoneNumber_TextPhoneSize, 0);
            if(mTextPhoneSize == 0) mTextPhoneSize = atHeight/3;
            vTxtPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextPhoneSize);
            vTxtCountryCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextPhoneSize);
            vTxtCountry.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextPhoneSize/2);
            vTxtPhoneNumber.setHintTextColor(getResources().getColor(R.color.sps_grey_light));

            vTxtPhoneNumber.setTextColor(getResources().getColor(R.color.sps_grey_dark));
            vTxtPhoneNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(mOnChangePhoneNumberListener != null)
                        mOnChangePhoneNumberListener.onEvent(vTxtCountryCode.getText().toString(), vTxtPhoneNumber.getText().toString(), count);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

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

            mTextMode_10 = typedArray.getString(R.styleable.VerifyPhoneNumber_TextMode_10);
            if(mTextMode_10 == null) mTextMode_10 = mContext.getResources().getString(R.string.text_mode_10);

            final CountryCodePicker countryCodePicker = new CountryCodePicker(mContext);
            final CountryCodeDialog countryCodeDialog = new CountryCodeDialog(countryCodePicker);
            String locale = context.getResources().getConfiguration().locale.getCountry();
            countryCodePicker.setDefaultCountryUsingNameCodeAndApply(locale);
            vIvCountryFlag_.setImageResource(CountryUtils.getFlagDrawableResId(countryCodePicker.getmSelectedCountry()));
//            vIvCountryFlag_.setBackgroundColor(typedArray.getColor(R.styleable.VerifyPhoneNumber_flagBackColor, Color.BLUE));
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

            vIvCountryFlag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(CurrentMode == 0)
                        countryCodeDialog.show();
                }
            });

            vVerifyText.setInputCompleteListener(new VerifyEditText.inputCompleteListener() {
                @Override
                public void inputComplete(VerifyEditText et, String content) {
                    if(vVerifyText.getContent().trim().length() == mVerifyCodeLength) {
                        if (mOnRegisterClickListener != null)
                            mOnRegisterClickListener.onEvent(vVerifyText.getContent(), vTxtPhoneNumber.getText().toString(), vTxtCountryCode.getText().toString());
                    }
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
                        SetMode(6, null);
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
                                        SetMode(0, null);
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
                    }
                    else
                        SetMode(0, null);
                }
            });

        }

        vButtonSendMobile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckMobileNumber(vTxtPhoneNumber.getText().toString())) {
                    SendPhoneNumber();
                }else {
                    SetMode(2, null);
                }
            }
        });

//        SetMode(0);
        vTxtDescription.setText(mTextMode_0);
    }

    public void SendPhoneNumber() {
        SetMode(1, null);
        if(mSendNumberClickListener != null)
            mSendNumberClickListener.onEvent(vTxtPhoneNumber.getText().toString(), vTxtCountryCode.getText().toString());
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
            SetMode(data, null);
        }
    }

    public void SetMode(int mode, String message) {
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
                vButtonSendMobile.setProgress(0);
                vTxtDescription.setText(message != null? message : mTextMode_0);
                if(vTxtPhoneNumber != null)
                    vTxtPhoneNumber.setEnabled(true);
                break;
            case 1: //SendingPhoneNumber
                vlySetNumber.setVisibility(VISIBLE);
                vlyVerify.setVisibility(GONE);
                vButtonSendMobile.setProgress(50);
                vTxtDescription.setText(message != null? message : mTextMode_1);
                break;
            case 2: //FailPhoneNumber
                vibrate();
                vTxtPhoneNumber.setEnabled(true);
                vTxtDescription.setText(message != null? message : mTextMode_2);
                vlySetNumber.setVisibility(VISIBLE);
                vlyVerify.setVisibility(GONE);
                vButtonSendMobile.setProgress(-1);
                postDelayed(new MyRunnable(0), 4000);
                break;
            case 3: //AcceptPhoneNumber
                vTxtDescription.setText(message != null? message : mTextMode_3);
                vlySetNumber.setVisibility(VISIBLE);
                vlyVerify.setVisibility(GONE);
                vButtonSendMobile.setProgress(100);
                postDelayed(new MyRunnable(4), 2000);
                break;
            case 4: //SetVerifyCode
                vibrate();
                vTxtDescription.setText(message != null? message : mTextMode_4);
                vVerifyText.resetContent();
                vIconSendCode.setImageResource(R.drawable.ic_baseline_verified_user_24);
                vCvSendCode.setBackgroundColor(Color.BLUE);
                vProgressExp.setPercent(0);
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);
                vVerifyText.SetEnableEditText(true);

                vProgressExp.stopAnimateIndeterminate();
                if(countDownTimer != null) countDownTimer.cancel();
                if(mVerifyTime == 0)
                    mVerifyTime = 120;
                int FullTime = mVerifyTime * 1000;
                countDownTimer = new CountDownTimer(FullTime, 1000) {

                    public void onTick(long millisUntilFinished) {
//                        if(millisUntilFinished < FullTime) {
                            vProgressExp.setPercent((((FullTime - millisUntilFinished) * 100) / FullTime));

                            if (millisUntilFinished < ((10 * FullTime) / 100)) {
                                YoYo.with(Techniques.Flash)
                                        .duration(200)
                                        .repeat(0)
                                        .playOn(vIcon);
                            }
//                        }
                    }

                    public void onFinish() {
                        countDownTimer_finish = true;
                        SetMode(9, null);
                        postDelayed(new MyRunnable(0), 3000);
                    }

                }.start();
                countDownTimer_finish = false;
                break;
            case 5: //SendingVerifyCode
                vTxtDescription.setText(message != null? message : mTextMode_5);
                vVerifyText.SetEnableEditText(false);
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);
                vProgressSendCode.animateIndeterminate();
                break;
            case 6: //FailVerifyCode
                vibrate();
                vTxtDescription.setText(message != null? message : mTextMode_6);
                vIconSendCode.setImageResource(R.drawable.ic_baseline_thumb_down_alt_24);
                vCvSendCode.setBackgroundColor(Color.RED);
                vVerifyText.SetEnableEditText(true);
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);
                if(!countDownTimer_finish)
                    postDelayed(new MyRunnable(7), 3000);
                break;
            case 7: //ReSendingVerifyCode
                vibrate();
                vTxtDescription.setText(message != null? message : mTextMode_7);
                vIconSendCode.setImageResource(R.drawable.ic_baseline_verified_user_24);
                vCvSendCode.setBackgroundColor(Color.GREEN);
                vVerifyText.SetEnableEditText(true);
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);
                break;
            case 8: //AccessVerifyCode
                vTxtDescription.setText(message != null? message : mTextMode_8);
                vCvSendCode.setBackgroundColor(Color.BLUE);
                vIconSendCode.setImageResource(R.drawable.ic_baseline_how_to_reg_24);
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);

                postDelayed(setCompleted, 2000);
                break;
            case 9: //ExpireVerifyCode
                vibrate();
                vTxtDescription.setText(message != null? message : mTextMode_9);
                vIconSendCode.setImageResource(R.drawable.ic_baseline_thumb_down_alt_24);
                vCvSendCode.setBackgroundColor(Color.RED);
                vVerifyText.SetEnableEditText(false);
                vVerifyText.resetContent();
                vlySetNumber.setVisibility(GONE);
                vlyVerify.setVisibility(VISIBLE);
                postDelayed(new MyRunnable(0), 2000);
                break;
            case 10: //CanceledVerifyCode
                vibrate();
                vTxtDescription.setText(message != null? message : mTextMode_10);
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

    private void vibrate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(100);
        }
    }

    private final Runnable setCompleted = new Runnable() {
        @Override
        public void run() {
            if(mOnRegisterCompletedListener != null)
                mOnRegisterCompletedListener.onEvent();
        }
    };

    public VerifyPhoneNumber setPaddingLR(int paddingLR){
        vlySetNumber.setPadding(paddingLR, vlySetNumber.getPaddingTop(), paddingLR, vlySetNumber.getPaddingBottom());
        return this;
    }

    public VerifyPhoneNumber setVerifyTime(int verifyTimeSecond){
        this.mVerifyTime = verifyTimeSecond;
        return this;
    }

    public VerifyPhoneNumber setVerifyCodeColor(int verifyCodeColor){
        vVerifyText.SetTextColor(verifyCodeColor);
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

    public interface OnChangePhoneNumberListener {
        void onEvent(String countryCode, String text, int count);
    }

    public void SetOnChangePhoneNumberListener(OnChangePhoneNumberListener eventListener) {
        mOnChangePhoneNumberListener = eventListener;
    }
}