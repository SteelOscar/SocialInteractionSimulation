package data.data_source.socket

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import javax.inject.Inject

class SocketInteractorImpl @Inject constructor() : SocketInteractor {

    private val socket by lazy { ServerSocket(65080) }

    override fun interceptResponseAuthCode(): String {

        return createAndListenSocket("GET /?code=", 11..74)
    }

    override fun interceptResponseAccessToken(): String {

        return createAndListenSocket("qwerty", 0..100)
    }

    private fun createAndListenSocket(filter: String, range: IntRange): String {

        val s = socket.accept()

        val inBuffer = BufferedReader(InputStreamReader(s.getInputStream()))

        var result = ""

        while (true) {

            val temp = inBuffer.readLine() ?: break

            if (temp.contains(filter)) {

                result = temp.substring(range)
                break
            }
        }

        return result
    }
}