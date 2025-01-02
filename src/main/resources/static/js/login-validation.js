function loginForm() {
    const userId = document.getElementById("userid").value;
    const password = document.getElementById("password").value;
    const userData = {
        "userid": userId,
        "password": password
    }

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        },
        body: new URLSearchParams(userData),
    })
        .then(response => response.text())
        .then(result => {
            if (result === "200") {
                console.log("SUCCESS");
                alert("로그인 성공");
                return "/index"; // 폼 제출 진행
            }
        })
        .catch(error => {
            alert(userId + "아이디 또는 비밀번호가 틀렸습니다.\n다시 시도 해주세요." + password);
            console.error(userId + "아이디 또는 비밀번호가 틀렸습니다." + password);
        });
}