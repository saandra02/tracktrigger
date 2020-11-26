window.onload=function(){
    render();
};
function render() {
    window.recapchaVerifier = new firebase.auth.RecapchaVerifier('recaptcha-container');
    recapchaVerifier.render();
}
function phoneAuth() {
    var number = document.getElementById('number').value;
    firebase.auth().SignInWithPhoneNumber(number,window.recapchaVerifier).then(function(confirmationResult){
        window.confirmationResult=confirmationResult;
        coderesult=confirmationResult;
        console.log(coderesult);
        alert("Message Sent");
    }).catch(function (error){
        alert(error.message);
    });
}

function codeverify() {
    var code = document.getElementById('verificationCode').value;
    coderesult.confirm(code).then(function(result) {
        alert("Successfully Registered!");
        var user = result.user;
        console.log(user);
    }).catch(function (error){
        alert(error.message);
    });
}