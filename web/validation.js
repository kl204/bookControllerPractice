

//web_test 프로젝트 validation.js 파일임.
//회원가입 폼 용 validation.js는 MemberSample_test 프로젝트 파일에 있음


//참고할 표현들
//{${idMinLen-1},${idMaxLen-1}}$ 이 표현식을 백틱안에 포함시키면 길이에 대한 조건식을 줄 수 있음


//--------------------------------- 유효성 검사 필수 조건일것 같은 함수 -------------------------

// ISBN 유효성 검사 함수
const checkIsbnValidation = (isbn) => {

    console.log("isbn체크 들어옴");
	const isbnMaxLen = 12;


    // 1. null 체크
    if(isbn == null || isbn == undefined || isbn == ''){
        alert("isbn이 입력되지 않았습니다.");
        return false
    }
    
    // 2. 대문자 시작 2글자 이상, 그 뒤 숫자로만 생성가능, 12글자로 작성 필
    // 첫번째 조건 : ^(시작)[A-Z](대문자 A부터 Z까지){2}(2글자 이상)
    // 두번째 조건 : [0-9](숫자만 가능){12-2}(총 글자 12글자지만, 앞 지정한 2글자 제외해야해서 -2)
    const regexPattern = new RegExp(`^[A-Z]{2}\\d{${isbnMaxLen-2}}$`, 'g');
    const chk =regexPattern.test(isbn);
    console.log(chk);
    if(!chk){
        alert("영문 대문자2개 + 숫자10개로 적어주세요");
		return false
	}
	
    return true
}


// 도서명 유효성 검사 함수
const checkTitleValidation = (book_title) => {

    // 1. null 체크
    if(book_title == null || book_title == undefined || book_title == ''){
        alert("book_title이 입력되지 않았습니다.");
        console.log("book_title이 입력되지 않았습니다.")
        return false
    }

    // 2. 도서명 탐색 조건들
    // ?=는 전방탐색으로 해당 패턴이 일치해야만 전체패턴이 일치함, 따라서 최소 1개 이상 존재해야한다는 뜻이 됨
    // . 은 모든 문자열을 말함
    // *은 없거나, 여러개 있거나를 뜻함
    // .* 은 여러개의 문자열을 뜻하며 (?=.*\\d)은 ?=로 인해 무조건 최소1개 이상, .*로 인해 모든 문자열 안이 범위가 되며 
    // //d로 인해 숫자가 포함되어야 한다는 의미가 되어 결국 '문자열 안에 숫자가 최소 1개 이상 포함되어야 한다'는 의미가 된다.
    // [0-9a-zA-Z:().=?] 숫자, 대소문자, 한글, 특수기호:,(,),.,=,? 를 
    // 추가 문법 : (?!.*\\d)를 쓰게 되면 숫자가 한번이라도 나오면 안되는다는 뜻, 즉, 사용하지 않는 것들 제외하기 위함
    
    //`^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z가-힣:().=?]$`, 'g'
    //= 최소 1개의 숫자가 있어야하고, 최소 1개의 대소문자가 있어야하는 조건에 숫자,대소문자,한글,특수기호:().=? 가능
    
    //도서명은 조건이 필요 없기 때문에 위의 내용을 가지고 추가 삭제 하면 됨
    //숫자, 대소문자, 한글, 정해진 특수기호 등이 포함 가능함
    const regexPattern = new RegExp(`^[0-9a-zA-Z\p{IsHangul}:().=?].*$`);
    console.log("book_title : ")
    console.log(regexPattern.test(book_title));
    if(!regexPattern.test(book_title)){
		return false
	}
    return true;
}

// 저자/역자 유효성 검사 함수
const checkAuthorValidation = (author) => {
 
    // 1. null 체크
    if(author == null || author == undefined || author == ''){
        alert("author이 입력되지 않았습니다.");
		console.log("author이 입력되지 않았습니다.")
        return false
    }

    //숫자, 대소문자, 한글, 정해진 특수기호 등이 포함 가능함
    const regexPattern = new RegExp(`^[0-9a-zA-Z가-힣; \\[\\]].*$`);
    console.log("author chk : ");
    console.log(author);
    console.log(regexPattern.test(author));
    if(!regexPattern.test(author)){
		return false
	}
    return true;
}

//publisher 유효성 검사 함수
const checkPublisherValidation = (publisher)=>{
	
	// 1. null 체크
    if(publisher == null || publisher == undefined || publisher == ''){
        alert("publisher가 입력되지 않았습니다.");
		console.log("publisher가 입력되지 않았습니다.")
        return false
    }

    console.log(publisher);

	const regexPattern = new RegExp(`^[0-9a-zA-Z가-힣]*$`);

	if (!regexPattern.test(publisher)) {
   		 return false;
	}

	return true;
}



//-------------------------------------- 선택 가능한 검사 항목들--------------------------


// date 형식의 날짜 검사
const validateDate = (dateString)=> {
  const date = new Date(dateString);
  
  // 날짜가 유효한지 검사
  if (isNaN(date.getTime())) {
    return false;
  }
  
  // 허용된 범위인지 검사
  const minDate = new Date("2010-01-01");
  const maxDate = new Date("2040-01-01");
  
  if (date < minDate || date > maxDate) {
    return false;
  }
  
  return true;
}

// 타임 스탬프 형식이 맞는지 검사 함수
const checkTimestampValidation = (publish_date) => {
	
	const timestampRegex = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/;

	if (!timestampRegex.test(String(publish_date))) {
   		 return false;
	}

	return true;

}




//book_position 유효성 검사
const checkPositionValidation = (book_position)=>{

	// 1. null 체크
    if(book_position == 'BS'){
		console.log("book_position이 선택되지 않았습니다.")
        return false
    }
	
    const regexPattern = new RegExp(`^BS-\\d{3}$`);
    if(!regexPattern.test(book_position)){
        return false;
    }
    return true;	
}

//book_status 유효성 검사
const checkStatusValidation = (book_status)=>{
	
	    // 1. null 체크
    if(book_status == 'BM'){
		console.log("book_status가 선택되지 않았습니다.")
        return false
    }
	
    const regexPattern = new RegExp(`^BM-\\d{3}$`);
    if(!regexPattern.test(book_status)){
        return false;
    }
    return true;	
}

//------------------------------------------- 이외의 검사 항목들


// 전화번호 유효성 검사 함수
const checkPhoneValidation = (phone) => {
	
	// 010으로 시작하는 번호만 가능, 3-4-4 자리수만 가능
    const regexPattern = new RegExp(`^010-\\d{4}-\\d{4}$`);
    if(!regexPattern.test(phone)){
        return false;
    }
    return true;
}

// 우편번호 양식 검사 함수
const checkZipValidation = (zip) => {
	
	// 01000~63999까지 가능
    const regexPattern = new RegExp(`^(0[1-9][0-9]{3}|[1-5][0-9]{4}|6[0-3][0-9]{3})$`);
    if(!regexPattern.test(zip)){
        return false;
    }
    return true;
}


// 이메일 유효성 검사 함수
const checkEmailValidation = (email) => {

// 영문자로 시작 gmail, naver만 가능
   const regexPattern = new RegExp(`^[a-zA-Z]+[a-zA-Z0-9]@(gmail|naver).com$`)
    if(!regexPattern.test(email)){

        return false;
    }
    return true;
}



//전체 일괄 검사
const checkValidationAll = () => {

    const book_isbn = document.getElementById("book_isbn").value;
    const book_title = document.getElementById("book_title").value;
    const book_author = document.getElementById("book_author").value;
    const book_publisher = document.getElementById("book_publisher").value;

    console.log(book_publisher);


    const isbnValidationResult = checkIsbnValidation(book_isbn);
	if(!isbnValidationResult){
        alert("ISBN 입력 형식에 맞지 않습니다.");
		document.getElementById("isbnChk").innerHTML += "<p>" + "ISBN 입력 형식에 맞지 않습니다." + "</p>";
	}

    const titleValidationResult = checkTitleValidation(book_title);
    	if(!titleValidationResult){
		document.getElementById("titleChk").innerHTML += "<p>" +"도서명 입력 형식에 맞지 않습니다." + "</p>";
	}

    const authorValidationResult = checkAuthorValidation(book_author);
    	if(!authorValidationResult){
		document.getElementById("authorChk").innerHTML += "<p>" + "저자/역자 입력 형식에 맞지 않습니다."+ "</p>";
	}

    const publisherValidationResult = checkPublisherValidation(book_publisher);
    	if(!publisherValidationResult){
		document.getElementById("pubChk").innerHTML += "<p>" + "출판사 입력 형식에 맞지 않습니다." + "</p>";
	}
    console.log(isbnValidationResult);
    console.log(titleValidationResult);
    console.log(authorValidationResult);
    console.log(publisherValidationResult);

    if(!isbnValidationResult || !titleValidationResult || !authorValidationResult || !publisherValidationResult){
        return false;
    }
	return true;

}

