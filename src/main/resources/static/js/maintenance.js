let maintenanceTable = null;
let maintenanceData = null;
let searchList =null;

    let leRequest_user =null;
    let leDescription = null;
    let leTitle=null;
    let leType=null;
    let leSolve=null;
    let leRequest_date= null;
    let leCustomerName= null;
    let leCustomerId= null;
    let leDomainName= null;
    let leDomainId= null;
    let leOwner= null;
    let leRequestDate=null;

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
                    {targets: 1, className: "maintenance-domain"},
                    {targets: 2, className: "maintenance-customerName"},
                    {targets: 3, className: "maintenance-name"},
                    {targets: 4, className: "maintenance-request_date"},
                    {targets: 5, className: "maintenance-owner"},
                    {targets: 6, className: "maintenance-title"},
                    {
                        targets: 7,
                        className: "maintenance-description",
                        orderable: false,
                        searchable: false

                    },
                    {
                        targets: 8,
                        className: "maintenance-solve",
                        orderable: false,
                        width: '40%',
                        searchable: false
                    }
                ],
                columns: [
                    {"data": null},
                    {"data": "domainName"},
                    {"data": "customerName"},
                    {"data": "request_user"},
                    {"data": "request_date"},
                    {"data": "owner"},
                    {"data": "title"},
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
            $(".maintenance-solve").each(function (index) {
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

                $('#info-modal-footer').css('display', 'block');
                $('#register-modal-footer').css('display', 'none');



                maintenanceData = maintenanceTable.row(this).data();
                setRedOnlyFromRegister(true);
                leRequest_user.value=maintenanceData.request_user;
                leOwner.value=maintenanceData.owner;
                leType.value=maintenanceData.type;
                leCustomerName.value=maintenanceData.customerName;
                leCustomerId.value=maintenanceData.customerId;
                leDomainName.value=maintenanceData.domainName;
                leDomainId.value=maintenanceData.domainId;
                leTitle.value=maintenanceData.title;
                leDescription.value=maintenanceData.description;

                //leRequestDate = maintenanceData.request_date;

                console.log(maintenanceData);


                new toastui.Editor.factory({
                    //el: document.querySelector('#info-editor'),
                    el: document.querySelector('#register-solve'),
                    height: window.innerHeight*3/7 + 'px',
                    viewer: true,
                    //initialValue: maintenanceData.description
                    initialValue: maintenanceData.solve
                });

                flatpickr("#register-request_date", {
                    clickOpens: false, // 달력 팝업 비활성화
                    defaultDate: maintenanceData.request_date
                });

                let registerModal = $('#registerModal');
                registerModal.css('display', 'block');

            });
        })
        .then(() => {
            searchList = maintenanceTable.columns(1).data().unique().toArray()[0];

            let substringMatcher = function (strs) {
                return function findMatches(searchWord, cb) {
                    let matches, substringRegex;
                    // 추천 목록으로 보일 배열
                    matches = [];

                    // searchWord로 만들어질 정규 표현식
                    substringRegex = new RegExp(searchWord, 'i');

                    //정규 표현식으로 가지고 있는 배열에서 탐색
                    $.each(strs, function (i, str) {
                        if (substringRegex.test(str)) {
                            //정규 표현식 일치하면 추천 목록에 푸시
                            matches.push(str);
                        }
                    });
                    cb(matches);//뿌려주는 함수
                };
            };

            const searchInput = $('.dt-input');
            searchInput.typeahead('destroy');
            searchInput.typeahead(
                {
                    hint: false, // 나머지 글자가 자동으로 보여지는지
                    highlight: true,// 일치하는 문자 하이라이팅
                    minLength: 1,    // 검색 시작하는 최소 문자 길이
                },
                {
                    limit: 5, // 자동완성 목록에 보여질 개수
                    name: 'search',
                    //source가 검색어 추천에 목록으로 뿌려질 목록이다.
                    source: substringMatcher(searchList),
                    templates: {
                        empty: [
                            '<div class="empty-message">',
                            '일치하는 결과가 없습니다',
                            '</div>'
                        ].join('\n')  // 일치하는 결과가 없을 때
                    }
                }
            );
        })
        .catch(error => {
            console.error(error);
        });
}

function getCombo()
{
    let domainListBox = null;
    let customerListBox = document.getElementById('UL-customerList');
    fetch('/department') // 백엔드 엔드포인트 URL
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            domainListBox = document.getElementById('UL-domainList');
            // 기존 내용 제거 (필요 시)
            domainListBox.innerHTML = '';

            // 데이터를 반복하여 li 태그 생성 및 추가
            let innerLI = "";
            data.forEach(item => {
                const li = document.createElement('li');
                li.id = item.id; // ID 설정
                li.role = 'option'; // ARIA 속성 추가
                li.textContent = item.dep_name; // 텍스트 추가
                domainListBox.appendChild(li);

            });

            const comboboxNode = document.getElementById('register-domain');
            const hiddenIdNode = document.getElementById('register-domainId');
            const buttonNode = document.getElementById('domain-button');
            new ComboboxAutocomplete(comboboxNode, buttonNode, domainListBox, hiddenIdNode);

        })
        .catch(error => {
            console.error('Error fetching options:', error);
        });


    fetch('/customer') // 백엔드 엔드포인트 URL
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            //customerListBox = document.getElementById('UL-customerList');
            // 기존 내용 제거 (필요 시)
            customerListBox.innerHTML = '';

            // 데이터를 반복하여 li 태그 생성 및 추가
            let innerLI = "";
            data.forEach(item => {
                const li = document.createElement('li');
                li.id = item.id; // ID 설정
                li.role = 'option'; // ARIA 속성 추가
                li.textContent = item.name; // 텍스트 추가
                customerListBox.appendChild(li);

            });

            const comboboxNode = document.getElementById('register-customer');
            const hiddenIdNode = document.getElementById('register-customerId');
            const buttonNode = document.getElementById('customer-button');
            new ComboboxAutocomplete(comboboxNode, buttonNode, customerListBox, hiddenIdNode);

            // Initialize comboboxes
            //var comboboxes = document.querySelectorAll('.combobox-list');
            /*for (var i = 0; i < comboboxes.length; i++) {
                var combobox = comboboxes[i];
                var comboboxNode = combobox.querySelector('input');
                var buttonNode = combobox.querySelector('button');
                var listboxNode = combobox.querySelector('[role="listbox"]');
                new ComboboxAutocomplete(comboboxNode, buttonNode, listboxNode);
            }*/

        })
        .catch(error => {
            console.error('Error fetching options:', error);
        });
}

function getElementFromRegister(){
    leRequest_user = document.getElementById('register-request_user');
    leDescription = document.getElementById('register-description');
    leTitle=document.getElementById('register-title');
    leType=document.getElementById('register-type');
    //leSolve=editor.getMarkdown();
    leRequest_date= document.getElementById('register-request_date');
    leCustomerId= document.getElementById('register-customerId');
    leCustomerName= document.getElementById('register-customer');
    leDomainId= document.getElementById('register-domainId');
    leDomainName= document.getElementById('register-domain');
    leOwner= document.getElementById('register-owner');
    leRequestDate = document.getElementById('register-request_date');
}

function setRedOnlyFromRegister(bool){
    leRequest_user.readOnly=bool;
    leOwner.readOnly=bool;
    leType.readOnly=bool;
    leCustomerName.readOnly=bool;
    leCustomerId.readOnly=bool;
    leDomainName.readOnly=bool;
    leDomainId.readOnly=bool;
    leTitle.readOnly=bool;
    leDescription.readOnly=bool;
    leRequestDate.readOnly=bool;
}

$(document).ready(function () {

    initmaintenanceTable();
    getCombo();
    getElementFromRegister();

});

document.addEventListener('DOMContentLoaded', function () {

    //등록
    const openRegisterBtn = document.getElementById('openRegisterButton');
    const registerModal = document.getElementById('registerModal');
    const closeRegisterModalBtn = document.getElementById('closeRegisterModal');
    const registerBtn = document.getElementById('registerMaintenance');

    //Footer
    const inforFooter = document.getElementById('info-modal-footer');
    const registerFooter = document.getElementById('register-modal-footer');

    //내용
    //const infoModal = document.getElementById('infoModal');
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
        el: document.querySelector('#register-solve'), // 에디터를 적용할 요소 (컨테이너)
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




    // 등록창 열기
    openRegisterBtn.addEventListener('click', function () {
        registerModal.style.display = 'block';
        registerFooter.style.display='block';

        document.getElementById('register-modal-form').reset();

        flatpickr("#register-request_date", {
            dateFormat: "Y-m-d", // 날짜 형식
            locale: "ko", // 한국어 설정
            defaultDate: new Date() // 오늘날짜 지정
        });

        setRedOnlyFromRegister(false);

    });

    // 등록창 닫기
    closeRegisterModalBtn.addEventListener('click', function () {
        registerModal.style.display = 'none';
        registerFooter.style.display='none';
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
            //infoModal.style.display = 'none';
            registerModal.style.display = 'none';
        else if (this.textContent === '취소') {
            new toastui.Editor.factory({
                el: document.querySelector('#register-solve'),
                height: window.innerHeight*3/7 + 'px',
                viewer: true,
                initialValue: maintenanceData.solve
            });
            this.textContent = '닫기';
            modifyInfoBtn.style.display = 'block';
            applyInfoBtn.style.display = 'none';

            inforFooter.style.display='none';
            registerModal.style.display = 'none';
        }

    });

    // 유지보수 리스트 정보 수정
    modifyInfoBtn.addEventListener('click', function () {
        setRedOnlyFromRegister(false);
        maintenanceEditor = new toastui.Editor.factory({
            //el: document.querySelector('#info-editor'),
            el: document.querySelector('#register-solve'),
            height: window.innerHeight*3/7 + 'px',
            initialEditType: 'markdown',
            previewStyle: 'vertical',
            initialValue: maintenanceData.solve
        });

        flatpickr("#register-request_date", {
            dateFormat: "Y-m-d", // 날짜 형식
            locale: "ko", // 한국어 설정
            defaultDate: maintenanceData.request_date
        });
        closeInfoModalBtn.textContent = '취소';
        modifyInfoBtn.style.display = 'none';
        applyInfoBtn.style.display = 'block';
    });

    applyInfoBtn.addEventListener('click', function () {
        const editmaintenanceData = {
            id: maintenanceData.id,
            name: maintenanceData.name,
            description: maintenanceData.description,
            solve: maintenanceEditor.getMarkdown()
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
                noticeMessage.textContent = '성공적으로 수정되었습니다.';
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
                inforFooter.style.display='none';
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
            request_user: document.getElementById('register-request_user').value,
            description: document.getElementById('register-description').value,
            title: document.getElementById('register-title').value,
            type: document.getElementById('register-type').value,
            solve: editor.getMarkdown(),
            request_date: document.getElementById('register-request_date').value,
            customerId: document.getElementById('register-customerId').value,
            domainId: document.getElementById('register-domainId').value,
            owner: document.getElementById('register-owner').value
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
                noticeMessage.textContent = '리스트를 성공적으로 등록되었습니다.';
                noticeModal.style.display = 'block';
                registerModal.style.display = 'none'; // 모달 닫기
                document.getElementById('register-modal-form').reset(); // 폼 초기화
                editor.setMarkdown('');
                maintenanceTable.destroy();
                initmaintenanceTable();
            })
            .catch(error => {
                if (error === 409) {
                    errorMessage.textContent = '중복된 유지보수 정보 이름이 있습니다. 다시 시도해주세요.';
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
                noticeMessage.textContent = '유지보수 정보가 성공적으로 삭제되었습니다.';
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




