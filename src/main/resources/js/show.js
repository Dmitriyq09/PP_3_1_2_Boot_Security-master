let currentUser = "";



fetch("/api/user/show")
    .then(res => res.json())
    .then(data => {
        currentUser = data;
        console.log(data)
        showOneUser(currentUser);
        document.getElementById("UsernameHead").innerText= currentUser.username;
        document.getElementById("RolesHead").innerText = currentUser.roles.map(role => role.name).join(" ");
    })

function showOneUser(user) {
    let temp = "";
    temp += "<tr>"
    temp += "<td>" + user.id + "</td>"
    temp += "<td>" + user.firstname + "</td>"
    temp += "<td>" + user.lastname + "</td>"
    temp += "<td>" + user.username + "</td>"
    temp += "<td>" + user.password + "</td>"
    temp += "<td>" + user.roles.map(role => role.name).join(" ") + "</td>"
    temp += "</tr>"
    document.getElementById("userBody").innerHTML = temp;
}