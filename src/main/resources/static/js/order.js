const requestOrder = () => {
  const credentials = sessionStorage.getItem('accessToken');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/login';
    return;
  }

  // TODO: [4단계] 장바구니 아이템 추가 스펙에 맞게 변경
  fetch('/api/order', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${credentials}`,
      'Content-Type': 'application/json'
    }
  }).then((response) => {
    // TODO: [4단계] 주문이 성공하면 주문 상세 페이지로 이동 (order.html 사용)
    response.json().then(body => {
      console.log(body);
      window.location.href = `/order-detail/${body.id}`;
    });
  }).catch((error) => {
    console.error(error);
  });
}
