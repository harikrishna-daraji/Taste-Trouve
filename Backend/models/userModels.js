const mongoose = require("mongoose");

const userSchema = new mongoose.Schema({
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  displayname: { type: String },
  fcmToken: { type: String },
  phoneNumber: { type: String },
  dateOfBirth: { type: String },
  isDriver: { type: Boolean, default: false },
  isOnline: { type: Boolean, default: false },
  isBussy: { type: Boolean, default: false },
});
module.exports = User = mongoose.model("User", userSchema);
