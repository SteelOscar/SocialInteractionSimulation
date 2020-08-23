package data.data_source.db.postgres

import common.AppConstant
import common.orEmpty
import data.data_source.db.postgres.model.Users
import data.data_source.db.postgres.model.UserProfile
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Date
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import javax.inject.Inject

class PostgresInteractorImpl @Inject constructor() : PostgresInteractor {

    private val db = Database.connect(
        url = "jdbc:postgresql://${AppConstant.POSTGRES_HOST}:${AppConstant.POSTGRES_PORT}/$dataBase",
        driver = "org.postgresql.Driver",
        user = username,
        password = password
    )

    override fun insertUser(user: UserProfile) {

        transaction {

            SchemaUtils.create(Users)

            Users.insert {

                it[id] = user.id
                it[username] = user.username
                it[serializedPrivateKey] = user.serializedPrivateKey
                it[gettingStarted] = user.gettingStarted
                it[disableMail] = user.disableMail
                it[language] = user.language
                it[email] = user.email
                it[encryptedPassword] = user.encryptedPassword
                it[resetPasswordToken] = "a"
                it[rememberCreatedAt] = DateTime()
                it[signInCount] = 1
                it[current_sign_in_at] = DateTime()
                it[last_sign_in_at] = DateTime()
                it[current_sign_in_ip] = "a"
                it[last_sign_in_ip] = "a"
                it[created_at] = DateTime()
                it[updated_at] = DateTime()
                it[invited_by_id] = 0
                it[authenticationToken] = user.authenticationToken
                it[unconfirmed_email] = "a"
                it[confirm_email_token] = "a"
                it[locked_at] = DateTime()
                it[showCommunitySpotlightInStream] = user.showCommunitySpotlightInStream
                it[auto_follow_back] = false
                it[auto_follow_back_aspect_id] = 0
                it[hidden_shareables] = "a"
                it[reset_password_sent_at] = DateTime()
                it[last_seen] = DateTime()
                it[remove_after] = DateTime()
                it[export] = "a"
                it[exported_at] = DateTime()
                it[exporting] = false
                it[stripExif] = user.stripExif
                it[exported_photos_file] = "a"
                it[exported_photos_at] = DateTime()
                it[exportingPhotos] = false
                it[colorTheme] = user.colorTheme
                it[post_default_public] = false
                it[consumed_timestep] = 0
                it[otp_required_for_login] = false
                it[otp_backup_codes] = "a"
                it[plain_otp_secret] = "a"
            }
        }
    }

    override fun getUser(id: Int) = transaction {

        Users.select { Users.id.eq(id) }.map {

            UserProfile(
                id = it[Users.id],
                username = it[Users.username],
                serializedPrivateKey = it[Users.serializedPrivateKey],
                gettingStarted = it[Users.gettingStarted],
                authenticationToken = it[Users.authenticationToken],
                disableMail = it[Users.disableMail],
                language = it[Users.language],
                email = it[Users.email],
                encryptedPassword = it[Users.encryptedPassword],
                showCommunitySpotlightInStream = it[Users.showCommunitySpotlightInStream],
                stripExif = it[Users.stripExif],
                exportingPhotos = it[Users.exportingPhotos],
                colorTheme = it[Users.colorTheme]
            )
        }.first()
    }

    override fun getLastUser() = transaction {

        Users.selectAll().map {

            UserProfile(
                id = it[Users.id],
                username = it[Users.username],
                serializedPrivateKey = it[Users.serializedPrivateKey],
                gettingStarted = it[Users.gettingStarted],
                authenticationToken = it[Users.authenticationToken],
                disableMail = it[Users.disableMail],
                language = it[Users.language],
                email = it[Users.email],
                encryptedPassword = it[Users.encryptedPassword],
                showCommunitySpotlightInStream = it[Users.showCommunitySpotlightInStream],
                stripExif = it[Users.stripExif],
                exportingPhotos = it[Users.exportingPhotos],
                colorTheme = it[Users.colorTheme]
            )
        }.last()
    }

    override fun removeUser(username: String) {

        transaction { Users.deleteWhere { Users.username.eq(username) } }
    }
}