const router = require("express").Router();
const admin = require("firebase-admin");
const Product = require("../models/Product");
const Order = require("../models/Order");
const Cart = require("../models/Cart");
const moment = require("moment");
// const OrderItem = require("../models/OrderItem");

router.post("/add", async (req, res) => {
  try {
    const { userId, addressId, delivery, tax, total, Products, restaurantId } =
      req.body;

    const newOrder = new Order({
      userId,
      addressId,
      delivery,
      orderStatus: "pending",
      tax,
      total,
      products: Products,
      orderDate: moment().format("LL"),
      restaurantId,
    });

    const savedOrder = await newOrder.save();

    const deleteCart = await Cart.deleteMany({ userId });

    res.json(savedOrder);
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

router.post("/getOrderByOwner", async (req, res) => {
  const { restaurantId, orderStatus } = req.body;

  const order = await Order.find({
    restaurantId,
    orderStatus,
  }).populate("userId addressId");
  res.json(order);
});

router.post("/getOrderByUser", async (req, res) => {
  const { userId } = req.body;

  const order = await Order.find({
    userId,
  }).populate("userId addressId");

  res.json(order);
});

router.post("/getOrderById", async (req, res) => {
  const { orederId } = req.body;

  const order = await Order.find({
    _id: orederId,
  });

  res.json(order);
});

router.post("/getReportByOwner", async (req, res) => {
  const { restaurantId } = req.body;

  const order = await Order.find({
    restaurantId,
    orderStatus: "accepted",
  }).select("total");

  let values = order.map(function (element) {
    return element.total;
  });

  const sum = values.reduce(function (sum, number) {
    const updatedSum = sum + number;
    return updatedSum;
  }, 0);

  res.json({ total: sum.toString() });
});

router.put("/UpdateOrderStatus", async (req, res) => {
  let { orderId, updateStatus } = req.body;

  if (updateStatus == "accepted") {
    const order = await Order.find({
      _id: orderId,
    }).populate("userId");

    var payload = {
      data: {
        title: "Order Accepted",
        message: "HURRY !! Your order is now proccsed by owner",
      },
    };

    admin.messaging().sendToDevice(order[0].userId.fcmToken, payload);

    for (var key in order[0].products) {
      const selectedProduct = await Product.find({
        _id: order[0].products[key].id,
      });

      await Product.updateOne(
        { _id: order[0].products[key].id },
        {
          quantity:
            parseInt(selectedProduct[0].quantity) -
            parseInt(order[0].products[key].quantity),
        },
        function (err, docs) {
          if (err) {
          } else {
          }
        }
      );
    }
  }

  await Order.updateOne(
    { _id: orderId },
    { orderStatus: updateStatus },
    function (err, docs) {
      if (err) {
        res.send(err);
      } else {
        res.send("Updated");
      }
    }
  );
});

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
