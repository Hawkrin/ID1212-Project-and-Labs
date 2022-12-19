//Model
const User = require("../model/user.model")

/**
 * Register user by taking username, password and email.
 * Encrypting password with hash and salt.
 * Returning a promise, resolve if successfully register user.
 * Reject if probles occured registering user. 
 * 
 * @param {String} username 
 * @param {String} password 
 * @param {String} email 
 * @returns {Promise}
 */
const register = (username, password, email) => {

    User.userAlreadyExists(username, email)
        .then((userExists) => {

            if (userExists) { return reject("User Already exists"); }

            const newUser = new User({
                username,
                password,
                email
            })

            newUser.save((error) => {
                if (error) { console.log(error); reject(error.message); }
                resolve(newUser)
            })
        })
        .catch((error) => {
            return reject(error.message);
        })

}

module.exports = { register }