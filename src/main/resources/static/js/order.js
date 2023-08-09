const requestOrder = () => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    fetch('/api/order', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${credentials}`,
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        if (response.ok) {
            window.location.href = response.headers.get("Location");
        } else {
            alert("주문에 실패하였습니다.")
        }
    }).catch((error) => {
        console.error(error);
    });
}
