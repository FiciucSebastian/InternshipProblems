package generator;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneratorUtils {

    static Pair<Integer,Integer> child = new Pair<>(0,1);
    static Pair<Integer,Integer> pupil = new Pair<>(2,7);
    static Pair<Integer,Integer> student = new Pair<>(8,18);
    static Pair<Integer,Integer> adult = new Pair<>(19,85);

    static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
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

    static List<Pair<Integer,Integer>> makingAgeCategoryList(){
        List<Pair<Integer,Integer>> copyOfAgeCategory = new ArrayList<>();
        copyOfAgeCategory.add(child);
        copyOfAgeCategory.add(pupil);
        copyOfAgeCategory.add(student);
        copyOfAgeCategory.add(adult);
        return copyOfAgeCategory;
    }
}
