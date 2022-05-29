public class Line {
    private final String numberLine;
    private final String nameLine;

    public Line(String nameLine, String numberLine) {
        this.nameLine = nameLine;
        this.numberLine = numberLine;
    }

    public String getNameLine() {
        return nameLine;
    }

    public String getNumberLine() {
        return numberLine;
    }

    @Override
    public String toString() {
        return "Линия: " + nameLine
                + "\n"
                + "Номер линии: " + numberLine
                + "\n\n";
    }
}
