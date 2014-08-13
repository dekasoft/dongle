package com.dekagames.dongle;


public abstract  class Screen {
    protected final Game game;
    public boolean isInitialized;

    public Screen(Game game) {
        this.game = game;
    }


    public Game getGame(){
        return game;
    }


    public void update(float deltaTime){}


    public abstract void draw (Graphics graphics);


    /**
     * Этот метод нужен для инициализации экрана, требующей обязательного наличия инициализированного OpenGL.
     * Для этого он вызывается из потока рендерера, а не из главного потока, в отличие от конструктора.
     * Перед прорисовкой экрана проверяется был ли он инициализирован, и, если не был, вызывается этот метод,
     * который должен, в случае успешной инициализации экрана устанавливать переменную isInitialized в true.
     * Этот метод используется главным образом для загрузки текстур. Текстуры, будучи однажды загруженными
     * больше не нуждаются в повторной загрузке - их перезагружает класс Game. Поэтому обычно нет необходимости
     * вызывать метод initialize больше одного раза для каждого экземпляра экрана.
     */
    public boolean initialize() {
        isInitialized = true;
        return isInitialized;
    }


    public void pause(){}


    public void resume(){}


    public void dispose(){}
}

