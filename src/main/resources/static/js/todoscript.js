function DisplayTasks(){
$.ajax({
url: "/demo/todo/read",
headers: {"Authorization": sessionStorage.getItem('access_token')},
type: "POST",
success: function(data){
for(var i=0; i<data.length; i++)
{
var li = document.createElement("li");
li.prop('id', data[i].id);
var inputValue = data[i].taskName;
var t = document.createTextNode(inputValue);
li.appendChild(t);
var span = document.createElement("SPAN");
var txt = document.createTextNode("\u00D7");
span.className = "close";
span.appendChild(txt);
li.appendChild(span);      
}
}
}

// Create a new list item when clicking on the "Add" button
function AddTask() {
var li = document.createElement("li");
var inputValue = document.getElementById("myInput").value;
var t = document.createTextNode(inputValue);
li.appendChild(t);
if (inputValue === '') {
alert("You must write something!");
} 
else {
var data = {};
data.task_name = inputValue;
data.task_priority = "low";
$.ajax({
url: "/demo/todo/create",
headers: {"Authorization": sessionStorage.getItem('access_token')},
type: "POST",
datatype: "json",
data: data,
success: function(data){
alert("Added successfully");
DisplayTasks();
}
})
.fail(function(){
alert("Task creation failed!");
})
}
}

