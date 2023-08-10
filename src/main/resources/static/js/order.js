const requestOrder = () => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    fetch('/orders', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${credentials}`,
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        if (response.ok) {
            // 주문이 성공적으로 생성되었을 경우, 주문 상세 페이지로 이동
            window.location.href = '/order.html'; // Replace with your actual order detail page URL
        } else {
            // 주문 생성에 실패한 경우
            console.error('Failed to create order.');
        }
    }).catch((error) => {
        console.error('An error occurred:', error);
    });
}
