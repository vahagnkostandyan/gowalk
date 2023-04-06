package io.gowalk.gowalk.util;

import io.gowalk.gowalk.model.Interest;
import io.gowalk.gowalk.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Random;

@UtilityClass
public class GeneralUtils {
    public static String randomlyGetInterest(User user) {
        final Random random = new Random();
        final List<Interest> interests = user.getInterests();
        int index = random.nextInt(interests.size());
        final Interest interest = interests.get(index);
        return interest.getInterest();
    }
}
