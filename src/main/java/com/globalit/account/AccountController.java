package com.globalit.account;

import com.globalit.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountRepository accountRepository;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        // webDataBinder 에
        webDataBinder.addValidators(signUpFormValidator);
    }


    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        // account/sign-up 으로 이동할 때,
        // SignUpForm 객체를 생성해서
        // signUpFrom 이라는 변수에 할당해서 넘김
        // SignUpForm signUpForm = new SignUpForm();
        // model.addAttribute(변수, 객체) 메소드에 변수와 SignUpForm 객체를
        // argument 로 넣어서 호출하면 SignUpForm 객체의 주소를
        // signUpForm 이라는 변수(이름)에 할당하고
        // signUpForm 이라는 변수(이름)를 메모리에 올림
        model.addAttribute("signUpForm", new SignUpForm());
        return "account/sign-up";

        // account/sign-up.html 로 이동한 후
        // account/sign-up.html 의 th:object="${signUpForm}" 부분을 작성하면
        // 메모리에 올라간 signUpForm 변수(이름)을 찾음
        // 이 변수를 메모리에 찾게 되면
        // th:field="*{nickname}", th:field="*{email}", th:field="*{password}"
        // 에서 .... 사용자가 회원 가입하면서 화면에 입력하는 내용들이
        // signUpForm 변수(이름)(SignUpForm 객체) 의 멤버변수
        // nickname, email, password 에 저장됨
    }

    // 회원가입 페이지에서 submit 버튼 눌렀을 때 동작하는 메소드
    @PostMapping("/sign-up")
    //public String signUpSubmit(@Valid @ModelAttribute SignUpForm signUpForm){
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors){

        signUpFormValidator.validate(signUpForm, errors);
        if(errors.hasErrors()){
            // 에러가 있으면 다음 페이지로 넘어가지 않고
            // sign-up 페이지를 다시 보여줌
            return "account/sign-up";
        }

        // 회원가입 폼 submit 처리
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(signUpForm.getPassword())
                .studyCreateByWeb(true)
                .studyEnrollmentResultByWeb(true)
                .studyUpdatedByWeb(true)
                .build();

        Account newAccount = accountRepository.save(account);
        // 회원가입 처리 페이지로 이동
        return "redirect:/";
    }

}