let currentUser = "";
let tableUsers = [];
let deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
let editModal = new bootstrap.Modal(document.getElementById('editModal'));
let request = new Request("/api/admin", {
    method: "GET",
    headers: {
        'Content-Type': 'application/json',
    },
});
getUsers()

function getUsers() {
    fetch(request).then(res =>
        res.json()).then(data => {
        tableUsers = [];
        if (data.length >  0) {
            data.forEach(user => {
                tableUsers.push(user)
            })
        } else {
            tableUsers = [];
        }
        showUsers(tableUsers);
    })
}

fetch("/api/admin/show").then(res => res.json())
    .then(data => {
        currentUser = data;
        console.log(data)
        showOneUser(currentUser);
        document.getElementById("UsernameHead").innerText= currentUser.username;
        document.getElementById("RolesHead").innerText = currentUser.roles.map(role => role.name).join(" ");
    })

function showUsers(table) {
    let temp = "";
    table.forEach(user => {
        temp += "<tr>"
        temp += "<td>" + user.id + "</td>"
        temp += "<td>" + user.firstname + "</td>"
        temp += "<td>" + user.lastname + "</td>"
        temp += "<td>" + user.username + "</td>"
        temp += "<td>" + user.password + "</td>"
        temp += "<td>" + user.roles.map(role => role.name).join(" ") + "</td>"
        temp += "<td>" + `<a onclick='showEditModal(${user.id})' class="btn btn-outline-info" id="edit">Edit</a>` + "</td>"
        temp += "<td>" + `<a onclick='showDeleteModal(${user.id})' class="btn btn-outline-danger" id="delete">Delete</a>` + "</td>"
        temp += "</tr>"
        document.getElementById("bodyUsersAll").innerHTML = temp;
    })
}

function getRoles(list) {
    let userRoles = [];
    for (let role of list) {
        if (role === 2 || role.id === 2) {
            userRoles.push("ADMIN");
        }
        if (role === 1 || role.id === 1) {
            userRoles.push("USER");
        }
    }
    return userRoles.join(" , ");
}

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
    document.getElementById("bodyUsers").innerHTML = temp;
}

function rolesUser(event) {
    let rolesAdmin = {};
    let rolesUser = {};
    let roles = [];
    let allRoles = [];
    let sel = document.querySelector(event);
    for (let i = 0, n = sel.options.length; i < n; i++) {
        if (sel.options[i].selected) {
            roles.push(sel.options[i].value);
        }
    }
    if (roles.includes('2')) {
        rolesAdmin["id"] = 2;
        rolesAdmin["name"] = "ROLE_ADMIN";
        allRoles.push(rolesAdmin);
    }
    if (roles.includes('1')) {
        rolesUser["id"] = 1;
        rolesUser["name"] = "ROLE_USER";
        allRoles.push(rolesUser);
    }
    return allRoles;
}

document.getElementById('creatUser').addEventListener('submit', addNewUser);

function addNewUser(event) {
    event.preventDefault();

    let newUserForm = new FormData(event.target);
    let user = {
        id: null,
        firstname: newUserForm.get('firstname'),
        lastname: newUserForm.get('lastname'),
        username: newUserForm.get('username'),
        password: newUserForm.get('password'),
        roles: rolesUser("#roles")
    };

    let req = new Request("/api/admin", {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json'
        }
    });

    fetch(req)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('User added successfully', data);
            getUsers();
            event.target.reset();
            const triggerE1 = document.querySelector('#v-pills-tabContent button[data-bs-target="#nav-home"]');
            bootstrap.Tab.getInstance(triggerE1).show();
        })
        .catch(error => {
            console.error('Error adding user:', error);
            console.error('Server error message:', error.message);
        });
}

function showDeleteModal(id) {
    document.getElementById('closeDeleteModal').setAttribute('onclick', () => {
        deleteModal.hide();
        document.getElementById('deleteUser').reset();
    });

    let request = new Request("/api/admin/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    fetch(request).then(res => res.json()).then(deleteUser => {
            console.log(deleteUser);
            document.getElementById('delete-id').setAttribute('value', deleteUser.id);
            document.getElementById('delete-username').setAttribute('value', deleteUser.username);
            document.getElementById('delete-password').setAttribute('value', deleteUser.password);
            document.getElementById('delete-firstname').setAttribute('value', deleteUser.firstname);
            document.getElementById('delete-lastname').setAttribute('value', deleteUser.lastname);

            if (getRoles(deleteUser.roles).includes("USER") && getRoles(deleteUser.roles).includes("ADMIN")) {
                document.getElementById('rolesDelete1').setAttribute('selected', 'true');
                document.getElementById('rolesDelete2').setAttribute('selected', 'true');
            } else if (getRoles(deleteUser.roles).includes("USER")) {
                document.getElementById('rolesDelete1').setAttribute('selected', 'true');
            } else if (getRoles(deleteUser.roles).includes("ADMIN")) {
                document.getElementById('rolesDelete2').setAttribute('selected', 'true');
            }
            deleteModal.show();
        }
    );
    let isDelete = false;
    document.getElementById('deleteUser').addEventListener('submit', event => {
        event.preventDefault();
        if (!isDelete) {
            isDelete = true;
            let request = new Request("/api/admin/" + id, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            fetch(request).then(() => {
                getUsers();
            });
            document.getElementById('deleteUser').reset();
        }
        deleteModal.hide();
    });
}

function showEditModal(id) {
    let request = new Request("/api/admin/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    fetch(request).then(res => res.json()).then(editUser => {
            document.getElementById('edit-id').value = editUser.id;
            document.getElementById('edit-username').value = editUser.username;
            document.getElementById('edit-password').value = editUser.password;
            document.getElementById('edit-firstname').value = editUser.firstname;
            document.getElementById('edit-lastname').value = editUser.lastname;

            let roleIds = editUser.roles.map(role => role.id);
            document.getElementById('rolesEdit1').selected = roleIds.includes(1);
            document.getElementById('rolesEdit2').selected = roleIds.includes(2);

            editModal.show();
        }
    );
}

document.getElementById('editUser').addEventListener('submit', submitFormEditUser);

function submitFormEditUser(event) {
    event.preventDefault();

    let redUserForm = new FormData(event.target);
    let user = {
        id: redUserForm.get('id'),
        username: redUserForm.get('username'),
        password: redUserForm.get('password'),
        firstname: redUserForm.get('firstname'),
        lastname: redUserForm.get('lastname'),
        roles: rolesUser("#rolesRed")
    }

    let request = new Request("/api/admin", {
        method: 'PUT',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json',
        },
    });

    fetch(request).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    }).then(data => {
        console.log('User updated successfully', data);
        getUsers();
        event.target.reset();
        editModal.hide();
    }).catch(error => {
        console.error('Error updating user:', error);
        console.error('Server error message:', error.message);
    });
}