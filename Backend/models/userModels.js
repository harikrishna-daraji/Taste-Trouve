const mongoose = require("mongoose");
//setting up the user schema - format of how the user objects are going to stored in the database.
const userSchema = new mongoose.Schema({
    email: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    displayname: { type: String },
    userType: {
        type: String,
        enum: [
            "coach",
            "athlete",
        ],
    },
})
module.exports = User = mongoose.model("User", userSchema);