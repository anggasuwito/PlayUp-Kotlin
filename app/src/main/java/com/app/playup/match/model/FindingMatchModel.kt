package com.app.playup.match.model

class FindingMatchModel (
    var id: String = "default",
    var photo: String = "defaultUserPhoto.jpeg",
    var username: String = "default",
    var user_full_name: String = "default",
    var gender: String = "default",
    var email: String = "default"
){}


class FindingMatchResponseDataModel(
    var match_id:String = "default",
    var match_players:String
){}

