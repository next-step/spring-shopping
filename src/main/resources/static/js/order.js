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
        const orderId = response.headers.get('Location').split("/")[3];
        window.location.href = '/order-detail/' + orderId;
    }).catch((error) => {
        console.error(error);
    });
}
