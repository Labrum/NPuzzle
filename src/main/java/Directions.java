enum Directions {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static Directions randomDirection() {
        return Directions.values()[(int) (Math.random() * Directions.values().length)];
    }
}
