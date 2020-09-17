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

    /**
     * Postgres DB
     */
    const val POSTGRES_HOST = "192.168.1.10"
    const val POSTGRES_PORT = 5432

    const val CLIENT_ID = "df7f0be036987a528e62d7c337b1f28d"
    const val CLIENT_SECRET = "5b3ff4f721152f4aa5f684f226b7fc47d759ab326d49d990bc3da829473e2842"
    const val REDIRECT_URI = "http://192.168.1.130:65080"
    const val AUTHENTICATION_CODE_URL = "${BASE_URL_API}api/openid_connect/authorizations/new?response_type=code&client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&scope=openid%20public%3Aread%20conversations"
    const val ACCESS_TOKEN_URL = "${BASE_URL_API}api/openid_connect/access_tokens"
}