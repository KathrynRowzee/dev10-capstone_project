const url = "http://localhost:8080/api/vacation";



export async function findAllVacations() {
    const response = await fetch(url);
    if (response.status === 200) {
        return await response.json();
    }
    
    return Promise.reject("not 200 OK");
}

export async function findVacationsByUser(userId) {
    const response = await fetch(`${url}/uid/${userId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function findById(vacationId) {
    const response = await fetch(`${url}/vid/${vacationId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}


export async function add(vacation) {
    const jwt = localStorage.getItem("jwt");
    if (!jwt) {
        return Promise.reject("forbidden");
    }
    
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": `Bearer ${jwt}`
        },
        body: JSON.stringify(vacation)
    }
    console.log(init);
    const response = await fetch(url, init);
    if (response.status === 201) {
        return await response.json();
    }

    const errors = await response.json();
    return Promise.reject(errors);
}

export async function update(vacation) {
    const jwt = localStorage.getItem("jwt");
    if (!jwt) {
        return Promise.reject("forbidden");
    }
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": `Bearer ${jwt}`
        },
        body: JSON.stringify(vacation)
    }
    const response = await fetch(`${url}/${vacation.vacationId}`, init);
    if (response.status !== 201) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
}

export async function deleteById(vacationId) {
const init = {
        method: "DELETE"
    }

    const response = await fetch(`${url}/${vacationId}`, init);
    if (response.status !== 204) {
        return Promise.reject("not 204 No Content");
    }
}
