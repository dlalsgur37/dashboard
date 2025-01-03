let maintenanceTable = null;
let maintenanceData = null;

function initmaintenanceTable() {
    fetch('/maintenance', {
            method: 'GET'
        }
    )
        .then(response => response.json())
        .then(data => {
            maintenanceTable = $('#maintenanceTable').DataTable({
                data: data,
                columnDefs: [
                    {
                        orderable: false,
                        render: DataTable.render.select(),
                        targets: 0,
                        width: '30px'
                    },
                    {targets: 1, className: "maintenance-name"},
                    {targets: 2, className: "maintenance-request_date"},
                    {
                        targets: 3,
                        className: "maintenance-description",
                        orderable: false,
                        searchable: false

                    },
                    {
                        targets: 4,
                        className: "maintenance-solve",
                        orderable: false,
                        width: '40%',
                        searchable: false
                    }
                ],
                columns: [
                    {"data": null},
                    {"data": "name"},
                    {"data": "request_date"},
                    {"data": "description"},
                    {"data": "solve"}
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
            $(".maintenance-description").each(function (index) {
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
            maintenanceTable.on('select', function (e, dt, type, indexes) {
                $('#deleteButton').attr('disabled', false);
            });

            maintenanceTable.on('deselect', function (e, dt, type, indexes) {
                $('#deleteButton').attr('disabled', true);
            });

            maintenanceTable.on('dblclick', 'tr', function () {
                maintenanceData = maintenanceTable.row(this).data();
                $('#maintenance-name').text(maintenanceData.name);
                let infoModal = $('#infoModal');
                new toastui.Editor.factory({
                    el: document.querySelector('#info-editor'),
                    height: window.innerHeight*3/7 + 'px',
                    viewer: true,
                    initialValue: maintenanceData.description
                });
                infoModal.css('display', 'block');
            });
        })
        .catch(error => {
            console.error(error);
        });
}

$(document).ready(function () {
    initmaintenanceTable();
});

document.addEventListener('DOMContentLoaded', function () {
    //등록
    const openRegisterBtn = document.getElementById('openRegisterButton');
    const registerModal = document.getElementById('registerModal');
    const closeRegisterModalBtn = document.getElementById('closeRegisterModal');
    const registerBtn = document.getElementById('registerMaintenance');

    //내용
    const infoModal = document.getElementById('infoModal');
    const closeInfoModalBtn = document.getElementById('closeInfoModal');
    const modifyInfoBtn = document.getElementById('modifiyMaintenance');
    const applyInfoBtn = document.getElementById('applyMaintenance');

    //알림창
    const noticeModal = document.getElementById('noticeModal');
    const noticeMessage = document.getElementById('noticeMessage');
    const closeNoticeBtn = document.getElementById('closeNoticeBtn');

    //오류창
    const errorModal = document.getElementById('errorModal');
    const errorMessage = document.getElementById('errorMessage');
    const closeErrorBtn = document.getElementById('closeErrorBtn');

    const deleteBtn = document.getElementById('deleteButton');

    let maintenanceEditor = null;
    const editor = new toastui.Editor({
        el: document.querySelector('#register-editor'), // 에디터를 적용할 요소 (컨테이너)
        height: '400px',                        // 에디터 영역의 높이 값 (OOOpx || auto)
        initialEditType: 'markdown',            // 최초로 보여줄 에디터 타입 (markdown || wysiwyg)
        initialValue: '',                       // 내용의 초기 값으로, 반드시 마크다운 문자열 형태여야 함
        previewStyle: 'vertical'                // 마크다운 프리뷰 스타일 (tab || vertical)
    });


    flatpickr("#register-request_date", {
        dateFormat: "Y-m-d", // 날짜 형식
        locale: "ko", // 한국어 설정
        defaultDate: new Date() // 오늘날짜 지정
    });


    //[B]mslim 고객사 테스트용
    const customerSelect = document.getElementById('register-customerId');
    const customerSearch = document.getElementById('register-customer');

    // 고객사 데이터 (실제로는 서버에서 가져와야 함)
    const customerData = [
        { id: 1, name: '고객사 A' },
        { id: 2, name: '고객사 B' },
        { id: 3, name: '고객사 C' },
        // ... 더 많은 고객사 데이터
    ];

    function populateCustomers(customers) {
        customerSelect.innerHTML = '<option value="">고객사 선택</option>';
        customers.forEach(customer => {
            const option = document.createElement('option');
            option.value = customer.id;
            option.text = customer.name;
            customerSelect.appendChild(option);
        });
    }
    populateCustomers(customerData)

    customerSearch.addEventListener('input', () => {
        const searchTerm = customerSearch.value.toLowerCase();
        const filteredCustomers = customerData.filter(customer =>
            customer.name.toLowerCase().includes(searchTerm)
        );
        populateCustomers(filteredCustomers);
    });

    customerSelect.addEventListener('change', () => {
        customerSearch.value = customerSelect.options[customerSelect.selectedIndex].text;
    })





    const inputCustomer = document.getElementById("register-customer");
    const suggestionsCustomer = document.getElementById("suggestionsCustomer");

// 고객사 데이터 (예제)
    const jsonCustomersInfo = '[{"name":"대원산업", "id":"C001"}, {"name":"대원정밀", "id":"C002"}, {"name":"오상헬스케어", "id":"C002"}]';
    const customer = ["대원산업","대원정밀","오상헬스케어"];

// 자동완성 표시
    inputCustomer.addEventListener("input", () => {
        const queryCustomer = inputCustomer.value.toLowerCase();
        suggestionsCustomer.innerHTML = ""; // 기존 내용 초기화

        if (queryCustomer) {
            const matches = customer.filter(customer =>
                customer.toLowerCase().includes(queryCustomer)
            );

            if (matches.length > 0) {
                // input 위치 계산
                const rect = inputCustomer.getBoundingClientRect();
                suggestionsCustomer.style.top = `${rect.bottom + window.scrollY}px`;
                suggestionsCustomer.style.left = `${rect.left + window.scrollX}px`;
                suggestionsCustomer.style.width = `${rect.width}px`; // input 크기에 맞춤
                suggestionsCustomer.style.display = "block"; // 팝업 표시

                // 검색된 항목 추가
                matches.forEach(match => {
                    const suggestion = document.createElement("div");
                    suggestion.textContent = match;

                    // 항목 클릭 이벤트
                    suggestion.addEventListener("click", () => {
                        inputCustomer.value = match;
                        suggestionsCustomer.style.display = "none"; // 팝업 숨김
                    });

                    suggestionsCustomer.appendChild(suggestion);
                });
            } else {
                suggestionsCustomer.style.display = "none";
            }
        } else {
            suggestionsCustomer.style.display = "none";
        }
    });

    // input에서 포커스가 벗어나면 자동완성 숨김
    inputCustomer.addEventListener("blur", () => {
        setTimeout(() => {
            suggestionsCustomer.style.display = "none";
        }, 200); // 클릭 처리 후 닫기
    });
    //[E]mslim 고객사 테스트용


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

    // 유지보수 정보창 닫기
    closeInfoModalBtn.addEventListener('click', function () {
        if (this.textContent === '닫기')
            infoModal.style.display = 'none';
        else if (this.textContent === '취소') {
            new toastui.Editor.factory({
                el: document.querySelector('#info-editor'),
                height: window.innerHeight*3/7 + 'px',
                viewer: true,
                initialValue: maintenanceData.description
            });
            this.textContent = '닫기';
            modifyInfoBtn.style.display = 'block';
            applyInfoBtn.style.display = 'none';
        }
    });

    // 유지보수 리스트 정보 수정
    modifyInfoBtn.addEventListener('click', function () {
        maintenanceEditor = new toastui.Editor.factory({
            el: document.querySelector('#info-editor'),
            height: window.innerHeight*3/7 + 'px',
            initialEditType: 'markdown',
            previewStyle: 'vertical',
            initialValue: maintenanceData.description
        });
        closeInfoModalBtn.textContent = '취소';
        modifyInfoBtn.style.display = 'none';
        applyInfoBtn.style.display = 'block';
    });

    applyInfoBtn.addEventListener('click', function () {
        const editmaintenanceData = {
            id: maintenanceData.id,
            name: maintenanceData.name,
            description: maintenanceEditor.getMarkdown(),
            solve: maintenanceData.solve
        };
        fetch('/maintenance', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify(editmaintenanceData),
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
                maintenanceData.description = editmaintenanceData.description;
                maintenanceEditor = new toastui.Editor.factory({
                    el: document.querySelector('#info-editor'),
                    height: window.innerHeight*3/7 + 'px',
                    viewer: true,
                    initialValue: maintenanceData.description
                });
            })
            .then(() => {
                maintenanceTable.destroy();
                initmaintenanceTable();
            })
            .catch(error => {
                if (error === 500) {
                    errorMessage.textContent = '수정 중 서버 오류가 발생했습니다.';
                    errorModal.style.display = 'block';
                }
            })
    });

    // 유지보수 리스트 등록 버튼 클릭 (Ajax 요청)
    registerBtn.addEventListener('click', function () {
        const maintenanceData = {
            name: document.getElementById('register-name').value,
            description: editor.getMarkdown(),
            solve: document.getElementById('register-solve').value,
            request_date: document.getElementById('register-request_date').value
        };

        // Ajax 요청
        fetch('/maintenance', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            },
            body: new URLSearchParams(maintenanceData),
        })
            .then(response => response.text())
            .then(() => {
                noticeMessage.textContent = '고객사가 성공적으로 등록되었습니다.';
                noticeModal.style.display = 'block';
                registerModal.style.display = 'none'; // 모달 닫기
                document.getElementById('register-modal-form').reset(); // 폼 초기화
                editor.setMarkdown('');
                maintenanceTable.destroy();
                initmaintenanceTable();
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
        fetch('/maintenance/'+maintenanceTable.row('.selected').data().id, {
            method: 'DELETE'
        })
            .then(response => response.text())
            .then(() => {
                noticeMessage.textContent = '고객사가 성공적으로 삭제되었습니다.';
                noticeModal.style.display = 'block';
                maintenanceTable.destroy();
                initmaintenanceTable();
            })
            .catch(error => {
                if (error === 500) {
                    errorMessage.textContent = '삭제 중 서버 오류가 발생했습니다.';
                    errorModal.style.display = 'block';
                }
            });
    });
});




