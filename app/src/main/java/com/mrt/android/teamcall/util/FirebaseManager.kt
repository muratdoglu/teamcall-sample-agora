package com.mrt.android.teamcall.util

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.mrt.android.teamcall.data.model.User
import java.lang.Exception

class FirebaseManager(val context: Context) {
    var firestore: FirebaseFirestore? = null
    var nick: String = ""
    val userList = MutableLiveData<ArrayList<User>>()
    fun getFireStore(): FirebaseFirestore {
        if (firestore == null) {
            firestore = FirebaseFirestore.getInstance()
        }
        return firestore!!
    }

    fun signIn(ready: (Boolean) -> Unit) {
        var mAuth = FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                ready(true)
            } else {
                ready(false)
            }
        }
    }

    fun register(nick: String, pushNotificationToken: String, success: () -> Unit) {
        signIn() {
            var user = User()
            user.id = ""
            user.uID = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            user.token = pushNotificationToken
            user.nick = nick
            user.free = true
            var doc = getFireStore()
                .collection("Users")
                .document(user.uID)
            doc.set(user)
            success()
        }
    }

    fun observeUsers(
    ) {
        val ref = getFireStore()
            .collection("Users")
        ref.addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
            var cacheUserList: ArrayList<User> = arrayListOf()
            querySnapshot?.documents?.forEach { documentSnapshot ->
                var user = documentSnapshot
                    ?.toObject(User::class.java)
                if (user?.uID != Settings.Secure.getString(
                        context.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                )
                    user?.let { cacheUserList.add(it) }
            }
            userList.postValue(cacheUserList)
        }
    }


    fun updateFreeState(state: Boolean) {
        getFireStore().collection("Users").document(
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        ).update("free", state)

    }

    fun updateCaller(user: User) {
        getFireStore().collection("Users").document(
            user.uID
        ).update(
            "caller", Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ),
            "callerName", nick

        )
    }

    fun getUserDetail(returnUser: (User) -> Unit) {
        if (FirebaseAuth.getInstance().currentUser == null) return
        getFireStore()
            .collection("Users")
            .whereEqualTo(
                "uid", Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            )
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot?.documents?.forEach { documentSnapshot ->
                    var user = querySnapshot?.documents?.get(0)
                        ?.toObject(User::class.java)
                    user?.let { returnUser(it) }
                }
            }
            .addOnFailureListener {
                System.out.println("")
            }
    }

}