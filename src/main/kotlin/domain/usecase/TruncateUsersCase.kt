package domain.usecase

import data.data_source.db.postgres.PostgresInteractor
import javax.inject.Inject

class TruncateUsersCase @Inject constructor(

    private val postgresInteractor: PostgresInteractor

) {

    fun execute() {

        postgresInteractor.clearUsers()
    }
}