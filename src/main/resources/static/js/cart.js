const addCartItem = (productId) => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    fetch('/api/cart-items', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${credentials}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({productId: productId})
    }).then((response) => {
        const json = response.json();
        if (!response.ok) {
            return json.then((errorData) => {
                throw new Error(errorData.message);
            });
        }
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        alert(error);
    });
}

const updateCartItemQuantity = (id, quantity) => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    fetch('/api/cart-items/' + id, {
        method: 'PATCH',
        headers: {
            'Authorization': `Bearer ${credentials}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({quantity: quantity})
    }).then((response) => {
        const json = response.json();
        if (!response.ok) {
            return json.then((errorData) => {
                throw new Error(errorData.message);
            });
        }
        window.location.reload();
    }).catch((error) => {
        alert(error);
    });
}

const removeCartItem = (id) => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    fetch('/api/cart-items/' + id, {
        method: 'DELETE',
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
        window.location.reload();
    }).catch((error) => {
        alert(error);
    });
}
