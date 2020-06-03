package data.mapper

import data.data_source.api.model.body.UpdateUserBody
import domain.model.UpdateUserDomain
import javax.inject.Inject

class UpdateUserDomainToBodyMapper @Inject constructor() : (UpdateUserDomain) -> UpdateUserBody {

    override fun invoke(domain: UpdateUserDomain) = with(domain) {

        UpdateUserBody(bio, birthday, gender, name)
    }
}