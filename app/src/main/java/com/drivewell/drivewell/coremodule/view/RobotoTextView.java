package com.drivewell.drivewell.coremodule.view;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class RobotoTextView extends android.support.v7.widget.AppCompatTextView {
    private Context m_context;

    public RobotoTextView(Context context) {
        super(context);
        this.m_context = context;
        init();
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_context = context;
        init();
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.m_context = context;
        init();
    }

    private void init() {
     //   setTypeface(Typeface.createFromAsset(this.m_context.getAssets(), "fonts/roboto.ttf"));
    }
}
