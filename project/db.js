const mongoose = require("mongoose")

const connect = () => {
    return mongoose.connect("mongodb+srv://" + process.env.USER + ":" + process.env.PASSWORD + "@mongocluster.mbbww.mongodb.net/kth-forum-db?retryWrites=true&w=majority")
}

module.exports = { connect }