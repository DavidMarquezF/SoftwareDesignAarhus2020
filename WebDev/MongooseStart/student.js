const mongoose = require("mongoose");
mongoose.connect("mongodb://localhost/test", {useNewUrlParser: true});

const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  // we're connected!
  console.log("Connection to mongodb susccessful")

  const studentSchema = new mongoose.Schema({name: {type: String, required: true}, studentNum: {unique: true, required: true, index: true, type: Number}});
  const Student = mongoose.model("Student", studentSchema);
  Student.createIndexes();
 /* const student = new Student({name: "Test", studentNum:1});
  student.save(function(err, result) {
      if(err) console.error(err);
      console.log(result);
  });
  const student1 = new Student({name: "Test1", studentNum:2});
  student1.save(function(err, result) {
      if(err) console.error(err);
      console.log(result);
  });
  const student2 = new Student({name: "Test2", studentNum:3});
  student2.save(function(err, result) {
      if(err) console.error(err);
      console.log(result);
  });

  Student.update({name: "Test"}, {$set: {name:"Hello World"}}, function(err, usr){
      if(err) throw err;
      console.log(usr);
  });*/

  Student.findOneAndDelete({studentNum: 2}, function (err, docs) { 
    if (err){ 
        console.log("Error");
        console.log(err) 
    } 
    else{ 
        console.log("Deleted User : ", docs); 
    } 
});
});

