const Student = require("../models/student");


module.exports.addStudentGrade = async function(req,res,next){
    const student = new Student({name: req.body.name, grade: req.body.grade});
    try{
        await student.save();
        res.send(student);
    }
    catch(err){
        res.status(500)
        .json({"errorMessage": "Error adding student"});
    }
    
}

module.exports.students = async function(req,res,next){
    try{
        const students = await Student.find(); 
        res.send(students);
    }
    catch(err){
        res.status(500)
        .json({"errorMessage": "Error retrieving students"})
    }
    
}

module.exports.deleteStudent = async function(req, res, next){
    try{
        const result = await Student.findByIdAndDelete(req.params.id);
        res.send(result);
    }
    catch(err){
        return next(err);
    }
}