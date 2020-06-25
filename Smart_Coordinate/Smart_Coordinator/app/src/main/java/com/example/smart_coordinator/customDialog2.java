package com.example.smart_coordinator;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class customDialog2 extends Dialog {

    private Button CancelBtn, OkBtn;
    private View.OnClickListener cancelListener;
    private View.OnClickListener okListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_dialog2);

        CancelBtn = (Button)findViewById(R.id.dialog_cancelBtn);
        OkBtn = (Button)findViewById(R.id.dialog_okBtn);

        CancelBtn.setOnClickListener(cancelListener);
        OkBtn.setOnClickListener(okListener);
    }

    public customDialog2(@NonNull Context context, View.OnClickListener OkListener, View.OnClickListener CancelListener) {
        super(context);
        this.okListener = OkListener;
        this.cancelListener = CancelListener;
    }
}
