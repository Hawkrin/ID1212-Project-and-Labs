const mongoose = require("mongoose");

const postsSchema = new mongoose.Schema({
    type: {
        type: String,
        required: true,
        default: "default"
    },
    title: {
        type: String,
        unique: false,
        required: true,
    },
    body: {
        type: String,
        required: true,
    },
    date: {
        type: Date,
        required: true,
        default: new Date()
    },
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "user"
    }
})

module.exports = mongoose.model("posts", postsSchema)