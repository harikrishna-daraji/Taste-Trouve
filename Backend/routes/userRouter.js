//creating the routes
const router = require("express").Router();
const User = require("../models/userModels");
// connection for registering a user

router.post("/register", async (req, res) => {
  try {
    let { email, password, displayname, fcmToken, phoneNumber, dateOfBirth } =
      req.body;

    // validation

    if (!email || !password) {
      return res.status(400).json({ msg: "Not all fields have been entered." });
    }

    const existingUser = await User.findOne({ email: email });
    if (existingUser)
      return res
        .status(400)
        .json({ msg: "An account with this email already exists." });

    const newUser = new User({
      email,
      password,
      displayname,
      fcmToken,
      phoneNumber,
      dateOfBirth,
    });

    const savedUser = await newUser.save();
    res.json(savedUser);
  } catch (err) {
    res.status(500).json({ AppError: err.message });
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
router.delete("/delete", async (req, res) => {
  try {
    const deletedUser = await User.findByIdAndDelete(req.user);
    res.json(deletedUser);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});
//token validation

router.put("/update", async (req, res) => {
  const data = req.body;

  await User.updateOne(
    { email: data.email },
    {
      ...data,
    }
  );

  const updatedUser = await User.findById(data.userId);

  return res.send({ data: updatedUser });
});

module.exports = router;
