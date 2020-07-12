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

    var auth_token = "token"

    const val BASE_URL_OAUTH = "http://192.168.1.10:3000/api/openid_connect/clients"
}