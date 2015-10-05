package com.example.aser.camtest;

/**
 * Created by ASER on 28.09.2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.example.aser.camtest.AnimateDrawable;

import java.util.ArrayList;

class DrawView extends View implements View.OnClickListener{

    Paint p;
    Rect rect;

    Bitmap bitmap;
    Drawable dr, dr1;

    int heb;
    int web;

    int h;
    int w;

    Animation an, an1;

    AnimateDrawable mDrawable, mDrawable1;

    int textSize = 20;

    int dotX, dotY, dotX1, dotY1;

    private Drawable[] drws;
    private Bitmap[] dots;
    private Animation[] anims;
    private AnimateDrawable[] ads;
    private int[] xs, ys;

    public static ArrayList<Point> props = new ArrayList<Point>();
    static {
        props.add(new Point(300, 300));
        props.add(new Point(400, 500));
        props.add(new Point(600, 200));
        props.add(new Point(1000, 600));
        props.add(new Point(800, 400));
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        rect = new Rect();

        xs = new int[props.size()];
        ys = new int[props.size()];
        for(int i =0;i<props.size();i++){
            xs[i]=props.get(i).x;
            ys[i]=props.get(i).y;
        }

        dotX = 400;
        dotY = 500;

        dotX1 = 300;
        dotY1 = 300;

        dots = new Bitmap[props.size()];
        anims = new Animation[props.size()];
        ads = new AnimateDrawable[props.size()];
        drws = new Drawable[props.size()];
        //xs = new int[2];
        //ys = new int[2];



        for(int i=0;i<dots.length;i++){
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.location);
            dots[i]=Bitmap.createScaledBitmap(b, 64, 64, false);
        }
        for(int i=0;i<dots.length;i++){
            drws[i] = new BitmapDrawable(context.getResources(), dots[i]);
        }

        for(int i=0;i<dots.length;i++){
            anims[i] = new TranslateAnimation(0, 0, 0, 50);
            anims[i].setInterpolator(new BounceInterpolator());
        }


        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.location);

        setFocusable(true);
        setFocusableInTouchMode(true);

        Bitmap b = Bitmap.createScaledBitmap(bitmap, 64, 64, false);
        //Bitmap b1 = Bitmap.createScaledBitmap(bitmap, 64, 64, false);

        heb = b.getHeight();
        web = b.getWidth();

        dr = new BitmapDrawable(context.getResources(), b);
        dr1 = new BitmapDrawable(context.getResources(), b);
        //Drawable dr = context.getResources().getDrawable(R.drawable.location);



        an = new TranslateAnimation(0, 0, 0, 50);
        an1 = new TranslateAnimation(0, 0, 0, 50);

        an.setInterpolator(new BounceInterpolator());
        an1.setInterpolator(new BounceInterpolator());
        //an.setInterpolator(new CycleInterpolator(1));



        //an.startNow();
    }


    public void fun(int s){
        //this.setVisibility(INVISIBLE);
        textSize = s;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // заливка канвы цветом
        //canvas.drawARGB(80, 102, 204, 255);

        h = canvas.getHeight();
        w = canvas.getWidth();


        p.setTextSize(textSize);
        //canvas.drawText("center" + String.valueOf(web) + " " + String.valueOf(heb), w / 2, h / 2, p);
        //canvas.drawBitmap(bitmap, w / 3, h / 3, p);
        // настройка кисти
        // красный цвет
        p.setColor(Color.RED);
        // толщина линии = 10
        p.setStrokeWidth(10);



        for(int i=0;i<dots.length;i++){
            //drws[i].setBounds(xs[i],ys[i], xs[i],ys[i], );
            drws[i].setBounds(xs[i], ys[i], xs[i] + drws[i].getIntrinsicWidth(), ys[i] + drws[i].getIntrinsicHeight());
            ads[i] = new AnimateDrawable(drws[i], anims[i]);
            ads[i].draw(canvas);
        }

        invalidate();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //Check if the x and y position of the touch is inside the bitmap
                //if( x > bitmapXPosition && x < bitmapXPosition + bitmapWidth && y > bitmapYPosition && y < bitmapYPosition + bitmapHeight )
                //if( x > w/3 && x < w/3 + we && y > h/3 && y < h/3 + he )
                for(int i=0;i<dots.length;i++){
                    if(x>xs[i] && x<xs[i]+64 && y>ys[i]&&y<ys[i]+64){
                        anims[i].setInterpolator(new CycleInterpolator(1));
                        anims[i].setDuration(300);
                        //an.setRepeatCount(-1);
                        anims[i].initialize(10, 10, 10, 10);
                        anims[i].start();
                    }
                }

                return true;
        }
        return false;
    }
}