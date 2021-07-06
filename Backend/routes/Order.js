const router = require("express").Router();
const admin = require("firebase-admin");
const Product = require("../models/Product");
const Order = require("../models/Order");
const OrderItem = require("../models/OrderItem");

router.post("/add", async (req, res) => {
  try {
    const { userId, addressId, delivery, Products } = req.body;

    const newOrder = new Order({
      userId,
      addressId,
      delivery,
      orderStatus: "pending",
      tax,
      total,
    });
    const savedOrder = await newOrder.save();

    for (var key in Products) {
      const newOrderItem = new OrderItem({
        orderId: savedOrder._id,
        userId,
        productId: Products[key].productid,
        quantity: Products[key].quant,
      });
      await newOrderItem.save();
    }

    res.json(savedOrder);
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

// router.post("/getCartByUser", async (req, res) => {
//   const { userId } = req.body;

//   const cart = await Cart.find({
//     userId,
//   }).populate("productId");
//   res.json(cart);
// });

// router.delete("/delete", async (req, res) => {
//   const { cartId } = req.body;
//   try {
//     const deleteCart = await Cart.findByIdAndDelete(cartId);
//     res.json(deleteCart);
//   } catch (err) {
//     res.status(500).json({ error: err.message });
//   }
// });

module.exports = router;
