package com.dekagames.dongle;


/**
 * Created with IntelliJ IDEA.
 * User: Deka
 * Date: 29.09.13
 * Time: 23:09
 * To change this template use File | Settings | File Templates.
 */
public class Shader {
    final static String DEFAULT_VERTEX_SHADER =
            "uniform mat4 u_MVPMatrix;                          \n"     // Константа отвечающая за комбинацию матриц МОДЕЛЬ/ВИД/ПРОЕКЦИЯ.
                    + "attribute vec4 a_Position;               \n"     // Информация о положении вершин.
                    + "attribute vec4 a_Color;                  \n"     // Информация о цвете вершин.
                    + "attribute vec2 a_TexCoordinate;          \n"     // текстурные координаты
                    + "varying vec4 v_Color;                    \n"     // Это будет передано в фрагментный шейдер.
                    + "varying vec2 v_TexCoordinate;            \n"

                    + "void main()                              \n"     // Начало программы вершинного шейдера.
                    + "{                                        \n"
                    + "   v_Color = a_Color;                    \n"     // Передаем цвет для фрагментного шейдера.
                                                                        // Он будет интерполирован для всего треугольника.
                    + "   v_TexCoordinate = a_TexCoordinate;    \n"     // передадим текстурную координату во фрагментный шейдер
                    + "   gl_Position = u_MVPMatrix             \n"     // gl_Position специальные переменные используемые для хранения конечного положения.
                    + "               * a_Position;             \n"     // Умножаем вершины на матрицу для получения конечного положения
                    + "}                                        \n";    // в нормированных координатах экрана.

    final static String DEFAULT_FRAGMENT_SHADER =
            // Устанавливаем по умолчанию среднюю точность для переменных. Максимальная точность
            // в фрагментном шейдере не нужна.
            // Примечание: устанавливаем точность если у нас андроидовский OpenGLES, так как директивы
            // precision не было в настольном OpenGL до версии 4.1 (или типа того)
                    "#ifdef GL_ES\n"
                    +"precision mediump float;\n"
                    +"#endif\n"

                    + "uniform sampler2D u_Texture;             \n"     // Наша Текстура.
                    + "varying vec4 v_Color;                    \n"     // Цвет вершинного шейдера преобразованного
                    // для фрагмента треугольников.
                    + "varying vec2 v_TexCoordinate;            \n"

                    + "void main()                              \n"     // Точка входа для фрагментного шейдера.
                    + "{                                        \n"
                    + " vec4 texColor = texture2D(u_Texture, v_TexCoordinate);  \n"     // берем цвет из текстуры
                    + " gl_FragColor = v_Color*texColor;                        \n"     // Передаем значения цветов.
                    + "}                                                        \n";


    private boolean     is_default_shader;
    private int         vertexShaderHandle;
    private int         fragmentShaderHandle;
    private int         shaderProgramHandle;

    // адреса обязательных атрибутов
    public int mpvMatrixUniformLocation;       // расположение сборной матрицы MPV в шейдере
    public int textureUniformLocation;         // расположение текстуры в шейдере
    public int positionAttrLocation;           // расоложение атрибута координат в шейдере
    public int colorAttrLocation;              // то же для цвета
    public int texCoordAttrLocation;           // то же для текстурных координат


    public Shader(Graphics graphics){
        is_default_shader = true;
        build(graphics.getGl(), DEFAULT_VERTEX_SHADER, DEFAULT_FRAGMENT_SHADER);
    }





    private void build(GLCommon gl, final String vertex_shader, final String fragment_shader){
        // Загрузка вершинного шейдера.
        vertexShaderHandle = gl.glCreateShader(GLCommon.GL_VERTEX_SHADER);
        if (vertexShaderHandle != 0)   {
            // Передаем в наш шейдер программу.
            gl.glShaderSource(vertexShaderHandle, vertex_shader);
            // Компиляция шейдера
            gl.glCompileShader(vertexShaderHandle);
            // Получаем результат процесса компиляции
            final int[] compileStatus = new int[1];
            gl.glGetShaderiv(vertexShaderHandle, GLCommon.GL_COMPILE_STATUS, compileStatus, 0);

            // Если компиляция не удалась, удаляем шейдер.
            if (compileStatus[0] == 0) {
                // проверим ошибку
                String error = gl.glGetShaderInfoLog(vertexShaderHandle);
                Log.error("Unable to compile vertex shader! Error - "+error);
                gl.glDeleteShader(vertexShaderHandle);
                vertexShaderHandle = 0;
            }
        }
        if (vertexShaderHandle == 0)  {
            throw new RuntimeException("Error creating vertex shader.");
        }

        // Загрузка фрагментного шейдера.
        fragmentShaderHandle = gl.glCreateShader(GLCommon.GL_FRAGMENT_SHADER);
        if (fragmentShaderHandle != 0)   {
            // Передаем в наш шейдер программу.
            gl.glShaderSource(fragmentShaderHandle, fragment_shader);
            // Компиляция шейдера
            gl.glCompileShader(fragmentShaderHandle);
            // Получаем результат процесса компиляции
            final int[] compileStatus = new int[1];
            gl.glGetShaderiv(fragmentShaderHandle, GLCommon.GL_COMPILE_STATUS, compileStatus, 0);
            // Если компиляция не удалась, удаляем шейдер.
            if (compileStatus[0] == 0) {
                String error = gl.glGetShaderInfoLog(fragmentShaderHandle);
                Log.error("Unable to compile fragment shader! Error - "+error);
                gl.glDeleteShader(fragmentShaderHandle);
                fragmentShaderHandle = 0;
            }
        }
        if (fragmentShaderHandle == 0)  {
            throw new RuntimeException("Error creating fragment shader.");
        }

        // Создаем объект программы вместе со ссылкой на нее.
        shaderProgramHandle = gl.glCreateProgram();

        if (shaderProgramHandle != 0)  {
            // Подключаем вершинный шейдер к программе.
            gl.glAttachShader(shaderProgramHandle, vertexShaderHandle);
            // Подключаем фрагментный шейдер к программе.
            gl.glAttachShader(shaderProgramHandle, fragmentShaderHandle);
            // Подключаем атрибуты цвета и положения
//            GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
//            GLES20.glBindAttribLocation(programHandle, 1, "a_Color");
            // Объединяем оба шейдера в программе.
            gl.glLinkProgram(shaderProgramHandle);
            // Получаем статус линковки
            final int[] linkStatus = new int[1];
            gl.glGetProgramiv(shaderProgramHandle, GLCommon.GL_LINK_STATUS, linkStatus, 0);

            // Если линковка не удалась - удаляем программу
            if (linkStatus[0] == 0)  {
                String error = gl.glGetProgramInfoLog(shaderProgramHandle);
                Log.error("Unable to link shader program! Error - "+error);
                gl.glDeleteProgram(shaderProgramHandle);
                shaderProgramHandle = 0;
                throw new RuntimeException("Error linking shader!");
            }

            // получим адреса обязательных атрибутов, которые должны присутствовать в шейдере
            mpvMatrixUniformLocation = gl.glGetUniformLocation(shaderProgramHandle, "u_MVPMatrix");  // адрес униформы матрицы
            textureUniformLocation = gl.glGetUniformLocation(shaderProgramHandle,"u_Texture");   // адрес униформы текстуры

            positionAttrLocation = gl.glGetAttribLocation(shaderProgramHandle, "a_Position");             // адреса атрибуов передаваемых в шейдер
            colorAttrLocation = gl.glGetAttribLocation(shaderProgramHandle, "a_Color");
            texCoordAttrLocation = gl.glGetAttribLocation(shaderProgramHandle,"a_TexCoordinate");
        }
    }


    public int getProgramId(){
        return shaderProgramHandle;
    }
}

