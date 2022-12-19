const mongoose = require("mongoose")

const roleSchema = mongoose.model("role", new mongoose.Schema({
    name: {
        type: String
    }
}))

module.exports = roleSchema;