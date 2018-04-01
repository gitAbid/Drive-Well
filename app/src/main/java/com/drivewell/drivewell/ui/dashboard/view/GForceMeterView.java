package com.drivewell.drivewell.ui.dashboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import com.drivewell.drivewell.ui.dashboard.util.Data2D;
import com.drivewell.drivewell.ui.dashboard.util.Data2DCache;
import com.drivewell.drivewell.ui.dashboard.util.SettingsWrapper;

public class GForceMeterView extends View {
    private float acc_brake_g = 0.0f;
    private Data2DCache data;
    private Path data_path;
    private final int dot_color = Color.rgb(255, 80, 80);
    private final float dotted_length = 15.0f;
    private PathEffect effect;
    private Typeface font;
    private final float g_divider = 4.0f;
    private int height;
    private boolean invert_horizontal = false;
    private boolean invert_vertical = false;
    private boolean is_dynamic = true;
    private Path line_path;
    private final float line_width = SettingsWrapper.MAX_G_FORCE_MAX_G;
    private Context m_context;
    private float max_g = 1.0f;
    private float min_g = (-this.max_g);
    private int padding;
    private final int padding_divider = 16;
    private Paint paint;
    private final int path_color = Color.argb(200, 250, 250, 250);
    private final float radius_divider = 24.0f;
    private float range_g = (this.max_g - this.min_g);
    private float right_left_g = 0.0f;
    private final int stroke_color = Color.rgb(0, 255, 0);
    private final float text_divider = 35.0f;
    public int trace_length = 1000;
    private int width;

    public GForceMeterView(Context context) {
        super(context);
        this.m_context = context;
        init();
    }

    public GForceMeterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_context = context;
        init();
    }

    public GForceMeterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.m_context = context;
        init();
    }

    private void init() {
        this.paint = new Paint();
        this.paint.setStrokeWidth(SettingsWrapper.MAX_G_FORCE_MAX_G);
        this.line_path = new Path();
        this.data_path = new Path();
        this.effect = new DashPathEffect(new float[]{15.0f, 15.0f, 15.0f, 15.0f}, 0.0f);
        this.trace_length = ((int) SettingsWrapper.G_FORCE_TRAIL_LENGTH) * ((int) SettingsWrapper.SENSOR_REFRESH_RATE);
        this.data = new Data2DCache(this.trace_length);
        this.font = Typeface.createFromAsset(this.m_context.getAssets(), "fonts/roboto.ttf");
        this.invert_vertical = SettingsWrapper.G_VERTICAL_INVERT;
        this.invert_horizontal = SettingsWrapper.G_HORIZONTAL_INVERT;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setStyle(Style.STROKE);
        this.paint.setColor(this.stroke_color);
        this.paint.setPathEffect(this.effect);
        canvas.drawCircle((float) (this.width / 2), (float) (this.height / 2), (float) ((this.height - this.padding) / 8), this.paint);
        this.paint.setPathEffect(null);
        canvas.drawCircle((float) (this.width / 2), (float) (this.height / 2), (float) (((this.height - this.padding) * 2) / 8), this.paint);
        this.paint.setPathEffect(this.effect);
        canvas.drawCircle((float) (this.width / 2), (float) (this.height / 2), (float) (((this.height - this.padding) * 3) / 8), this.paint);
        this.paint.setPathEffect(null);
        canvas.drawCircle((float) (this.width / 2), (float) (this.height / 2), (float) (((this.height - this.padding) * 4) / 8), this.paint);
        this.paint.setPathEffect(this.effect);
        this.line_path.reset();
        this.line_path.moveTo((float) (this.padding / 2), (float) (this.height / 2));
        this.line_path.lineTo((float) (this.width - (this.padding / 2)), (float) (this.height / 2));
        canvas.drawPath(this.line_path, this.paint);
        this.line_path.reset();
        this.line_path.moveTo((float) (this.width / 2), (float) (this.padding / 2));
        this.line_path.lineTo((float) (this.width / 2), (float) (this.height - (this.padding / 2)));
        canvas.drawPath(this.line_path, this.paint);
        this.paint.setPathEffect(null);
        this.paint.setColor(this.stroke_color);
        this.paint.setStyle(Style.FILL);
        this.paint.setTextSize(((float) this.width) / 35.0f);
        this.paint.setTypeface(this.font);
        canvas.drawText(String.format("%.2f", new Object[]{Float.valueOf((this.max_g / 4.0f) * 1.0f)}) + " G", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 4.75f) / 8.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 3.25f) / 8.0f), this.paint);
        canvas.drawText(String.format("%.2f", new Object[]{Float.valueOf((this.max_g / 4.0f) * 2.0f)}) + " G", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 5.48f) / 8.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 2.52f) / 8.0f), this.paint);
        canvas.drawText(String.format("%.2f", new Object[]{Float.valueOf((this.max_g / 4.0f) * 3.0f)}) + " G", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 6.21f) / 8.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 1.78f) / 8.0f), this.paint);
        canvas.drawText(String.format("%.2f", new Object[]{Float.valueOf((this.max_g / 4.0f) * 4.0f)}) + " G", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 6.93f) / 8.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 1.03f) / 8.0f), this.paint);
        if (this.invert_horizontal) {
            canvas.drawText("RIGHT", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 0.6f) / 28.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 15.2f) / 28.0f), this.paint);
            canvas.drawText("LEFT", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 25.25f) / 28.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 13.5f) / 28.0f), this.paint);
        } else {
            canvas.drawText("LEFT", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 0.9f) / 28.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 15.2f) / 28.0f), this.paint);
            canvas.drawText("RIGHT", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 25.0f) / 28.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 13.5f) / 28.0f), this.paint);
        }
        if (this.invert_vertical) {
            canvas.drawText("BRAKE", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 11.0f) / 28.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 2.0f) / 28.0f), this.paint);
            canvas.drawText("ACC", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 14.5f) / 28.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 26.5f) / 28.0f), this.paint);
        } else {
            canvas.drawText("ACC", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 12.0f) / 28.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 2.0f) / 28.0f), this.paint);
            canvas.drawText("BRAKE", ((float) (this.padding / 2)) + ((((float) (this.width - this.padding)) * 14.5f) / 28.0f), ((float) (this.padding / 2)) + ((((float) (this.height - this.padding)) * 26.5f) / 28.0f), this.paint);
        }
        this.data_path.reset();
        this.data_path.moveTo(((float) (this.padding / 2)) + (((this.max_g + (this.invert_horizontal ? -this.right_left_g : this.right_left_g)) / this.range_g) * ((float) (this.width - this.padding))), ((float) (this.padding / 2)) + (((this.max_g + (this.invert_vertical ? this.acc_brake_g : -this.acc_brake_g)) / this.range_g) * ((float) (this.height - this.padding))));
        for (int i = this.data.getLength() - 1; i >= 0; i--) {
            this.data_path.lineTo(((float) (this.padding / 2)) + (((this.max_g + (this.invert_horizontal ? -((Data2D) this.data.get(i)).right_left : ((Data2D) this.data.get(i)).right_left)) / this.range_g) * ((float) (this.width - this.padding))), ((float) (this.padding / 2)) + (((this.max_g + (this.invert_vertical ? ((Data2D) this.data.get(i)).acc_brake : -((Data2D) this.data.get(i)).acc_brake)) / this.range_g) * ((float) (this.height - this.padding))));
        }
        this.paint.setPathEffect(null);
        this.paint.setColor(this.path_color);
        this.paint.setStyle(Style.STROKE);
        canvas.drawPath(this.data_path, this.paint);
        if (this.is_dynamic) {
            float radius = ((float) (this.height - (this.padding * 2))) / 24.0f;
            float x = ((float) (this.padding / 2)) + (((this.max_g + (this.invert_horizontal ? -this.right_left_g : this.right_left_g)) / this.range_g) * ((float) (this.width - this.padding)));
            float y = ((float) (this.padding / 2)) + (((this.max_g + (this.invert_vertical ? this.acc_brake_g : -this.acc_brake_g)) / this.range_g) * ((float) (this.height - this.padding)));
            this.paint.setPathEffect(null);
            this.paint.setStyle(Style.FILL);
            this.paint.setColor(this.dot_color);
            canvas.drawCircle(x, y, radius, this.paint);
            this.paint.setStyle(Style.STROKE);
            this.paint.setColor(this.stroke_color);
            canvas.drawCircle(x, y, radius, this.paint);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.width = MeasureSpec.getSize(widthMeasureSpec);
        this.height = MeasureSpec.getSize(heightMeasureSpec);
        this.width = this.width < this.height ? this.width : this.height;
        this.height = this.width < this.height ? this.width : this.height;
        this.padding = this.width / 16;
        setMeasuredDimension(this.width, this.height);
    }

    public void setGForce(Data2D d) {
        this.is_dynamic = true;
        this.right_left_g = d.right_left;
        this.acc_brake_g = d.acc_brake;
        this.data.addData(d);
        invalidate();
    }

    public void showData(Data2DCache d) {
        float f = 0.0f;
        this.is_dynamic = false;
        this.right_left_g = d == null ? 0.0f : ((Data2D) d.getLast()).right_left;
        if (d != null) {
            f = ((Data2D) d.getLast()).acc_brake;
        }
        this.acc_brake_g = f;
        this.data = d;
        invalidate();
    }

    public void resetMaxLength(int l) {
        this.trace_length = l;
        this.data.setLength(this.trace_length);
        Log.d("TEST", String.valueOf(this.data.length));
    }

    public void resetPath() {
        this.data.clear();
    }

    public void setMaxG(float g) {
        if (g > 0.0f) {
            this.max_g = g;
            this.min_g = -g;
            this.range_g = 2.0f * g;
        }
    }
}
