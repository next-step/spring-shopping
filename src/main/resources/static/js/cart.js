const addCartItem = (productId) => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    fetch('/api/carts', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${credentials}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "productId": productId
        })
    }).then((response) => {
        if (!response.ok) {
            response.json().then(body => {
                alert(body.message)
            })
        } else {
            alert('장바구니에 담았습니다.');
        }
    }).catch((error) => {
        console.error(error);
    });
}

const updateCartItemQuantity = (id, quantity) => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    // TODO: [3단계] 장바구니 아이템 수량 변경 스펙에 맞게 변경
    fetch(`/api/carts/${id}/quantity`, {
        method: 'PATCH',
        headers: {
            'Authorization': `Bearer ${credentials}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({quantity})
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}

const removeCartItem = (id) => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    // TODO: [3단계] 장바구니 아이템 삭제 스펙에 맞게 변경
    fetch(`/api/carts/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${credentials}`,
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
