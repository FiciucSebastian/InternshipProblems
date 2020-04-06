package generator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GeneratorUtils {

    static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    static <T> List<T> shuffleList(List<T> stringsForCompany){
        Collections.shuffle(stringsForCompany, new Random());
        return stringsForCompany;
    }

    static String getRandomStringGenerator(int targetStringLength){
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

    public static double generateRandomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static LocalDateTime generateRandomDate(LocalDateTime dateTime, int dayRange){
        LocalDateTime dateMin = dateTime;
        LocalDate randomDate = null;
        long randomDay = 0;
        Calendar calendar = Calendar.getInstance();
        Date date = Date.from( dateMin.atZone( ZoneId.systemDefault()).toInstant());
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, dayRange);

        Date dateMmax = calendar.getTime();

        LocalDateTime dateMax = Instant.ofEpochMilli( dateMmax.getTime() )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
        

        long minDay = dateMin.toLocalDate().toEpochDay();
        long maxDay = dateMax.toLocalDate().toEpochDay();

        if(dayRange < 0) {
            randomDay = ThreadLocalRandom.current().nextLong(maxDay, minDay + 1);
        }

        else {
            randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay + 1);
        }

        randomDate = LocalDate.ofEpochDay(randomDay);
        LocalDateTime dueDate = randomDate.atStartOfDay();

        return dueDate;
    }
}
