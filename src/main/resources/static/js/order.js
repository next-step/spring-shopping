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
        if (!response.ok) {
            console.error('Failed to create order.');
        }
        return response.json();
    }).then(data =>  {
        window.location.href = '/orders/' + data.id;
    }).catch((error) => {
        console.error('An error occurred:', error);
    });
}
