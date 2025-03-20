package contacts;

public class Main {
    public static void main(String[] args) {
        Engine.args = args;
        Engine engine = new Engine();
        engine.run();
    }
}

