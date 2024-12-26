let customerTable = null;
let customerData = null;

function initCustomerTable() {
    fetch('/customer', {
            method: 'GET'
        }
    )
        .then(response => response.json())
        .then(data => {
            customerTable = $('#customerTable').DataTable({
                data: data,
                columnDefs: [
                    {
                        orderable: false,
                        render: DataTable.render.select(),
                        targets: 0,
                        width: '30px'
                    },
                    {targets: 1, className: "customer-name"},
                    {
                        targets: 2,
                        className: "customer-information",
                        orderable: false,
                        searchable: false
                    }
                ],
                columns: [
                    {"data": null},
                    {"data": "name"},
                    {"data": "information"}
                ],
                processing: true,
                select: {
                    style: 'single',
                    selector: 'td:first-child'
                },
                order: [1, 'asc']
            });
        })
        .then(() => {
            $(".customer-information").each(function (index) {
                if (index !== 0) {
                    new toastui.Editor.factory({
                        el: this,
                        height: 'auto',
                        viewer: true,
                        initialValue: this.innerHTML
                    });
                }
            });
        })
        .then(() => {
            customerTable.on('select', function (e, dt, type, indexes) {
                $('#deleteButton').attr('disabled', false);
            });

            customerTable.on('deselect', function (e, dt, type, indexes) {
                $('#deleteButton').attr('disabled', true);
            });

            customerTable.on('dblclick', 'tr', function () {
                customerData = customerTable.row(this).data();
                $('#customer-name').text(customerData.name);
                let infoModal = $('#infoModal');
                new toastui.Editor.factory({
                    el: document.querySelector('#info-editor'),
                    height: window.innerHeight*3/7 + 'px',
                    viewer: true,
                    initialValue: customerData.information
                });
                infoModal.css('display', 'block');
            });
        })
        .catch(error => {
            console.error(error);
        });
}

$(document).ready(function () {
    initCustomerTable();
});

document.addEventListener('DOMContentLoaded', function () {
    //등록
    const openRegisterBtn = document.getElementById('openRegisterButton');
    const registerModal = document.getElementById('registerModal');
    const closeRegisterModalBtn = document.getElementById('closeRegisterModal');
    const registerBtn = document.getElementById('registerCustomer');

    //고객사 정보
    const infoModal = document.getElementById('infoModal');
    const closeInfoModalBtn = document.getElementById('closeInfoModal');
    const modifyInfoBtn = document.getElementById('modifiyCustomer');
    const applyInfoBtn = document.getElementById('applyCustomer');

    //알림창
    const noticeModal = document.getElementById('noticeModal');
    const noticeMessage = document.getElementById('noticeMessage');
    const closeNoticeBtn = document.getElementById('closeNoticeBtn');

    //오류창
    const errorModal = document.getElementById('errorModal');
    const errorMessage = document.getElementById('errorMessage');
    const closeErrorBtn = document.getElementById('closeErrorBtn');

    const deleteBtn = document.getElementById('deleteButton');

    let customerEditor = null;
    const editor = new toastui.Editor({
        el: document.querySelector('#register-editor'), // 에디터를 적용할 요소 (컨테이너)
        height: '400px',                        // 에디터 영역의 높이 값 (OOOpx || auto)
        initialEditType: 'markdown',            // 최초로 보여줄 에디터 타입 (markdown || wysiwyg)
        initialValue: '',                       // 내용의 초기 값으로, 반드시 마크다운 문자열 형태여야 함
        previewStyle: 'vertical'                // 마크다운 프리뷰 스타일 (tab || vertical)
    });

    // 등록창 열기
    openRegisterBtn.addEventListener('click', function () {
        registerModal.style.display = 'block';
    });

    // 등록창 닫기
    closeRegisterModalBtn.addEventListener('click', function () {
        registerModal.style.display = 'none';
        editor.setMarkdown('');
    });

    // 오류 팝업 닫기
    closeErrorBtn.addEventListener('click', function () {
        errorModal.style.display = 'none';
    });

    // 알림창 닫기
    closeNoticeBtn.addEventListener('click', function () {
        noticeModal.style.display = 'none';
    });

    // 고객사 정보창 닫기
    closeInfoModalBtn.addEventListener('click', function () {
        if (this.textContent === '닫기')
            infoModal.style.display = 'none';
        else if (this.textContent === '취소') {
            new toastui.Editor.factory({
                el: document.querySelector('#info-editor'),
                height: window.innerHeight*3/7 + 'px',
                viewer: true,
                initialValue: customerData.information
            });
            this.textContent = '닫기';
            modifyInfoBtn.style.display = 'block';
            applyInfoBtn.style.display = 'none';
        }
    });

    // 고객사 정보 수정
    modifyInfoBtn.addEventListener('click', function () {
        customerEditor = new toastui.Editor.factory({
            el: document.querySelector('#info-editor'),
            height: window.innerHeight*3/7 + 'px',
            initialEditType: 'markdown',
            previewStyle: 'vertical',
            initialValue: customerData.information
        });
        closeInfoModalBtn.textContent = '취소';
        modifyInfoBtn.style.display = 'none';
        applyInfoBtn.style.display = 'block';
    });

    applyInfoBtn.addEventListener('click', function () {
        const editCustomerData = {
            id: customerData.id,
            name: customerData.name,
            information: customerEditor.getMarkdown()
        };
        fetch('/customer', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify(editCustomerData),
        })
            .then(response => response.text())
            .then(() => {
                noticeMessage.textContent = '고객사 정보가 성공적으로 수정되었습니다.';
                noticeModal.style.display = 'block';
                closeInfoModalBtn.textContent = '닫기';
                modifyInfoBtn.style.display = 'block';
                applyInfoBtn.style.display = 'none';
            })
            .then(() => {
                customerData.information = editCustomerData.information;
                customerEditor = new toastui.Editor.factory({
                    el: document.querySelector('#info-editor'),
                    height: window.innerHeight*3/7 + 'px',
                    viewer: true,
                    initialValue: customerData.information
                });
            })
            .then(() => {
                customerTable.destroy();
                initCustomerTable();
            })
            .catch(error => {
                if (error === 500) {
                    errorMessage.textContent = '수정 중 서버 오류가 발생했습니다.';
                    errorModal.style.display = 'block';
                }
            })
    });

    // 고객사 등록 버튼 클릭 (Ajax 요청)
    registerBtn.addEventListener('click', function () {
        const customerData = {
            name: document.getElementById('register-name').value,
            information: editor.getMarkdown(),
        };

        // Ajax 요청
        fetch('/customer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            },
            body: new URLSearchParams(customerData),
        })
            .then(response => response.text())
            .then(() => {
                noticeMessage.textContent = '고객사가 성공적으로 등록되었습니다.';
                noticeModal.style.display = 'block';
                registerModal.style.display = 'none'; // 모달 닫기
                document.getElementById('customerForm').reset(); // 폼 초기화
                editor.setMarkdown('');
                customerTable.destroy();
                initCustomerTable();
            })
            .catch(error => {
                if (error === 409) {
                    errorMessage.textContent = '중복된 고객사 이름이 있습니다. 다시 시도해주세요.';
                    errorModal.style.display = 'block';
                } else if (error === 500) {
                    errorMessage.textContent = '추가 중 알 수 없는 오류가 발생했습니다. 다시 시도해주세요.';
                    errorModal.style.display = 'block';
                }
            });
    });

    // 삭제 버튼
    deleteBtn.addEventListener('click', function () {
        // Ajax 요청
        fetch('/customer/'+customerTable.row('.selected').data().id, {
            method: 'DELETE'
        })
            .then(response => response.text())
            .then(() => {
                noticeMessage.textContent = '고객사가 성공적으로 삭제되었습니다.';
                noticeModal.style.display = 'block';
                customerTable.destroy();
                initCustomerTable();
            })
            .catch(error => {
                if (error === 500) {
                    errorMessage.textContent = '삭제 중 서버 오류가 발생했습니다.';
                    errorModal.style.display = 'block';
                }
            });
    });
});