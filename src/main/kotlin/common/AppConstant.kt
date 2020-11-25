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
    var POSTGRES_USERNAME = "diaspora"
    var POSTGRES_PASSWORD = "12345"
    var POSTGRES_PORT = 5432

    /**
     * SSH configuration
     */
    var SSH_USERNAME = "diaspora"
    var SSH_PASSWORD = "12345"
    var SSH_PORT = 22

    var SNIFFING_INTERFACE = ""

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
    var MONDAY = 0.0
    var TUESDAY = 0.0
    var WEDNESDAY = 0.0
    var THURSDAY = 0.0
    var FRIDAY = 0.0
    var SATURDAY = 0.0
    var SUNDAY = 0.0

    var GENERATOR_PATH = ""
}