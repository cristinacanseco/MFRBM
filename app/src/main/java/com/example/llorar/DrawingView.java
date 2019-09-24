package com.example.llorar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

import static android.graphics.Color.rgb;

public class DrawingView extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = 0xFF000000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private float brushSize, lastBrushSize;
    private boolean erase=false;
    private float mX, mY;
    private  BrushStroke brush;

    private ArrayList<BrushStroke> mBrushStrokes = new ArrayList<>();

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing(){
        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;
        drawPath = new Path();
        drawPaint = new android.graphics.Paint();

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(android.graphics.Paint.Style.STROKE);
        drawPaint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        drawPaint.setStrokeCap(android.graphics.Paint.Cap.ROUND);

        canvasPaint = new android.graphics.Paint(Paint.DITHER_FLAG);
        brush = new BrushStroke(paintColor, brushSize);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                touch_start(touchX, touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                fingerMovedTo(touchX, touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                invalidate();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public void setBrushSize(float newSize){
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    public float getBrushSize() {
        return brushSize;
    }

    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }
    public float getLastBrushSize(){
        return lastBrushSize;
    }

    public void setErase(boolean isErase){

        erase=isErase;

        if(erase) {
            drawPaint.setColor(Color.TRANSPARENT);
        } else {
            drawPaint.setXfermode(null);
        }
    }

    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void onClickUndo () {
        if (!mBrushStrokes.isEmpty()) {
            mBrushStrokes.remove(mBrushStrokes.size());
            brush.setmPathOfStroke(drawPath);
            for(int i=0; i<mBrushStrokes.size(); i++){
                for(int j=0; j<mBrushStrokes.get(i).getmPathPoints().size(); j++){
                    brush.lineTo(mBrushStrokes.get(i).getmPathPoints().get(j)[0], mBrushStrokes.get(i).getmPathPoints().get(j)[1]);
                }
            }
            drawCanvas.drawPath(brush.getPathOfStroke(), drawPaint);
        }else {
            invalidate();
        }
    }

    private void touch_start(float x, float y) {
        BrushStroke stroke = new BrushStroke(paintColor,brushSize);
        stroke.moveTo(x,y);
        mBrushStrokes.add(stroke);
    }

    public void fingerMovedTo(float x, float y) {
        //BrushStroke stroke = mBrushStrokes.peek();
        //stroke.lineTo(x,y);
    }

    public void toggleDrawTools(View view, Boolean showView) {
        if (showView){
            view.animate(). translationY(0);
        }else{
            view.animate().translationY(168);
        }
    }

    public ArrayList<BrushStroke> getmBrushStrokes() {
        return mBrushStrokes;
    }


    public LinkedList<int[]> obtenerPuntos(){
        LinkedList<int[]> mPathPoints = new LinkedList<>();
        for (int i=0; i< canvasBitmap.getWidth(); i++){
            for(int j=0; j<canvasBitmap.getHeight(); j++){
                int pix = canvasBitmap.getPixel(i,j);
                int r = Color.red(pix);
                int g = Color.green(pix);
                int b = Color.blue(pix);
                int s = Color.rgb(r, g, b);
                if(paintColor == s){
                    mPathPoints.add(new int[]{i, j});
                }
               }
        }
        return mPathPoints;
    }

    public LinkedList<int[]> obtenerDatos(Bitmap drawingCache) {

        LinkedList<int[]> mPathPoints = new LinkedList<>();
        //Agregar  punto 00,
        for (int i=0; i< drawingCache.getWidth(); i++){
            for(int j=0; j<drawingCache.getHeight(); j++){
                int pix = drawingCache.getPixel(i,j);
                int r = Color.red(pix);
                int g = Color.green(pix);
                int b = Color.blue(pix);
                int s = Color.rgb(r, g, b);
                if(paintColor == s){
                    mPathPoints.add(new int[]{i, j});
                }
            }
        }
        return mPathPoints;
    }
}