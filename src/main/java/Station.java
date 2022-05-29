public class Station {
    private final String numberLine;
    private final String nameStation;

    public Station(String nameStation, String numberStation) {
        this.nameStation = nameStation;
        this.numberLine = numberStation;
    }

    public String getNameStation() {
        return nameStation;
    }

    public String getNumberLine() {
        return numberLine;
    }

    @Override
    public String toString() {
        return "Станция: " + nameStation
                + ", "
                + "Номер линии: " + numberLine + "\n";
    }
}
