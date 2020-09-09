package com.app.playup.user.model

class UserLoginModel(
    var username: String = "default",
    var password: String = "default"
) {}

class UserLoginResponseDataModel(
    var id: String = "default",
    var photo: String = "defaultUserPhoto.jpeg",
    var username: String = "default",
    var user_full_name: String = "default",
    var gender: String = "default",
    var email: String = "default",
    var password: String = "default",
    var rank_id:String="default",
    var rank_user_match_count:String= "default",
    var rank_user_grade_count:String= "default",
    var created: String = "default",
    var updated: String = "default"
) {}