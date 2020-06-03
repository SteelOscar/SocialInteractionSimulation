package domain.usecase

import domain.Neo4jRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(

    private val neo4jRepository: Neo4jRepository

) {

    fun getUsers() = neo4jRepository.createUsers()

    fun close() = neo4jRepository.closeNeo4j()
}