const requestOrder = () => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    // TODO: [4단계] 장바구니 아이템 추가 스펙에 맞게 변경
    fetch('/orders', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${credentials}`,
            'Content-Type': 'application/json',
        }
    }).then((response) => {
        const orderId = response.headers.get('Location').split("/")[2];
        window.location.href = '/order-detail/' + orderId;
    }).catch((error) => {
        console.error(error);
    });
}
