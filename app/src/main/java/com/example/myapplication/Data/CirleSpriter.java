package com.example.myapplication.Data;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CirleSpriter {
    float x,y,radius;
    double direction;
    float maxWidth,maxHeight;

    public CirleSpriter(float x,float y, float radius,float maxWidth,float maxHeight)
    {
        this.x=x;
        this.y=y;
        this.radius=radius;
        this.direction=Math.random();
        this.maxHeight=maxHeight;
        this.maxWidth=maxWidth;
    }
    public void draw(Canvas canvas)
    {
        Paint paint=new Paint();
        paint.setColor(Color.RED);

        canvas.drawCircle(x,y,radius,paint);
    }
    public void setRadius(float radius){
        this.radius=radius;
    }
    public void move()
    {
        this.y-=40*Math.sin(direction);
        if(this.y<0) this.y+=maxHeight;
        if(this.y>maxHeight) this.y-=maxHeight;
    }

    public boolean isShot(float touchedX, float touchedY) {
        double distance=(touchedX-this.x)*(touchedX-this.x)+(touchedY-this.y)*(touchedY-this.y);
        return distance<radius*radius;
    }
}
