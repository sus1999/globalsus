package com.globalit.account;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

  // DB 를 조회해서 email 이나 nickname 이 중복되는지 검사하려면
  // AccountRepository 가 있어야 함
  private final AccountRepository accountRepository;

  @Override
  public boolean supports(Class<?> clazz) {
    return clazz.isAssignableFrom((SignUpForm.class));
  }

  @Override
  public void validate(Object target, Errors errors) {
    // DB 를 조회해서 email 이나 nickname 이 중복되는지 검사함
    SignUpForm signUpForm = (SignUpForm)errors;
    if (accountRepository.existsByEmail(signUpForm.getEmail())){
      errors.rejectValue("email", "invalid.email",
                         new Object[]{signUpForm.getEmail()},
          "이미 사용 중인 이메일입니다");
    }
    if (accountRepository.existsByNickName(signUpForm.getNickname())){
      errors.rejectValue("nickname", "invalid.nickname",
        new Object[]{signUpForm.getNickname()},
        "이미 사용 중인 닉네임입니다");
    }
  }
}
