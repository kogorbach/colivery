package shpp.myapplication.colivery.data

data class UserModel(
    val email: String,
    val nickname: String,
    val telegram: String? = null,
    val phone: String? = null,
    val image: String? = null
)
