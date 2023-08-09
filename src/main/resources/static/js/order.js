const requestOrder = () => {
  const credentials = sessionStorage.getItem('accessToken');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/login';
    return;
  }

  // TODO: [4단계] 장바구니 상품 추가 스펙에 맞게 변경
  fetch('/order', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${credentials}`,
      'Content-Type': 'application/json'
    }
  }).then((response) => {
    return response.json();
  }).then((data) => {
    console.log(data);
    window.location.href = '/order-detail/' + data.orderId;
  }).catch((error) => {
    console.error(error);
  });
}
