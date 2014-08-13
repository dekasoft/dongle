package com.dekagames.dongle;

/**
 *  Самый общий интерфейс приложения. Единственный метод этого интерфейса должны реализовывать классы,
 *  отвечающие за запуск игры (наследника класса {@link com.dekagames.dongle.Game Game} на различных
 *  операционных системах. Примеры реализации этого интерфейса в классах JoglApp и AndroidApp.
 *  Они выполнены отдельными модулями в проекте движка, так как для каждого модуля необходимо указывать
 *  свой единственный SDK, а SDK у них разный: для AndroidApp - это AndroidSDK, а для JoglApp -
 *  стандартный JDK. Для создания desktop-ной версии игры используется библиотека JOGL. Подробную
 *  документацию к ней можно найти на ее <a href="http://www.jogamp.org">сайте</a>
 *  <br><br>
 *  Вкратце, запуск desktop-ной версии игры в первом приближении осуществляется так:
 *  <br><br>
 *  <pre>
 *  {@code
 *      public class JoglStarter extends JoglApp {
 *
 *          public static void main(String[] args) {
 *              new JoglStarter().start(new MainGame());
 *          }
 *      }
 *  }
 *  </pre>
 *  <br>
 *  Запуск android-ной версии игры осуществляется практически аналогично:
 *  <br><br>
 *  <pre>
 *  {@code
 *  public class AndroidStarter extends AndroidApp {
 *      public void onCreate(Bundle savedInstanceState) {
 *          super.onCreate(savedInstanceState);
 *          start(new MainGame());
 *      }
 *  }
 *  }
 *  </pre>
 *
 *
 */
public interface Application {

    /**
     * Основной и единственный метод, который реализуют классы JoglApp и  AndroidApp. Запускает
     * само приложение. То есть инициализирует поля класса {@link com.dekagames.dongle.Game Game}
     * и создает (или использует уже готовый) главный цикл игры. Дальнейшая работа с движком делается
     * исключительно в классе {@link com.dekagames.dongle.Game Game}.
     * @param game предварительно созданный класс игры.
     * @param title заголовок окна.
     */
    public void start(Game game, String title);
}
