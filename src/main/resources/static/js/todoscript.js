function doDelClick(span, id) {
   span.onclick = function() {
      DeleteTask(id);
   }
};
function doChkClick(li, id) {
   li.onclick = function() {
      CheckTask(id);
   }
};
function DisplayTasks(){
$("#myUL").empty();
$.ajax({
url: "/demo/todo/read",
headers: {"Authorization": sessionStorage.getItem('access_token')},
type: "GET",
success: function(data){
for(var i=0; i<data.length; i++)
{
var li = document.createElement("li");
li.setAttribute("id", data[i].id);
li.setAttribute("title", data[i].taskStatus);
doChkClick(li, data[i].id);
var inputValue = data[i].taskName;
var t = document.createTextNode(inputValue);
if(data[i].taskStatus==true){
  li.style.textDecoration = "line-through";
  li.style.fontStyle = "italic";
  li.style.fontColor = "#A9A9A9";
}
li.appendChild(t);
document.getElementById("myUL").appendChild(li);
document.getElementById("myInput").value="";
var span = document.createElement("SPAN");
doDelClick(span, data[i].id);
var txt = document.createTextNode("\u00D7");
span.className = "close";
span.appendChild(txt);
li.appendChild(span);
}
}
})
.fail(function(){
alert("Task loading failed!");
})
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
DisplayTasks();
}
})
.fail(function(){
alert("Task creation failed!");
})
}
}

function DeleteTask(id){
  data = {}
  data.id = id;
  $.ajax({
  url: "/demo/todo/delete",
  headers: {"Authorization": sessionStorage.getItem('access_token')},
  type: "DELETE",
  datatype: "json",
  data: data,
  success: function(data){
  DisplayTasks();
  }
  })
  .fail(function(){
  alert("Task deletion failed!");
  })
}

function CheckTask(id){
  var task = document.getElementById(id);
  var status = task.getAttribute("title");
  console.log(status);
  if (status=="true"){
    status = "false";
  } else {
    status = "true";
  }
  console.log(status);
  data = {};
  data.id = id;
  data.task_status = status;
  $.ajax({
  url: "/demo/todo/update",
  headers: {"Authorization": sessionStorage.getItem('access_token')},
  type: "POST",
  datatype: "json",
  data: data,
  success: function(data){
  DisplayTasks();
  }
  })
  .fail(function(){
  alert("Task update failed!");
  })


}
