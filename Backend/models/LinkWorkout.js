const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const LinkWorkoutSchema = new mongoose.Schema({

    CoachId: { type: String, required: true },
    WorkoutId: { type: Schema.Types.ObjectId, ref: 'AddTraining' },
    Date: { type: String, required: true },


}
);
module.exports = LinkWorkout = mongoose.model("LinkWorkout", LinkWorkoutSchema);


