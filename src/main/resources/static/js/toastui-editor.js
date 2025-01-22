let tempImages = [];

function replaceMarkdownImageUrl(content, oldUrl, newUrl) {
    // 특수문자 이스케이프 (정규식에서 올바르게 처리되도록)
    let escapedOldUrl = oldUrl.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');

    // 개행문자, 공백 포함하여 URL을 찾는 정규식 생성
    let regex = new RegExp(`\\(\\s*${escapedOldUrl}\\s*\\)`, "g");

    return content.replace(regex, `(${newUrl})`);
}

async function handleImageUpload(blob, callback) {
    const url = URL.createObjectURL(blob); // 미리보기 URL 생성
    tempImages.push({blob, url}); // 임시 저장

    callback(encodeURI(url), blob.filename ? blob.filename : blob.name); // 미리보기 적용
}

async function uploadEditorImage(editor) {
    const imageUrlPrefix = "/tui/image?filename=";
    if (tempImages.length > 0) {
        const uploadedUrls = await Promise.all(tempImages.map(async ({blob}) => {
            const formData = new FormData();
            formData.append("image", blob);

            // 서버에 업로드
            const response = await fetch("/tui/image", {
                method: "POST", body: formData
            });
            return await response.text(); // 실제 URL 반환
        }));

        // 에디터 내용 가져오기
        let content = editor.getMarkdown();
        // 미리보기 URL을 실제 업로드된 URL로 변경
        tempImages.forEach(({url}, index) => {
            content = replaceMarkdownImageUrl(content, url.toString(), imageUrlPrefix + uploadedUrls[index]);
        });

        // 에디터 내용 업데이트
        editor.setMarkdown(content);

        tempImages = []; // 임시 저장 목록 초기화
    }
}


function initTUIEditor(element, mode, height, initialValue) {
    let editor;
    let targetElement = typeof element === 'string' ? document.querySelector('#' + element) : element;
    if (typeof element !== 'string') element.textContext = '';

    if (mode === 'view') {
        editor = new toastui.Editor.factory({
            el: targetElement, height: height, viewer: true, initialValue: initialValue,
        });
    } else {
        editor = new toastui.Editor.factory({
            el: targetElement,
            height: height,
            initialEditType: 'wysiwyg',
            previewStyle: 'vertical',
            initialValue: initialValue,
            hideModeSwitch: true,
            hooks: {
                addImageBlobHook: handleImageUpload
            }
        });
    }

    return editor;
}