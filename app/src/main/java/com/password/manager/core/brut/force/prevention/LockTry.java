package com.password.manager.core.brut.force.prevention;

/**
 * Created by Clemens on 06.01.2015.
 */
public class LockTry {
    public static final int FIRST_STRIKE = 3;

    public static final int SECOND_STRIKE = 7;

    public static final int THIRD_STRIKE = 10;

    public static final int FOURTH_STRIKE = 13;

    public static final int FIFTH_STRIKE = 15;

    public static boolean isBlock(int iVal) {
        return iVal == FIRST_STRIKE || iVal == SECOND_STRIKE || iVal == THIRD_STRIKE || iVal == FOURTH_STRIKE || iVal == FIFTH_STRIKE;
    }
}
