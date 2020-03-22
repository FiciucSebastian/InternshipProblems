package domain;

import java.util.Random;

public enum VisitPurpose {
    consultation(30,  50),
    prescription(20, 20),
    treatment(40, 35);

    private final Integer time;
    private final Integer money;
    private final float report;

    VisitPurpose(Integer time, Integer money) {
        this.time = time;
        this.money = money;
        this.report = (float)money/(float)time ;
    }

    public float getReport() {
        return report;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getMoney() {
        return money;
    }

    public static VisitPurpose getRandomVisitPurpose() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
