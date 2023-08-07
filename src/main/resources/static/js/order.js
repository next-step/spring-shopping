const requestOrder = () => {
    const credentials = sessionStorage.getItem('accessToken');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/login';
        return;
    }

    fetch('/order', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${credentials}`,
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        if (response.status === 400) {
            alert("장바구니에 물건이 없습니다.")
            window.location.reload();
        }
        return response.json();
    }).then((data) => {
      window.location.href = '/order/'+data.id+'/detail';
    }).catch((error) => {
        console.error(error);
    });
}