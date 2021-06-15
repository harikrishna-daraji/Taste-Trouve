const router = require("express").Router();

const SubCategory = require("../models/SubCategory");

router.post("/add", async (req, res) => {
  try {
    const { name, image } = req.body;

    const newSubCategory = new SubCategory({
      name,
      image,
      categoryId,
    });
    const savedSubCategory = await newSubCategory.save();
    res.json(savedSubCategory);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

router.get("/get", async (req, res) => {
  const category = await SubCategory.find();
  res.json(category);
});

module.exports = router;
