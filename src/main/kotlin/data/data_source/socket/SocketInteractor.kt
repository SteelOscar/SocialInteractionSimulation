package data.data_source.socket

interface SocketInteractor {

    fun interceptResponseAuthCode(): String
    fun interceptResponseAccessToken(): String
}