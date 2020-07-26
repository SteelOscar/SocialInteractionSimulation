package data.data_source.db.postgres

import common.AppConstant
import common.orEmpty
import data.data_source.db.postgres.model.Users
import data.data_source.db.postgres.model.UserProfile
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
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
                it[authenticationToken] = user.authenticationToken
                it[showCommunitySpotlightInStream] = user.showCommunitySpotlightInStream
                it[exporting] = user.exporting
                it[stripExif] = user.stripExif
                it[colorTheme] = user.colorTheme
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
                resetPasswordToken = it.getOrNull(Users.resetPasswordToken) ?: "",
                rememberCreatedAt = it.getOrNull(Users.rememberCreatedAt) ?: DateTime(),
                signInCount = it[Users.signInCount],
                currentSignInAt = it.getOrNull(Users.current_sign_in_at) ?: DateTime(),
                lastSignInAt = it.getOrNull(Users.last_sign_in_at) ?: DateTime(),
                currentSignInIp = it[Users.current_sign_in_ip],
                lastSignInIp = it[Users.last_sign_in_ip],
                createdAt = it.getOrNull(Users.created_at) ?: DateTime(),
                updatedAt = it.getOrNull(Users.updated_at) ?: DateTime(),
                invitedById = it.getOrNull(Users.invited_by_id) ?: 0,
                unconfirmedEmail = it.getOrNull(Users.unconfirmed_email) ?: "",
                confirmEmailToken = it.getOrNull(Users.confirm_email_token) ?: "",
                lockedAt = it[Users.locked_at],
                showCommunitySpotlightInStream = it[Users.showCommunitySpotlightInStream],
                autoFollowBack = it[Users.auto_follow_back],
                autoFollowBackAspectId = it.getOrNull(Users.auto_follow_back_aspect_id) ?: 0,
                hiddenShareables = it[Users.hidden_shareables],
                resetPasswordSentAt = it.getOrNull(Users.reset_password_sent_at) ?: DateTime(),
                lastSeen = it[Users.last_seen],
                removeAfter = it[Users.remove_after],
                export = it[Users.export],
                exportedAt = it[Users.exported_at],
                exporting = it[Users.exporting],
                stripExif = it[Users.stripExif],
                exportedPhotosFile = it[Users.exported_photos_file],
                exportedPhotosAt = it[Users.exported_photos_at],
                exportingPhotos = it[Users.exportingPhotos],
                colorTheme = it[Users.colorTheme],
                postDefaultPublic = it[Users.post_default_public],
                consumedTimestep = it.getOrNull(Users.consumed_timestep) ?: 0,
                otpRequiredForLogin = it.getOrNull(Users.otp_required_for_login) ?: false,
                otpBackupCodes = it[Users.otp_backup_codes],
                plainOtpSecret = it[Users.plain_otp_secret]
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
                resetPasswordToken = it[Users.resetPasswordToken],
                rememberCreatedAt = it[Users.rememberCreatedAt],
                signInCount = it[Users.signInCount],
                currentSignInAt = it[Users.current_sign_in_at],
                lastSignInAt = it[Users.last_sign_in_at],
                currentSignInIp = it[Users.current_sign_in_ip],
                lastSignInIp = it[Users.last_sign_in_ip],
                createdAt = it[Users.created_at],
                updatedAt = it[Users.updated_at],
                invitedById = it[Users.invited_by_id],
                unconfirmedEmail = it[Users.unconfirmed_email],
                confirmEmailToken = it[Users.confirm_email_token],
                lockedAt = it[Users.locked_at],
                showCommunitySpotlightInStream = it[Users.showCommunitySpotlightInStream],
                autoFollowBack = it[Users.auto_follow_back],
                autoFollowBackAspectId = it[Users.auto_follow_back_aspect_id],
                hiddenShareables = it[Users.hidden_shareables],
                resetPasswordSentAt = it[Users.reset_password_sent_at],
                lastSeen = it[Users.last_seen],
                removeAfter = it[Users.remove_after],
                export = it[Users.export],
                exportedAt = it[Users.exported_at],
                exporting = it[Users.exporting],
                stripExif = it[Users.stripExif],
                exportedPhotosFile = it[Users.exported_photos_file],
                exportedPhotosAt = it[Users.exported_photos_at],
                exportingPhotos = it[Users.exportingPhotos],
                colorTheme = it[Users.colorTheme],
                postDefaultPublic = it[Users.post_default_public],
                consumedTimestep = it[Users.consumed_timestep],
                otpRequiredForLogin = it[Users.otp_required_for_login],
                otpBackupCodes = it[Users.otp_backup_codes],
                plainOtpSecret = it[Users.plain_otp_secret]
            )
        }.last()
    }
}