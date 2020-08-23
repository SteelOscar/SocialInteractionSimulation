package data.data_source.db.postgres.model

data class UserProfile(

    var id: Int,
    var username: String,
    var serializedPrivateKey: String,
    var gettingStarted: Boolean,
    var disableMail: Boolean,
    var language: String,
    var email: String,
    var encryptedPassword: String,
    var authenticationToken: String,
    var showCommunitySpotlightInStream: Boolean,
    var stripExif: Boolean,
    var exportingPhotos: Boolean,
    var colorTheme: String
)