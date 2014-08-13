package com.dekagames.dongle;

import com.dekagames.slon.Slon;
import com.dekagames.slon.SlonException;
import com.dekagames.slon.SlonNode;

import java.io.InputStream;
import java.util.ArrayList;


class SkeletonFrame {
    public float[] coords;  // в массиве содержатся по порядку координаты для каждой кости скелета в порядке:
                            // координата x, координата y, угол alpha в градусах, соответственно количество элементов в массиве
                            // равно утроенному количеству костей

    public SkeletonFrame(int bonesCount){
        coords = new float[bonesCount * 3];
    }
}



/**
 * Класс для работы с моделями скелетной анимации созданными в редакторе скелета.
 */
public class SkeletonModel {
//    protected TextureRegion textureRegion;          // кусок текстуры (атласа) с частями модели
    protected ArrayList<Sprite> bones;              // части скелета, в том порядке, в котором они должны рисоваться
    protected ArrayList<SkeletonFrame> frames;      // кадры скелета

    private     int bonesCount;
    private     int framesCount;
    private     int n_fps;                                      // частота кадров
    private     float fscale;
    private     int	n_curr_frame, n_start_frame, n_end_frame;

    private boolean					b_flip_h, b_flip_v;							// будет ли спрайт рисоваться отраженным по вертикали или горизонталм
    private boolean					b_loop;
    private boolean					b_playing;

    private float					f_fixed_delta;
    private float					f_delta;


    String        modelFileName;         // Имя файла модели


    /**
     *
     * @param game - текущая игра
     * @param region - регион текстуры с мини-атласом деталей модели. Сначала делается мини-атлас и файл модели
     *               в редакторе SkelEd, а затем мини-атлас присоединяется к общей текстуре с помощью упаковщика
     *               текстур как кадр спрайта (для того, чтобы избежать лишнего переключения текстур). Перед
     *               загрузкой тиз общего атлас извлекается мини-атлас в виде TextureRegion, а потом уже грузится модель.
     * @param modelfile - файл с описанием модели
     */
    public SkeletonModel (Game game, TextureRegion region, String modelfile, int fps){
        if (fps<=0) fps=1;
        n_fps = fps;

        f_fixed_delta = 1.0f/n_fps;
        b_loop = true;
        b_playing =false;
        fscale = 1.0f;

        modelFileName = modelfile;
        InputStream modelInStream;
        Slon slonModel = new Slon();
        try {
            modelInStream = game.fileIO.readAsset(modelfile);
            slonModel.load(modelInStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
        } catch (SlonException e1) {
            e1.printStackTrace();
            return;
        }
        // загрузим сначала все части модели в виде спрайтов
        bones = new ArrayList<Sprite>();
        SlonNode bonesNode = slonModel.getRoot().getChildWithKeyValue("name", "bonesNode");
        bonesCount = bonesNode.getChildCount();

        for (int i=0; i<bonesCount; i++){
            SlonNode boneNode = bonesNode.getChildAt(i);
            int nx = boneNode.getKeyAsInt("x");
            int ny = boneNode.getKeyAsInt("y");
            int nw = boneNode.getKeyAsInt("w");
            int nh = boneNode.getKeyAsInt("h");

            float pivx = boneNode.getKeyAsFloat("pivotx");
            float pivy = boneNode.getKeyAsFloat("pivoty");

            Sprite sprBone = new Sprite(region.texture, 1, 1, region.x + nx, region.y + ny, nw, nh );
            sprBone.setHotSpot(Math.round(pivx), Math.round(pivy));

            bones.add(sprBone);
        }


        // загрузим кадры
        SlonNode framesNode = slonModel.getRoot().getChildWithKeyValue("name", "framesNode");
        framesCount = framesNode.getChildCount();
        frames = new ArrayList<SkeletonFrame>();

        // цикл по кадрам
        for (int i=0; i<framesCount; i++){
            SlonNode frameNode = framesNode.getChildAt(i);
            SkeletonFrame frame = new SkeletonFrame(bonesCount);

            // цикл по костям в кадре
            for (int j=0; j<bonesCount; j++){
                SlonNode boneNode = frameNode.getChildAt(j);
                float fx = boneNode.getKeyAsFloat("x");
                float fy = boneNode.getKeyAsFloat("y");
                float fa = boneNode.getKeyAsFloat("a");
                frame.coords[j*3] = fx;
                frame.coords[j*3+1] = fy;
                frame.coords[j*3+2] = fa * MathUtils.radiansToDefrees;      // переводим угол в градусы, так как в модели он в радианах
            }

            frames.add(frame);
        }


    }

    public SkeletonModel(SkeletonModel clone){

    }



    /** Запускает анимацию модели
     *
     * @param startFrame
     * @param endFrame
     * @param loop
     */
    public void play(int startFrame, int endFrame, boolean loop){
        if (b_playing && (startFrame==n_start_frame) && (endFrame==n_end_frame)) return;
        b_playing = true;
        b_loop = loop;
        n_start_frame = startFrame;
        n_curr_frame = startFrame;
        n_end_frame = endFrame;
        f_delta = 0;
    }


    /**  запускает полную анимацию модели
     *
     *   @param loop
     */
    public void play(boolean loop){
        if (b_playing) return;
        b_playing = true;
        b_loop = loop;
        n_start_frame = 0;
        n_curr_frame = 0;
        n_end_frame = framesCount - 1;
        f_delta = 0;
    }




    /**
     * останавливает анимацию модели
     */
    public void stop(){
        b_playing = false;
    }


    /**
     *  processing animation
     * @param delta
     */
    public void update(float delta){
        if(!b_playing) return;

        f_delta += delta; 			// время с предыдущего кадра
        // найдем текущий кадр
        while(f_delta >= f_fixed_delta) {
            f_delta -= f_fixed_delta;
            if(n_curr_frame + 1 > n_end_frame)	{
                if (b_loop)
                    n_curr_frame = n_start_frame;
                else
                    b_playing = false;
            }
            else
                n_curr_frame++;
        }
    }

    public void setScale(float scale){
        if (scale < 0) scale = 0;
        fscale = scale;
    }

    public void setFlip(boolean horizontal, boolean vertical){
        b_flip_h = horizontal;
        b_flip_v = vertical;
        //для каждой кости сделаем флип
        for (Sprite spr: bones)
            spr.setFlip(horizontal, vertical);

    }

    public boolean getHFlip(){
        return b_flip_h;
    }

    public boolean getVFlip(){
        return b_flip_v;
    }


    /**
     * простое рисование модели - без скалирования, без флипа, без поворота, без окраски
     * @param graphics
     * @param x
     * @param y
     */
    public void draw(Graphics graphics, float x, float y, float angle) {
        float dx, dy;
        SkeletonFrame currFrame = frames.get(n_curr_frame);
        // при флипе координаты и угол хитро переворачиваются - умножаютсяна коэффициенты dx, dy
        dx = b_flip_h ? -1 : 1;
        dy = b_flip_v ? -1 : 1;

        // цикл по костям с индексом так как надо делать выборку координат
        for (int i=0; i<bonesCount; i++){
            Sprite sprBone = bones.get(i);
            if (sprBone != null) {
                float fx = currFrame.coords[i*3];
                float fy = currFrame.coords[i*3+1];
                float fa = currFrame.coords[i*3+2];

                // учтем флип
                fx = fx*dx;
                fy = fy*dy;
                fa = fa*dx*dy;


                // повернем на угол если указан
                if (angle > 0.5f || angle < -0.5f){         // при меньших углах нет смысла это делать
                    Point p = MathUtils.rotatePointDeg(0,0,fx, fy, angle);
                    fx = p.x;
                    fy = p.y;
                    fa += angle;
                }

                // изменим масштаб, если указан
                if (fscale > 1.02f || fscale < 0.98f){         // при меньшеи скейле нет смысла это делать
                    if (fscale < 0 ) fscale = 0;
                    fx *= fscale;
                    fy *= fscale;
                }


                sprBone.drawEx(graphics, x + fx, y + fy, fa, fscale, fscale);
            }
        }
    }



//    public void draw

}
