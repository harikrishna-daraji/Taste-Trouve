const router = require("express").Router();
const admin = require("firebase-admin");
const Product = require("../models/Product");
const Cart = require("../models/Cart");

router.post("/add", async (req, res) => {
  try {
    const { userId, productId, quantity } = req.body;

    const checkOldEntry = await Cart.find({
      userId,
      productId,
    });

    if (checkOldEntry.length > 0) {
      var myquery = { _id: checkOldEntry[0]._id };
      var newvalues = { $set: { quantity: checkOldEntry[0].quantity + 1 } };
      await Cart.updateOne(myquery, newvalues, function (err, res) {
        if (err) throw err;
      });
      return res.json({ data: "cartUpdated" });
    } else {
      const newCart = new Cart({
        userId,
        productId,
        quantity,
      });
      const savedCart = await newCart.save();
      res.json(savedCart);
    }
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

router.post("/getCartByUser", async (req, res) => {
  const { userId } = req.body;

  const cart = await Cart.find({
    userId,
  }).populate("productId");
  res.json(cart);
});

router.delete("/delete", async (req, res) => {
  try {
    const deleteCart = await Cart.findByIdAndDelete(req.cartId);
    res.json(deleteCart);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;
