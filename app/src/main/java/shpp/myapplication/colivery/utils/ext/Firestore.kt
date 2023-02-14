package shpp.myapplication.colivery.utils.ext

import com.google.firebase.firestore.FirebaseFirestore

const val FIRESTORE_USERS_COLLECTION = "users"

val FirebaseFirestore.usersCollection
    get() = collection(FIRESTORE_USERS_COLLECTION)

fun FirebaseFirestore.userDocument(document: String) = usersCollection.document(document)