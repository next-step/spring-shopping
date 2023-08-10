sessionStorage.removeItem('accessToken');
const form = document.getElementById('login-form');

form.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    let loginRequest = {};
    for (const entry of formData.entries()) {
        const [key, value] = entry;
        loginRequest[key] = value;
    }

    fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginRequest)
    }).then((response) => {
        const json = response.json();
        if (!response.ok) {
            return json.then((errorData) => {
                throw new Error(errorData.message);
            });
        }
        return json;
    }).then((data) => {
        const {token} = data;
        sessionStorage.setItem('accessToken', token);
        window.location.href = '/';
    }).catch((error) => {
        alert(error.message);
    });

});
