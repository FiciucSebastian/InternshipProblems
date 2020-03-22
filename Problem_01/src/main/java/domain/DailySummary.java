package domain;

public class DailySummary {
    private final int numberOfPatients;
    private final int time;
    private final int money;

    public DailySummary(int numberOfPatients, int time, int money) {
        this.numberOfPatients = numberOfPatients;
        this.time = time;
        this.money = money;
    }

    public int getNumberOfPatients() {
        return numberOfPatients;
    }

    public int getTime() {
        return time;
    }

    public int getMoney() {
        return money;
    }

}
