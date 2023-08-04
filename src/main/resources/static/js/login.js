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
    if (!response.ok) {
      response.json().then(body => {
        alert(body.message);
      });
    } else {
      alert("로그인 성공");
    }
    return response.json();
  }).then((data) => {
    const {accessToken} = data;
    sessionStorage.setItem('accessToken', accessToken);
    window.location.href = '/';
  }).catch((error) => {
    console.error(error);
  });

});
