const mongoose = require("mongoose");
const AddTrainingSchema = new mongoose.Schema({
    // No:{type:String, required:true},
    Exercise: { type: String, required: true },
    Sets: { type: String, required: true },
    Reps: { type: String, required: true },
    Tempo: { type: String, required: true },
    Rest: { type: String, required: true },
    // Stretching_exercise:{type:String,required:true},
    // SetsF:{type:String,required:true},
    // Hold:{type:String,required:true},
    // startDate:{type:Date,required:true},
    // endDate:{type:Date,required:true},
    userId: { type: String, required: true },


}
);
module.exports = AddTraining = mongoose.model("AddTraining", AddTrainingSchema);


