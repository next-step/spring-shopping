const requestOrder = () => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    fetch('/api/orders', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${credentials}`,
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        const json = response.json();
        if (!response.ok) {
            return json.then((errorData) => {
                throw new Error(errorData.message);
            });
        }
        window.location.href = response.headers.get("Location");
    }).catch((error) => {
        alert(error.message);
    });
}
