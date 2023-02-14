package shpp.myapplication.colivery.data.network

data class UserModel(
    val nickname: String,
    val telegram: String? = null,
    val phone: String? = null,
    val image: String? = null
)
