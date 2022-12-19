const mongoose = require("mongoose")
const validator = require('validator');

//Utils
const bcrypt = require("bcrypt")

const userSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true,
        unique: true
    },

    password: {
        type: String,
        required: true,
    },

    email: {
        type: String,
        required: true,
        lowercase: true,
        unique: true
    }
})

userSchema.pre("save", function (next) {
    //User instance
    const user = this;

    //Checks if user has changed password, if not just continue.
    if (!user.isModified("password") && !user.isModified("email")) return next();

    //Validate password
    if (!validator.isLength(user.password, { min: 8, max: 16 })) return next(new Error("Password is not strong."))

    //Validate Email
    if (!validator.isEmail(user.email)) return next(new Error("Email is not a valid email."))

    //Generate encrypted password and setting password to the hash.
    const salt = bcrypt.genSaltSync(10);
    const encryptedPassword = bcrypt.hashSync(user.password, salt);
    user.password = encryptedPassword;

    next();
})

userSchema.statics.userAlreadyExists = function (username, email) {
    return this.findOne({ $or: [{ username }, { email }] })
}

module.exports = mongoose.model("user", userSchema)