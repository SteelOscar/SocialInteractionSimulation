package common

object AppConstant {

    /**
     * Database Neo4j
     */
    var NEO4J_HOST = ""

    val BASE_URL_NEO4J
        get() = "bolt://$NEO4J_HOST"
    var USERNAME = "neo4j"
    var PASSWORD = "12345"

    /**
     * Network
     */
    var DIASPORA_HOST = ""

    val BASE_URL_API
        get() = "http://$DIASPORA_HOST:3000/"
    val BASE_URL_OAUTH
        get() = "http://$DIASPORA_HOST:3000/api/openid_connect"
    val LOGIN_URL
        get() = "http://$DIASPORA_HOST:3000/users/sign_in"

    /**
     * Postgres DB
     */
    const val POSTGRES_HOST = "192.168.1.10"
    const val POSTGRES_PORT = 5432

    /**
     * Started from ip
     */
    var LOCAL_IP = ""
    val REDIRECT_URI
        get() = "http://$LOCAL_IP:65080"

    val AUTHENTICATION_CODE_URL
        get() = "${BASE_URL_API}api/openid_connect/authorizations/new?response_type=code&client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&scope=openid%20private:read%20private:modify%20contacts:read%20contacts:modify%20public:modify%20conversations%20profile%20profile:modify"
    val ACCESS_TOKEN_URL
        get() = "${BASE_URL_API}api/openid_connect/access_tokens"

    var CLIENT_ID = ""
    var CLIENT_SECRET = ""
    var CURRENT_USER_TOKEN = "token"

    /**
     * Statistics params
     */
    var daysCount = 7
    var messageCountPerDay = 6
    var postPercentage = 50

    /**
     * DAY COEFFICIENTS
     */
    var MONDAY = 0.144
    var TUESDAY = 0.137
    var WEDNESDAY = 0.139
    var THURSDAY = 0.146
    var FRIDAY = 0.137
    var SATURDAY = 0.147
    var SUNDAY = 0.15
}