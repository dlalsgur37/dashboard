// .js

// 폼 제출 시 이벤트 처리
document.getElementById('signupForm').addEventListener('submit', function(event) {
    event.preventDefault();  // 폼 제출을 막고, 대신 AJAX로 처리

    var password = document.getElementById('password').value;
    var confirmPassword = document.getElementById('confirmPassword').value;

    // 비밀번호와 비밀번호 확인이 일치하는지 확인
    if (password !== confirmPassword) {
        document.getElementById('errorMessage').textContent = "비밀번호가 일치하지 않습니다.";
        return;
    }

    // 폼 데이터 준비
    var formData = {
        userId: document.getElementById('user-id').value,
        password: password,
        email: document.getElementById('email').value,
        username: document.getElementById('user-name').value,
        department: document.getElementById('department').value,
        cellPhone: document.getElementById('cellphone').value,
        officePhone: document.getElementById('office-phone').value,
    };

    // AJAX 요청
    $.ajax({
        url: '/register',  // Spring Controller의 URL 경로
        type: 'POST',
        data: JSON.stringify(formData),  // 폼 데이터
        contentType: 'application/json',  // JSON 형식으로 데이터 전송
        success: function(response) {
            // 서버에서 성공 응답을 받으면
            alert('회원가입이 완료되었습니다!');
            window.location.href = '/login';  // 로그인 페이지로 리다이렉션
        },
        error: function(xhr, status, error) {
            // 오류 처리
            document.getElementById('errorMessage').textContent = "회원가입 중 오류가 발생했습니다.";
        }
    });
});
