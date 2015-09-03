package com.dekagames.dongle;

import java.io.InputStream;
import java.nio.*;

import static com.dekagames.dongle.GLCommon.*;

/**
 * Created with IntelliJ IDEA.
 * User: Deka
 * Date: 30.09.13
 * Time: 1:21
 * To change this template use File | Settings | File Templates.
 */

// функциональный аналог SpriteBatch из LibGDX передается в спрайт при прорисовке
public abstract class Graphics {
    public static int MAX_SPRITES_DEFAULT = 2000;
    protected static final int VERTEX_SIZE = 2 + 1+1+1+1 + 2;			        // x,y,r,g,b,a,s,t
    protected static final int VERTICES_PER_QUAD = 6;                          // сколькими вершинами задается прямоугольная область
    protected static final int QUAD_SIZE = VERTICES_PER_QUAD * VERTEX_SIZE;    // сколько значений типа float требуется для задания одного QUAD

    protected static final int BYTES_PER_FLOAT = 4;

    // расположение данных в буфере вершин
    protected static final int POSITION_OFFSET = 0;   // смещение координат вершин в буфере вершин
    protected static final int POSITION_SIZE = 2;     // размер данных по координатам (2 - X и Y)

    protected static final int COLOR_OFFSET = 2;      // то же для данных о цвете
    protected static final int COLOR_SIZE = 4;        // (R,G,B,A)

    protected static final int TEX_COORD_OFFSET = 6;  // то же для текстурных координат
    protected static final int TEX_COORD_SIZE = 2;    //

    protected static final int STRIDE = VERTEX_SIZE * BYTES_PER_FLOAT;  // количество байт на вершину


    protected Game game;

    protected GLCommon gl;                      // интерфейс для обращения к функиям OpenGL

    private     Shader shaderForSprites;          // стандартный шейдер для вывода текстурных объектов
    private     Shader shaderForPrimitives;       // standart shader for primitives

    protected   FloatBuffer vertexBuffer;       // буффер с прорисовываемыми вершинами

    protected   int spritesCount;               // сколько спрайтов будем рисовать
    protected   int vertexCount;                // how many vertexes to draw
    protected   int maxSpritesAmount;           // сколько максимум спрайтов рисуется за один проход
    public      int physicalWidth, physicalHeight;

    public static float SCALE;                  // коэффициент трансформации виртуального разрешения в реальное
    public static int XOFFSET, YOFFSET;         // смещение картинки относительно левого верхнего угла из-за разного соотношения сторон
    public static float BOTTOM_OFFSET;          // смещение низа картинки

    private int viewportWidth, viewportHeight;    //реальная ширина и высота viewport-a

    private Texture currentTexture;
    private Shader currentShader;
    private boolean isFirstDrawCall;

    // матрицы
    protected float[] projectionMatrix = new float[16];     // матрица проекции
    protected float[] viewModelMatrix = new float[16];      // произведение матриц вида и модели
    protected float[] mpvMatrix = new float[16];            // произведение матриц вида, модели и проекции


    public Graphics(Game game){
        this.game = game;
        isFirstDrawCall = true;

        // подготовим массив вершин
        vertexBuffer = ByteBuffer.
                allocateDirect(QUAD_SIZE * BYTES_PER_FLOAT * MAX_SPRITES_DEFAULT).
                order(ByteOrder.nativeOrder()).
                asFloatBuffer();
    }


    public  GLCommon getGl(){
        return gl;
    }


    public int getViewportWidth(){return viewportWidth;}


    public int getViewportHeight() {return viewportHeight;}


//    public Game getGame(){
//        return game;
//    }


    public final void clearScreen(float r, float g, float b){
        gl.glClearColor(r, g, b, 1);
        gl.glClear(GLCommon.GL_COLOR_BUFFER_BIT);
    }


    public final void clearViewport(float r, float g, float b){
        gl.glEnable(GL_SCISSOR_TEST);
        gl.glScissor(XOFFSET, YOFFSET, viewportWidth, viewportHeight);
        gl.glClearColor(r,g,b,1);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glDisable(GL_SCISSOR_TEST);
    }


    public void init(int physical_width, int physical_height){
        Log.info("Game graphics.init("+physical_width+", "+physical_height+") called");
        physicalWidth = physical_width;
        physicalHeight = physical_height;

        // определим как вытянут экран и коэффициент растяжения
        float virtRatio = (float)game.getVirtualWidth()/(float)game.getVirtualHeight();
        float realRatio = (float)physical_width/(float)physical_height;
        if (realRatio>virtRatio){ // экран вытянут по ширине - будут полоски слева и справа
            SCALE = (float)physical_height/(float)game.getVirtualHeight();
            YOFFSET = 0;
            BOTTOM_OFFSET = 0;
            XOFFSET = Math.round(physical_width - SCALE*game.getVirtualWidth())/2;
        }
        else {  // экран вытянут по высоте - будут полоски сверху и снизу
            SCALE = (float)physical_width/(float)game.getVirtualWidth();
            XOFFSET = 0;
            YOFFSET = Math.round(physical_height - SCALE*game.getVirtualHeight())/2;
            BOTTOM_OFFSET = YOFFSET;
        }

        viewportWidth = Math.round(SCALE*game.getVirtualWidth());
        viewportHeight= Math.round(SCALE*game.getVirtualHeight());

        gl.glDisable(GL_DEPTH_TEST);
        gl.glDisable(GL_LIGHTING);
        gl.glDisable(GL_SCISSOR_TEST);

        // для прозрачности разные установки для платформ хз почему
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // !!!!!! // for desktop

        // Устанавливаем viewport  в соответствии с виртуальным экраном.
        gl.glViewport(XOFFSET, YOFFSET, viewportWidth, viewportHeight);

        // получим произведение матриц вида и модели. они используются совместно всегда
        init_view_model_matrix();

        // Создаем новую матрицу проекции. Ортогональную.
        final float left = 0;
        final float right = game.getVirtualWidth();
        final float bottom = game.getVirtualHeight();   // так как стоим ровно, то верх и низ просто меняем местами - чтобы
        final float top = 0;                            // координата Y на экране была направлена вниз
        final float near = 2.0f;
        final float far = -2.0f;
        Matrix.orthoM(projectionMatrix, 0, left, right, bottom, top, near, far);

        // Перемножаем матрицу модели*вида на матрицу проекции, сохраняем в MVP матрицу.
        // (которая теперь содержит модель*вид*проекцию).
        Matrix.multiplyMM(mpvMatrix, 0, projectionMatrix, 0, viewModelMatrix, 0);


        // создаем два дефолтных шейдера - один для спрайтов, другой для простых примитивов
        shaderForSprites = createShader(Shader.DEFAULT_VERTEX_SHADER, Shader.DEFAULT_FRAGMENT_SHADER);
        shaderForPrimitives = createShader(Shader.PRIMITIVE_VERTEX_SHADER, Shader.PRIMITIVE_FRAGMENT_SHADER);

        // send MPV matrix to both shaders
        // before we send uniform to shader we MUST enable shader by glUseProgram command!!!
        gl.glUseProgram(shaderForSprites.getProgramId());
        gl.glUniformMatrix4fv(shaderForSprites.mpvMatrixUniformLocation, 1, false, mpvMatrix, 0);
        gl.glUseProgram(shaderForPrimitives.getProgramId());
        gl.glUniformMatrix4fv(shaderForPrimitives.mpvMatrixUniformLocation, 1, false, mpvMatrix, 0);

        // перезагрузим текстуры
        // восстановим текстуры при создании поверхности - это происходит в самом начале, при запуске игрыы
        // и при паузе-возобновлении
        if (game != null) {
            if (game.getNeedToReloadTextures()) {
                game.reloadTextures();
                game.setNeedToReloadTextures(false);

            }
        }
    }



    public final void begin(){
        maxSpritesAmount = MAX_SPRITES_DEFAULT;
        vertexBuffer.position(0);
    }


    public final void end(){
        // we always send ALL data to ANY shader. e.g. we send texture data to primitives shader too.
        // texture's uniforms in primitive's shader are NOT exist, thus its location variables is -1.
        if (currentShader == null){
            if (currentTexture == null)
                currentShader = shaderForPrimitives;
            else
                currentShader = shaderForSprites;
        }

        gl.glUseProgram(currentShader.getProgramId());

        vertexBuffer.rewind();
        // значения позиции и цвета передаются в любой шейдер

        // Передаем значения о расположении.
        vertexBuffer.position(POSITION_OFFSET);
        gl.glVertexAttribPointer(currentShader.positionAttrLocation, POSITION_SIZE, GL_FLOAT, false, STRIDE, vertexBuffer);
        gl.glEnableVertexAttribArray(currentShader.positionAttrLocation);

        // Передаем значения о цвете.
        vertexBuffer.position(COLOR_OFFSET);
        gl.glVertexAttribPointer(currentShader.colorAttrLocation, COLOR_SIZE, GL_FLOAT, false, STRIDE, vertexBuffer);
        gl.glEnableVertexAttribArray(currentShader.colorAttrLocation);

        if (currentTexture != null) { //shader.getKind() != Shader.DEFAULT_FOR_PRIMITIVES) {
            // Передаем значения о текстурных координатах.
            vertexBuffer.position(TEX_COORD_OFFSET);
            gl.glVertexAttribPointer(currentShader.texCoordAttrLocation, TEX_COORD_SIZE, GL_FLOAT, false, STRIDE, vertexBuffer);
            gl.glEnableVertexAttribArray(currentShader.texCoordAttrLocation);
        }

        // непосредственно рисуем все что накопилось в буфере
        gl.glDrawArrays(GL_TRIANGLES, 0, vertexCount);//VERTICES_PER_QUAD * spritesCount);
        gl.glFlush();   // нехило ускоряется процесс из-за этой команды
        // обнулим счетчик спрайтов - мы их уже нарисовали
        spritesCount = 0;
        vertexCount = 0;
        if (currentTexture != null){
            gl.glBindTexture(GLCommon.GL_TEXTURE_2D, 0);
        }
        currentTexture = null;
        isFirstDrawCall = true;
    }


    /**
     * Устанавливаем текущий шейдер для отрисовки. Если shader равен null, значит используется
     * шейдер по умолчанию.
     * @param shader
     */
    public void useShader(Shader shader){
        if (shader == currentShader)
            return;

        if (!isFirstDrawCall) {
            end();
            begin();
        }

        // если шейдер свой - сразу загрузим ему mpv матрицу - она неизменна
        // в шейдеры по умолчанию она уже загружена при инициализации графики
        if (shader != null){
            gl.glUseProgram(shader.getProgramId());
            gl.glUniformMatrix4fv(shader.mpvMatrixUniformLocation, 1, false, mpvMatrix, 0);
        }
        currentShader = shader;
    }


    // устанавливает произведение матриц вида и модели
    private void init_view_model_matrix(){
        float[] viewMatrix = new float[16];
        float[] modelMatrix = new float[16];

        // Устанавливаем матрицу ВИДА. Она описывает положение камеры.
        // Примечание: В OpenGL 1, матрица ModelView используется как комбинация матрицы МОДЕЛИ
        // и матрицы ВИДА. В OpenGL 2, мы можем работать с этими матрицами отдельно по выбору.
        // Положение глаза, точки наблюдения в пространстве.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 1.0f;

        // направление взгляда - смотрим против оси Z (ось Z направлена к нам)
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -1.0f;

        // голова смотрит по оси Y (стоим вертикально - ось Y направлена вверх).
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        Matrix.setLookAtM(viewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
        //устанавливаем матрицу модели - мы ее не используем, поэтому просто единичная
        Matrix.setIdentityM(modelMatrix, 0);
        viewModelMatrix = new float[16];
        Matrix.multiplyMM(viewModelMatrix, 0, viewMatrix, 0, modelMatrix, 0);
    }


    void draw(Texture texture, float[] vertices) {
        // maximum vertices[] size is 48 - 6 vertices of 8 floats!!!! If we draw primitive, it is not
        // allowed to send more then 6 vertices by method call.

        // if texture changing detected - we'll finish previous draw and send new texture to shader
        if (currentTexture != texture) {
            if (!isFirstDrawCall) {
                end();
                begin();
            }
            currentTexture = texture;
            // Передаем текстуру в шейдер
            if (currentTexture != null) {
                gl.glActiveTexture(GLCommon.GL_TEXTURE0);                           // установим активный текстурный блок
                gl.glBindTexture(GLCommon.GL_TEXTURE_2D, texture.textureId);        // установим активную текстуру
                if (currentShader == null)                                          // шейдер по умолчанию
                    gl.glUniform1i(shaderForSprites.textureUniformLocation, 0);        // свяжем адрес униформы текстуры с номером акивного блока
                else                                                                // пользовательский шейдер
                    gl.glUniform1i(currentShader.textureUniformLocation, 0);
            }
        }
        isFirstDrawCall = false;


        // добавим вершины в буфер
        vertexBuffer.put(vertices);
        vertexCount += vertices.length/VERTEX_SIZE;

        spritesCount++;

        if (spritesCount >= MAX_SPRITES_DEFAULT) {
            end();
            begin();
        }
    }


    // перечитывает текстуру из файла, записывает ее в готовый уже Id
    // используется при повторной перезагрузке ранее загруженных текстур после потери контекста
    protected abstract void reload_texture_from_file(Texture texture);


    public abstract Texture createTexture(String filepath);


    public Shader createShader(InputStream is_vertex, InputStream is_fragment){
        Shader sh = new Shader(this, is_vertex, is_fragment);
        game.managedShaders.add(sh);
        return sh;
    }


    public Shader createShader(String str_vertex, String str_fragment){
        Shader sh = new Shader(this, str_vertex, str_fragment);
        game.managedShaders.add(sh);
        return sh;
    }


    public void deleteShader(Shader sh){
        gl.glDeleteProgram(sh.getProgramId());
        game.managedShaders.remove(sh);
    }

    public void deleteTexture(Texture texture){
        gl.glBindTexture(GLCommon.GL_TEXTURE_2D, texture.textureId);
        int[] texureIds = {texture.textureId};
        gl.glDeleteTextures(1,texureIds,0);
        game.getManagedTextures().remove(texture);
    }
}
