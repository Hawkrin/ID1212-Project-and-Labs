const mongoose = require("mongoose");

const postsSchema = new mongoose.Schema({
    type: {
        type: String,
        required: true,
        default: "default"
    },
    title: {
        type: String,
        unique: true,
        required: true,
    },
    body: {
        type: String,
        required: true,
    },
    date: {
        type: Date,
        required: true,
    },
    owner: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "user"
    }
})

module.exports = mongoose.model("posts", postsSchema)