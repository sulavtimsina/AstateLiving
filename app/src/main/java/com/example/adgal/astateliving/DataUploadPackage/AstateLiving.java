package com.example.adgal.astateliving.DataUploadPackage;


import android.app.Application;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class AstateLiving extends Application {

    private static String userEmail;
    private static String userDisplayName;
    private static FirebaseUser firebaseUser;

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        AstateLiving.userEmail = userEmail;
    }

    public static String getUserDisplayName() {
        return userDisplayName;
    }

    public static void setUserDisplayName(String userDisplayName) {
        AstateLiving.userDisplayName = userDisplayName;
    }

    public static FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public static void setFirebaseUser(FirebaseUser firebaseUser) {
        AstateLiving.firebaseUser = firebaseUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
