//creating the routes
const router = require("express").Router();
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const auth = require("../middleware/auth");
const User = require("../models/userModels");
// connection for registering a user

router.post("/register", async (req, res) => {
  try {
    let { email, password, displayname, userType } = req.body;

    // validation

    if (!email || !password) {
      return res
        .status(400).json({ msg: "Not all fields have been entered." });
    }


    const existingUser = await User.findOne({ email: email });
    if (existingUser)
      return res
        .status(400)
        .json({ msg: "An account with this email already exists." });

    if (!displayname) displayname = email;


    const newUser = new User({
      email,
      password,
      displayname,
      userType
    });

    const savedUser = await newUser.save();
    res.json(savedUser);
  } catch (err) {
    res.status(500).json({ sporterror: err.message });
  }
});
//login
router.post("/login", async (req, res) => {
  try {
    const { email, password } = req.body;


    // validate
    if (!email || !password)
      return res.status(400).json({ msg: "Not all fields have been entered." });

    const user = await User.findOne({ email: email, password: password });

    res.json(user);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});
//delete
router.delete("/delete", auth, async (req, res) => {
  try {
    const deletedUser = await User.findByIdAndDelete(req.user);
    res.json(deletedUser);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});
//token validation
router.post("/tokenIsValid", async (req, res) => {
  try {
    const token = req.header("x-auth-token");
    if (!token) return res.json(false);

    const verified = jwt.verify(token, process.env.JWT_SECRET);
    if (!verified) return res.json(false);

    const user = await User.findById(verified.id);
    if (!user) return res.json(false);

    return res.json(true);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// getting the user that is currently logged in 
router.get("/getCoach", async (req, res) => {
  const users = await User.find({ userType: 'coach' });
  res.json(users);
});

module.exports = router;