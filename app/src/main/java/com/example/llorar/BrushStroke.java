package com.example.llorar;

import android.graphics.Path;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by varamach on 2/23/18.
 * A model that stores information about a single brush stroke.
 */

public class BrushStroke implements Serializable {

    //Color used for the brush stroke
    private int mColor;

    //Size of brush
    private float mSizeOfBrush;

    private transient Path mPathOfStroke = null;

    //The path are converted to a float list and persisted.
    private LinkedList<float[]> mPathPoints = new LinkedList<>();

    public BrushStroke(int selectedColor, float sizeOfBrush) {
        this.mColor = selectedColor;
        this.mSizeOfBrush = sizeOfBrush;
        this.mPathPoints = new LinkedList<>();
        this.convertFromPointsToPath();
    }

    public int getColor() {
        return mColor;
    }


    public void moveTo(float x, float y) {
        this.mPathPoints.add(new float[]{x, y});
    }

    public void lineTo(float x, float y) {
        this.mPathPoints.add(new float[]{x, y});
    }

    public Path getPathOfStroke() {
        this.convertFromPointsToPath();
        return mPathOfStroke;
    }


    private void convertFromPointsToPath() {
        //Path already exists, dont need a conversion.
        if (this.mPathOfStroke != null) {
            return;
        }

        //The path list is empty. Nothing available to convert.
        if (this.mPathPoints == null || this.mPathPoints.isEmpty()) {
            return;
        }

        float[] initPoints = mPathPoints.getFirst();
        this.mPathOfStroke.moveTo(initPoints[0], initPoints[1]);
        for (float[] pointSet : mPathPoints) {
            this.mPathOfStroke.lineTo(pointSet[0], pointSet[1]);
        }
    }

    public LinkedList<float[]> getmPathPoints() {
        return mPathPoints;
    }


    public void setmPathOfStroke(Path mPathOfStroke) {
        this.mPathOfStroke = mPathOfStroke;
    }
}
