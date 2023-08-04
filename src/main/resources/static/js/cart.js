const addCartItem = (productId) => {
  console.log(productId);
  const credentials = sessionStorage.getItem('accessToken');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/login';
    return;
  }

  fetch('/api/cartProduct', {
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

const updateCartItemQuantity = (cartProductId, quantity) => {
  const credentials = sessionStorage.getItem('accessToken');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/login';
    return;
  }

  fetch(`/api/cartProduct/${cartProductId}`, {
    method: 'PATCH',
    headers: {
      'Authorization': `Bearer ${credentials}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      'quantity': quantity
    })
  }).then((response) => {
    alert('수정되었습니다.')
    window.location.reload();
  }).catch((error) => {
    console.error(error);
  });
}

const removeCartItem = (cartProductId) => {
  const credentials = sessionStorage.getItem('accessToken');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/login';
    return;
  }

  fetch(`/api/cartProduct/${cartProductId}`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${credentials}`,
      'Content-Type': 'application/json'
    }
  }).then((response) => {
    alert('삭제되었습니다.')
    window.location.reload();
  }).catch((error) => {
    console.error(error);
  });
}
