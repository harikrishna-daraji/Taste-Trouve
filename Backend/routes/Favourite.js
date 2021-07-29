const router = require("express").Router();
const admin = require("firebase-admin");
const Product = require("../models/Product");
const Favourite = require("../models/favourite");

router.post("/toogleFav", async (req, res) => {
  try {
    const { userId, productId, flag } = req.body;

    const newFav = new Favourite({
      userId,
      productId,
    });
    if (flag == "true") {
      const savedFavourite = await newFav.save();
    } else {
      const fav = await Favourite.find({
        userId,
        productId,
      });
      console.log(fav);
      if (fav.length > 0) {
        const deleteCart = await Favourite.findByIdAndDelete(fav[0]._id);
      }
    }
    return res.json("added");
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

router.post("/getCartByUser", async (req, res) => {
  const { userId } = req.body;

  const cart = await Favourite.find({
    userId,
  }).populate("productId");
  res.json(cart);
});

router.delete("/delete", async (req, res) => {
  const { cartId } = req.body;
  try {
    const deleteCart = await Favourite.findByIdAndDelete(cartId);
    res.json(deleteCart);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;
