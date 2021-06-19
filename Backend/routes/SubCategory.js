const router = require("express").Router();

const SubCategory = require("../models/SubCategory");

router.post("/add", async (req, res) => {
  try {
    const { name, image, categoryId } = req.body;

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

router.post("/getById", async (req, res) => {
  try {
    const { categoryId } = req.body;

    const subCategory = await SubCategory.find({ categoryId: categoryId });
    res.json(category);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

router.get("/get", async (req, res) => {
  const category = await SubCategory.find();
  res.json(category);
});

module.exports = router;
