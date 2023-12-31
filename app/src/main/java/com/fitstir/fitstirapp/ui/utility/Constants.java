package com.fitstir.fitstirapp.ui.utility;

public class Constants {
    public static final String TIMED_NOTIFICATION_TAG = "TimedNotifications";
    public static final String LAST_ON_DESTROY_TAG = "LastOnDestroy";
    public static final int REQUEST_CODE_PERMISSION =3;
    public static final long MILLISECS_PER_SEC = 1000L;
    public static final long MILLISECS_PER_DAY = 86400000L;
    public static final String DATE_TIME_FORMAT ="yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT ="yyyy-MM-dd";
    public static final long MEGA_BYTE = 200000 * 2000;

    public static final class FOOD_DATA_BASE_PARSER {
        public static final String APP_ID = "ab4a541a";
        public static final String APP_KEY = "5435c61858cc52c36783f9f36fc2f1f2";
    }

    public static final class RECIPE_V2 {
        public static final String APP_ID = "456d7882";
        public static final String APP_KEY = "772e2aed7f585b9eeacffbfaacf59de6";
    }
    public static final class MAP_ACTION {
        public static final int RUN_ACTION = 1;
        public static final int HISTORY_ACTION = 2;
    }
    public static  final class YOGA_ID{
        public static final int  BEGINNER = 1;
        public static final int  MEDIUM = 2;
        public static final int  EXPERT = 3;
        public static final int  EXPLORE = 4;
        public static final int  QUICK_START = 5;
        public static final int  LEARN = 6;
        public static final int  STANDING = 7;
        public static final int  BALANCE = 8;
        public static final String BEGINNER_POSE = "Beginner Poses";
        public static final String INTERMEDIATE_POSE = "Intermediate Poses";
        public static final String EXPERT_POSE = "Expert Poses";
        public static final String EXPLORE_POSES = "Explore Poses";
        public static final String LEARN_POSES = "Learn Poses";
        public static final String STANDING_POSE = "Standing Poses";
        public static final String BALANCE_POSE = "Balance Poses";
        public static final String QUICK_START_POSES = "Quick Start";
        public static final String BEGINNER_YOGA = "BeginnerYoga";
        public static final String INTERMEDIATE_YOGA = "IntermediateYoga";
        public static final String EXPERT_YOGA = "ExpertYoga";
        public static final int CLICKTOADD = 0;
        public static final int CLICKTOSUBTRACT = 1;


    }

    public static final String MAPS_API_KEY = "AIzaSyCVnELwsZKk_41fNEdIrkpjc1S6GUUSvsc";

    public static final class WORKOUT_BODYPART{
        public  static final String UPPER_BODY = "UpperBody";
        public static final String LOWER_BODY = "LowerBody";
        public static final String WEIGHT_LIFTING = "Weights";
        public static final String BEGINNER_CIRCUIT = "BeginnerCircuit";
        public static final String TONER_CIRCUIT = "TonerCircuit";
        public static final String ACTIVE_CIRCUIT = "ActiveCircuit";
        public static final String BB_CIRCUIT = "BBCircuit";
        public static final String WARRIORS = "WarriorsCircuit";
        public static final String BCCIRCUIT ="BootCampCircuit";
        public static final String CHALLENGE = "FitChallenge";
    }

    public static final class ACTIVITY{
        public static final int NON_ACTIVE = 1;
        public static final int LIGHT_ACTIVE = 3;
        public static final int ACTIVE = 6;
    }

    public static final class INTENSITY{
        public static final int TONE_UP = 1;
        public static final int BULK_UP = 3;
        public static final int STRENGTHEN = 2;
    }

    public static final class GOAL{
        public static final int GAIN_WEIGHT = 5;
        public static final int MAINTAIN = 0;
        public static final int GAIN_MUSCLE = 35;
        public static final int MODIFY_DIET = 1;
        public static final int MANAGE_STRESS = 20;
        public static final int INCREASE_CARDIO = 2;
        public static final int LOSE_WEIGHT = 50;
    }
    public static final class WORKOUT_DAYS{
        public static final int defaultDays = 3;
        public static final int  intenseDays = 6;
        public static final int liteDays = 2;
        public static final int loseMinDays = 4;
        public static final int loseMedDays = 5;
        public static final int loseMaxDays =6;
    }
}
