const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const studentSchema = new Schema({
    name: { type: String, required: true },
    grade: {type: Number, required: true}
});

const Student = mongoose.model("Student", studentSchema);

Student.createIndexes();
module.exports = Student;