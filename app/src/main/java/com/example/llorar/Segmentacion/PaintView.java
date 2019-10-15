package com.example.llorar.Segmentacion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.llorar.Muestreo.Muestreo_usuario;
import com.example.llorar.R;

public class PaintView extends AppCompatActivity implements View.OnClickListener{

    private DrawingView drawView;
    private ImageView currPaint, drawBtn, eraseBtn,newBtn,saveBtn, undoBtn, drawColor, image_send_drawing;
    private float smallBrush, mediumBrush, largeBrush;
    private Drawable iD;
    private Imagen img;
    private ConstraintLayout draw_tools;
    private View draw_color_palette,draw_brush_palette;
    private ImageButton small_brush, medium_brush, large_brush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        drawView = (DrawingView) findViewById(R.id.drawing);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Bitmap image = (Bitmap) extras.get("image");
            if (image != null) {
                img = new Imagen(image);
                iD = (Drawable) img.getImagenD();
                Toast.makeText(this, "H:"+ img.getAltura() + " W: "+img.getAncho(), Toast.LENGTH_SHORT).show();
            }
        }

        drawView = (DrawingView)findViewById(R.id.drawing);
        drawView.setBackground(iD);

        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.draw_color_palette);

        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        drawBtn = (ImageView) findViewById(R.id.image_draw_width);
        drawBtn.setOnClickListener(this);

        drawView.setBrushSize(mediumBrush);

        eraseBtn = (ImageView) findViewById(R.id.image_draw_eraser);
        eraseBtn.setOnClickListener(this);

        newBtn = (ImageView) findViewById(R.id.image_close_drawing);
        newBtn.setOnClickListener(this);

        saveBtn = (ImageView) findViewById(R.id.image_send_drawing);
        saveBtn.setOnClickListener(this);

        undoBtn = (ImageView) findViewById(R.id.image_draw_undo);
        undoBtn.setOnClickListener(this);

        draw_tools = (ConstraintLayout) findViewById(R.id.draw_tools);

        drawColor = (ImageView) findViewById(R.id.image_draw_color);
        drawColor.setOnClickListener(this);

        image_send_drawing = findViewById(R.id.image_send_drawing);
        image_send_drawing.setOnClickListener(this);

        draw_color_palette = findViewById(R.id.draw_color_palette);
        draw_color_palette.setVisibility(View.GONE);

        draw_brush_palette = findViewById(R.id.draw_brush_palette);
        draw_brush_palette.setVisibility(View.GONE);

        small_brush = findViewById(R.id.small_brush);
        medium_brush = findViewById(R.id.medium_brush);
        large_brush = findViewById(R.id.large_brush);
    }

    public void paintClicked(View view){
        drawView.setBrushSize(drawView.getLastBrushSize());
        drawView.setErase(false);
        ImageButton imgView = null;
        if(view!=currPaint) {
            imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            currPaint = (ImageButton) view;
            seleccionColor(imgView);
        }

    }

    private void seleccionColor(ImageButton imgView) {
        ImageButton image_color_black = findViewById(R.id.image_color_black);
        image_color_black.setScaleX(1f);
        image_color_black.setScaleY(1f);
        image_color_black.setImageDrawable(image_color_black.getDrawable());

        ImageButton image_color_blue = findViewById(R.id.image_color_blue);
        image_color_blue.setScaleX(1f);
        image_color_blue.setScaleY(1f);
        image_color_blue.setImageDrawable(image_color_blue.getDrawable());

        ImageButton image_color_green = findViewById(R.id.image_color_green);
        image_color_green.setScaleX(1f);
        image_color_green.setScaleY(1f);
        image_color_green.setImageDrawable(image_color_green.getDrawable());

        ImageButton image_color_pink = findViewById(R.id.image_color_pink);
        image_color_pink.setScaleX(1f);
        image_color_pink.setScaleY(1f);
        image_color_pink.setImageDrawable(image_color_pink.getDrawable());

        ImageButton image_color_yellow = findViewById(R.id.image_color_yellow);
        image_color_yellow.setScaleX(1f);
        image_color_yellow.setScaleY(1f);
        image_color_yellow.setImageDrawable(image_color_yellow.getDrawable());

        ImageButton image_color_red = findViewById(R.id.image_color_red);
        image_color_red.setScaleX(1f);
        image_color_red.setScaleY(1f);
        image_color_red.setImageDrawable(image_color_red.getDrawable());

        imgView.setScaleX(1.5f);
        imgView.setScaleY(1.5f);
    }

    private void seleccionBrocha(float val) {

        small_brush.setScaleX(1f);
        small_brush.setScaleY(1f);

        medium_brush.setScaleX(1f);
        medium_brush.setScaleY(1f);

        large_brush.setScaleX(1f);
        large_brush.setScaleY(1f);

        if(val==10) {
            small_brush.setScaleX(1.5f);
            small_brush.setScaleY(1.5f);
            small_brush.setBackgroundColor(Integer.parseInt("@color/icon_color"));
        }else if(val == 20) {
            medium_brush.setScaleX(1.5f);
            medium_brush.setScaleY(1.5f);
            medium_brush.setBackgroundColor(Integer.parseInt("@color/icon_color"));
        }else if(val== 30) {
            large_brush.setScaleX(1.5f);
            large_brush.setScaleY(1.5f);
            large_brush.setBackgroundColor(Integer.parseInt("@color/icon_color"));
        }

    }

    @Override
    public void onClick(View view){

        if(view.getId()==R.id.image_draw_width){
            if(draw_tools.getTranslationY() == 168){
                drawView.toggleDrawTools(draw_tools, true);
                draw_brush_palette.setVisibility(View.VISIBLE);
                elegirBrocha();
                seleccionBrocha(drawView.getBrushSize());
            }else if(draw_tools.getTranslationY() == 0 || draw_brush_palette.getVisibility() == View.VISIBLE ){
                drawView.toggleDrawTools(draw_tools, false);
                draw_brush_palette.setVisibility(View.GONE);
            }
            draw_color_palette.setVisibility(View.GONE);

        }
        else if(view.getId()==R.id.image_close_drawing){
            //new button
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Nuevo dibujo");
            newDialog.setMessage("Empezar un nuevo dibujo (perderás el dibujo actual?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    drawView.startNew();
                    drawView.setBackground(iD);
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            newDialog.show();
            drawView.toggleDrawTools(draw_tools, false);
        }
        else if(view.getId() == R.id.image_draw_color){
            if(draw_tools.getTranslationY() == 168){
                drawView.toggleDrawTools(draw_tools, true);
                draw_color_palette.setVisibility(View.VISIBLE);
            }else if(draw_tools.getTranslationY() == 0 || draw_color_palette.getVisibility() == View.VISIBLE){
                drawView.toggleDrawTools(draw_tools, false);
                draw_color_palette.setVisibility(View.GONE);
            }
            draw_brush_palette.setVisibility(View.GONE);
        }
        else if(view.getId() == R.id.image_draw_undo){
            drawView.onClickUndo();
            drawView.invalidate();
        }
        else if(view.getId() == R.id.image_send_drawing){
            Intent intent = new Intent(PaintView.this, Muestreo_usuario.class);
            intent.putExtra("stack",drawView.obtenerDatos(drawView.getCanvasBitmap()));
            intent.putExtra("image", img.getImagenB());
            startActivity(intent);
        }
    }

    private void elegirBrocha() {
        small_brush.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawView.setBrushSize(smallBrush);
                drawView.setLastBrushSize(smallBrush);
            }
        });

        medium_brush.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawView.setBrushSize(mediumBrush);
                drawView.setLastBrushSize(mediumBrush);
            }
        });

        large_brush.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawView.setBrushSize(largeBrush);
                drawView.setLastBrushSize(largeBrush);
            }
        });
    }

}