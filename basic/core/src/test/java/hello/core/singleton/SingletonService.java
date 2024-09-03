package hello.core.singleton;

public class SingletonService {

    private static SingletonService instance = new SingletonService();

    private SingletonService() {}

    public static SingletonService getInstance() {
        return instance;
    }
}
