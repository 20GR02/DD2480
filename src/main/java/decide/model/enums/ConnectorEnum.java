package decide.model.enums;

public enum ConnectorEnum {
    NOTUSED, ORR, ANDD;

    public static ConnectorEnum random() {
        return values()[(int) (Math.random() * values().length)];
    }
}
