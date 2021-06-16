//creating the routes
const router = require("express").Router();
const Restaurants = require("../models/Restaurants");
// connection for registering a user

router.post("/register", async (req, res) => {
  try {
    let {
      restaurantName,
      email,
      password,
      fcmToken,
      phoneNumber,
      address,
      status,
      userType,
    } = req.body;

    // validation

    if (!email || !password) {
      return res.status(400).json({ msg: "Not all fields have been entered." });
    }

    const existingUser = await Restaurants.findOne({ email: email });
    if (existingUser)
      return res
        .status(400)
        .json({ msg: "An account with this email already exists." });

    const newUser = new Restaurants({
      restaurantName,
      email,
      password,
      fcmToken,
      phoneNumber,
      address,
      status,
      userType,
    });

    const savedUser = await newUser.save();
    res.json(savedUser);
  } catch (err) {
    res.status(500).json({ sporterror: err.message });
  }
});
// //login
// router.post("/login", async (req, res) => {
//   try {
//     const { email, password } = req.body;

//     // validate
//     if (!email || !password)
//       return res.status(400).json({ msg: "Not all fields have been entered." });

//     const user = await Restaurants.findOne({
//       email: email,
//       password: password,
//     });

//     res.json(user);
//   } catch (err) {
//     res.status(500).json({ error: err.message });
//   }
// });
// //delete
// router.delete("/delete", auth, async (req, res) => {
//   try {
//     const deletedUser = await Restaurants.findByIdAndDelete(req.user);
//     res.json(deletedUser);
//   } catch (err) {
//     res.status(500).json({ error: err.message });
//   }
// });

module.exports = router;
