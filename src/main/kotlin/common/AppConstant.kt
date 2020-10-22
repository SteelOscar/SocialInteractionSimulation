package common

object AppConstant {

    /**
     * Database Neo4j
     */
    const val BASE_URL_NEO4J = "bolt://192.168.1.30:"
    const val USERNAME = "neo4j"
    const val PASSWORD = "12345"

    /**
     * Network
     */
    const val BASE_URL_API = "http://192.168.1.10:3000/"

    const val BASE_URL_OAUTH = "http://192.168.1.10:3000/api/openid_connect"

    var CURRENT_USER_TOKEN = "token"

    /**
     * Postgres DB
     */
    const val POSTGRES_HOST = "192.168.1.10"
    const val POSTGRES_PORT = 5432

    var CLIENT_ID = ""
    var CLIENT_SECRET = ""
    const val REDIRECT_URI = "http://192.168.1.130:65080"
    const val LOGIN_URL = "http://192.168.1.10:3000/users/sign_in"
    val AUTHENTICATION_CODE_URL
        get() = "${BASE_URL_API}api/openid_connect/authorizations/new?response_type=code&client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&scope=openid%20public%3Aread%20conversations%20profile%20contacts:modify"
    const val ACCESS_TOKEN_URL = "${BASE_URL_API}api/openid_connect/access_tokens"
}